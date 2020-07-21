import model.WordCount;
import utils.FileReader;
import utils.TextAnalyzer;

import java.util.List;

/*
 * @created 19/07/2020 - 06:49
 * @project gutenberg-analysis
 * @author Varsha Varadarajan
 */
public class Main {

    private static String USER_DIRECTORY = "user.dir";
    private static String BOOK_FILENAME = "\\src\\main\\resources\\1342.txt";
    private static TextAnalyzer textAnalyzer;

    public static void main(String[] args) {
        try {
            FileReader reader = FileReader.getInstance();
            String s = reader.readFileAsString(System.getProperty(USER_DIRECTORY) + BOOK_FILENAME);

            textAnalyzer = new TextAnalyzer(s);

            System.out.println("Total number of words: " + textAnalyzer.getTotalNumberOfWords() + "\n");

            System.out.println("Total number of unique words: " + textAnalyzer.getTotalUniqueWords() + "\n");

            printWordCountList("Top 20 most frequent words", textAnalyzer.getNMostFrequentWords(20));

            printWordCountList("Top 20 unique words after removing stop words", textAnalyzer.getNMostFrequentWordsWithoutStopWords(20));

            printWordCountList("Least 20 unique words", textAnalyzer.getNLeastFrequentWords(20));

            printWordCountList("Darcy frequency map", textAnalyzer.getFrequencyOfWord("Darcy"));

            printQuoteInChapter("Till this moment I never knew myself");

            printQuoteInChapter("My good opinion once lost, is lost forever.");

            printGeneratedSentence();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printWordCountList(String s, List<WordCount> wordCountList) {
        System.out.println(s);
        for (int i = 0; i < wordCountList.size(); i++) {
            WordCount c = wordCountList.get(i);
            System.out.println(i + 1 + ". " + c.getWord() + " : " + c.getCount());
        }
        System.out.println();
    }

    private static void printQuoteInChapter(String quote) {
        System.out.println(quote);
        System.out.println("Chapter no: " + textAnalyzer.getChapterQuoteAppears(quote));
        System.out.println();
    }

    private static void printGeneratedSentence() {
        for (int i = 1; i <= 5; i ++) {
            System.out.println(textAnalyzer.generateSentence());
            System.out.println();
        }
    }
}
