package etcss.word_extractor;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Wings
 * Date: 13-8-21
 * Time: 下午5:07
 */
public class ValueExtractor {
    private static ArrayList<String> sentences;

    public static ArrayList<String> ExtractValuesFromFile(String filePathName, String keyName, FileFormat fileFormat) throws IOException, ParserConfigurationException, SAXException {
        switch (fileFormat) {
            case JSON:
                return ExtractValuesFromFileJSON(filePathName, keyName);
            case XML:
                return ExtractValuesFromFileXML(filePathName, keyName);
            case CSV:
                return ExtractValuesFromFileXML(filePathName, keyName);
            case MS_Excel_97_03:
                return ExtractValuesFromFileXML(filePathName, keyName);
            case MS_Excel_07_13:
                return ExtractValuesFromFileXML(filePathName, keyName);
            default:
                return ExtractValuesFromFileXML(filePathName, keyName);
        }
    }

    private static ArrayList<String> ExtractValuesFromFileXML(String filePathName, String keyName) throws IOException, SAXException, ParserConfigurationException {
        InputStream inputStream = new FileInputStream(filePathName);
        XMLValueExtractorHandler handler = new XMLValueExtractorHandler();
        return handler.getData(inputStream, keyName);
    }

    private static ArrayList<String> ExtractValuesFromFileJSON(String filePathName, String keyName) {
        return null;
    }

    public static boolean ExportToFile(String filePathName, FileFormat fileFormat) {

        return false;
    }

    public ArrayList<String> getSentences() {
        return sentences;
    }

    public enum FileFormat {
        JSON,
        XML,
        CSV,
        MS_Excel_97_03,
        MS_Excel_07_13,
    }
}
