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

public class ShipData {
    private Map<Integer, Integer> harborVolume = new HashMap<Integer, Integer>();
    private Map<Integer, List<HarborShipData>> harbor = new HashMap<Integer, List<HarborShipData>>();
    public ShipData() {
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

            ///skip first line
            br.readLine();

            for (String line; (line = br.readLine()) != null;) {
                /// Process line
                String[] split = line.split(";");
                int rowVolume = 0;


                ArrayList<HarborShipData> currentBoundShipData = new ArrayList<>();
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
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
