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
    final int MAXSEEDNUM = 10;
    String[] seeds = new String[MAXSEEDNUM];

    public Crawler() {
    }

    public Crawler(String[] seeds) {
        for (int i = 0; i < seeds.length; i++) {
            UrlQueue.addUnvisitedUrl(seeds[i]);
        }
    }


    public void crawling() throws IOException {
        while (downloadFileNum < MAXNUM) {
            if (!UrlQueue.isUnVisitedUrlEmpty()) {

                String visitUrl = (String) UrlQueue.unVisitedUrlDeQueue();


                //Test the url is redirect or not
                HttpURLConnection con = (HttpURLConnection) (new URL(visitUrl).openConnection());
                con.setInstanceFollowRedirects(false);
                con.connect();

                int responseCode = con.getResponseCode();
                if (responseCode == 302 || responseCode == 301) {
                    visitUrl += con.getHeaderField("Location");
                }
//
//                DownloadFile downloadFile = new DownloadFile();
//                downloadFile.downloadFile(visitUrl);
                downloadFileNum++;
                UrlQueue.addVisitedUrl(visitUrl);
                //TODO extract all words from this web pages. if not listed in the given blacklist and ignore list
                //store the words, URL with its title, the number of word containing in the page.
                List<String> links = getURLs(visitUrl);

                for (String url : links) {
                    UrlQueue.addUnvisitedUrl(url);
                }
            }
        }
    }


    public static List<String> getURLs(String srcPage) throws IOException {
        URL url = null;
        URLConnection con = new URL(srcPage).openConnection();
        con.connect();
        InputStream is = con.getInputStream();
        url = con.getURL();
        InputStreamReader reader = new InputStreamReader(url.openStream());
        ParserDelegator parser = new ParserDelegator();
        HTMLParser callback = new HTMLParser();
        parser.parse(reader, callback, true);

        for (int i = 0; i < callback.urls.size(); i++) {
            String str = callback.urls.get(i);
            if (!isAbsURL(str)) {
                callback.urls.set(i, toAbsURL(str, url).toString());
            }
        }

        return callback.urls;
    }


    public static boolean isAbsURL(String str) {
        return str.matches("^[a-z0-9]+://.+");
    }

    public static URL toAbsURL(String str, URL ref) throws MalformedURLException {
        URL url = null;
        String prefix = ref.getProtocol() + "://" + ref.getHost() + ref.getPath();
        if(prefix.endsWith("/")) {
            prefix = prefix.substring(0,prefix.length() -1);
        }

        if (ref.getPort() > -1)
            prefix += ":" + ref.getPort();

        if (!str.startsWith("/")) {
            int len = ref.getPath().length() - ref.getFile().length();
            String tmp = "/" + ref.getPath().substring(0, len) + "/";
            prefix += tmp.replace("//", "/");
        }
        url = new URL(prefix + str);

        return url;
    }
}
