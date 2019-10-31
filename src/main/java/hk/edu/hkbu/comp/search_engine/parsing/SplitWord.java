package hk.edu.hkbu.comp.search_engine.parsing;

import hk.edu.hkbu.comp.search_engine.crawler.FilterTool;
import hk.edu.hkbu.comp.search_engine.crawler.HTMLParser;
import hk.edu.hkbu.comp.search_engine.crawler.IgnoreWordFilter;
import hk.edu.hkbu.comp.search_engine.model.Page;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static hk.edu.hkbu.comp.search_engine.crawler.HTMLParser.loadBodyText;

public class SplitWord {
    private Set<String> processedUrlQueue;
    private String wordDir;

    public SplitWord() {
    }

    public SplitWord(Set<String> processedUrlQueue, String wordDir) {
        this.processedUrlQueue = processedUrlQueue;
        this.wordDir = wordDir;
    }


    public void split() throws IOException {
        //1.get the title of the web page

        //2.get the the word

        //3.get the url

        //4.filtering if ignore words in the content, remove it from the set

        //5.get the number of words in the page

        //6.make the file, the title of the file is name after the title of the page

        FilterTool ignoreWordFilter = new IgnoreWordFilter();
        Iterator<String> it = processedUrlQueue.iterator();
        while (it.hasNext()) {
            String url = it.next();
            String title = HTMLParser.loadTitleText(url);
            String words = HTMLParser.loadBodyText(url);
            //get list of the words in the given url;
            List<String> list = getUniqueWords(words);

            //filtering if ignore words in the content, remove it from the words list
//            for (String word : list) {
//                // if the filter not accept the word, we remove the word from the list
//                if (!ignoreWordFilter.accept(word)) {
//                    list.remove(word);
//                }
//            }

            //get the number of words in the page
            int wordSize = list.size();

            //create a Page object that we will serialized later
            Page page = new Page(url, title, wordSize, list);
            System.out.println("url -- " + page.getUrl());
            System.out.println("title -- " + page.getTitle());
            System.out.println("count -- " + page.getWordCount());
            //TODO there are some url that are no title ,
            //serialized an object
//            FileOutputStream fileOutputStream = new FileOutputStream(wordDir + getFileNameByTitle(title) + ".ser");
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            objectOutputStream.writeObject(objectOutputStream);
//            fileOutputStream.close();
//            objectOutputStream.close();

        }

    }

    public final String getFileNameByTitle(final String title) {
        String result = new String();
        //remove all whitespace from String
        result = title.replaceAll("\\\\s+", "");
        //if there is a special character, replace it to _.
        result = result.replaceAll("[\\?/:*|<>\"]", "_");
        return result;
    }

    /**
     * @param url to be split word
     * @return
     */

    public static List<String> getSplitWord(String url) throws IOException {
        String bodyText = HTMLParser.loadBodyText(url);
        return getUniqueWords(bodyText);
    }

    /**
     * @param text body of the web page
     * @return list of the tokenized  String
     */
    public static List<String> getUniqueWords(String text) {
        String[] words = text.split("[\\d\\W]+");
        ArrayList<String> uniqueWords = new ArrayList<String>();

        for (String w : words) {
            w = w.toLowerCase();

            if (!uniqueWords.contains(w))
                uniqueWords.add(w);
        }

        return uniqueWords;
    }
}
