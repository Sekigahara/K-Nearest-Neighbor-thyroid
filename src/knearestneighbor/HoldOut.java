/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knearestneighbor;

import java.util.ArrayList;

public class HoldOut {
    
    public ArrayList<double[][]> holdOut(double[][] dataset, int ratio){
        int sumLabel1 = 0, sumLabel2 = 0;
        int sumLabel3 = 0;
        float highpercentage = ratio / 100f;

        //get index of train data
        for (int i = 0; i < dataset[2].length; i++) {
            if (dataset[5][i] == 1) {
                sumLabel1++;
            } else if (dataset[5][i] == 2) {
                sumLabel2++;
            } else {
                sumLabel3++;
            }
        }

        //higher ration
        sumLabel1 = (int) ((float) sumLabel1 * highpercentage);
        sumLabel2 = (int) ((float) sumLabel2 * highpercentage);
        sumLabel3 = (int) ((float) sumLabel3 * highpercentage);
        int totalSum = sumLabel1 + sumLabel2 + sumLabel3;

        double[][] trainingData = new double[dataset.length][totalSum];

        //get leftover index
        int leftover1 = 0, leftover2 = 0;
        int leftover3 = 0;
        for (int i = 0; i < dataset[0].length; i++) {
            if (dataset[5][i] == 1) {
                if (i >= sumLabel1) {
                    leftover1++;
                }
            } else if (dataset[5][i] == 2) {
                if (i >= (leftover1 + sumLabel1 + sumLabel2)) {
                    leftover2++;
                }
            } else if (dataset[5][i] == 3) {
                if (i >= (leftover1 + leftover2 + sumLabel1 + sumLabel2 + sumLabel3)) {
                    leftover3++;
                }
            }
        }

        int totalLeftOver = leftover1 + leftover2 + leftover3;
        double[][] testingData = new double[dataset.length][totalLeftOver];

        //get the data
        int k = 0, j = 0;
        for (int i = 0; i < dataset[0].length; i++) {
            if (dataset[5][i] == 1) {
                if (i < sumLabel1) {
                    trainingData[0][k] = dataset[0][i];
                    trainingData[1][k] = dataset[1][i];
                    trainingData[2][k] = dataset[2][i];
                    trainingData[3][k] = dataset[3][i];
                    trainingData[4][k] = dataset[4][i];
                    trainingData[5][k] = dataset[5][i];
                    k++;
                } else {
                    testingData[0][j] = dataset[0][i];
                    testingData[1][j] = dataset[1][i];
                    testingData[2][j] = dataset[2][i];
                    testingData[3][j] = dataset[3][i];
                    testingData[4][j] = dataset[4][i];
                    testingData[5][j] = dataset[5][i];
                    j++;
                }
            } else if (dataset[5][i] == 2) {
                if (i < (sumLabel1 + sumLabel2 + leftover1)) {
                    trainingData[0][k] = dataset[0][i];
                    trainingData[1][k] = dataset[1][i];
                    trainingData[2][k] = dataset[2][i];
                    trainingData[3][k] = dataset[3][i];
                    trainingData[4][k] = dataset[4][i];
                    trainingData[5][k] = dataset[5][i];
                    k++;
                } else {
                    testingData[0][j] = dataset[0][i];
                    testingData[1][j] = dataset[1][i];
                    testingData[2][j] = dataset[2][i];
                    testingData[3][j] = dataset[3][i];
                    testingData[4][j] = dataset[4][i];
                    testingData[5][j] = dataset[5][i];
                    j++;
                }
            } else if (dataset[5][i] == 3) {
                if (i < (sumLabel1 + sumLabel2 + sumLabel3 + leftover1 + leftover2)) {
                    trainingData[0][k] = dataset[0][i];
                    trainingData[1][k] = dataset[1][i];
                    trainingData[2][k] = dataset[2][i];
                    trainingData[3][k] = dataset[3][i];
                    trainingData[4][k] = dataset[4][i];
                    trainingData[5][k] = dataset[5][i];
                    k++;
                } else {
                    testingData[0][j] = dataset[0][i];
                    testingData[1][j] = dataset[1][i];
                    testingData[2][j] = dataset[2][i];
                    testingData[3][j] = dataset[3][i];
                    testingData[4][j] = dataset[4][i];
                    testingData[5][j] = dataset[5][i];
                    j++;
                }
            }
        }

        ArrayList<double[][]> listData = new ArrayList<>();
        listData.add(trainingData);
        listData.add(testingData);

        return listData;
    } 
}
