import etcss.word_segment_processor.tools.Xml2PosedWordsWorker;

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
        System.out.println(posedWords);
        //[推荐/v, 知/v, 还要/v, 好/a, 用/v, 随便/ad, 拿/v, 出/v, 应该/v, 能/v, 搜/v, 到/v, 神奇/a, 好/a, 用/v, 是/v, 做/v, 回顾/v, 可以/v, 下载/v, 精确/ad, 到/v, 推荐/v, 知/v, 还要/v, 好/a, 用/v, 随便/ad, 拿/v, 出/v, 应该/v, 能/v, 搜/v, 到/v, 神奇/a, 好/a, 用/v, 是/v, 做/v, 回顾/v, 可以/v, 下载/v, 精确/ad, 到/v]

        //todo: 关注词性：/v /a /ad.
    }
}
