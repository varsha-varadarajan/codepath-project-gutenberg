/*
 * @created 19/07/2020 - 06:52
 * @project gutenberg-analysis
 * @author Varsha Varadarajan
 */

package utils;

import model.WordCount;

import java.util.*;

public class TextAnalyzer implements ITextAnalyzer {
    private String data;
    private HashSet<String> stopWords = new HashSet<>();

    public TextAnalyzer(String st) {
        if (st == null) {
            throw new IllegalArgumentException();
        }
        data = st;
        prepareData();
        processStopWords();
    }

    @Override
    public int getTotalNumberOfWords() {
        return getTotalNumberOfWords(" ");
    }

    @Override
    public int getTotalUniqueWords() {
        HashSet<String> set = new HashSet<>();
        for (String s: getWords(" ")) {
            set.add(s);
        }
        return set.size();
    }

    @Override
    public List<WordCount> getNMostFrequentWords(int n) {
        return getNMostFrequentWords(n, false, false);
    }

    @Override
    public List<WordCount> getNMostFrequentWordsWithoutStopWords(int n) {
        return getNMostFrequentWords(n, true, false);
    }

    @Override
    public List<WordCount> getNLeastFrequentWords(int n) {
        return  getNMostFrequentWords(n, false, true);
    }

    @Override
    public List<WordCount> getFrequencyOfWord(String word) {
        List<WordCount> result = new ArrayList<>();
        String[] chapters = getWords("chapter");
        for (int i = 1; i < chapters.length; i++) {
            result.add(getChapterWordCount("Chapter " + String.valueOf(i), chapters[i], word));
        }
        return result;
    }

    @Override
    public int getChapterQuoteAppears(String quote) {
        quote = prepareData(quote);
        String[] chapters = getWords("chapter");
        for (int i = 1; i < chapters.length; i++) {
            if (isQuotePresent(chapters[i], quote)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String generateSentence() {
        StringBuilder sb = new StringBuilder();
        String seed = "the";
        for (int i = 1; i <= 19; i++) {
            sb.append(seed + " ");
            seed = getNextRandomWordAfter(seed);
        }
        sb.append(seed);
        return sb.toString();
    }

    private String getNextRandomWordAfter(String word) {
        List<String> nextWords = new ArrayList<>();
        String[] chunks = getWords(data, " ");
        for (int i = 0; i < chunks.length - 1; i++) {
            if (chunks[i].equals(word)) {
                nextWords.add(chunks[i+1]);
            }
        }
        return nextWords.get((int)(Math.random()*nextWords.size()));
    }

    private boolean isQuotePresent(String chapterText, String quote) {
        if (chapterText.contains(quote)) {
            return true;
        }
        return false;
    }

    private WordCount getChapterWordCount(String chapterNo, String chapterText, String word) {
        int count = 0;
        for (String s: getWords(chapterText, " ")) {
            if(s.equals(word.toLowerCase())) {
                count++;
            }
        }
        return new WordCount(chapterNo, count);
    }

    private List<WordCount> getNMostFrequentWords(int n, boolean ignoreStopWords, boolean least) {
        List<WordCount> topNWordList = new ArrayList<>();
        PriorityQueue<Map.Entry<String, Integer>> topNWords;
        if (least) {
            topNWords = new PriorityQueue<Map.Entry<String, Integer>>((a,b) ->
                    a.getValue() - b.getValue()
            );
        } else {
            topNWords = new PriorityQueue<Map.Entry<String, Integer>>((a,b) ->
                    b.getValue() - a.getValue()
            );
        }

        HashMap<String, Integer> map = new HashMap<>();
        for (String s: getWords(" ")) {
            if (ignoreStopWords) {
                if (!stopWords.contains(s)) {
                    map.put(s, map.getOrDefault(s, 0) + 1);
                }
            } else {
                map.put(s, map.getOrDefault(s, 0) + 1);
            }
        }
        for(Map.Entry<String, Integer> entry: map.entrySet()) {
            topNWords.offer(entry);
        }
        for (int i = 0; i < n; i++) {
            Map.Entry<String, Integer> entry = topNWords.poll();
            topNWordList.add(new WordCount(entry.getKey(), entry.getValue()));
        }
        return topNWordList;
    }

    public void processStopWords() {
        FileReader reader = FileReader.getInstance();
        String currDirectory = System.getProperty("user.dir");
        try {
            String s = reader.readFileAsString(currDirectory + "\\src\\main\\resources\\stopwords.txt");
            s = prepareData(s);
            processStopWords(s);
        } catch (Exception e) {
            System.out.println("Error in reading stopwords file");
        }
    }

    private void processStopWords(String s) {
        for (String st: getWords(s, " ")) {
            stopWords.add(st);
        }
    }

    private int getTotalNumberOfWords(String delimiter) {
        try {
            return getWords(delimiter).length;
        } catch(IllegalArgumentException e) {
            System.out.println("Empty string error" + e.getMessage());
        }
        return -1;
    }

    public String[] getWords(String delimiter) {
        return data.split(delimiter);
    }

    private String[] getWords(String s, String delimiter) {
        return s.split(delimiter);
    }

    private void prepareData() {
        removeLineBreaks();
        convertToLowerCase();
        removePunctuation();
        removeSpaces();
        // convertToLowerCase();
    }

    private String prepareData(String s) {
        s = removeLineBreaks(s);
        s = convertToLowerCase(s);
        s = removePunctuation(s);
        s = removeSpaces(s);
        return s;
    }

    private void convertToLowerCase() {
        data = data.toLowerCase();
    }

    private String convertToLowerCase(String s) {
        return s.toLowerCase();
    }

    private void removePunctuation() {
        data = data.replaceAll("\\p{Punct}", "");
    }

    private String removePunctuation(String s) {
        return s.replaceAll("\\p{Punct}", "");
    }

    private void removeSpaces() {
        data = data.replaceAll("\\s+", " ");
    }

    private String removeSpaces(String s) {
        return s.replaceAll("\\s+", " ");
    }

    private void removeLineBreaks() {
        data = data.replaceAll("\\r\\n|\\r|\\n", " ");
    }

    private String removeLineBreaks(String s) {
        return s.replaceAll("\\r\\n|\\r|\\n", " ");
    }
}
