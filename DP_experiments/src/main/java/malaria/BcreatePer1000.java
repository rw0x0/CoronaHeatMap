package malaria;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class BcreatePer1000 {
    
    // create the per 1000 dataset
    // the one located at the repo seems to be wrong

    private static String input = "/data/.../datasets/malaria/orig_malaria_average_trips.csv";
    private static String output = "/data/.../datasets/malaria/malaria_average_trips_per_1000.csv";

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(input), "UTF8"));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), StandardCharsets.UTF_8));

        String line;
        while ((line = br.readLine()) != null) {
            String newline = "";

            for (String value : line.split(",")) {
                int newValue = (int) Math.rint(Integer.parseInt(value) / (double) 1000.0);

                newline += newValue + ",";
            }

            newline = newline.substring(0, newline.length() - 1);

            bw.write(newline + "\n");
        }

        br.close();
        bw.close();
    }
}
