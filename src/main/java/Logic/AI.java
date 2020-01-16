package Logic;

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
    static int counter = 0;
    private static int initialX, initialY, direction = -1;
    static int limit = 0;

    /*
    Easy Mode: Just fires in a random pattern at the enemy.
    Normal Mode: Shoots in a random pattern at the enemy. If its manages to hit a ship, it will try to find out its direction and shoot in this direction.
                 At the first "MISS" it will fire in the opposite direction, starting form the initial hit+1.
    Hard Mode: Its first shot will be the middle of the grid. After that it will fire in a chess pattern at the enemy. It also tries to locate the direction of a damaged ship
               and knows his initial hit of the ship.
     */

    public AI(int mode)
    {
        this.chessPattern = new int[grid.getBound()][grid.getBound()];
        this.randomPattern = new int[grid.getBound()][grid.getBound()];
        switch (mode)
        {
            case 0:
                this.mode = Mode.EASY;
                setRandomPattern();
                break;
            case 1:
                this.mode = Mode.NORMAL;
                setRandomPattern();
                break;
            case 2:
                this.mode = Mode.HARD;
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
        }
    }

//Marks the grid in a chess pattern.
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

//Marks the grid in a random pattern without reoccurring indices
    public int[][] setRandomPattern()
    {
        int counter = 0;

            while(counter < Math.pow(grid.getBound(),2))
        {
            int x = Util.GetRandomNumberInRange(0,grid.getBound()-1);
            int y = Util.GetRandomNumberInRange(0,grid.getBound()-1);

            if(randomPattern[x][y] != 1)
            {//If the value at index x y != 1 --> field wasn't marked and indices are unique
                randomPattern[x][y] = 1;
                counter++;
            }
        }
        return randomPattern;
    }

//Shoots in the middle of the grid
    public void firstShot()
    {
        int x = grid.getBound()/2;
        int y = grid.getBound()/2;

        s.sendmsg(String.format("SHOOT %d %d", x, y));
        chessPattern[x][y] = 0;
        if (s.listenToNetwork().equals("HIT"))
        {
            destroy(x,y);
        }
        counter++;
    }

    public void search(int[][] pattern)
    {
        if (counter == 0 && this.mode == Mode.HARD)
        {
            firstShot();
        }
//If a ship is already damaged, it will fire at the initial coordinates but in the opposite direction
        if(direction != -1)
        {
            shootInDirection(initialX, initialY, direction);
        }

//Shoots at the marked fields of the pattern and searches so for a ship
        for (int x = 0; x < pattern.length; x++)
        {
            for (int y = 0; y < pattern.length; y++)
            {
                if (pattern[x][y] == 1)
                {
                    s.sendmsg(String.format("SHOOT %d %d", x, y));
                    pattern[x][y] = 0;

                    if(s.listenToNetwork().equals("HIT") && this.mode == Mode.NORMAL || this.mode == Mode.HARD)
                    {
                        destroy(x,y);
                    }
                }
            }
        }
    }
    public void destroy(int x, int y)
    {
//Limit tightens the range and so prevents that the same field is shot at twice
        int i = Util.GetRandomNumberInRange(0+ limit,3);
        {
//Locates the direction in which a ship lays
            switch (i)
            {
                case 0:
                    s.sendmsg(String.format("SHOOT %d %d", x, y-1));
                    if(s.listenToNetwork().equals("HIT"))
                    {
                        shootInDirection(x, y-1, i);
                        initialY = y-1;
                        initialX = x;
                        direction = i;
                        break;
                    }
                case 1:
                    s.sendmsg(String.format("SHOOT %d %d", x+1, y));
                    if(s.listenToNetwork().equals("HIT"))
                    {
                        shootInDirection(x+1, y, i);
                        initialY = y;
                        initialX = x+1;
                        direction = i;
                        break;
                    }
                case 2:
                    s.sendmsg(String.format("SHOOT %d %d", x, y+1));
                    if(s.listenToNetwork().equals("HIT"))
                    {
                        shootInDirection(x, y+1, i);
                        initialY = y+1;
                        initialX = x;
                        direction = i;
                        break;
                    }
                case 3:
                    s.sendmsg(String.format("SHOOT %d %d", x-1, y));
                    if(s.listenToNetwork().equals("HIT"))
                    {
                        shootInDirection(x-1, y, i);
                        initialY = y;
                        initialX = x-1;
                        direction = i;
                        break;
                    }
            }
            limit++;
        }
    }
    public void shootInDirection(int x, int y, int i)
    {
        int directionX, directionY;

        if (direction != -1)
        {
            switchDirection(direction);
            i = direction;
        }

//Shoots in a direction till the answer is "SUNK" or "MISS"
        switch (i)
        {
            case 0:
                directionY = -1;
                do
                {
                    s.sendmsg(String.format("SHOOT %d %d", x, y + directionY));
                    if (s.listenToNetwork().equals("SUNK"))
                    {
                        limit = 0;
                        break;
                    }
                    directionY--;
                }
                while (s.listenToNetwork().equals("HIT"));
                break;
            case 1:
                directionX = 1;
                do
                {
                    s.sendmsg(String.format("SHOOT %d %d", x + directionX, y));
                    if (s.listenToNetwork().equals("SUNK"))
                    {
                        limit = 0;
                        break;
                    }
                    directionX++;
                }
                while (s.listenToNetwork().equals("HIT"));
                break;
            case 2:
                directionY = 1;
                do
                {
                    s.sendmsg(String.format("SHOOT %d %d", x, y + directionY));
                    if (s.listenToNetwork().equals("SUNK"))
                    {
                        limit = 0;
                        break;
                    }
                    directionY++;
                }
                while (s.listenToNetwork().equals("HIT"));
                break;
            case 3:
                directionX = -1;
                do
                {
                    s.sendmsg(String.format("SHOOT %d %d", x + directionX, y));
                    if (s.listenToNetwork().equals("SUNK"))
                    {
                        limit = 0;
                        break;
                    }
                    directionX--;
                }
                while (s.listenToNetwork().equals("HIT"));
                break;
        }
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
}
