package Control;

import Logic.Character;
import Logic.Grid2D;
import Logic.Ship;
import Logic.ShipHarbor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LogicMain {
    //args[0]: size;
    public static void main(String[] args) {

        ShipHarbor harbor = new ShipHarbor();
        harbor.load();

        if(args.length <= 0) {
            System.exit(1);
        }

        Grid2D g = new Grid2D(Integer.parseInt(args[0]));
        g.generateRandom();
        System.out.printf("Grid:\n%s\n", g);
        Scanner s = new Scanner(System.in);

        int border = harbor.getBorder(g.getBound());
        int counter = harbor.getTotalShipCount(g.getBound());

        ArrayList<Ship> shipArrayList = new ArrayList<>();

        while(counter > 0)
        {
            System.out.println("Available ships:");

            for(int i = 0; i < border; i++)
            {
                System.out.println(harbor.getCopyOfHarborData(g.getBound()).get(i)+"er: " + harbor.getCopyOfHarborData(g.getBound()).get(i));
            }
            System.out.println("Please choose a ship and the Position");
            System.out.println("e.g. 3er Position x y");

            String userIO = s.nextLine();
            while(s.hasNextLine())
            {
                try
                {
                    String[] split = s.nextLine().split(" ");

                    if(split[1].equals("Position"))
                    {
                        int x = Integer.parseInt(split[2]);
                        int y = Integer.parseInt(split[3]);

                        if(border == 2)
                        {
                            switch (userIO)
                            {
                                case ("3er"):
                                    //i = total count of all 3er ships
                                    int i = harbor.getAmounts(g.getBound()).get(0);
                                    --i;
                                    if(i > 0)
                                    {//decrease total amount of 3er ships
                                        harbor.getAmounts(g.getBound()).set(0,i);
                                        shipArrayList.add(new Ship(harbor.getSize(g.getBound()).get(0)));//create new ship object with the wished size
                                        g.put(x,y,shipArrayList.get(0));//put the ship on the grid
                                    }else
                                        {//the wanted ship isn't available
                                            System.out.println("Please choose a different ship");
                                            counter++;
                                        }
                                    break;
                                case ("2er"):
                                    i = harbor.getAmounts(g.getBound()).get(1);
                                    --i;
                                    if(i > 0)
                                    {
                                        harbor.getAmounts(g.getBound()).set(1,i);
                                        shipArrayList.add(new Ship(harbor.getSize(g.getBound()).get(1)));
                                        g.put(x,y,shipArrayList.get(1));
                                    }else
                                    {
                                        System.out.println("Please choose a different ship");
                                        counter++;
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid input");
                                    counter++;
                            }
                            counter--;
                        }else if(border == 3)
                        {
                            switch (userIO)
                            {
                                case ("4er"):
                                    int i = harbor.getAmounts(g.getBound()).get(0);
                                    --i;
                                    if(i > 0)
                                    {
                                        harbor.getAmounts(g.getBound()).set(0,i);
                                        shipArrayList.add(new Ship(harbor.getSize(g.getBound()).get(0)));
                                        g.put(x,y,shipArrayList.get(0));
                                    }else
                                    {
                                        System.out.println("Please choose a different ship");
                                        counter++;
                                    }
                                    break;
                                case ("3er"):
                                    i = harbor.getAmounts(g.getBound()).get(1);
                                    --i;
                                    if(i > 0)
                                    {
                                        harbor.getAmounts(g.getBound()).set(1,i);
                                        shipArrayList.add(new Ship(harbor.getSize(g.getBound()).get(1)));
                                        g.put(x,y,shipArrayList.get(1));
                                    }else
                                    {
                                        System.out.println("Please choose a different ship");
                                        counter++;
                                    }
                                    break;
                                case ("2er"):
                                    i = harbor.getAmounts(g.getBound()).get(2);
                                    --i;
                                    if(i > 0)
                                    {
                                        harbor.getAmounts(g.getBound()).set(2,i);
                                        shipArrayList.add(new Ship(harbor.getSize(g.getBound()).get(2)));
                                        g.put(x,y,shipArrayList.get(2));
                                    }else
                                    {
                                        System.out.println("Please choose a different ship");
                                        counter++;
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid input");
                                    counter++;
                            }
                            counter--;
                        }else
                        {
                            switch (userIO)
                            {
                                case ("5er"):
                                    int i = harbor.getAmounts(g.getBound()).get(0);
                                    --i;
                                    if(i > 0)
                                    {
                                        harbor.getAmounts(g.getBound()).set(0,i);
                                        shipArrayList.add(new Ship(harbor.getSize(g.getBound()).get(0)));
                                        g.put(x,y,shipArrayList.get(0));
                                    }else
                                    {
                                        System.out.println("Please choose a different ship");
                                        counter++;
                                    }
                                    break;
                                case ("4er"):
                                    i = harbor.getAmounts(g.getBound()).get(1);
                                    --i;
                                    if(i > 0)
                                    {
                                        harbor.getAmounts(g.getBound()).set(1,i);
                                        shipArrayList.add(new Ship(harbor.getSize(g.getBound()).get(1)));
                                        g.put(x,y,shipArrayList.get(1));
                                    }else
                                    {
                                        System.out.println("Please choose a different ship");
                                        counter++;
                                    }
                                    break;
                                case ("3er"):
                                    i = harbor.getAmounts(g.getBound()).get(2);
                                    --i;
                                    if(i > 0)
                                    {
                                        harbor.getAmounts(g.getBound()).set(2,i);
                                        shipArrayList.add(new Ship(harbor.getSize(g.getBound()).get(2)));
                                        g.put(x,y,shipArrayList.get(2));
                                    }else
                                    {
                                        System.out.println("Please choose a different ship");
                                        counter++;
                                    }
                                    break;
                                case ("2er"):
                                    i = harbor.getAmounts(g.getBound()).get(3);
                                    --i;
                                    if(i > 0)
                                    {
                                        harbor.getAmounts(g.getBound()).set(3,i);
                                        shipArrayList.add(new Ship(harbor.getSize(g.getBound()).get(3)));
                                        g.put(x,y,shipArrayList.get(3));
                                    }else
                                    {
                                        System.out.println("Please choose a different ship");
                                        counter++;
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid input");
                                    counter++;
                            }
                            counter--;
                        }
                    }
                }
                catch (Exception e)
                {
                    System.out.println("Invalid command! reenter command");
                    System.out.println("e.g. 3er Position x y");
                }
            }
        }

        System.out.println("enter command.");
        System.out.println("e.g. shoot x y");
        while(s.hasNextLine()) {
            try {
                String[] split = s.nextLine().split(" ");

                //simulate shit with x: split[1]&y: split[2]
                if (split[0].equals("shoot")) {
                    int x = Integer.parseInt(split[1]);
                    int y = Integer.parseInt(split[2]);

                    System.out.format("boom at x:%d y:%d returns %s \n", x, y, g.shoot(x, y));
                }
            } catch(Exception e) {
                System.out.println("error! enter command.");
                System.out.println("e.g. shoot x y");
            }
        }
    }
}