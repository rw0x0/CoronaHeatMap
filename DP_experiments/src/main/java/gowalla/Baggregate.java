package gowalla;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Baggregate {

    private static String input = "/data/.../datasets/gowalla/dataset_step_2.csv";
    private static String datasetsFile = "/data/.../experiments/datasets/gowalla_datasets.csv";
    private static String outputFolder = "/data/.../experiments/datasets/original/gowalla/";

    public static void main(String args[]) throws Exception {
        Map<String, List<String>> antennaIDsUserIDs = new HashMap<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(input), "UTF8"));

        String line = br.readLine();
        while ((line = br.readLine()) != null) {
            String locationID = line.split(",")[3];
            String userid = line.split(",")[0];

            List<String> temp;
            if (antennaIDsUserIDs.containsKey(locationID)) {
                temp = antennaIDsUserIDs.get(locationID);
            } else {
                temp = new ArrayList<>();
            }
            temp.add(userid);

            antennaIDsUserIDs.put(locationID, temp);
        }

        br.close();
        
        br = new BufferedReader(new InputStreamReader(new FileInputStream(datasetsFile), "UTF8"));

        line = br.readLine();
        while ((line = br.readLine()) != null) {
            String fields[] = line.split(";");
            
            String datasetID = fields[0];System.out.println(datasetID);
            List<String> userIDs = Arrays.asList(fields[1].split(","));
            List<String> antennaIDs = Arrays.asList(fields[2].split(","));
            
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFolder + "did_" + datasetID + ".txt"), StandardCharsets.UTF_8));
            
            for (String antennaID : antennaIDs) {
                int sum = 0;
                
                for (String userID : antennaIDsUserIDs.get(antennaID)) {
                    if (userIDs.contains(userID)) {
                        sum++;
                    }
                }
                
                bw.write(sum + "\n");
            }
            
            bw.close();
        }
        
        br.close();
    }
}
