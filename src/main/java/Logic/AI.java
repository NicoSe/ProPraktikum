package Logic;

import Network.Connector;

import java.util.Arrays;

enum Mode
{
    EASY,
    NORMAL,
    HARD,
}

public class AI
{
    private Grid2D grid;
    private Network.Connector s;
    private int[][] chessPattern;
    private int[][] randomPattern;
    private Mode mode;
    static boolean firstAction = true;
    private static int initialX, initialY, direction = -1;
    private int[] usedCases = new int[4];

    /**
     * Easy Mode: Just fires in a random pattern at the enemy.
     * Normal Mode: Shoots in a random pattern at the enemy. If its manages to hit a ship, it will try to find out its direction and shoot in this direction.
     *              At the first "MISS" it will fire in the opposite direction, starting form the initial hit+1.
     * Hard Mode: Its first shot will be the middle of the grid. After that it will fire in a chess pattern at the enemy. It also tries to locate the direction of a damaged ship
     *            and knows his initial hit of the ship.
     */

    public AI(Network.Connector s, int mode)
    {
        this.s = s;

        switch (mode)
        {
            case 0:
                this.mode = Mode.EASY;
                break;
            case 1:
                this.mode = Mode.NORMAL;
                break;
            case 2:
                this.mode = Mode.HARD;
                break;
        }

        handleData(this.s);
    }

    public void close() {
        s.Close();
    }

