package etcss.word_segment_processor.utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
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
    }

    /**
     * Created with IntelliJ IDEA.
     * User: Wings
     * Date: 13-8-21
     * Time: 下午6:29
     */

    private static class XMLValueExtractorHandler extends DefaultHandler {
        private ArrayList<String> dataCollection;
        private String lastNodeName;
        private String keyName;

        public XMLValueExtractorHandler() {
        }

        public ArrayList<String> getDataCollection() {
            return dataCollection;
        }

        public void setDataCollection(ArrayList<String> dataCollection) {
            this.dataCollection = dataCollection;
        }

        public String getKeyName() {
            return keyName;
        }

        public void setKeyName(String keyName) {
            this.keyName = keyName;
        }

        public ArrayList<String> getData(InputStream xmlStream, String keyName) throws ParserConfigurationException, SAXException, IOException {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLValueExtractorHandler handler = new XMLValueExtractorHandler();
            handler.setKeyName(keyName);  //这里设置handler的keyName很重要，因为handler是当前类的新实例。
            parser.parse(xmlStream, handler);
            return handler.getDataCollection();
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            dataCollection = new ArrayList<String>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            lastNodeName = qName;  //将正在解析的节点名称赋给lastNodeName
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            lastNodeName = null;  //此处必须置空，否则characters()函数判断出错
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
                String content = new String(ch,start,length);
            if (this.keyName.equals(this.lastNodeName)){
                dataCollection.add(content);
            }
        }
    }
}
