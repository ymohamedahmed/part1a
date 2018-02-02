package uk.ac.cam.cl.mlrd.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Zipf {
    private List<BestFit.Point> points;
    private ArrayList<Word> words;
    private HashMap<String, Integer> ranks = new HashMap<>();
    private HashMap<String, Integer> mWords = new HashMap<>();
    private String[] testWords = {"horrific", "genuine", "emotional", "good", "bad", "tragic", "annoying", "frustrating", "boring", "complex"};
    private BestFit.Line l;

    public static void main(String[] args) {
        Zipf zipf = new Zipf();
        zipf.loadIntoMap();
        zipf.plotFreqRank();
        zipf.plotLog();
        for (String s : zipf.testWords) {
            System.out.println("Word: " + s + ", Actual Frequency: " + zipf.mWords.get(s) + ", Predicted Frequency: " + zipf.predict(s));
        }
        System.out.println("Alpha predicted: " + zipf.predictAlpha());
        System.out.println("K predicted " + zipf.predictK());
//        zipf.heaps();
    }

    public void loadIntoMap() {
        try (Stream<Path> paths = Files.walk(Paths.get("dataset/large_dataset"))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(p -> {
                        try {
                            Tokenizer.tokenize(p).forEach(w -> add(w));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(String w) {
        if (mWords.containsKey(w)) {
            mWords.put(w, mWords.get(w) + 1);
        } else {
            mWords.put(w, 1);
        }
    }

    public void plotFreqRank() {
        ArrayList<Word> words = new ArrayList<>();
        for (String w : mWords.keySet()) {
            words.add(new Word(w, mWords.get(w)));
        }

        Collections.sort(words);
//        List<Integer> freqs = new ArrayList<Integer>(mWords.values());
//        freqs.sort((Integer i1, Integer i2)->i2-i1);
//        List<Integer> topFreqs = freqs.subList(0, 10000);
        List<BestFit.Point> points = new ArrayList<BestFit.Point>();
        for (int i = 0; i < 10000; i++) {
            points.add(new BestFit.Point(i, words.get(i).getFreq()));
        }

        List<BestFit.Point> testPoints = new ArrayList<>();

        ArrayList<String> listTestWords = new ArrayList<String>(Arrays.asList(testWords));
        for (Word w : words) {
            if (listTestWords.contains(w.getWord())) {
                testPoints.add(new BestFit.Point(words.indexOf(w), w.getFreq()));
            }
        }
        ChartPlotter.plotLines(points, testPoints);
    }

    public void plotLog() {
        words = new ArrayList<>();
        for (String w : mWords.keySet()) {
            words.add(new Word(w, mWords.get(w)));
        }

        Collections.sort(words);
        for (Word w : words) {
            ranks.put(w.getWord(), words.indexOf(w));
        }
        points = new ArrayList<BestFit.Point>();
        Map<BestFit.Point, Double> bestFit = new HashMap<BestFit.Point, Double>();
        for (int i = 0; i < 10000; i++) {
            points.add(new BestFit.Point(Math.log(i + 1), Math.log(words.get(i).getFreq())));
            bestFit.put(points.get(i), (double)words.get(i).getFreq());
        }
        l = BestFit.leastSquares(bestFit);
        List<BestFit.Point> linePoints = new ArrayList<>();
        linePoints.add(new BestFit.Point(0, l.yIntercept + l.gradient));
        linePoints.add(new BestFit.Point(Math.log(10000), l.yIntercept + (Math.log(10000) * l.gradient)));
        ChartPlotter.plotLines(points, linePoints);
    }

    public double predict(String w) {
        int rank = ranks.get(w);
        return Math.exp(l.gradient * Math.log(rank) + l.yIntercept);
    }

    public double predictK() {
        return Math.exp(l.yIntercept);
    }

    public double predictAlpha() {
        return -l.gradient;
    }

    public void heaps()  {
        HashSet<String> wordHashSet = new HashSet<>();
        int i = 1;
        Stream<Path> paths = null;
        try {
            paths = Files.walk(Paths.get("dataset/large_dataset")).filter(Files::isRegularFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<BestFit.Point> heapPoints = new ArrayList<>();
        Iterator<Path> iter = paths.iterator();
        while(iter.hasNext()){
            try {
                for(String word : Tokenizer.tokenize(iter.next())){
                    wordHashSet.add(word);
                    if((i&-i)==i){
//                        System.out.println(i);
                        System.out.println(Math.log(i));
//                        System.out.println(Math.log(words.size()));
//                        System.out.println();
                        heapPoints.add(new BestFit.Point(Math.log(i), Math.log(wordHashSet.size())));
                    }
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        heapPoints.add(new BestFit.Point(Math.log(i),Math.log(wordHashSet.size())));
        ChartPlotter.plotLines(heapPoints);
    }

    class Word implements Comparable<Word> {
        private String mWord;
        private int mFreq;

        public Word(String word, int freq) {
            mWord = word;
            mFreq = freq;
        }

        public int getFreq() {
            return mFreq;
        }

        public String getWord() {
            return mWord;
        }

        @Override
        public int compareTo(Word o) {
            return o.getFreq() - mFreq;
        }
    }

}
