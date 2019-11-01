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
//    public void split() throws IOException {
//        //1.get the title of the web page
//
//        //2.get the the word
//
//        //3.get the url
//
//        //4.filtering if ignore words in the content, remove it from the set
//
//        //5.get the number of words in the page
//
//        //6.make the file, the title of the file is name after the title of the page
//
//        Iterator<String> it = processedUrlQueue.iterator();
//        FileOutputStream fileOutputStream = null;
//        ObjectOutputStream objectOutputStream = null;
//        while (it.hasNext()) {
//            String url = it.next();
//            String title = HTMLParser.loadTitleText(url);
//            String words = HTMLParser.loadBodyText(url);
//            //get list of the words in the given url;
//            ArrayList<String> list = getSplitWord(url);
//
//            //filtering if ignore words in the content, remove it from the words list
////            for (String word : list) {
////                // if the filter not accept the word, we remove the word from the list
////                if (!ignoreWordFilter.accept(word)) {
////                    list.remove(word);
////                }
////            }
//
//            //get the number of words in the page
//            int wordSize = list.size();
//
//            //create a Page object that we will serialized later
//            Page page = new Page(url, title, list);
//            System.out.println("url -- " + page.getUrl());
//            System.out.println("title -- " + page.getTitle());
//            System.out.println("count -- " + page.getWordCount());
//            //TODO there are some url that has no title ,
//
//            //serialized an object
//            //TODO you guys thinks a proper name for the file that should be unique...wordSize is not an unique name.
//            //it just for demo how to serialized an object
//            fileOutputStream = new FileOutputStream(wordSize + ".ser");
//            objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            objectOutputStream.writeObject(page);
//
//        }
//
//        fileOutputStream.close();
//        objectOutputStream.close();
//
//    }

//    public final String getFileNameByTitle(final String title) {
//        String result = new String();
//        //remove all whitespace from String
//        result = title.replaceAll("\\\\s+", "");
//        //if there is a special character, replace it to _.
//        result = result.replaceAll("[\\?/:*|<>\"]", "_");
//        return result;
//    }

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
