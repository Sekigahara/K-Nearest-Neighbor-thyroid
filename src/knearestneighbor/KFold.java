package knearestneighbor;

import java.util.ArrayList;
import java.math.*;

public class KFold {

    private double[][] dataset;

    public KFold(double[][] dataset) {
        this.dataset = dataset;
    }

    public double[][][] separatingData(int fold) {
        int sumLabel1 = 0, sumLabel2 = 0, sumLabel3 = 0;
        double[][][] separatedData = new double[fold][dataset.length][(int) (double) dataset[0].length / fold];

        //get long of the index of traindata label
        for (int i = 0; i < dataset[2].length; i++) {
            if (dataset[5][i] == 1) {
                sumLabel1++;
            } else if (dataset[5][i] == 2) {
                sumLabel2++;
            } else {
                sumLabel3++;
            }
        }

        //index each foldd
        int separateIndex1 = (int) ((double) sumLabel1 / fold);
        int separateIndex2 = (int) ((double) sumLabel2 / fold);
        int separateIndex3 = (int) ((double) sumLabel3 / fold);
        //data that cant reached by index
        int leftData1 = sumLabel1 - (separateIndex1 * fold);
        int leftData2 = sumLabel2 - (separateIndex2 * fold);
        int leftData3 = sumLabel3 - (separateIndex3 * fold);
        //last index each label
        int lastIndex1 = sumLabel1;
        int lastIndex2 = sumLabel1 + sumLabel2;
        int lastIndex3 = sumLabel1 + sumLabel2 + sumLabel3;

        //success use for other ref
        //label1 slicing
        if (leftData1 == 0 && leftData2 == 0 && leftData3 == 0) {
            int index = 0;
            int b = 0;
            int max = 0;
            for (int i = 0; i < lastIndex1; i = i + separateIndex1) {
                for (int k = i; k < i + separateIndex1; k++) {
                    //k + 1
                    for (int j = 0; j < separatedData[0].length; j++) {
                        separatedData[index][j][b] = dataset[j][k];
                    }
                    b++;
                }
                max = b;
                b = 0;
                index++;
            }

            //label2 slicing
            int max1 = 0;
            index = 0;
            b = max;
            for (int i = lastIndex1; i < lastIndex2; i = i + separateIndex2) {
                for (int k = i; k < i + separateIndex2; k++) {
                    //k + 1
                    for (int j = 0; j < separatedData[0].length; j++) {
                        //separatedData[index][j][b] = dataset[j][k];
                    }
                    b++;
                }
                max1 = b;
                b = max;
                index++;
            }

            //label3 slicing
            index = 0;
            b = max1;
            for (int i = lastIndex2; i < lastIndex3; i = i + separateIndex3) {
                for (int k = i; k < i + separateIndex3; k++) {
                    //k + 1
                    for (int j = 0; j < separatedData[0].length; j++) {
                        separatedData[index][j][b] = dataset[j][k];
                    }
                    b++;
                }
                b = max1;
                index++;
            }
        } else {
            int index = 0;
            int b = 0;
            int max = 0;
            for (int i = 0; i < (lastIndex1 - leftData1); i = i + separateIndex1) {
                for (int k = i; k < i + separateIndex1; k++) {
                    //k + 1
                    for (int j = 0; j < separatedData[0].length; j++) {
                        separatedData[index][j][b] = dataset[j][k];
                    }
                    b++;
                }
                max = b;
                b = 0;
                index++;
            }

            //label2 slicing
            int max1 = 0;
            index = 0;
            b = max;
            for (int i = lastIndex1; i < (lastIndex2 - leftData2); i = i + separateIndex2) {
                for (int k = i; k < i + separateIndex2; k++) {
                    //k + 1
                    for (int j = 0; j < separatedData[0].length; j++) {
                        //separatedData[index][j][b] = dataset[j][k];
                    }
                    b++;
                }
                max1 = b;
                b = max;
                index++;
            }

            //label3 slicing
            index = 0;
            b = max1;
            for (int i = lastIndex2; i < (lastIndex3 - leftData3); i = i + separateIndex3) {
                for (int k = i; k < i + separateIndex3; k++) {
                    //k + 1
                    for (int j = 0; j < separatedData[0].length; j++) {
                        separatedData[index][j][b] = dataset[j][k];
                    }
                    b++;
                }
                b = max1;
                index++;
            }
        }

        return separatedData;
    }

    public double[][] getTestingData(double[][][] trainingData, int index) {
        double testData[][] = new double[trainingData[0].length][trainingData[0][0].length];

        for (int i = 0; i < trainingData.length; i++) {
            if (i == index) {
                for (int k = 0; k < trainingData[0].length; k++) {
                    for (int j = 0; j < trainingData[0][0].length; j++) {
                        testData[k][j] = trainingData[i][k][j];
                    }
                }
            }
        }

        return testData;
    }

