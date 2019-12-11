package Logic;

public class AI extends Ship
{
    private Grid2D grid;
    private int[][] chessPattern;
    private static int counter = 0;

    public AI(int size)
    {
        super(size);
        this.chessPattern = new int[grid.getBound()][grid.getBound()];
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

    public int[][] setPositions(int[][] chessPattern)//marks the grid in a chessPattern. Every Position = 1 will be shot
    {
        for(int x = 0; x < chessPattern.length-1; x++)
        {
            for(int y = 0; y < chessPattern.length-1; y++)
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
//
//    public ShotResult searching()
//    {
//        setPositions(this.chessPattern);
//    }
}
