package Logic;

import java.io.*;
import java.lang.ClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ShipHarbor {
    private Map<Integer, List<Integer>> harbor = new HashMap<Integer, List<Integer>>();
    public ShipHarbor() {
    }

    public void load() {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("csv/ships.csv");
            InputStreamReader sr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(sr);

            //skip first line
            br.readLine();

            for (String line; (line = br.readLine()) != null;) {
                // Process line
                String[] split = line.split(";");
                Integer[] integerSplit = new Integer[split.length];
                for(int i = 0; i < split.length-1; ++i) {
                    integerSplit[i] = Integer.parseInt(split[i]);
                }
                harbor.put(Integer.parseInt(split[split.length-1]), Arrays.asList(integerSplit).subList(0, integerSplit.length-1));
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getValues(int bound)//Use if the souts need to be in the main method
    {
       List<Integer> values = new ArrayList<>(harbor.get(bound));
       return values;
    }

    public void put(int bound)
    {
        load();
        List<Integer> values = new ArrayList<>(harbor.get(bound));

        while(values.get(0) != 0 || values.get(1) != 0 || values.get(2) != 0 || values.get(3) != 0)
        {
            System.out.println("Available ships:");
            if(values.get(0) > 0)
            {
                System.out.println("5er: " + values.get(0));
            }
            if(values.get(1) > 0)
            {
                System.out.println("4er: " + values.get(1));
            }
            if(values.get(2) > 0)
            {
                System.out.println("3er: " + values.get(2));
            }
            if(values.get(3) > 0)
            {
                System.out.println("2er: " + values.get(3));
            }

            System.out.println("Please choose a ship");

            Scanner s = new Scanner(System.in);
            String userIO = s.nextLine();

            switch (userIO)
            {                                       //decreases the amount of ships by 1
                case"5er":
                    int i = values.get(0);
                    --i;
                    values.set(0,i);
                    break;
                case"4er":
                    i = values.get(1);
                    --i;
                    values.set(1,i);
                    break;
                case"3er":
                    i = values.get(2);
                    --i;
                    values.set(2,i);
                    break;
                case"2er":
                    i = values.get(3);
                    --i;
                    values.set(3,i);
                    break;
                default:
                    System.out.println("Invalid input");
            }                                       //if the stupid user chooses a not available ship nothing happens. Good or Bad?

            System.out.println("Choose the Position ,x y, for your ship:");

        }
    }
}
