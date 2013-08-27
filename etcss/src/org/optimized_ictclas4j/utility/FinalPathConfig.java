package org.optimized_ictclas4j.utility;

/**
 * Created with IntelliJ IDEA.
 * User: Wings
 * Date: 13-7-3
 * Time: 下午2:41
 */
public final class FinalPathConfig {
    private static FinalPathConfig ourInstance = new FinalPathConfig();
    public final String coreDictIn = "data/coreDict.dct";
    public final String bigRamDictIn = "data/BigramDict.dct";
    public final String personTaggerDctIn = "data/nr.dct";
    public final String personTaggerCtxIn = "data/nr.ctx";
    public final String transPersonTaggerDctIn = "data/tr.dct";
    public final String transPersonTaggerCtxIn = "data/tr.ctx";
    public final String placeTaggerDctIn = "data/ns.dct";
    public final String placeTaggerCtxIn = "data/ns.ctx";
    public final String lexTaggerCtxIn = "data/lexical.ctx";

    private FinalPathConfig() {
    }

    public static FinalPathConfig getConfigInstance() {
        return ourInstance;
    }
}
