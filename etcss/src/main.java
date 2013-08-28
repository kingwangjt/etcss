import etcss.word_segment_processor.bean.PreProcessRule;
import etcss.word_segment_processor.adapter.PreProcessor;
import etcss.word_segment_processor.utils.POSFilter;
import etcss.word_segment_processor.utils.ValueExtractor;
import org.optimized_ictclas4j.bean.SegResult;
import org.optimized_ictclas4j.segment.SegTag;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Wings
 * Date: 13-7-29
 * Time: 下午10:19
 */
public class main {
    public static void main(String[] args) {
        System.out.println("hello world.");
        //svm_toy.main(new String[]{""});

        final String MESSAGE_KEY_NAME = "message";
        String TEST_FilePath = "F:\\status_test_utf-8-2.xml";
        ArrayList<String> sentences;
        try {
            //获得单个xml中所有状态
            sentences = ValueExtractor.ExtractValuesFromFile(TEST_FilePath, MESSAGE_KEY_NAME, ValueExtractor.FileFormat.XML);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return;
        } catch (SAXException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        PreProcessor preProcessor = new PreProcessor(sentences);
        preProcessor.process(PreProcessRule.RenrenRule);

        System.out.println("Loading dictionaries, please wait . . .");
        System.out.println("(This may take a few minutes.)");
        long startTime = System.currentTimeMillis();  //计时开始

        SegTag segTag;
        try {
            segTag = new SegTag(1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        long endTime = System.currentTimeMillis();  //计时结束
        System.out.println("All dictionaries loaded." + " (within " + (endTime - startTime) + " milliseconds.)");

        System.out.println("Start segment:");

        final String[] POS_FILTER=new String[]{"/v","/a","/ad"};
        for (String sentence : sentences) {
            SegResult seg_res = segTag.split(sentence);
            String segResult=seg_res.getFinalResult();
            ArrayList<String> filteredWords = POSFilter.FilterWordsFromSentence(segResult, POS_FILTER);
            System.out.println(filteredWords);
        }

        //[推荐/v, 知/v, 还要/v, 好/a, 用/v, 随便/ad, 拿/v, 出/v, 应该/v, 能/v, 搜/v, 到/v, 神奇/a, 好/a, 用/v, 是/v, 做/v, 回顾/v, 可以/v, 下载/v, 精确/ad, 到/v, 推荐/v, 知/v, 还要/v, 好/a, 用/v, 随便/ad, 拿/v, 出/v, 应该/v, 能/v, 搜/v, 到/v, 神奇/a, 好/a, 用/v, 是/v, 做/v, 回顾/v, 可以/v, 下载/v, 精确/ad, 到/v]

        //todo: 关注词性：/v /a /ad.
    }
}
