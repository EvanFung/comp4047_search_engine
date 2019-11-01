package hk.edu.hkbu.comp.search_engine.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class IgnoreWordFilter extends FilterTool {

    public IgnoreWordFilter() {
        super();
        filterDir = "./src/main/java/hk/edu/hkbu/comp/search_engine/constant/ignore_of_words.txt";
    }
}
