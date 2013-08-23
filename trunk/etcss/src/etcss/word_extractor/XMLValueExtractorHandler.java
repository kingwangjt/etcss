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
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Wings
 * Date: 13-8-21
 * Time: 下午6:29
 */
public class XMLValueExtractorHandler extends DefaultHandler {
    private ArrayList<String> data;
    private String currentData;

    public ArrayList<String> getData() {
        return data;
    }

    public ArrayList<String> getData(InputStream xmlStream,String keyName) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLValueExtractorHandler handler = new XMLValueExtractorHandler();
        parser.parse(xmlStream, handler);
        return handler.getData();
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
    }
}
