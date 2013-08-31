package etcss.word_segment_processor.tools;

import etcss.word_segment_processor.adapter.POSFilter;
import etcss.word_segment_processor.adapter.PreProcessor;
import etcss.word_segment_processor.bean.PreProcessRule;
import etcss.word_segment_processor.utils.SegmentLoader;
import etcss.word_segment_processor.utils.ValueExtractor;
import org.optimized_ictclas4j.bean.SegResult;
import org.optimized_ictclas4j.segment.SegTag;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * Project Name: etcss
 * Date Created: 2013/08/29 上午11:44
 *
 * @author Wings
 */
public class Xml2PosedWordsWorker implements Workable {
    private static final String RENREN_MESSAGE_KEY_NAME = "message";
    private ArrayList<String> xmlPathCollection;
    private String[] wantedPOS;

    public Xml2PosedWordsWorker(ArrayList<String> xmlPathCollection, String[] wantedPOS) {
        this.xmlPathCollection = xmlPathCollection;
        this.wantedPOS = wantedPOS;
    }

    public ArrayList<String> getXmlPathCollection() {
        return xmlPathCollection;
    }

    public void setXmlPathCollection(ArrayList<String> xmlPathCollection) {
        this.xmlPathCollection = xmlPathCollection;
    }

    public String[] getWantedPOS() {
        return wantedPOS;
    }

    public void setWantedPOS(String[] wantedPOS) {
        this.wantedPOS = wantedPOS;
    }

    @Override
    public HashSet<String> doWork() {
        HashSet<String> resultHashSet = new HashSet<String>();

        SegTag segTag= SegmentLoader.LoadSegmentTagger(1);
        for (String path : xmlPathCollection) {
            ArrayList<String> sentences;
            try {
                //获得单个xml中所有状态
                sentences = ValueExtractor.ExtractValuesFromFile(path, RENREN_MESSAGE_KEY_NAME, ValueExtractor.FileFormat.XML);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                return null;
            } catch (SAXException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            PreProcessor preProcessor = new PreProcessor(sentences);
            preProcessor.process(PreProcessRule.RenrenRule);

            final String[] POS_WHITE_LIST = new String[]{"/v", "/a", "/ad"};

            POSFilter filteredWords = new POSFilter();
            for (String sentence : sentences) {
                SegResult seg_res = segTag.split(sentence);
                String segResult = seg_res.getFinalResult();
                filteredWords.addWordsFromSentence(segResult, POS_WHITE_LIST);
            }
            resultHashSet.addAll(filteredWords.getWordsSet());
        }
        return resultHashSet;
    }
}
