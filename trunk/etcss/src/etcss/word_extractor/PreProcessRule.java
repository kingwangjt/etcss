package etcss.word_extractor;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Wings
 * Date: 13-8-19
 * Time: 下午12:20
 */
public class PreProcessRule {
    public static final PreProcessRule RenrenRule = initRenrenRule();  //提供人人网的预处理规则
    public static final PreProcessRule WeiboRule = initWeiboRule();  //提供新浪微博的预处理规则
    private ArrayList<ReplaceRuleItem> ReplaceRule;

    private static final PreProcessRule initRenrenRule() {
        PreProcessRule renrenRule = new PreProcessRule();

        //设定替换规则
        ArrayList<ReplaceRuleItem> renrenReplaceRule = new ArrayList<ReplaceRuleItem>();
        //去除转发与点名等
        renrenReplaceRule.add(new ReplaceRuleItem("转自.*?:", ""));
        renrenReplaceRule.add(new ReplaceRuleItem("@.*? ", ""));
        //表情符号替换为汉字表示。
        renrenReplaceRule.add(new ReplaceRuleItem("\\(kb\\)", "(抠鼻)"));
        renrenReplaceRule.add(new ReplaceRuleItem("\\(sx\\)", "(烧香)"));
        renrenReplaceRule.add(new ReplaceRuleItem("\\(ju\\)", "(人人聚焦)"));
        renrenReplaceRule.add(new ReplaceRuleItem("\\(gl\\)", "(给力)"));
        renrenReplaceRule.add(new ReplaceRuleItem("\\(yl\\)", "(压力)"));
        renrenReplaceRule.add(new ReplaceRuleItem("\\(cold\\)", "(寒)"));
        renrenReplaceRule.add(new ReplaceRuleItem("\\(sbq\\)", "(伤不起)"));
        renrenReplaceRule.add(new ReplaceRuleItem("\\(th\\)", "(惊叹)"));
        renrenReplaceRule.add(new ReplaceRuleItem("\\(mb\\)", "(膜拜)"));
        renrenReplaceRule.add(new ReplaceRuleItem("\\(tucao\\)", "(吐槽)"));
        renrenReplaceRule.add(new ReplaceRuleItem("\\(zy\\)", "(同意)"));
        renrenReplaceRule.add(new ReplaceRuleItem("\\(good\\)", "(赞同)"));

        //todo:可能添加其他预处理规则，目前只包含字符串置换规则

        renrenRule.setReplaceRule(renrenReplaceRule);  //设定预处理规则中的置换规则
        return renrenRule;
    }

    private static final PreProcessRule initWeiboRule() {
        PreProcessRule weiboRule = new PreProcessRule();

        //设定替换规则
        ArrayList<ReplaceRuleItem> weiboReplaceRule = new ArrayList<ReplaceRuleItem>();
        //todo:

        //todo:可能添加其他预处理规则，目前只包含字符串置换规则

        weiboRule.setReplaceRule(weiboReplaceRule);  //设定预处理规则中的置换规则
        return weiboRule;
    }

    public ArrayList<ReplaceRuleItem> getReplaceRule() {
        return ReplaceRule;
    }

    public void setReplaceRule(ArrayList<ReplaceRuleItem> replaceRule) {
        ReplaceRule = replaceRule;
    }
}
