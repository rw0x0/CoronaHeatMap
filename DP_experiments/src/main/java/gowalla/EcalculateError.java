package gowalla;

import cholera.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class EcalculateError {

    private static String originalPath = "/data/.../experiments/datasets/original/gowalla/";
    private static String privatePath = "/data/.../experiments/datasets/private/gowalla/";
    private static String datasetInfo = "/data/.../experiments/datasets/gowalla_datasets.csv";
    private static String output = "/data/.../experiments/gowalla.csv";

    private static int maxID = 0;

    private static double minPrivacy = 0.05;

    private static double maxPrivacy = 1;

    private static double step = 0.05;

    private static DecimalFormat format = new DecimalFormat("#.##");

    private static String headerToAdd = ";privacy;error\n";

    public static void main(String args[]) throws Exception {
        Map<String, String> datasetInfoMap = new HashMap<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(datasetInfo), "UTF8"));

        String header = br.readLine();
        String line;
        while ((line = br.readLine()) != null) {
            datasetInfoMap.put(line.split(";")[0], line);
        }

        br.close();

        header += headerToAdd;

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), StandardCharsets.UTF_8));

        bw.write(header);

        for (int i = 0; i <= maxID; i++) {
            int antennas = Integer.parseInt(datasetInfoMap.get(i + "").split(";")[4]);

            int originalValues[] = new int[antennas];
            int index = 0;

            br = new BufferedReader(new InputStreamReader(new FileInputStream(originalPath + "did_" + i + ".txt"), "UTF8"));

            while ((line = br.readLine()) != null) {
                originalValues[index++] = Integer.parseInt(line);
            }

            br.close();

            for (double privacy = minPrivacy; privacy <= maxPrivacy; privacy += step) {
                privacy = Double.valueOf(format.format(privacy));

                int privateValues[] = new int[antennas];
                index = 0;

                br = new BufferedReader(new InputStreamReader(new FileInputStream(privatePath + "did_" + i + "_e_" + privacy + ".txt"), "UTF8"));

                while ((line = br.readLine()) != null) {
                    privateValues[index++] = Integer.parseInt(line);
                }

                br.close();

                int sumOfErrors = 0;
                for (int j = 0; j < antennas; j++) {
                    sumOfErrors += Math.abs(originalValues[j] - privateValues[j]);
                }

                double meanError = (double) sumOfErrors / antennas;

                int meanErrorInt = (int) Math.rint(meanError);

                bw.write(datasetInfoMap.get(i + "") + ";" + privacy + ";" + meanErrorInt + "\n");
            }
        }

        bw.close();
    }
}
