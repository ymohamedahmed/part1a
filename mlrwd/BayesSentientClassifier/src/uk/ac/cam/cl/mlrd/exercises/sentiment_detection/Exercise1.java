package uk.ac.cam.cl.mlrd.exercises.sentiment_detection;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.IExercise1;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.Sentiment;
import uk.ac.cam.cl.mlrd.exercises.sentiment_detection.Tokenizer;

public class Exercise1 implements IExercise1 {

    @Override
    public Map<Path, Sentiment> simpleClassifier(Set<Path> testSet, Path lexiconFile) throws IOException {
        HashMap<Path, List<String>> tokenized = new HashMap<>();
        HashMap<String, Sentiment> lexicon = new HashMap<>();
        List<String> tokenizedLexicon = Tokenizer.tokenize(lexiconFile);
        Map<Path, Sentiment> result = new HashMap<>();

        for (Path p : testSet) {
            tokenized.put(p, Tokenizer.tokenize(p));
        }
        for (int i = 0; i < tokenizedLexicon.size() - 9; i += 9) {
            lexicon.put(tokenizedLexicon.get(i + 2),
                    (tokenizedLexicon.get(i + 8).equals("negative")) ? Sentiment.NEGATIVE : Sentiment.POSITIVE);
        }
        for (Path path : testSet) {
            int score = 0;
            for (String word : tokenized.get(path)) {
                if (lexicon.get(word) == Sentiment.POSITIVE) {
                    score++;
                } else if (lexicon.get(word) == Sentiment.NEGATIVE) {
                    score--;
                }
            }
            result.put(path, score >= 0 ? Sentiment.POSITIVE : Sentiment.NEGATIVE);
        }

        return result;
    }

    @Override
    public double calculateAccuracy(Map<Path, Sentiment> trueSentiments, Map<Path, Sentiment> predictedSentiments) {
        int correct = 0;
        for (Path p : predictedSentiments.keySet()) {
            if (trueSentiments.get(p) == predictedSentiments.get(p)) {
                correct++;
            }
        }

        return (double) correct / (double) trueSentiments.size();
    }

    @Override
    public Map<Path, Sentiment> improvedClassifier(Set<Path> testSet, Path lexiconFile) throws IOException {
        // TODO Auto-generated method stub
        HashMap<Path, List<String>> tokenized = new HashMap<>();
        HashMap<String, Sentiment> lexicon = new HashMap<>();
        HashMap<String, Intensity> intensity = new HashMap<>();

        List<String> tokenizedLexicon = Tokenizer.tokenize(lexiconFile);
        Map<Path, Sentiment> result = new HashMap<>();

        for (Path p : testSet) {
            tokenized.put(p, Tokenizer.tokenize(p));
        }
        for (int i = 0; i < tokenizedLexicon.size() - 9; i += 9) {
            lexicon.put(tokenizedLexicon.get(i + 2),
                    (tokenizedLexicon.get(i + 8).equals("negative")) ? Sentiment.NEGATIVE : Sentiment.POSITIVE);
            intensity.put(tokenizedLexicon.get(i + 2),
                    (tokenizedLexicon.get(i + 5).equals("strong"))? Intensity.STRONG : Intensity.WEAK);
        }
        for (Path path : testSet) {
            int score = 0;
            for (String word : tokenized.get(path)) {
                int factor = (intensity.get(word) == Intensity.STRONG ? 10 : 1);
                if (lexicon.get(word) == Sentiment.POSITIVE) {
                    score = factor + score;
                } else if (lexicon.get(word) == Sentiment.NEGATIVE) {
                    score = score - factor;
                }
            }
            result.put(path, score > 0 ? Sentiment.POSITIVE : Sentiment.NEGATIVE);
        }
        return result;
    }

    enum Intensity {
        STRONG, WEAK;
    }

}

