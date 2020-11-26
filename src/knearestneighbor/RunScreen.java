package knearestneighbor;

import java.util.ArrayList;

public class RunScreen {

    public static void main(String args[]) {
        ReadCSV readCSV = new ReadCSV();
        double dataset[][] = readCSV.read("C:\\Users\\Sekigahara\\Documents\\Semester 5\\Machine Learning\\thyroid.csv");

        NearestNeighbor nn = new NearestNeighbor();

        System.out.println("\n Method Hold Out \n");
        methodHoldOut(nn, dataset);

        System.out.println("\n Method K-Fold \n");
        methodKFold(dataset);

        System.out.println("\n Method Leave One Out \n");
        methodLeaveOneOut(dataset);
    }

    private static void methodHoldOut(NearestNeighbor nn, double[][] dataset) {
        Normalization normalization = new Normalization();
        HoldOut holdOut = new HoldOut();

        ArrayList<double[][]> listData = holdOut.holdOut(dataset, 80);
        double[][] trainingData = listData.get(0);
        double[][] testingData = listData.get(1);

        double mean[] = normalization.getMean(dataset);
        double std[] = normalization.getStd(dataset, mean);

        double newDataTraining[][] = normalization.normalizeData(trainingData, mean, std);
        double newDataTesting[][] = normalization.normalizeData(testingData, mean, std);

        double labelTraining[] = nn.readLabelTraining(newDataTraining);
        double labelTesting[] = nn.readLabelTraining(newDataTesting);

        listData = nn.getAllDistance(newDataTraining, newDataTesting);
        double[][] distanceTraining = listData.get(0);
        double[][] distanceLabel = listData.get(1);

        listData = nn.sortData(distanceTraining, distanceLabel, true);
        distanceTraining = listData.get(0);
        distanceLabel = listData.get(1);

        double sum = 0;
        for (int a = 1; a < 11; a = a + 5) {
            //System.out.println("--------------------------");
            int k = a;
            sum = sum + nn.getError(nn.kNearestNeighbor(k, distanceTraining, distanceLabel), labelTesting);
        }
        System.out.println("Error Rate for HoldOut : " + ((double) sum / 3) + "%");
    }

    private static void methodKFold(double[][] dataset) {
        int fold = 5;

        KFold kFold = new KFold(dataset);

        double[][][] separatedData = kFold.separatingData(fold);
        double[] sumError = new double[fold];
        double[][] temp = new double[separatedData[0].length][(separatedData[0][0].length) * (separatedData.length - 1)];
        double[][] dataTrain = new double[separatedData[0].length][(separatedData.length) * separatedData[0][0].length];

        for (int i = 0; i < fold; i++) {
            double[][] dataTesting = kFold.getTestingData(separatedData, i);
            double[] labelTesting = kFold.getTestingLabel(dataTesting);

            for (int j = 0; j < fold; j++) {
                if (j == i) {
                    continue;
                } else {
                    int count = 0;
                    for (int a = 0; a < temp.length; a++) {
                        for (int b = 0; b < temp[0].length; b++) {
                            temp[a][b] = separatedData[j][a][count];
                            if (count + 1 == separatedData[0][0].length) {
                                count = 0;
                            } else {
                                count++;
                            }
                        }
                    }
                }
            }

            Normalization nn = new Normalization();

            double mean[] = nn.getMean(temp);
            double std[] = nn.getStd(temp, mean);
            temp = nn.normalizeData(temp, mean, std);
            dataTesting = nn.normalizeData(dataTesting, mean, std);

            ArrayList<double[][]> listDistance = new ArrayList<>();
            listDistance = kFold.getAllDistance(temp, dataTesting);
            double[][] distance = listDistance.get(0);
            double[][] distanceLabel = listDistance.get(1);

            listDistance = kFold.sortData(distance, distanceLabel, true);
            distance = listDistance.get(0);
            distanceLabel = listDistance.get(1);

            double sum = 0;
            for (int a = 0; a < 11; a = a + 5) {
                //System.out.println("--------------------------");
                sum = sum + kFold.getError(kFold.kNearestNeighbor(a, distanceLabel), labelTesting);
            }
            sumError[i] = sum / 3;
        }

        double sum = 0;
        for (int i = 1; i < sumError.length; i++) {
            sum = sum + sumError[i];
        }

        System.out.println("Error Rate for K-Fold : " + ((double) sum / fold) + "%");
    }

    private static void methodLeaveOneOut(double[][] dataset) {
        double sum = 0;
        double[][] trainingData = new double[dataset.length][dataset[0].length - 1];
        double[][] testingData = new double[dataset.length][1];

        for (int a = 0; a < dataset[0].length; a++) {
            //extract testing data
            for (int i = 0; i < dataset.length; i++) {
                testingData[i][0] = dataset[i][a];
            }

            //extract training data
            for (int i = 0; i < dataset.length; i++) {
                for (int k = 0, j = 0; k < dataset[0].length; k++) {
                    if (k == a) {
                        continue;
                    } else {
                        trainingData[i][j] = dataset[i][k];
                        j++;
                    }
                }
            }
            
            Normalization nn = new Normalization();
            
            double mean[] = nn.getMean(trainingData);
            double std[] = nn.getStd(trainingData, mean);
            trainingData = nn.normalizeData(trainingData, mean, std);
            testingData = nn.normalizeData(testingData, mean, std);
            
            LeaveOneOut loo = new LeaveOneOut();
            
            double[] testingLabel = loo.readLabelTesting(testingData);
            ArrayList<double[][]> listDistance = loo.getAllDistance(trainingData, testingData);
            double[][] distance = listDistance.get(0);
            double[][] distanceLabel = listDistance.get(1);
            
            listDistance = loo.sortData(distance, distanceLabel, true);
            distance = listDistance.get(0);
            distanceLabel = listDistance.get(1);
            
            for (int i = 1; i < 11; i = i + 5) {
                sum = sum + loo.getError(loo.kNearestNeighbor(i, distanceLabel), testingLabel);
            }
        }
        System.out.println("Error Rate for Leave One Out : " + ((double) sum / (dataset[0].length * 3)) + "%");
    }
}
