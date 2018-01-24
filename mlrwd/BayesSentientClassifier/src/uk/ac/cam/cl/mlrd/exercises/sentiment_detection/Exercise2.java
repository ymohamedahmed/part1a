package uk.ac.cam.cl.mlrd.exercises.sentiment_detection;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Exercise2 implements IExercise2 {
    @Override
    public Map<Sentiment, Double> calculateClassProbabilities(Map<Path, Sentiment> trainingSet) throws IOException {
        int negativeCount = 0;
        int positiveCount = 0;
        HashMap<Sentiment, Double> probabilities = new HashMap<>();
        for (Sentiment s : trainingSet.values()) {
            if(s == Sentiment.POSITIVE){
                positiveCount++;
            }else{
                negativeCount++;
            }
        }
        probabilities.put(Sentiment.NEGATIVE, (double) negativeCount / (double)trainingSet.size());
        probabilities.put(Sentiment.POSITIVE, (double) positiveCount / (double)trainingSet.size());
        return probabilities;
    }

    @Override
    public Map<String, Map<Sentiment, Double>> calculateUnsmoothedLogProbs(Map<Path, Sentiment> trainingSet) throws IOException {
        HashMap<String, Map<Sentiment, Double>> probabilities = new HashMap<>();
        HashMap<String, Integer> negativeCount = new HashMap<>();
        HashMap<String, Integer> positiveCount = new HashMap<>();
        int posCount = 0;
        int negCount = 0;

        int totalCount = 0;
        HashSet<String> words = new HashSet<>();

        for (Path p : trainingSet.keySet()) {
            List<String> tokenized = Tokenizer.tokenize(p);
            for(String word : tokenized){
                words.add(word);
                if(trainingSet.get(p) == Sentiment.NEGATIVE){
                    negCount++;
                    negativeCount.put(word, negativeCount.get(word) == null ? 1 : negativeCount.get(word)+1);
                    positiveCount.put(word, positiveCount.get(word) == null ? 0 : positiveCount.get(word));
                }else{
                    posCount++;
                    negativeCount.put(word, negativeCount.get(word) == null ? 0 : negativeCount.get(word));
                    positiveCount.put(word, positiveCount.get(word) == null ? 1 : positiveCount.get(word)+1);
                }
            }
        }
        for(String w : words){
            HashMap<Sentiment, Double> probability = new HashMap<>();
            probability.put(Sentiment.NEGATIVE, Math.log((double)(negativeCount.get(w))/(double)(negCount)));
            probability.put(Sentiment.POSITIVE, Math.log((double)positiveCount.get(w)/(double)posCount));
            probabilities.put(w, probability);
        }

        return probabilities;
    }

    @Override
    public Map<String, Map<Sentiment, Double>> calculateSmoothedLogProbs(Map<Path, Sentiment> trainingSet) throws IOException {
        HashMap<String, Map<Sentiment, Double>> probabilities = new HashMap<>();
        HashMap<String, Integer> negativeCount = new HashMap<>();
        HashMap<String, Integer> positiveCount = new HashMap<>();
        HashSet<String> unique = new HashSet<>();
        int negCount = 0;
        int posCount = 0;

        for (Path p : trainingSet.keySet()) {
            List<String> tokenized = Tokenizer.tokenize(p);
            for(String word : tokenized){
                if(trainingSet.get(p) == Sentiment.NEGATIVE){
                    negCount++;
                    negativeCount.put(word, negativeCount.get(word) == null ? 1 : negativeCount.get(word)+1);
                    positiveCount.put(word, positiveCount.get(word) == null ? 0 : positiveCount.get(word));
                }else{
                    posCount++;
                    negativeCount.put(word, negativeCount.get(word) == null ? 0 : negativeCount.get(word));
                    positiveCount.put(word, positiveCount.get(word) == null ? 1 : positiveCount.get(word)+1);
                }
                unique.add(word);
            }
        }
        for(String w : unique){
            HashMap<Sentiment, Double> probability = new HashMap<>();
            probability.put(Sentiment.POSITIVE, Math.log((double)(positiveCount.get(w) + 1)/(double)(posCount + unique.size())));
            probability.put(Sentiment.NEGATIVE, Math.log((double)(negativeCount.get(w) + 1)/(double)(negCount + unique.size())));
            probabilities.put(w,probability);
        }
        return probabilities;
    }

    @Override
    public Map<Path, Sentiment> naiveBayes(Set<Path> testSet, Map<String, Map<Sentiment, Double>> tokenLogProbs, Map<Sentiment, Double> classProbabilities) throws IOException {
        Map<Path, Sentiment> result = new HashMap<>();
        for(Path p : testSet){
            double positive = Math.log(classProbabilities.get(Sentiment.POSITIVE));
            double negative = Math.log(classProbabilities.get(Sentiment.NEGATIVE));
            for(String word : Tokenizer.tokenize(p)){
                if(tokenLogProbs.containsKey(word)){
                    positive +=  tokenLogProbs.get(word).get(Sentiment.POSITIVE);
                    negative +=  tokenLogProbs.get(word).get(Sentiment.NEGATIVE);
                }
           }
           result.put(p, (positive > negative) ? Sentiment.POSITIVE : Sentiment.NEGATIVE);
        }
        return result;
    }
}
