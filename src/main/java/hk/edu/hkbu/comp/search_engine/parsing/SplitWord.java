package hk.edu.hkbu.comp.search_engine.parsing;

import hk.edu.hkbu.comp.search_engine.crawler.BlackListWordFilter;
import hk.edu.hkbu.comp.search_engine.crawler.FilterTool;
import hk.edu.hkbu.comp.search_engine.crawler.HTMLParser;
import hk.edu.hkbu.comp.search_engine.crawler.IgnoreWordFilter;
import hk.edu.hkbu.comp.search_engine.model.ConnectionPack;

import java.io.*;
import java.util.*;

public class SplitWord {

    public SplitWord() {
    }

    public static ArrayList<String> splitToUniqueWords(String text) {
        String[] words = text.split("[\\d\\W]+");
        Set<String> uniqueWords = new HashSet<>();

        for (String w : words) {
            w = w.toLowerCase();
            uniqueWords.add(w);
        }
        return new ArrayList<String>(uniqueWords);
    }

}
