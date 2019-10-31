package hk.edu.hkbu.comp.search_engine.crawler;

import java.util.Set;

public class BlackListWordFilter extends FilterTool {
    public BlackListWordFilter() {
    }

    @Override
    public boolean accept(String str) {
        Set<String> blackListWords = readIgnoreWordFile("./src/main/java/hk/edu/hkbu/comp/search_engine/constant/blacklist_of_words.txt");
        for (String s : blackListWords) {
            if (str.equalsIgnoreCase(s)) {
                return false;
            }
        }
        return true;
    }
}
