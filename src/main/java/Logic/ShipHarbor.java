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
}
