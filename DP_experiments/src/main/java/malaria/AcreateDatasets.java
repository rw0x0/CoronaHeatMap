package malaria;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class AcreateDatasets {
    
    // create all the combinations of datasets (given that the minimum number of users are 15)

    private static String output = "/data/.../experiments/datasets/malaria_datasets.csv";
    private static int datasetID = 0;

    public static void main(String args[]) throws Exception {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), StandardCharsets.UTF_8));

        bw.write("dataset-id;user-ids;w\n");

        String sequence[] = new String[20];
        for (int i = 0; i < sequence.length; i++) {
            sequence[i] = "" + i;
        }

        String data[] = new String[20];

        for (int r = 15; r <= sequence.length; r++) {
            combinations(sequence, data, 0, 19, 0, r, ",", bw);
        }

        bw.close();
    }

    private static void combinations(String[] sequence, String[] data, int start, int end, int index, int r,
            String separator, BufferedWriter bw) throws Exception {
        if (index == r) {
            String combination = "";

            for (int j = 0; j < r; j++) {
                combination += data[j] + separator;
            }

            if (!combination.equals("")) {
                combination = combination.substring(0, combination.length() - 1);
                bw.write(datasetID++ + ";" + combination + ";" + combination.split(separator).length + "\n");
                System.out.println(combination);
            }
        }

        for (int i = start; i <= end && ((end - i + 1) >= (r - index)); i++) {
            data[index] = sequence[i];
            combinations(sequence, data, i + 1, end, index + 1, r, separator, bw);
        }
    }
}
