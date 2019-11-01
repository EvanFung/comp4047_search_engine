package hk.edu.hkbu.comp.search_engine.crawler;

import hk.edu.hkbu.comp.search_engine.model.ConnectionPack;
import hk.edu.hkbu.comp.search_engine.parsing.SplitWord;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FilterTool {
    private String ENCODING = "UTF-8";
    protected String filterDir;

    public boolean accept(String str) {
        HashSet<String> words = readIgnoreWordFile(filterDir);
        for(String s: words)
        {
            if(words.contains(str))
            {
                return false;
            }
        }
        return true;
    }

    public HashSet<String> readIgnoreWordFile(String fileName) {
        HashSet<String> wordSets = null;
        File file = new File(fileName);
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING);
            if (file.isFile() && file.exists())
            {
                wordSets = new HashSet<String>();
                BufferedReader br = new BufferedReader(read);
                String txt = null;
                while ((txt = br.readLine()) != null)
                {
                    txt.toLowerCase();
                    wordSets.add(txt);
                }
                br.close();
            }
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordSets;
    }


    public static ArrayList<String> filterWords(ArrayList<String> words) throws IOException {
        FilterTool blackListWordFilter = new BlackListWordFilter();
        FilterTool ignoreWordFilter = new IgnoreWordFilter();

        Iterator<String> iterator = words.iterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            if(!blackListWordFilter.accept(word) || !ignoreWordFilter.accept(word)) {
                iterator.remove();
            }
        }
        return words;
    }
}

