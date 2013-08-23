package etcss.word_extractor;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Wings
 * Date: 13-8-19
 * Time: 上午11:39
 */
public class PreProcessor {
    private ArrayList<String> sentencesList;

    public PreProcessor(ArrayList<String> sentences) {
        this.sentencesList = sentences;
    }

    public ArrayList<String> getSentences() {
        return sentencesList;
    }

    public void setSentences(ArrayList<String> sentences) {
        this.sentencesList = sentences;
    }

    public void process(PreProcessRule rule) {
        for (String sentence : sentencesList) {

        }
    }
}
