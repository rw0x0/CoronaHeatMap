... cholera;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class AprocessDatasetPerTAndAggregate {

    private static String input = "/data/.../datasets/cholera/pnas.1522305113.sd02.txt";
    private static String outputFolder = "/data/.../datasets/cholera/";

    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(input), "UTF8"));

        String line;
        BufferedWriter bw = null;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("t = ")) {
                String t = line.split("=")[1].trim();
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFolder + "t_" + t + ".txt"), StandardCharsets.UTF_8));
            } else if (line.equals("")) {
                bw.close();
            } else {
                String newline = "";

                String values[] = line.split("\t");
                for (String value : values) {
                    int hours = (int) Math.rint(24.0 * Double.parseDouble(value));
                    
                    newline += hours + ";";
                }
                
                newline = newline.substring(0, newline.length() - 1);
                
                bw.write(newline + "\n");
            }
        }

        br.close();
    }
}
