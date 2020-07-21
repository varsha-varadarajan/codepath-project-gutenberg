/*
 * @created 19/07/2020 - 07:18
 * @project gutenberg-analysis
 * @author Varsha Varadarajan
 */
package utils;
import model.WordCount;

import java.util.List;

public interface ITextAnalyzer {

    int getTotalNumberOfWords();

    int getTotalUniqueWords();

    List<WordCount> getNMostFrequentWords(int n);

    List<WordCount> getNMostFrequentWordsWithoutStopWords(int n);

    List<WordCount> getNLeastFrequentWords(int n);

    List<WordCount> getFrequencyOfWord(String s);

    int getChapterQuoteAppears(String s);

    String generateSentence();
}
