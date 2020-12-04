package malaria;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

public class D1createBashFile {

    private static String output = "/data/.../experiments/datasets/run_malaria.sh";

    private static int maxID = 21699;

    private static String plainPath = "/data/.../repo/privacy-preserving-disease-analysis/code/bin/plain";

    private static double minPrivacy = 0.05;

    private static double maxPrivacy = 1;

    private static double step = 0.05;

    private static DecimalFormat format = new DecimalFormat("#.##");

    public static void main(String args[]) throws Exception {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), StandardCharsets.UTF_8));

        for (int i = 0; i <= maxID; i++) {
            for (double privacy = minPrivacy; privacy <= maxPrivacy; privacy += step) {
                privacy = Double.valueOf(format.format(privacy));

                bw.write(plainPath + " ./original/malaria/did_" + i + ".txt"
                        + " ./private/malaria/did_" + i + "_e_" + privacy + ".txt"
                        + " " + privacy + "\n");
            }

        }

        bw.close();
    }
}
