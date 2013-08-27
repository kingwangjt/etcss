package org.optimized_ictclas4j.segment;

import org.optimized_ictclas4j.bean.Sentence;
import org.optimized_ictclas4j.utility.GFString;
import org.optimized_ictclas4j.utility.Utility;

import java.util.ArrayList;


public class SentenceSeg {
    private String src;
    private ArrayList<Sentence> sens;

    public SentenceSeg(String src) {
        this.src = src;
        sens = split();
    }

    /**
     * 进行句子分隔
     *
     * @param
     * @return
     */
    private ArrayList<Sentence> split() {
        ArrayList<Sentence> result = null;

        if (src != null) {
            result = new ArrayList<Sentence>();
            String s1 = Utility.SENTENCE_BEGIN;
            String[] ss = GFString.atomSplit(src);
//todo: optimize, change += to sb.append();
            for (String s : ss) {
                // 如果是分隔符，比如回车换行/逗号等
                if (Utility.SEPERATOR_C_SENTENCE.contains(s)
                        || Utility.SEPERATOR_LINK.contains(s)
                        || Utility.SEPERATOR_C_SUB_SENTENCE.contains(s)
                        || Utility.SEPERATOR_E_SUB_SENTENCE.contains(s)) {
                    // 如果不是回车换行和空格
                    if (!Utility.SEPERATOR_LINK.contains(s))
                        s1 += s;
                    // 断句
                    if (s1.length() > 0 && !Utility.SENTENCE_BEGIN.equals(s1)) {
                        if (!Utility.SEPERATOR_C_SUB_SENTENCE.contains(s)
                                && !Utility.SEPERATOR_E_SUB_SENTENCE.contains(s))
                            s1 += Utility.SENTENCE_END;

                        result.add(new Sentence(s1, true));
                        s1 = "";
                    }

                    // 是回车换行符或空格，则不需要进行分析处理
                    if (Utility.SEPERATOR_LINK.contains(s)) {
                        result.add(new Sentence(s));
                        s1 = Utility.SENTENCE_BEGIN;

                    } else if (Utility.SEPERATOR_C_SENTENCE.contains(s)
                            || Utility.SEPERATOR_E_SENTENCE.contains(s))
                        s1 = Utility.SENTENCE_BEGIN;
                    else s1 = Utility.SENTENCE_BEGIN;
//						s1 = ss[i];

                } else
                    s1 += s;
            }

            if (s1.length() > 0 && !Utility.SENTENCE_BEGIN.equals(s1)) {
                s1 += Utility.SENTENCE_END;
                result.add(new Sentence(s1, true));
            }
        }
        return result;
    }

    public ArrayList<Sentence> getSens() {
        return sens;
    }


}
