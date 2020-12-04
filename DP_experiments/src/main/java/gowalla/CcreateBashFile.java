package gowalla;

import cholera.*;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

public class CcreateBashFile {

    private static String outputFolder = "/data/.../experiments/datasets/";
    
    private static int minID = 4080000;

    private static int maxID = 4829999;

    private static String plainPath = "/data/.../repo/privacy-preserving-disease-analysis/code/bin/plain";

    private static double minPrivacy = 0.05;

    private static double maxPrivacy = 1;

    private static double step = 0.05;

    private static DecimalFormat format = new DecimalFormat("#.##");

    private static int minW = 151;

    private static int maxW = 175;

    private static int numberOfDatasetsPerW = 30000;

    public static void main(String args[]) throws Exception {
        int currentW = minW;

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFolder + "run_gowalla_w_" + currentW + ".sh"), StandardCharsets.UTF_8));;

        for (int i = minID; i <= maxID; i++) {
            for (double privacy = minPrivacy; privacy <= maxPrivacy; privacy += step) {
                privacy = Double.valueOf(format.format(privacy));

                bw.write(plainPath + " ./original/gowalla/did_" + i + ".txt"
                        + " ./private/gowalla/did_" + i + "_e_" + privacy + ".txt"
                        + " " + privacy + "\n");
            }

            if (i % numberOfDatasetsPerW == numberOfDatasetsPerW - 1) {
                bw.close();

                currentW++;

                if (currentW <= maxW) {
                    bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFolder + "run_gowalla_w_" + currentW + ".sh"), StandardCharsets.UTF_8));
                }
            }
        }

    }
}
