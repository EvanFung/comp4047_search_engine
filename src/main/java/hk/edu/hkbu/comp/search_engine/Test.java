package hk.edu.hkbu.comp.search_engine;

import hk.edu.hkbu.comp.search_engine.crawler.Crawler;
import hk.edu.hkbu.comp.search_engine.crawler.HTMLParser;
import org.apache.logging.log4j.Level;

import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        Crawler crawler = new Crawler(new String[]{"https://www.hkbu.edu.hk/eng/main/index.jsp"});
//        crawler.crawling();
        List<String> list = getURLs("https://www.hkbu.edu.hk/eng/main/index.jsp");
        for(String s : list) {
            System.out.println(s);
        }
    }
    public static boolean isAbsURL(String str) {
        return str.matches("^[a-z0-9]+://.+");
    }

    public static List<String> getURLs(String srcPage) throws IOException {
        URL url = new URL(srcPage);
        InputStreamReader reader = new InputStreamReader(url.openStream());

        ParserDelegator parser = new ParserDelegator();
        HTMLParser callback = new HTMLParser();
        parser.parse(reader, callback, true);

        for (int i=0; i<callback.urls.size(); i++) {
            String str = callback.urls.get(i);
            if (!isAbsURL(str)) {
                callback.urls.set(i, Crawler.toAbsURL(str, url).toString());
            } else {
            }
        }

        return callback.urls;
    }
}
