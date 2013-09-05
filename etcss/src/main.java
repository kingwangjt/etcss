import etcss.word_segment_processor.tools.Xml2PosedWordsWorker;
import etcss.word_segment_processor.utils.JavaConsoleLogger;
import etcss.word_segment_processor.utils.SimpleLogger;

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
        //svm_toy.main(new String[]{""});

        final String TEST_FilePath_1 = "F:\\status_test_utf-8.xml";
        final String TEST_FilePath_2 = "F:\\status_test_utf-8-2.xml";
        final String[] WANTED_POS = new String[]{"/v", "/a", "/ad"};

        ArrayList<String> fileList = new ArrayList<String>();
        fileList.add(TEST_FilePath_1);
        fileList.add(TEST_FilePath_2);

        JavaConsoleLogger.SetMuteLevel(SimpleLogger.MuteLevel.Log);
        Xml2PosedWordsWorker xml2PosedWordsWorker = new Xml2PosedWordsWorker(fileList, WANTED_POS);
        HashSet<String> posedWords = xml2PosedWordsWorker.getWords(true);
        JavaConsoleLogger.Log("Logging " + posedWords.size() + " posed words entries . . ."
                , JavaConsoleLogger.MessagePriorityLevel.Log);
        BufferedWriter bufferedWriter;
        final String LOG_FILE_PATH = "C:\\a.csv";
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(LOG_FILE_PATH));
            boolean isFirstWord = true;
            for (String word : posedWords) {
                if (!isFirstWord) {
                    bufferedWriter.write(",");
                    bufferedWriter.newLine();
                }
                bufferedWriter.write(word);
                isFirstWord = false;
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaConsoleLogger.Log("Logging complete, " + posedWords.size() + " words successfully logged."
                , JavaConsoleLogger.MessagePriorityLevel.Log);
        JavaConsoleLogger.Log("Log file: " + LOG_FILE_PATH, JavaConsoleLogger.MessagePriorityLevel.Log);
    }
}
