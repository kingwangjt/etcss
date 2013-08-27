package org.optimized_ictclas4j.segment;

import org.optimized_ictclas4j.bean.*;
import org.optimized_ictclas4j.utility.FinalPathConfig;
import org.optimized_ictclas4j.utility.POSTag;
import org.optimized_ictclas4j.utility.Utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class SegTag {
    private Dictionary coreDict;
    private Dictionary bigRamDict;
    private PosTagger personTagger;
    private PosTagger transPersonTagger;
    private PosTagger placeTagger;
    private PosTagger lexTagger;
    private int segPathCount = 1;  //分词路径的数目

    public SegTag(int segPathCount) throws FileNotFoundException {
        this(segPathCount,
                new FileInputStream(FinalPathConfig.getConfigInstance().coreDictIn),
                new FileInputStream(FinalPathConfig.getConfigInstance().bigRamDictIn),
                new FileInputStream(FinalPathConfig.getConfigInstance().personTaggerDctIn),
                new FileInputStream(FinalPathConfig.getConfigInstance().personTaggerCtxIn),
                new FileInputStream(FinalPathConfig.getConfigInstance().transPersonTaggerDctIn),
                new FileInputStream(FinalPathConfig.getConfigInstance().transPersonTaggerCtxIn),
                new FileInputStream(FinalPathConfig.getConfigInstance().placeTaggerDctIn),
                new FileInputStream(FinalPathConfig.getConfigInstance().placeTaggerCtxIn),
                new FileInputStream(FinalPathConfig.getConfigInstance().lexTaggerCtxIn));
    }

    public SegTag(int segPathCount, InputStream coreDictIn, InputStream bigramDictIn,
                  InputStream personTaggerDctIn, InputStream personTaggerCtxIn,
                  InputStream transPersonTaggerDctIn, InputStream transPersonTaggerCtxIn,
                  InputStream placeTaggerDctIn, InputStream placeTaggerCtxIn, InputStream lexTaggerCtxIn) {
        this.segPathCount = segPathCount;
        this.coreDict = new Dictionary();
        this.coreDict.load(coreDictIn, false);
        //this.coreDict.addEntryForOnce("天津大学",POSTag.NOUN_ORG,300);
        this.bigRamDict = new Dictionary();
        this.bigRamDict.load(bigramDictIn, false);
        this.personTagger = new PosTagger(Utility.TAG_TYPE.TT_PERSON, personTaggerDctIn, personTaggerCtxIn, coreDict);
        this.transPersonTagger = new PosTagger(Utility.TAG_TYPE.TT_TRANS_PERSON, transPersonTaggerDctIn,
                transPersonTaggerCtxIn, coreDict);
        this.placeTagger = new PosTagger(Utility.TAG_TYPE.TT_TRANS_PERSON, placeTaggerDctIn, placeTaggerCtxIn, coreDict);
        this.lexTagger = new PosTagger(Utility.TAG_TYPE.TT_NORMAL, null, lexTaggerCtxIn, coreDict);
    }

    private static void printErr(String str) {
        //For debug only.
        //System.err.println(str);
    }

    public SegResult split(String src) {
        SegResult sr = new SegResult(src);  // 分词结果
        String finalResult;

        if (src != null) {
            finalResult = "";
            int index = 0;
            String midResult = null;
            SentenceSeg ss = new SentenceSeg(src);
            ArrayList<Sentence> sens = ss.getSens();

            for (Sentence sen : sens) {
                long start = System.currentTimeMillis();
                MidResult mr = new MidResult();
                mr.setIndex(index++);
                mr.setSource(sen.getContent());
                if (sen.isSeg()) {
                    // 原子分词
                    AtomSeg as = new AtomSeg(sen.getContent());
                    ArrayList<Atom> atoms = as.getAtoms();
                    mr.setAtoms(atoms);
                    printErr("[atom time]:" + (System.currentTimeMillis() - start));
                    start = System.currentTimeMillis();

                    // 生成分词图表,先进行初步分词，然后进行优化，最后进行词性标记
                    SegGraph segGraph = GraphGenerate.generate(atoms, coreDict);
                    mr.setSegGraph(segGraph.getSnList());
                    // 生成二叉分词图表
                    SegGraph biSegGraph = GraphGenerate.biGenerate(segGraph, coreDict, bigRamDict);
                    mr.setBiSegGraph(biSegGraph.getSnList());
                    printErr("[graph time]:" + (System.currentTimeMillis() - start));
                    start = System.currentTimeMillis();

                    // 求N最短路径
                    NShortPath nsp = new NShortPath(biSegGraph, segPathCount);
                    ArrayList<ArrayList<Integer>> bipath = nsp.getPaths();  //获取全部最短路径
                    mr.setBipath(bipath);
                    printErr("[NSP time]:" + (System.currentTimeMillis() - start));
                    start = System.currentTimeMillis();

                    for (ArrayList<Integer> onePath : bipath) {
                        // 得到初次分词路径
                        ArrayList<SegNode> segPath = getSegPath(segGraph, onePath);
                        ArrayList<SegNode> firstPath = AdjustSeg.firstAdjust(segPath);
                        String firstResult = outputResult(firstPath);
                        mr.addFirstResult(firstResult);
                        printErr("[first time]:" + (System.currentTimeMillis() - start));
                        start = System.currentTimeMillis();

                        // 处理未登陆词，进对初次分词结果进行优化
                        SegGraph optSegGraph = new SegGraph(firstPath);
                        ArrayList<SegNode> sns;
                        sns = (ArrayList<SegNode>) firstPath.clone();
                        personTagger.recognition(optSegGraph, sns);
                        transPersonTagger.recognition(optSegGraph, sns);
                        placeTagger.recognition(optSegGraph, sns);
                        mr.setOptSegGraph(optSegGraph.getSnList());
                        printErr("[unknown time]:" + (System.currentTimeMillis() - start));
                        start = System.currentTimeMillis();

                        // 根据优化后的结果，重新进行生成二叉分词图表
                        SegGraph optBiSegGraph = GraphGenerate.biGenerate(optSegGraph, coreDict, bigRamDict);
                        mr.setOptBiSegGraph(optBiSegGraph.getSnList());

                        // 重新求取N－最短路径
                        NShortPath optNsp = new NShortPath(optBiSegGraph, segPathCount);
                        ArrayList<ArrayList<Integer>> optBipath = optNsp.getPaths();
                        mr.setOptBipath(optBipath);

                        // 生成优化后的分词结果，并对结果进行词性标记和最后的优化调整处理
                        ArrayList<SegNode> adjResult;
                        for (ArrayList<Integer> optOnePath : optBipath) {
                            ArrayList<SegNode> optSegPath = getSegPath(optSegGraph, optOnePath);
                            lexTagger.recognition(optSegPath);
                            String optResult = outputResult(optSegPath);
                            mr.addOptResult(optResult);
                            adjResult = AdjustSeg.finalAdjust(optSegPath, personTagger, placeTagger);
                            String adjrs = outputResult(adjResult);
                            printErr("[last time]:" + (System.currentTimeMillis() - start));
                            start = System.currentTimeMillis();
                            if (midResult == null)
                                midResult = adjrs;
                            break;
                        }
                    }
                    sr.addMidResult(mr);
                } else
                    midResult = sen.getContent();
                finalResult += midResult;
                midResult = null;
            }

            sr.setFinalResult(finalResult);
        }

        return sr;
    }

    // 根据二叉分词路径生成分词路径
    private ArrayList<SegNode> getSegPath(SegGraph sg, ArrayList<Integer> bipath) {
        ArrayList<SegNode> path = null;

        if (sg != null && bipath != null) {
            ArrayList<SegNode> sns = sg.getSnList();
            path = new ArrayList<SegNode>();

            for (int index : bipath)
                path.add(sns.get(index));
        }
        return path;
    }

    // 根据分词路径生成分词结果
    private String outputResult(ArrayList<SegNode> wrList) {
        String result = null;
        String temp;
        char[] pos = new char[2];
        if (wrList != null && wrList.size() > 0) {
            result = "";
            for (SegNode sn : wrList) {
                if (sn.getPos() != POSTag.SEN_BEGIN && sn.getPos() != POSTag.SEN_END) {
                    int tag = Math.abs(sn.getPos());
                    pos[0] = (char) (tag / 256);
                    pos[1] = (char) (tag % 256);
                    temp = "" + pos[0];
                    if (pos[1] > 0)
                        temp += "" + pos[1];
                    result += sn.getSrcWord() + "/" + temp + " ";
                }
            }
        }
        return result;
    }
}
