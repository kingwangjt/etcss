package etcss.word_extractor;

import java.util.AbstractMap;

/**
 * Created with IntelliJ IDEA.
 * User: Wings
 * Date: 13-8-22
 * Time: 下午9:09
 */
public class ReplaceRuleItem extends AbstractMap.SimpleEntry<String,String> {
    public ReplaceRuleItem(String s, String s2) {
        super(s, s2);
        if (s == null) {
            throw new IllegalArgumentException("\"null\" key not allowed.");
        }
    }
}
