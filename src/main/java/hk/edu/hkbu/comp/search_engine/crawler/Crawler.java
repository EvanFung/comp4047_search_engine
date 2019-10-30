package hk.edu.hkbu.comp.search_engine.crawler;

import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;

/*
 *   pull data process flow
 * */
public class Crawler {
    //maximum number of web pages that crawled
    final int MAXNUM = 100;
    int downloadFileNum = 0;
    final int X = 10;
    String[] seeds = new String[X];

    public Crawler() {
    }

    public Crawler(String[] seeds) throws IOException {
        for (int i = 0; i < seeds.length; i++) {
            UrlQueue.addUnvisitedUrl(toRedirectedUrl(seeds[i]));
        }
    }


    public void crawling() throws IOException {
        while (UrlQueue.getVisitedUrlNum() < MAXNUM && UrlQueue.getUnVisitedUrlNum() > 0) {

            System.out.println("List of unVisited Pool: ");

            //Retrieve and remove an URL from URL Pool
            String visitUrl = (String) UrlQueue.unVisitedUrlDeQueue();
            //Transform to redirected url
            visitUrl = toRedirectedUrl(visitUrl);
            System.out.println("visit url : " + visitUrl);


            //TODO extract all words from this web pages. if not listed in the given blacklist and ignore list
            //Extract all words from this web page.
            //For each word, if it is not listed in the given blacklist and ignore list, store the following items:
            // i) the word,
            // ii) URL with its title, and
            // iii) number of the word containing in the page.


            //TODO store the words, URL with its title, the number of word containing in the page.
            //get the corresponding web page.
            List<String> links = getURLs(visitUrl);

            //add this URL to Processed URL Pool
            UrlQueue.addVisitedUrl(visitUrl);

            for (String url : links) {
                if (UrlQueue.getUnVisitedUrlNum() < X && IsReturn200(url)) {

                    UrlQueue.addUnvisitedUrl(url);
                }
            }


        }
    }

    public static boolean IsReturn200(String srcPage) throws IOException
    {
        URL url = new URL(srcPage);
        HttpURLConnection huc = (HttpURLConnection)url.openConnection();
        huc.connect();
        return huc.getResponseCode() == 200 ? true: false;
    }

    public static List<String> getURLs(String srcPage) throws IOException {
        URL url = new URL(srcPage);
        InputStreamReader reader = new InputStreamReader(url.openStream());
        ParserDelegator parser = new ParserDelegator();
        HTMLParser callback = new HTMLParser();
        parser.parse(reader, callback, true);

        for (int i = 0; i < callback.urls.size(); i++) {
            String str = callback.urls.get(i);
            if (!isAbsURL(str)) {
                //str ?page=hkbu relative url
//                System.out.println("str : "+str);

//                System.out.println("url is : " + url.toString());
//                System.out.println("abs : " + toAbsURL(str, url));
                callback.urls.set(i, toAbsURL(str, url).toString());
            }
        }

        return callback.urls;
    }

    public String toRedirectedUrl(String srcUrl) throws IOException {
        URLConnection con = new URL(srcUrl).openConnection();
        con.connect();
        InputStream is = con.getInputStream();
        URL url = con.getURL();
        return url.toString();
    }


    public static boolean isAbsURL(String str) {
        return str.matches("^[a-z0-9]+://.+");
    }

    public static URL toAbsURL(String str, URL ref) throws MalformedURLException {
        URL url = null;
        String prefix = ref.getProtocol() + "://" + ref.getHost() + ref.getPath();
        if (prefix.endsWith("/")) {
            prefix = prefix.substring(0, prefix.length() - 1);
        }
//     System.out.println("get prefix:" + prefix);
//    System.out.println("get host:" + ref.getHost());
//        System.out.println("get path:" +  ref.getPath());
//        System.out.println("get file:" +  ref.getFile());
//        System.out.println("str in abs : " + str);
        if (ref.getPort() > -1)
            prefix += ":" + ref.getPort();
//
//        if (!str.startsWith("/")) {
//            int len = ref.getPath().length() - ref.getFile().length();
//            String tmp = "/" + ref.getPath().substring(0, len) + "/";
//            prefix += tmp.replace("//", "/");
//        }

        url = new URL(prefix + str);
        return url;
    }
}
