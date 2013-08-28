package etcss.word_segment_processor.utils;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * Project Name: etcss
 * Date Created: 2013/08/28 下午8:21
 *
 * @author Wings
 */
public class POSFilter {
    public static final String SPLITTER = " ";

    public static ArrayList<String> FilterWordsFromSentence(String segResultSentence, String[] filter) {
        String[] allWords = segResultSentence.split(SPLITTER);
        ArrayList<String> resultWords = new ArrayList<String>();
        for (String word : allWords) {
            for (String filterItem : filter) {
                if (word.endsWith(filterItem)){
                    resultWords.add(word);
                }
            }
            System.out.println(word);
        }
        return resultWords;
    }
}
