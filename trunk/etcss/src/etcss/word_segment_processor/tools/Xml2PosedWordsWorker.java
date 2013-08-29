package etcss.word_segment_processor.tools;

import etcss.word_segment_processor.adapter.POSFilter;
import etcss.word_segment_processor.adapter.PreProcessor;
import etcss.word_segment_processor.bean.PreProcessRule;
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

            System.out.println("Loading dictionaries, please wait . . .");
            System.out.println("(This may take a few minutes.)");
            long startTime = System.currentTimeMillis();  //计时开始

            SegTag segTag;
            try {
                segTag = new SegTag(1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }

            long endTime = System.currentTimeMillis();  //计时结束
            System.out.println("All dictionaries loaded." + " (within " + (endTime - startTime) + " milliseconds.)");
            System.out.println("Start segment:");

            final String[] POS_WHITE_LIST = new String[]{"/v", "/a", "/ad"};

            POSFilter filteredWords = new POSFilter();
            for (String sentence : sentences) {
                SegResult seg_res = segTag.split(sentence);
                String segResult = seg_res.getFinalResult();
                filteredWords.addWordsFromSentence(segResult, POS_WHITE_LIST);
            }
            System.out.println(filteredWords);
            resultHashSet.addAll(filteredWords.getWordsSet());
            //[推荐/v, 知/v, 还要/v, 好/a, 用/v, 随便/ad, 拿/v, 出/v, 应该/v, 能/v, 搜/v, 到/v, 神奇/a, 好/a, 用/v, 是/v, 做/v, 回顾/v, 可以/v, 下载/v, 精确/ad, 到/v, 推荐/v, 知/v, 还要/v, 好/a, 用/v, 随便/ad, 拿/v, 出/v, 应该/v, 能/v, 搜/v, 到/v, 神奇/a, 好/a, 用/v, 是/v, 做/v, 回顾/v, 可以/v, 下载/v, 精确/ad, 到/v]

            //todo: 关注词性：/v /a /ad.
        }
        return resultHashSet;
    }
}
