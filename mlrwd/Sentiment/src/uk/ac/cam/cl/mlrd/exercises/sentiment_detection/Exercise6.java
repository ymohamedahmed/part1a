package uk.ac.cam.cl.mlrd.exercises.sentiment_detection;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Exercise6 implements IExercise6 {

    @Override
    public Map<NuancedSentiment, Double> calculateClassProbabilities(Map<Path, NuancedSentiment> trainingSet) throws IOException {
        int negativeCount = 0;
        int positiveCount = 0;
        int neutralCount = 0;
        HashMap<NuancedSentiment, Double> probabilities = new HashMap<>();
        for (NuancedSentiment s : trainingSet.values()) {
            if (s == NuancedSentiment.POSITIVE) {
                positiveCount++;
            } else if (s == NuancedSentiment.NEGATIVE) {
                negativeCount++;
            } else {
                neutralCount++;
            }
        }
        probabilities.put(NuancedSentiment.NEGATIVE, (double) negativeCount / (double) trainingSet.size());
        probabilities.put(NuancedSentiment.POSITIVE, (double) positiveCount / (double) trainingSet.size());
        probabilities.put(NuancedSentiment.NEUTRAL, (double) neutralCount / (double) trainingSet.size());
        return probabilities;
    }

    @Override
    public Map<String, Map<NuancedSentiment, Double>> calculateNuancedLogProbs(Map<Path, NuancedSentiment> trainingSet) throws IOException {
        HashMap<String, Map<NuancedSentiment, Double>> probabilities = new HashMap<>();
        HashMap<String, Integer> negativeCount = new HashMap<>();
        HashMap<String, Integer> positiveCount = new HashMap<>();
        HashMap<String, Integer> neutralCount = new HashMap<>();

        HashSet<String> unique = new HashSet<>();
        int negCount = 0;
        int posCount = 0;
        int neuCount = 0;

        for (Path p : trainingSet.keySet()) {
            List<String> tokenized = Tokenizer.tokenize(p);
            for (String word : tokenized) {
                if (trainingSet.get(p) == NuancedSentiment.NEGATIVE) {
                    negCount++;
                    negativeCount.put(word, negativeCount.get(word) == null ? 1 : negativeCount.get(word) + 1);
                    positiveCount.put(word, positiveCount.get(word) == null ? 0 : positiveCount.get(word));
                    neutralCount.put(word, neutralCount.get(word) == null ? 0 : neutralCount.get(word));
                } else if (trainingSet.get(p) == NuancedSentiment.POSITIVE) {
                    posCount++;
                    negativeCount.put(word, negativeCount.get(word) == null ? 0 : negativeCount.get(word));
                    neutralCount.put(word, neutralCount.get(word) == null ? 0 : neutralCount.get(word));
                    positiveCount.put(word, positiveCount.get(word) == null ? 1 : positiveCount.get(word) + 1);
                } else {
                    neuCount++;
                    negativeCount.put(word, negativeCount.get(word) == null ? 0 : negativeCount.get(word));
                    positiveCount.put(word, positiveCount.get(word) == null ? 0 : positiveCount.get(word));
                    neutralCount.put(word, neutralCount.get(word) == null ? 1 : neutralCount.get(word) + 1);
                }
                unique.add(word);
            }
        }
        for (String w : unique) {
            HashMap<NuancedSentiment, Double> probability = new HashMap<>();
            probability.put(NuancedSentiment.POSITIVE, Math.log((double) (positiveCount.get(w) + 1) / (double) (posCount + unique.size())));
            probability.put(NuancedSentiment.NEGATIVE, Math.log((double) (negativeCount.get(w) + 1) / (double) (negCount + unique.size())));
            probability.put(NuancedSentiment.NEUTRAL, Math.log((double) (neutralCount.get(w) + 1) / (double) (neuCount + unique.size())));
            probabilities.put(w, probability);
        }
        return probabilities;
    }

    @Override
    public Map<Path, NuancedSentiment> nuancedClassifier(Set<Path> testSet, Map<String, Map<NuancedSentiment, Double>> tokenLogProbs, Map<NuancedSentiment, Double> classProbabilities) throws IOException {
        Map<Path, NuancedSentiment> result = new HashMap<>();
        for (Path p : testSet) {
            double positive = Math.log(classProbabilities.get(NuancedSentiment.POSITIVE));
            double negative = Math.log(classProbabilities.get(NuancedSentiment.NEGATIVE));
            double neutral = Math.log(classProbabilities.get(NuancedSentiment.NEUTRAL));
            for (String word : Tokenizer.tokenize(p)) {
                if (tokenLogProbs.containsKey(word)) {
                    positive += tokenLogProbs.get(word).get(NuancedSentiment.POSITIVE);
                    negative += tokenLogProbs.get(word).get(NuancedSentiment.NEGATIVE);
                    neutral += tokenLogProbs.get(word).get(NuancedSentiment.NEUTRAL);
                }
            }
            if (neutral > positive && positive > negative) {
                result.put(p, NuancedSentiment.NEUTRAL);
            } else {
                result.put(p, (positive > negative) ? NuancedSentiment.POSITIVE : NuancedSentiment.NEGATIVE);
            }
        }
        return result;
    }

    @Override
    public double nuancedAccuracy(Map<Path, NuancedSentiment> trueSentiments, Map<Path, NuancedSentiment> predictedSentiments) {
        int correct = 0;
        for (Path p : predictedSentiments.keySet()) {
            if (trueSentiments.get(p) == predictedSentiments.get(p)) {
                correct++;
            }
        }

        return (double) correct / (double) trueSentiments.size();
    }

    @Override
    public Map<Integer, Map<Sentiment, Integer>> agreementTable(Collection<Map<Integer, Sentiment>> predictedSentiments) {
        Map<Integer, Map<Sentiment, Integer>> result = new HashMap<>();
        for (Map<Integer, Sentiment> m : predictedSentiments) {
            for (Integer i : m.keySet()) {
                Sentiment s = m.get(i);
                if (!result.containsKey(i)) {
                    result.put(i, new HashMap<Sentiment, Integer>());
                }
                if (result.get(i).containsKey(s)) {
                    result.get(i).put(s, result.get(i).get(s) + 1);
                } else {
                    result.get(i).put(s, 1);
                }
            }
        }
        return result;
    }

    @Override
    public double kappa(Map<Integer, Map<Sentiment, Integer>> agreementTable) {
        double pe = 0;
        double N = agreementTable.size();
        for (Sentiment j : new Sentiment[]{Sentiment.NEGATIVE, Sentiment.POSITIVE}) {
            double sum = 0;
            for (int i : agreementTable.keySet()) {
                double ni = 0;
                for (Sentiment jN : new Sentiment[]{Sentiment.NEGATIVE, Sentiment.POSITIVE}) {
                    if(agreementTable.get(i).containsKey(jN)) {
                        ni += agreementTable.get(i).get(jN);
                    }
                }
                if (agreementTable.get(i).containsKey(j)) {
                    double nij = agreementTable.get(i).get(j);
                    sum += nij / ni;
                }
            }
            pe += Math.pow((1 / N) * sum, 2);
        }
        double niSum = 0;
        for (int i : agreementTable.keySet()) {
            double nijSum = 0;
            double ni = 0;
            for (Sentiment j : new Sentiment[]{Sentiment.NEGATIVE, Sentiment.POSITIVE}) {
                if (agreementTable.get(i).containsKey(j)) {
                    double nij = agreementTable.get(i).get(j);
                    nijSum += nij * (nij - 1);
                    ni += nij;
                }
            }
            niSum += 1 / (ni * (ni - 1)) * nijSum;
        }
        double pa = (1 / N) * niSum;
        double k = (pa - pe) / (1 - pe);
        return k;
    }
}
