package etcss.word_extractor;

import java.util.AbstractMap;

/**
 * Created with IntelliJ IDEA.
 * User: Wings
 * Date: 13-8-22
 * Time: 下午9:09
 */
public class ReplaceRuleItem extends AbstractMap.SimpleEntry {
    public ReplaceRuleItem(Object o, Object o2) {
        super(o, o2);
        if (o == null) {
            throw new IllegalArgumentException("\"null\" key not allowed.");
        }
    }
}
