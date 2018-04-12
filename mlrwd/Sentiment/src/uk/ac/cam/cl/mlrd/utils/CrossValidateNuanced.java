package uk.ac.cam.cl.mlrd.utils;

import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.Exercise1;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.Exercise6;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.NuancedSentiment;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class CrossValidateNuanced {
    public List<Map<Path, NuancedSentiment>> splitCVRandom(Map<Path, NuancedSentiment> dataSet, int seed) {
        Random rand = new Random(seed);
        List<Map<Path, NuancedSentiment>> result = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            result.add(new HashMap<Path, NuancedSentiment>());
        }
        int amountPerFold = dataSet.size() / 10;
        int[] sizePerFoldCurrently = new int[10];
        for (Path p : dataSet.keySet()) {
            int index = rand.nextInt(10);
            while (sizePerFoldCurrently[index] >= amountPerFold) {
                index = rand.nextInt(10);
            }
            result.get(index).put(p, dataSet.get(p));
            sizePerFoldCurrently[index]++;
        }
        return result;
    }

    public List<Map<Path, NuancedSentiment>> splitCVStratifiedRandom(Map<Path, NuancedSentiment> dataSet, int seed) {
        Random rand = new Random(seed);
        List<Map<Path, NuancedSentiment>> result = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            result.add(new HashMap<Path, NuancedSentiment>());
        }
        int positive = 0;
        int negative = 0;
        int neutral = 0;
        for (NuancedSentiment s : dataSet.values()) {
            if (s == NuancedSentiment.POSITIVE) {
                positive++;
            } else if (s == NuancedSentiment.NEGATIVE) {
                negative++;
            } else {
                neutral++;
            }
        }
        int posPerFold = positive / 10;
        int negPerFold = negative / 10;
        int neuPerFold = neutral / 10;
        int amountPerFold = dataSet.size() / 10;

        int[] posPerFoldCurrently = new int[10];
        int[] negPerFoldCurrently = new int[10];
        int[] neuPerFoldCurrently = new int[10];
        int[] amountPerFoldCurrently = new int[10];

        for (Path p : dataSet.keySet()) {
            NuancedSentiment s = dataSet.get(p);
            int index = rand.nextInt(10);
            while (amountPerFoldCurrently[index] >= amountPerFold || (s == NuancedSentiment.POSITIVE && posPerFoldCurrently[index] >= posPerFold) || (s == NuancedSentiment.NEGATIVE && negPerFoldCurrently[index] >= negPerFold) || (s == NuancedSentiment.NEUTRAL && neuPerFoldCurrently[index] >= neuPerFold)) {
                index = rand.nextInt(10);
            }
            result.get(index).put(p, s);
            if (s == NuancedSentiment.NEGATIVE) {
                negPerFoldCurrently[index]++;
            } else if (s == NuancedSentiment.POSITIVE) {
                posPerFoldCurrently[index]++;
            } else {
                neuPerFoldCurrently[index]++;
            }
            amountPerFoldCurrently[index]++;
        }
        return result;
    }

    public double[] crossValidate(List<Map<Path, NuancedSentiment>> folds) throws IOException {
        Exercise6 ex6 = new Exercise6();
        double[] result = new double[10];
        for (int i = 0; i < folds.size(); i++) {
            Map<Path, NuancedSentiment> trainingSet = merge(i, folds);
            Map<Path, NuancedSentiment> testSet = folds.get(i);
            Exercise1 ex1 = new Exercise1();
            Map<NuancedSentiment, Double> classProbabilities = ex6.calculateClassProbabilities(trainingSet);
            Map<String, Map<NuancedSentiment, Double>> smoothedLogProbs = ex6.calculateNuancedLogProbs(trainingSet);
            Map<Path, NuancedSentiment> smoothedNBPredictions = ex6.nuancedClassifier(testSet.keySet(), smoothedLogProbs, classProbabilities);
            double smoothedNBAccuracy = ex6.nuancedAccuracy(testSet, smoothedNBPredictions);
            result[i] = smoothedNBAccuracy;
        }
        return result;
    }

    private HashMap<Path, NuancedSentiment> merge(int excludedIndex, List<Map<Path, NuancedSentiment>> inputMaps) {
        HashMap<Path, NuancedSentiment> result = new HashMap<>();
        for (int i = 0; i < inputMaps.size(); i++) {
            if (i != excludedIndex) {
                Map<Path, NuancedSentiment> map = inputMaps.get(i);
                map.keySet().forEach(p -> result.put(p, map.get(p)));
            }
        }
        return result;
    }

    public double cvAccuracy(double[] scores) {
        double sum = 0;
        for (double d : scores) {
            sum += d;
        }
        return sum / scores.length;
    }

    public double cvVariance(double[] scores) {
        double mean = cvAccuracy(scores);
        double n = scores.length;
        double variance = 0;
        for (int i = 0; i < n; i++) {
            variance += Math.pow((scores[i] - mean), 2);
        }

        return 1 / n * variance;
    }
}
