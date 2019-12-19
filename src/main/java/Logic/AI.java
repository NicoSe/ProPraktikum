package Logic;

public class AI
{
    private Grid2D grid;
    private int[][] chessPattern;
    private int[][] randomPattern;
    private static int counter = 0;

    public AI()
    {
        this.chessPattern = new int[grid.getBound()][grid.getBound()];
        this.randomPattern = new int[grid.getBound()][grid.getBound()];
    }

    public void placeShips()
    {
        grid.generateRandom();
    }

    public void firstShot(ShotResult result)
    {
        if(counter == 0)//checks if it's his first action and fires int middle of the grid
        {
            int x = grid.getBound()/2;
            int y = grid.getBound()/2;
            grid.shoot(x,y);
            counter++;
        }
    }

    public int[][] setChessPattern()
//marks the grid in a chessPattern. Every Position = 1 will be shot. AI Hardmode
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
    public int[][] setRandomPattern()
//Like setChessPattern, just in a random pattern without reoccurring indices
    {
        int counter = 0;

            while(counter < Math.pow(grid.getBound(),2))//iterates till counter is == bound²
        {
            int x = Util.GetRandomNumberInRange(0,grid.getBound()-1);
            int y = Util.GetRandomNumberInRange(0,grid.getBound()-1);

            if(randomPattern[x][y] != 1)
            {//if the value at index x y != 1 --> field wasn't marked and indices are unique
                randomPattern[x][y] = 1;
                counter++;
            }
        }
        return randomPattern;
    }
    public void search(int[][] pattern)//should return a Server massage
    {
        int nextHit = 1;
        //while(ShotResult == MISS){}
        for (int x = 0; x < pattern.length; x++)
        {
            for (int y = 0; y < pattern.length; y++)
            {
                if (pattern[x][y] == 1)
                {
                    grid.shoot(x,y);
                    pattern[x][y] = 0;      //marks the position as shot

//                    if(ShotResult == HIT)
                        for(int i = 1; i < 4; i++)
                        {
                            switch(i)
                            {
                                case 1:
                                    grid.shoot(x,y+1);
//                                    if(ShotResult == HIT)
//                                    while(ShotResult != MISS || ShotResult != SUNK){}//SHOULD shoot till the ship is sunken
//                                    grid.shoot(x, y+nextHit);
//                                    nextHit++;
                                break;

                                case 2:
                                    grid.shoot(x+1,y);
//                                    if(ShotResult == HIT)
//                                    while(ShotResult != MISS || ShotResult != SUNK){}//SHOULD shoot till the ship is sunken
//                                    grid.shoot(x+nextHit, y);
//                                    nextHit++;
                                break;

                                case 3:
                                    grid.shoot(x,y-1);
//                                    if(ShotResult == HIT)
//                                    while(ShotResult != MISS || ShotResult != SUNK){}//SHOULD shoot till the ship is sunken
//                                    grid.shoot(x, y-nextHit);
//                                    nextHit++;
                                break;

                                case 4:
                                    grid.shoot(x-1,y);
//                                    if(ShotResult == HIT)
//                                    while(ShotResult != MISS || ShotResult != SUNK){}//SHOULD shoot till the ship is sunken
//                                    grid.shoot(x-nextHit, y);
//                                    nextHit++;
                                break;

                                default:
                                    System.out.println("Sollte eig nicht passieren");
                            }
                        }
                }
            }
        }
    }
}
