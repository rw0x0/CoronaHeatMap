package malaria;

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

public class Z1extractInformationFromDatasets {

    private static String originalPath = "/data/.../experiments/datasets/original/malaria/";
    private static String datasetInfo = "/data/.../experiments/datasets/malaria_datasets.csv";

    private static int maxID = 21699;

    private static DecimalFormat format = new DecimalFormat("#.##");

    private static String headerToAdd = ";mean;std-dev\n";

    public static void main(String args[]) throws Exception {
        Map<String, String> original = new HashMap<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(datasetInfo), "UTF8"));

        String header = br.readLine();
        String line;
        while ((line = br.readLine()) != null) {
            String id = line.split(";")[0];

            original.put(id, line);
        }

        br.close();

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(datasetInfo), StandardCharsets.UTF_8));
        
        bw.write(header + headerToAdd);

        for (int i = 0; i <= maxID; i++) {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(originalPath + "did_" + i + ".txt"), "UTF8"));
            
            int w = Integer.parseInt(original.get(i + "").split(";")[2]);
            
            int sum = 0;
            
            int values[] = new int[w];
            int index = 0;
            
            while ((line = br.readLine()) != null) {
                sum += Integer.parseInt(line);
                values[index++] = Integer.parseInt(line);
            }
            
            double mean = (double) sum / w;
            
            double stdDev = 0;
            for (int value : values) {
                stdDev += Math.pow((double) value - mean, 2);
            }
            
            stdDev = Math.sqrt(stdDev / w);
            
            br.close();
            
            bw.write(original.get(i + "") + ";" + format.format(mean) + ";" + format.format(stdDev) + "\n");
        }

        bw.close();
    }
}
