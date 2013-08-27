import etcss.word_extractor.ValueExtractor;
import etcss.word_extractor.PreProcessRule;
import etcss.word_extractor.PreProcessor;
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
        ////svm_toy.main(new String[]{""});

        final String MESSAGE_KEY_NAME="message";
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

    }
}
