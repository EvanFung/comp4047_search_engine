package hk.edu.hkbu.comp.search_engine.crawler;

import java.util.Set;

public class BlackListWordFilter extends FilterTool {

    public BlackListWordFilter() {
        super();
        filterDir = "./src/main/java/hk/edu/hkbu/comp/search_engine/constant/blacklist_of_words.txt";
    }
}
