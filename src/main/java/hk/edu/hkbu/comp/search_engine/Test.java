package hk.edu.hkbu.comp.search_engine;

import hk.edu.hkbu.comp.search_engine.crawler.Crawler;
import hk.edu.hkbu.comp.search_engine.crawler.HTMLParser;
import hk.edu.hkbu.comp.search_engine.utils.SensitiveFilterService;
import hk.edu.hkbu.comp.search_engine.utils.SensitiveType;


import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) throws Exception {
        Crawler crawler = new Crawler("http://www.comp.hkbu.edu.hk/", 10,20);
        crawler.crawling();
//        List<String> urls = crawler.getURLs("http://www.comp.hkbu.edu.hk/");
////        List<String> urls = crawler.getURLs("https://www.hkbu.edu.hk/eng/main/index.jsp");
//        for (String s : urls) {
//            System.out.println(s);
//        }
//
//
//



    }




    public static String loadPlainText(String urlString) throws IOException {
        HTMLParser callback = new HTMLParser();
        ParserDelegator parser = new ParserDelegator();

        URL url = new URL(urlString);
        InputStreamReader reader = new InputStreamReader(url.openStream());
        parser.parse(reader, callback, true); // call MyParserCallback to process the URL stream

        return callback.content;
    }


    public static void loadKeyWord(String urlString) throws IOException {
        HTMLParser callback = new HTMLParser();
        ParserDelegator parser = new ParserDelegator();
        URL url = new URL(urlString);
        InputStreamReader reader = new InputStreamReader(url.openStream());
        parser.parse(reader, callback, true);
        System.out.println(callback.keywordContent);
    }



}
