package uk.ac.cam.cl.mlrd.exercises.sentiment_detection;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Exercise4 implements IExercise4 {
    @Override
    public Map<Path, Sentiment> magnitudeClassifier(Set<Path> testSet, Path lexiconFile) throws IOException {
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
                    (tokenizedLexicon.get(i + 5).equals("strong")) ? Intensity.STRONG : Intensity.WEAK);
        }
        for (Path path : testSet) {
            int score = 0;
            for (String word : tokenized.get(path)) {
                int factor = (intensity.get(word) == Intensity.STRONG ? 2 : 1);
                if (lexicon.get(word) == Sentiment.POSITIVE) {
                    score = factor + score;
                } else if (lexicon.get(word) == Sentiment.NEGATIVE) {
                    score = score - factor;
                }
            }
            result.put(path, score >= 0 ? Sentiment.POSITIVE : Sentiment.NEGATIVE);
        }
        return result;
    }

    @Override
    public double signTest(Map<Path, Sentiment> actualSentiments, Map<Path, Sentiment> classificationA, Map<Path, Sentiment> classificationB) {

        int plus = 0;
        int noNull = 0;
        int minus = 0;
        for (Path p : actualSentiments.keySet()) {
            Sentiment actual = actualSentiments.get(p);
            Sentiment sentA = classificationA.get(p);
            Sentiment sentB = classificationB.get(p);
            if (sentA == sentB) {
                noNull++;
            } else if (sentA == actual) {
                plus++;
            } else {
                minus++;
            }
        }
        int n = (int) (2*Math.ceil(noNull/2)+plus+minus);
        int k = (int) Math.ceil(noNull/2)+Math.min(plus,minus);
        double p = 0;
        for (int i = 0; i <= k; i++) {
            p+=new BigDecimal(choose(n, i)).multiply(new BigDecimal(Math.pow(0.5, i))).multiply(new BigDecimal(Math.pow(0.5, n- i))).doubleValue();
        }
        return 2*p;
    }

    private BigInteger choose(int p, int r) {
        return factorial(p).divide(factorial(r).multiply(factorial(p - r)));
    }

    private BigInteger factorial(int x) {
        BigInteger result = BigInteger.ONE;
        for (int i = 1; i <= x; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
    enum Intensity{
        STRONG, WEAK
    }
}
