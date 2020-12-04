... cholera;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Caggregate {

    private static String input = "/data/.../datasets/cholera/t_1.txt";
    private static String datasetsFile = "/data/.../experiments/datasets/cholera_datasets.csv";
    private static String outputFolder = "/data/.../experiments/datasets/original/cholera/";

    public static void main(String args[]) throws Exception {
        Map<String, String> dataset = new HashMap<>();
        int userid = 0;

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(input), "UTF8"));

        String line;
        while ((line = br.readLine()) != null) {
            dataset.put(userid++ + "", line);
        }

        br.close();

        br = new BufferedReader(new InputStreamReader(new FileInputStream(datasetsFile), "UTF8"));

        line = br.readLine();
        while ((line = br.readLine()) != null) {
            String fields[] = line.split(";");

            String datasetID = fields[0];
            System.out.println(datasetID);
            List<String> userIDs = Arrays.asList(fields[1].split(","));

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFolder + "did_" + datasetID + ".txt"), StandardCharsets.UTF_8));

            for (String userID : userIDs) {
                int sum = 0;

                for (String userID2 : userIDs) {
                    sum += Integer.parseInt(dataset.get(userID2).split(";")[Integer.parseInt(userID)]);
                }

                bw.write(sum + "\n");
            }

            bw.close();
        }

        br.close();
    }
}
