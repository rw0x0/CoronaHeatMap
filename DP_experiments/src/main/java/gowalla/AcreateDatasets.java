package gowalla;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcreateDatasets {

    private static String input = "/data/.../datasets/gowalla/dataset_step_2.csv";
    private static String output = "/data/.../experiments/datasets/gowalla_datasets.csv";
    private static String datasetsOutputFolder = "/data/.../experiments/datasets/original/gowalla/";

    private static int minW = 151;
    private static int maxW = 175;

    private static int datasetsPerW = 30000;
    
    private static int startingID = 4080000;

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(input), "UTF8"));

        Map<String, List<String>> dataset = new HashMap<>();

        String line = br.readLine();
        while ((line = br.readLine()) != null) {
            String userid = line.split(",")[0];

            List<String> temp;
            if (!dataset.containsKey(userid)) {
                temp = new ArrayList<>();
            } else {
                temp = dataset.get(userid);
            }
            temp.add(line);

            dataset.put(userid, temp);
        }

        br.close();

        List<String> userIDs = new ArrayList(dataset.keySet());

        BufferedWriter bwOverview = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), StandardCharsets.UTF_8));

        bwOverview.write("datasetid;userids;antennaids;w;antennas;mean\n");

        int datasetID = startingID;

        for (int w = minW; w <= maxW; w++) {System.out.println(w);
            List<String> stack = new ArrayList<>();

            while (stack.size() < datasetsPerW) {
                Collections.shuffle(userIDs);

                String uIDs = "";
                for (int i = 0; i < w; i++) {
                    uIDs += userIDs.get(i) + ",";
                }
                uIDs = uIDs.substring(0, uIDs.length() - 1);

                if (!stack.contains(uIDs)) {
                    stack.add(uIDs);

                    Map<String, List<String>> antennasUserIDs = new HashMap<>();

                    for (String userID : uIDs.split(",")) {
                        List<String> locations = dataset.get(userID);

                        for (String location : locations) {
                            String antenna = location.split(",")[3];

                            List<String> temp;
                            if (!antennasUserIDs.containsKey(antenna)) {
                                temp = new ArrayList<>();
                            } else {
                                temp = antennasUserIDs.get(antenna);
                            }

                            if (!temp.contains(userID)) {
                                temp.add(userID);
                            }

                            antennasUserIDs.put(antenna, temp);
                        }
                    }

                    String antennaIDs = "";
                    int sum = 0;
                    int antennas = antennasUserIDs.size();
                    
                    for (String antenna : antennasUserIDs.keySet()) {
                        antennaIDs += antenna + ",";
                        
                        sum += antennasUserIDs.get(antenna).size();
                    }
                    antennaIDs = antennaIDs.substring(0, antennaIDs.length() - 1);                                       
                    
                    double mean = (double) sum / antennas;

                    bwOverview.write(datasetID++ + ";" + uIDs + ";" + antennaIDs + ";" + w + ";" + antennas + ";" + mean + "\n");
                }
            }
        }

        bwOverview.close();
    }
}
