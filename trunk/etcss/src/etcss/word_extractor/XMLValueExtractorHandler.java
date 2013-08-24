package etcss.word_extractor;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Wings
 * Date: 13-8-21
 * Time: 下午6:29
 */

public class XMLValueExtractorHandler extends DefaultHandler {
    private ArrayList<String> dataCollection;
    private String currentData;
    private String lastNodeName;
    private String keyName;

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
        lastNodeName = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
            String content = new String(ch,start,length);
        if (this.keyName.equals(this.lastNodeName)){
            dataCollection.add(content);
        }
    }
}
