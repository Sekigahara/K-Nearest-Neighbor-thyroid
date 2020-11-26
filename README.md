This Source Code using a Basic Java Library.

The Main Concern of this Project are to implementing one of the Approach from Supervised Learning Algorythm K-Nearest Neighbor.
This Source Code Work Well with Thyroid Dataset(Given Dataset).

Inorder to Test the Data, i provide a few class for Splitting Data Training and Data Testing.

How this program Works : 
1. Separating The Data

Separating the data are necessary to measure how accurate the model that we trained.

K-Fold
- This Class implements KFold menthod which Folding each label by-K and adding it to be one Dataset and data training.
- Each of Label/Target will divided by K and added with other label and will Separated by Data Training and Testing.

Hold Out
- This Method requires a Parameter for splitting the data.
- The Parameter Input will automatically separate the by percentage.
  For Example my input are 80 which will be 80%, The 80% of dataset will become data training and the rest are data testing.
  
Leave One Out
- This Method are The same with K-fold, what makes it difference are the data training.
- This Method get only 1 set of data from dataset for data testing and the rest are data training.

This Source Code Also Included Normalization method using Z-score method.
- Get Mean From Data Training.
- Get Standar Deviance From Data Training.
- Each of the Row and Column will subtract by mean and then divided by Standart Deviance.
*the Data Training Also have to be normalized.

2. Count the Distance

Using the Vector Space Formulas to Count the Position.
- Each of Data training will be used to count each of the Data Testing.
- One Data Testing will be Counted with Every Data Training.
- The Result will be Stored with the amount of Data Training and labeled as Data Training labels.
- After we get all of the distance from data testing and data training then we can sort them by ascending.

3. Classification

This is the step which implementing the approach of Nearest Neighbor.

- After we get the sorted Data, we will get the smallest values by K.
- Then we Voted for it.
- If the k of shortest distance label having quantity more than the other labels then that the given training data will labeled with that training data label.


4. Error Count (Optional)

This Approach is only to Know how accurate our trained model are.

- This Step is just to Count error.
- By Comparing the result of classification with latest data training label we could get how many was correctly classified and not.


Bug : 
1. This Source Code are not Automated and only could using Thyroid datasets
2. This Source Code can't take K=1 as input for K-Nearest Neighbor.

Capability of Improvement in the future : 
1. Automated Code which can almost implements lot of datasets variance.
2. Proper OOP Uses.
3. Optimization for K-Nearest Neighbor when the Vote are Even or Draw.
    Approach : 
    1. Get the Mean of the Draw label then compare it, The smallest it values will win.
    2. Just add it without counting the mean, The smallest values also won
