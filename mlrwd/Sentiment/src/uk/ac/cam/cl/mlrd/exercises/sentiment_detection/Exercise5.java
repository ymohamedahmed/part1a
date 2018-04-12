package uk.ac.cam.cl.mlrd.exercises.sentiment_detection;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Exercise5 implements IExercise5 {
    @Override
    public List<Map<Path, Sentiment>> splitCVRandom(Map<Path, Sentiment> dataSet, int seed) {
        Random rand = new Random(seed);
        List<Map<Path, Sentiment>> result = new ArrayList<>(10);
        for(int i = 0; i < 10; i++){
            result.add(new HashMap<Path, Sentiment>());
        }
        int amountPerFold = dataSet.size()/10;
        int[] sizePerFoldCurrently = new int[10];
        for(Path p : dataSet.keySet()){
            int index = rand.nextInt(10);
            while(sizePerFoldCurrently[index] >= amountPerFold){
                index = rand.nextInt(10);
            }
            result.get(index).put(p, dataSet.get(p));
            sizePerFoldCurrently[index]++;
        }
        return result;
    }

    @Override
    public List<Map<Path, Sentiment>> splitCVStratifiedRandom(Map<Path, Sentiment> dataSet, int seed) {
        Random rand = new Random(seed);
        List<Map<Path, Sentiment>> result = new ArrayList<>(10);
        for(int i = 0; i < 10; i++){
            result.add(new HashMap<Path, Sentiment>());
        }
        int positive = 0;
        int negative = 0;
        for(Sentiment s : dataSet.values()){
            if(s == Sentiment.POSITIVE){
                positive++;
            }else{
                negative++;
            }
        }
        int posPerFold = positive/10;
        int negPerFold = negative/10;
        int amountPerFold = dataSet.size()/10;

        int[] posPerFoldCurrently = new int[10];
        int[] negPerFoldCurrently = new int[10];
        int[] amountPerFoldCurrently = new int[10];

        for(Path p : dataSet.keySet()){
            Sentiment s = dataSet.get(p);
            int index = rand.nextInt(10);
            while(amountPerFoldCurrently[index] >= amountPerFold || (s==Sentiment.POSITIVE && posPerFoldCurrently[index]>=posPerFold) || (s==Sentiment.NEGATIVE && negPerFoldCurrently[index]>=negPerFold)){
                index = rand.nextInt(10);
            }
            result.get(index).put(p, s);
            if(s==Sentiment.NEGATIVE){
                negPerFoldCurrently[index]++;
            }else{
                posPerFoldCurrently[index]++;
            }
            amountPerFoldCurrently[index]++;
        }
        return result;
    }

    @Override
    public double[] crossValidate(List<Map<Path, Sentiment>> folds) throws IOException {
        Exercise2 ex2 = new Exercise2();
        double[] result = new double[10];
        for(int i = 0; i < folds.size(); i++){
            Map<Path, Sentiment> trainingSet = merge(i, folds);
            Map<Path, Sentiment> testSet = folds.get(i);
            Exercise1 ex1 = new Exercise1();
            Map<Sentiment, Double> classProbabilities = ex2.calculateClassProbabilities(trainingSet);
            Map<String, Map<Sentiment, Double>> smoothedLogProbs = ex2.calculateSmoothedLogProbs(trainingSet);
            Map<Path, Sentiment> smoothedNBPredictions = ex2.naiveBayes(testSet.keySet(), smoothedLogProbs, classProbabilities);
            double smoothedNBAccuracy = ex1.calculateAccuracy(testSet, smoothedNBPredictions);
            result[i] = smoothedNBAccuracy;
        }
        return result;
    }
    private HashMap<Path, Sentiment> merge(int excludedIndex, List<Map<Path, Sentiment>> inputMaps){
        HashMap<Path, Sentiment> result = new HashMap<>();
        for(int i = 0; i < inputMaps.size(); i++){
            if(i != excludedIndex){
                Map<Path, Sentiment> map = inputMaps.get(i);
                map.keySet().forEach(p -> result.put(p, map.get(p)));
            }
        }
        return result;
    }

    @Override
    public double cvAccuracy(double[] scores) {
        double sum = 0;
        for(double d : scores){
            sum+=d;
        }
        return sum/scores.length;
    }

    @Override
    public double cvVariance(double[] scores) {
        double mean = cvAccuracy(scores);
        double n = scores.length;
        double variance = 0;
        for(int i = 0; i<n; i++){
            variance+=Math.pow((scores[i]-mean),2);
        }

        return 1/n * variance;
    }
}
