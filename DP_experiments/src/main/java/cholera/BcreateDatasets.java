... cholera;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BcreateDatasets {

    private static int minW = 15;
    private static int maxW = 25;
    
    private static int datasetsPerW = 30000;
    
    private static int maxID = 122;
    
    private static String output = "/data/.../experiments/datasets/cholera_datasets.csv";
    
    public static void main(String args[]) throws Exception {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), StandardCharsets.UTF_8));
        
        List<String> userIDs = new ArrayList<>();
        
        for (int i = 0; i <= maxID; i++) {
            userIDs.add(i + "");
        }
        
        bw.write("dataset-id;user-ids;w\n");              
        
        int datasetID = 0;
        
        for (int w = minW; w <= maxW; w++) {System.out.println(w);
            List<String> stack = new ArrayList<>();
            
            while (stack.size() < datasetsPerW) {
                Collections.shuffle(userIDs);
                
                String uIDs = "";
                for (int i = 0; i < w; i++) {
                    uIDs += userIDs.get(i) + ",";
                }
                uIDs = uIDs.substring(0, uIDs.length() - 1);
                
                if (!stack.contains(uIDs)) {
                    stack.add(uIDs);
                    bw.write(datasetID++ + ";" + uIDs + ";" + w + "\n");
                }
            }
        }
        
        bw.close();
    }
}