    public double[] getTestingLabel(double[][] testingData) {
        double[] label = new double[testingData[0].length];

        for (int k = 0; k < testingData[0].length; k++) {
            label[k] = testingData[5][k];
        }

        return label;
    }

    private double getDistance(double[] trainingData, double[] testingData) {
        double a1 = trainingData[0], a2 = testingData[0];
        double b1 = trainingData[1], b2 = testingData[1];
        double c1 = trainingData[2], c2 = testingData[2];
        double d1 = trainingData[3], d2 = testingData[3];
        double e1 = trainingData[4], e2 = testingData[4];

        return Math.sqrt(Math.pow((a2 - a1), 2) + Math.pow((b2 - b1), 2)
                + Math.pow((c2 - c1), 2)
                + Math.pow((d2 - d1), 2)
                + Math.pow((e2 - e1), 2));
    }

    public ArrayList<double[][]> getAllDistance(double[][] trainingData, double[][] testingData) {
        double[][] distance = new double[testingData[0].length][trainingData[0].length];
        double[][] distanceLabel = new double[testingData[0].length][trainingData[0].length];
        double[] tempTrainingData = new double[trainingData.length];
        double[] tempTestingData = new double[testingData.length];

        for (int i = 0; i < testingData[0].length; i++) {
            for (int k = 0; k < trainingData[0].length; k++) {
                tempTrainingData[0] = trainingData[0][k];
                tempTrainingData[1] = trainingData[1][k];
                tempTrainingData[2] = trainingData[2][k];
                tempTrainingData[3] = trainingData[3][k];
                tempTrainingData[4] = trainingData[4][k];

                tempTestingData[0] = testingData[0][i];
                tempTestingData[1] = testingData[1][i];
                tempTestingData[2] = testingData[2][i];
                tempTestingData[3] = testingData[3][i];
                tempTestingData[4] = testingData[4][i];

                distance[i][k] = getDistance(tempTrainingData, tempTestingData);
                distanceLabel[i][k] = trainingData[5][k];
            }
        }

        ArrayList<double[][]> listDistance = new ArrayList<>();
        listDistance.add(distance);
        listDistance.add(distanceLabel);
        return listDistance;
    }

    public ArrayList<double[][]> sortData(double[][] data, double[][] dataLabel, boolean isAscending) {
        int length = data[0].length;

        for (int j = 0; j < data.length; j++) {
            for (int i = 0; i < length - 1; i++) {
                int min_idx = i;
                for (int k = i + 1; k < length; k++) {
                    if (data[j][k] < data[j][min_idx]) {
                        min_idx = k;
                    }
                }

                double value1 = data[j][min_idx];
                double value2 = dataLabel[j][min_idx];

                data[j][min_idx] = data[j][i];
                dataLabel[j][min_idx] = dataLabel[j][i];

                data[j][i] = value1;
                dataLabel[j][i] = value2;
            }
        }
        ArrayList<double[][]> listDistance = new ArrayList<>();
        listDistance.add(data);
        listDistance.add(dataLabel);

        return listDistance;
    }

    public double[] kNearestNeighbor(int k, double[][] distanceLabel) {
        double[][] selectedLabel = new double[distanceLabel.length][k];
        double[] result = new double[distanceLabel.length];

        for (int i = 0; i < selectedLabel.length; i++) {
            //System.out.println(i + ". -----------");
            for (int j = 0; j < k; j++) {
                selectedLabel[i][j] = distanceLabel[i][j];
                //System.out.println(j + ". "+selectedLabel[i][j] + " | " + distanceLabel[i][j]);
            }
        }

        int label1 = 0, label2 = 0;
        int label3 = 0;
        for (int i = 0; i < selectedLabel.length; i++) {
            for (int j = 0; j < selectedLabel[0].length; j++) {
                if (selectedLabel[i][j] == 1) {
                    label1++;
                } else if (selectedLabel[i][j] == 2) {
                    label2++;
                } else {
                    label3++;
                }
            }
            if (label1 > label2 && label1 > label3) {
                result[i] = 1;
            } else if (label2 > label1 && label2 > label3) {
                result[i] = 2;
            } else if (label3 > label1 && label3 > label2) {
                result[i] = 3;
            }

            label1 = 0;
            label2 = 0;
            label3 = 0;
        }

        return result;
    }

    public double getError(double[] result, double[] labelTesting) {
        int sumCorrect = 0, sumFalse = 0;

        for (int i = 0; i < labelTesting.length; i++) {
            if (result[i] == labelTesting[i]) {
                sumCorrect = sumCorrect + 1;
            } else {
                sumFalse = sumFalse + 1;
            }
            //System.out.println(result[i] + " | " + labelTesting[i]);
        }

        int error = 0;
        error = result.length - sumCorrect;

        //System.out.println("Total Data : " + result.length);
        //System.out.println("Correct Clustering : "+ sumCorrect);
        //System.out.println("False Clustering : "+ sumFalse);
        //System.out.println("Error : " + ((double)error / result.length) * 100 + "%");
        return ((double) error / result.length) * 100;
    }
}
