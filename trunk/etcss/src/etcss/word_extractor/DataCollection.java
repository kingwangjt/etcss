package etcss.word_extractor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Wings
 * Date: 13-8-21
 * Time: 下午5:07
 */
public class DataCollection {
    private static ArrayList<String> sentences;

    public static boolean ImportFromFile(String filePathName, FileFormat fileFormat) throws FileNotFoundException {
        switch (fileFormat) {
            case JSON:
                return ImportFromFileJSON(filePathName);
            case XML:
                return ImportFromFileXML(filePathName);
            case CSV:
                break;
            case MS_Excel_97_03:
                break;
            case MS_Excel_07_13:
                break;
            default:
                return ImportFromFileXML(filePathName);
        }
        return false;
    }

    private static boolean ImportFromFileXML(String filePathName) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(filePathName);
        XMLValueExtractorHandler handler = new XMLValueExtractorHandler();
        //sentences

        return false;
    }

    private static boolean ImportFromFileJSON(String filePathName) {
        return false;
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
