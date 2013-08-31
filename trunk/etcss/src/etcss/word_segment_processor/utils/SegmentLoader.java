package etcss.word_segment_processor.utils;

import org.optimized_ictclas4j.segment.SegTag;

import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * Project Name: etcss
 * Date Created: 2013/08/31 下午2:45
 *
 * @author Wings
 */
public class SegmentLoader {
    public static SegTag LoadSegmentTagger(int segPathCount) {
        SimpleLogger.Log("Loading dictionaries, please wait . . .");
        System.out.println("(This may take a few minutes.)");
        long startTime = System.currentTimeMillis();  //计时开始

        SegTag segTag;
        try {
            segTag = new SegTag(segPathCount);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        long endTime = System.currentTimeMillis();  //计时结束
        System.out.println("All dictionaries loaded." + " (within " + (endTime - startTime) + " milliseconds.)");
        return segTag;
    }
}
