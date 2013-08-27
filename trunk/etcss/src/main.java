import etcss.word_extractor.PreProcessRule;
import etcss.word_extractor.PreProcessor;
import etcss.word_extractor.ValueExtractor;
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
        String TEST_FilePath = "F:\\status_test_utf-8.xml";
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

        for (String sentence : sentences) {
            SegResult seg_res = segTag.split(sentence);
            System.out.println(seg_res.getFinalResult());
        }

    }
}
