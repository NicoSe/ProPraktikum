package Logic;

import java.io.*;
import java.lang.ClassLoader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

class HarborShipData implements Cloneable {
    public int size;
    public int amount;

    @Override
    public Object clone() {
        HarborShipData sd = new HarborShipData();
        sd.size = this.size;
        sd.amount = this.amount;
        return sd;
    }
}

public class ShipHarbor {
    private Map<Integer, Integer> harborVolume = new HashMap<Integer, Integer>();
    private Map<Integer, List<HarborShipData>> harbor = new HashMap<Integer, List<HarborShipData>>();
    public ShipHarbor() {
    }

    public int getTotalShipCount(int gridsize) {
        return harborVolume.get(gridsize);
    }

    /**
     * @param gridsize size of grid
     * @return copy of HarborShipData of ships 5-4-3-2
     */
    public List<HarborShipData> getCopyOfHarborData(int gridsize) {

        if(gridsize > 30 || gridsize < 0) {
            return null;
        }

        List<HarborShipData> clone = new ArrayList<HarborShipData>(harbor.size());
        for(HarborShipData data : harbor.get(gridsize)) {
            clone.add((HarborShipData)data.clone());
        }
        return clone;
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
                int rowVolume = 0;

                //
                ArrayList<HarborShipData> currentBoundShipData = new ArrayList();
                int gridBounds = Integer.parseInt(split[split.length-1]);
                for(int i = 0; i < split.length-1; ++i) {
                    int shipCountOfCurrentType = Integer.parseInt(split[i]);
                    if (shipCountOfCurrentType <= 0) {
                        continue;
                    }
                    HarborShipData shipData = new HarborShipData();
                    shipData.size = 5 - i;
                    shipData.amount = shipCountOfCurrentType;
                    currentBoundShipData.add(shipData);
                    rowVolume += shipCountOfCurrentType;
                }

                harborVolume.put(gridBounds, rowVolume);
                harbor.put(gridBounds, currentBoundShipData);
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Integer> getSize(int bound)
    {//the value at index 0 is dependent of the grid
        List<Integer> sizes = new ArrayList<>();

        for(int i = 0; i < getBorder(bound); i++)
        {
//if the border > 9, the size at index 0 will be 5, if border < 9, the size at index 0 will be 4...
            sizes.add(i, getCopyOfHarborData(bound).get(i).size);
        }
        return sizes;
    }
    public List<Integer> getAmounts(int bound)
    {
        List<Integer> amounts = new ArrayList<>();

        for(int i = 0; i < getBorder(bound); i++)
        {
            amounts.add(i, getCopyOfHarborData(bound).get(i).amount);
        }
        return amounts;
    }
    public int getBorder(int bound)
    {//the size of getCopyOfHarborData is dependent of the grid
        int border = 0;
        if(bound == 5)
        {
            border = 2;
        }else if(bound < 9)
        {
            border = 3;
        }else
        {
            border = 4;
        }
        return border;
    }
}