    private void handleData(Connector c) {
        while (true) {
            if(!c.isConnected()) {
                break;
            }
            String res = c.listenToNetwork();
            String[] cmd = res.split(" ");
            switch(cmd[0]) {
                case "confirmed":
                    routine();
                    return;
                case "size":
                    this.grid = new Grid2D(Integer.parseInt(cmd[1]));
                    this.grid.generateRandom();
                    run();
                    c.sendmsg("confirmed");
                    break;
                case "answer":
                    System.out.println("ERROR: this should be handled by AI.");
                    break;
                case "shot":
                    ShotResult sr = grid.shoot(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
                    c.sendmsg(String.format("answer %d", sr.ordinal()));
                    ///wanna revalidate ui?
                    break;
                case "pass":
                    routine();
                    return;
                default:
                    System.out.println("Invalid command.");
                    System.out.println(res);
                    break;
            }
        }
    }

    public void run() {
        this.chessPattern = new int[grid.getBound()][grid.getBound()];
        this.randomPattern = new int[grid.getBound()][grid.getBound()];
        switch (mode)
        {
            case EASY:
            case NORMAL:
                setRandomPattern();
                break;
            case HARD:
                setChessPattern();
                break;
        }
    }

    public void placeShips()
    {
        grid.generateRandom();
    }

    public void routine()
    {
        while (true)
        {
            if (this.mode == Mode.NORMAL || this.mode == Mode.EASY)
            {
                search(randomPattern);
            }else
            {
                search(chessPattern);
            }
            handleData(s);
        }
    }

///Marks the grid in a chess pattern.
    public int[][] setChessPattern()
    {
        for(int x = 0; x < chessPattern.length; x++)
        {
            for(int y = 0; y < chessPattern.length; y++)
            {
                if(x % 2 != 0 && y % 2 == 0)
                {
                    chessPattern[x][y] = 1;
                }else if(x % 2 == 0 && y % 2 != 0)
                {
                    chessPattern[x][y] = 1;
                }
            }
        }
        return chessPattern;
    }

///Marks the grid in a random pattern without reoccurring indices
    public int[][] setRandomPattern()
    {
        int counter = 0;

            while(counter < Math.pow(grid.getBound(),2))
        {
            int x = Util.GetRandomNumberInRange(0,grid.getBound()-1);
            int y = Util.GetRandomNumberInRange(0,grid.getBound()-1);

            if(randomPattern[x][y] != 1)
            {///If the value at index x y != 1 --> field wasn't marked and indices are unique
                randomPattern[x][y] = 1;
                counter++;
            }
        }
        return randomPattern;
    }

///Shoots in the middle of the grid
    public boolean firstShot()
    {
        int x = grid.getBound()/2;
        int y = grid.getBound()/2;

        s.sendmsg(String.format("shot %d %d", x, y));
        chessPattern[x][y] = 0;

        String res = s.listenToNetwork();
        firstAction = false;

        if(res.equals("answer 0")) {
            return false;
        } else if (res.equals("answer 1")) {
            if(!destroy(x, y)) {
                return false;
            }
            return true;
        }
        ///this can never happen.
        return false;
    }

    public void search(int[][] pattern)
    {
        if (firstAction == true && this.mode == Mode.HARD)
        {
            if(!firstShot()) {
                s.sendmsg("pass");
                return;
            }
        }
///If a ship is already damaged, it will fire at the initial coordinates but in the opposite direction
        if(direction != -1)
        {
            if(!shootInDirection(initialX, initialY, direction)) {
                s.sendmsg("pass");
                return;
            }
        }
///Shoots at the marked fields of the pattern and searches so for a ship
        for (int y = 0; y < pattern.length; y++)
        {
            for (int x = 0; x < pattern.length; x++)
            {
                if (pattern[x][y] == 1)
                {
                    s.sendmsg(String.format("shot %d %d", x, y));
                    pattern[x][y] = 0;

                    String res = s.listenToNetwork();
                    ///stop shooting when we missed.
                    if(res.equals("answer 0")) {
                        s.sendmsg("pass");
                        return;
                    }

                    if(res.equals("answer 1") && this.mode == Mode.NORMAL || this.mode == Mode.HARD)
                    {
                        if(!destroy(x, y)) {
                            s.sendmsg("pass");
                            return;
                        }
                    }
                }
            }
        }
    }
    public boolean destroy(int x, int y)
    {
        int i = Util.GetRandomNumberInRange(1,4);
        {
///Locates the direction in which a ship lays
            int shootx = -1;
            int shooty = -1;

            switch (i)
            {
                case 1:
                    if (unusedCase(i))
                    {
                        shootx = x;
                        shooty = y-1;
                        break;
                    }
                case 2:
                    if (unusedCase(i))
                    {
                        shootx = x+1;
                        shooty = y;
                        break;
                    }
                case 3:
                    if (unusedCase(i))
                    {
                        shootx = x;
                        shooty = y+1;
                        break;
                    }
                case 4:
                    if (unusedCase(i))
                    {
                        shootx = x-1;
                        shooty = y;
                        break;
                    }
            }
            s.sendmsg(String.format("shot %d %d", shootx, shooty));
            String res = s.listenToNetwork();
            ///return false so the calling function sendmsg pass to oponent and breaks the for loop.
            if(res.equals("answer 0"))
            {
                return false;
            }
            if(res.equals("answer 1"))
            {
                if(!shootInDirection(x, y, i)) {
                    initialY = y;//wird nie gemacht weil es immer ein return false von shootInDirection gibt --> if == true und nie dieser block, fix: schieb in den if
                    initialX = x;//TODO: fix, pull within upper if
                    direction = i;
                    return false;
                }
            }
        }
        return true;
    }

    public boolean shootInDirection(int x, int y, int i)
    {
        int directionX, directionY;

        if (direction != -1)
        {
            switchDirection(direction);
            i = direction;
        }

///Shoots in a direction till the answer is "SUNK" or "MISS"
        String res = "";
        switch (i)
        {
            case 1:
                directionY = -1;
///If isValid == true, try direction else go to next case
                if (isValid(x, directionY))
                {
                    do
                    {
                        s.sendmsg(String.format("shot %d %d", x, y + directionY));
                        res = s.listenToNetwork();
                        directionY--;
                    }
                    while (res.equals("answer 1"));
                    break;
                }
            case 2:
                directionX = 1;
                if (isValid(directionX, y))
                {
                    do
                    {
                        s.sendmsg(String.format("shot %d %d", x + directionX, y));
                        res = s.listenToNetwork();
                        directionX++;
                    }
                    while (res.equals("answer 1"));
                    break;
                }
            case 3:
                directionY = 1;
                if (isValid(x, directionY))
                {
                    do
                    {
                        s.sendmsg(String.format("shot %d %d", x, y + directionY));
                        res = s.listenToNetwork();
                        directionY++;
                    }
                    while (res.equals("answer 1"));
                    break;
                }
            case 4:
                directionX = -1;
                if (isValid(directionX, y))
                {
                    do
                    {
                        s.sendmsg(String.format("shot %d %d", x + directionX, y));
                        res = s.listenToNetwork();
                        directionX--;
                    }
                    while (res.equals("answer 1"));
                    break;
                }
        }

        if (res.equals("answer 0")) {
            return false;
        }
        if (res.equals("answer 2"))
        {
            Arrays.fill(usedCases, 0);///set all values to 0
        }
        return true;
    }

    public void switchDirection(int direction)
    {
        if (direction < 2)
        {
            direction = direction % 2 + 2;
        }else
        {
            direction = direction % 2;
        }
    }
///checks if the field is within the grid
    public boolean isValid(int x, int y)
    {
        if (x < 0 || x > grid.getBound()-1 || y < 0 || y > grid.getBound()-1)
        {
            return false;
        }
        return true;
    }
///checks if the direction selected in the destroy method was already used
    public boolean unusedCase(int i)
    {
        for (int j = 0; j < usedCases.length; j++)
        {
            if (usedCases[j] == i)
            {
                return false;
            }else if(usedCases[j] == 0)
            {
                usedCases[j] = i;
            }
        }
        return true;
    }
}
