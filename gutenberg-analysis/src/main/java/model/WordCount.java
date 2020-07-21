/*
 * @created 19/07/2020 - 09:31
 * @project gutenberg-analysis
 * @author Varsha Varadarajan
 */

package model;

public class WordCount {
    private String word;
    private  int count;

    public String getWord() {
        return this.word;
    }

    public int getCount() {
        return this.count;
    }

    public WordCount(String s, int count) {
        this.word = s;
        this.count = count;
    }
}
