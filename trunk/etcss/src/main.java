import etcss.word_segment_processor.tools.Xml2PosedWordsWorker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

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

        final String TEST_FilePath_1 = "F:\\status_test_utf-8.xml";
        final String TEST_FilePath_2 = "F:\\status_test_utf-8-2.xml";
        final String[] WANTED_POS = new String[]{"/v", "/a", "/ad"};

        ArrayList<String> fileList = new ArrayList<String>();
        fileList.add(TEST_FilePath_1);
        fileList.add(TEST_FilePath_2);

        Xml2PosedWordsWorker xml2PosedWordsWorker = new Xml2PosedWordsWorker(fileList, WANTED_POS);
        HashSet<String> posedWords = xml2PosedWordsWorker.doWork();
        BufferedWriter bufferedWriter;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter("C:\\a.csv"));
            boolean isFirstWord = true;
            for (String word : posedWords) {
                if (!isFirstWord) {
                    bufferedWriter.write(",");
                }
                bufferedWriter.write(word);
                isFirstWord=false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
