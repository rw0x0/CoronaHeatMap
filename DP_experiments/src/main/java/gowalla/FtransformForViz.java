package gowalla;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FtransformForViz {

    private static String datasetInput = "/data/.../datasets/gowalla/dataset_step_2.csv";
    private static String datasetInfo = "/data/.../experiments/datasets/gowalla_datasets.csv";
    private static String originalPath = "/data/.../experiments/datasets/original/gowalla/";
    private static String privatePath = "/data/.../experiments/datasets/private/gowalla/";
    private static String vizPath = "/data/.../experiments/datasets/viz/gowalla/";

    private static double minPrivacy = 0.05;

    private static double maxPrivacy = 1;

    private static double step = 0.05;

    private static DecimalFormat format = new DecimalFormat("#.##");

    public static void main(String args[]) throws Exception {
        Map<String, String> antennaIDsCoords = new HashMap<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(datasetInput), "UTF8"));

        String line = br.readLine();
        while ((line = br.readLine()) != null) {
            String fields[] = line.split(",");

            String antennaID = fields[3];
            if (!antennaIDsCoords.containsKey(antennaID)) {
                String lat = fields[1];
                String lon = fields[2];

                antennaIDsCoords.put(antennaID, lat + "," + lon);
            }
        }

        br.close();

        br = new BufferedReader(new InputStreamReader(new FileInputStream(datasetInfo), "UTF8"));

        line = br.readLine();
        while ((line = br.readLine()) != null) {
            String fields[] = line.split(";");

            String datasetID = fields[0];
            String antennaIDs[] = fields[2].split(",");

            BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(originalPath + "did_" + datasetID + ".txt"), "UTF8"));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(vizPath + "did_" + datasetID + ".csv"), StandardCharsets.UTF_8));
            bw.write("lat,lon,sum\n");

            String line2;
            List<String> temp = new ArrayList<>();
            while ((line2 = br2.readLine()) != null) {
                temp.add(line2);
            }

            br2.close();

            for (int i = 0; i < antennaIDs.length; i++) {
                for (int j = 0; j < Integer.parseInt(temp.get(i)); j++) {
                    bw.write(antennaIDsCoords.get(antennaIDs[i]) + "," + temp.get(i) + "\n");
                }
            }

            bw.close();

            for (double privacy = minPrivacy; privacy <= maxPrivacy; privacy += step) {
                privacy = Double.valueOf(format.format(privacy));

                br2 = new BufferedReader(new InputStreamReader(new FileInputStream(privatePath + "did_" + datasetID + "_e_" + privacy + ".txt"), "UTF8"));
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(vizPath + "did_" + datasetID + "_e_" + privacy + ".csv"), StandardCharsets.UTF_8));
                bw.write("lat,lon,sum\n");

                temp = new ArrayList<>();
                while ((line2 = br2.readLine()) != null) {
                    if (line2.contains("-")) {
                        temp.add("0");
                    } else {
                        temp.add(line2);
                    }
                }

                br2.close();

                for (int i = 0; i < antennaIDs.length; i++) {
                    bw.write(antennaIDsCoords.get(antennaIDs[i]) + "," + temp.get(i) + "\n");
                }

                bw.close();
            }

            if (datasetID.equals("0")) {
                break;
            }
        }

        br.close();
    }
}
