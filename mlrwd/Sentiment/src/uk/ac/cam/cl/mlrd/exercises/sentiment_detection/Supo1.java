package uk.ac.cam.cl.mlrd.exercises.sentiment_detection;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Supo1 {
    public static void main(String[] args) {
        List<String> tokenized = null;
        Path lexiconFile = Paths.get("data/sentiment_lexicon");
        try {
            tokenized = Tokenizer.tokenize(Paths.get("data/supervision/testReview"));
            Collections.sort(tokenized);
//            tokenized.forEach(System.out::println);
            Set<Path> pathSet = new HashSet<>();
            pathSet.add(Paths.get("data/supervision/testReview"));
            Exercise1 ex1 = new Exercise1();
            System.out.println(ex1.improvedClassifier(pathSet, lexiconFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
