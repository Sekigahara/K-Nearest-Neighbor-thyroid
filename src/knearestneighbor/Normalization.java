package knearestneighbor;

import java.util.ArrayList;
import java.math.*;

public class Normalization {
    public Normalization(){
        
    }
    
    public double[] getMean(double[][] dataset){
        double[] sumData = new double[dataset.length - 1];
        double[] meanData = new double[dataset.length - 1];
        
        for(int i = 0; i < dataset.length - 1;i++){
            for(int k = 0; k < dataset[0].length; k++)
                sumData[i] = sumData[i] + dataset[i][k];
            meanData[i] = sumData[i] / dataset[i].length;
        }

        return meanData;
    }
    
    public double[] getStd(double[][] dataset, double[] mean){
        double[] std = new double[dataset.length - 1];
        double[] total = new double[dataset.length - 1];
        
        for(int i = 0 ; i < dataset.length - 1;i++){
            for(int k = 0 ; k < dataset[0].length;k++)
                total[i] = total[i] + Math.pow((dataset[i][k] - mean[i]), 2);
            std[i] = Math.sqrt(total[i] / (dataset[0].length - 1));
        }
        
        return std;
    }
    
    public double[][] normalizeData(double[][] dataset, double[] mean, double[] std){
        double[][] newData = new double[dataset.length][dataset[0].length];
        
        for(int i = 0 ; i < dataset[0].length;i++)
            newData[5][i] = dataset[5][i];
        
        for(int i = 0 ; i < dataset.length - 1; i++){
            for(int k = 0 ; k < dataset[0].length ;k++)
                newData[i][k] = (dataset[i][k] - mean[i]) / std[i];
        }
        
        return newData;
    }
}
