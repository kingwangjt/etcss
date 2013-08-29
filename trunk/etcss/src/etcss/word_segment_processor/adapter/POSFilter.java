package etcss.word_segment_processor.adapter;

import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Project Name: etcss
 * Date Created: 2013/08/28 下午8:21
 *
 * @author Wings
 */
public class POSFilter {
    public static final String SPLITTER = " ";
    private HashSet<String> wordsSet;

    public POSFilter() {
        this.wordsSet = new HashSet<String>();
    }

    public HashSet<String> getWordsSet() {
        return wordsSet;
    }

    public void addWordsFromSentence(String segResultSentence, String[] filter) {
        String[] allWords = segResultSentence.split(SPLITTER);
        for (String word : allWords) {
            for (String filterItem : filter) {
                if (word.endsWith(filterItem)) {
                    this.wordsSet.add(word);
                }
            }
        }
    }

    /**
     * @deprecated
     */
    public void addWordsFromSentenceList(List<String> segResultSentenceList, String[] filter) {
        for (String segResultSentence : segResultSentenceList) {
            String[] allWords = segResultSentence.split(SPLITTER);
            for (String word : allWords) {
                for (String filterItem : filter) {
                    if (word.endsWith(filterItem)) {
                        this.wordsSet.add(word);
                    }
                }
            }
        }
    }

    public void clearWords() {
        this.wordsSet = new HashSet<String>();
        /**
         * I didn't call hashSet.clear() for performance reasons.
         * thanks to Uwe Plonus, see for details: http://stackoverflow.com/questions/18509734/is-it-better-to-call-create-a-new-hashset-than-to-reuse-after-hashset-clear
         */

    }
}
