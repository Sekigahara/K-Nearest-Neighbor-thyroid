package knearestneighbor;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.*;

public class ReadCSV {

    public ReadCSV() {

    }

    public double[][] read(String fileName) {
        ArrayList<String[]> tempData = new ArrayList<>();
        String COMMA_DELIMITER = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();

            while (line != null) {
                String[] values = line.split(COMMA_DELIMITER);

                tempData.add(values);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {

        } catch (IOException ie) {

        }

        int i = 0;
        double[][] dataset = new double[6][tempData.size()];

        for (String[] stringData : tempData) {
            dataset[0][i] = Double.parseDouble(stringData[0]);
            dataset[1][i] = Double.parseDouble(stringData[1]);
            dataset[2][i] = Double.parseDouble(stringData[2]);
            dataset[3][i] = Double.parseDouble(stringData[3]);
            dataset[4][i] = Double.parseDouble(stringData[4]);
            dataset[5][i] = Double.parseDouble(stringData[5]);
            i++;
        }

        return dataset;
    }
}
