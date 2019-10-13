package hk.edu.hkbu.comp.search_engine.crawler;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpParams;
import org.apache.http.client.params.AllClientPNames;

import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

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
        while (!UrlQueue.isUnVisitedUrlEmpty() && downloadFileNum < MAXNUM && UrlQueue.getUnvisitedUrlSize() < 99) {
            String visitUrl = (String) UrlQueue.unVisitedUrlDeQueue();
//            DownloadFile downloadFile = new DownloadFile();
//            downloadFile.downloadFile(visitUrl);
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


    public static List<String> getURLs(String srcPage) throws IOException {
//        String realUrl = getReal(srcPage);
        URL url = new URL(srcPage);
        InputStreamReader reader = new InputStreamReader(url.openStream());

        ParserDelegator parser = new ParserDelegator();
        HTMLParser callback = new HTMLParser();
        parser.parse(reader, callback, true);

        for (int i = 0; i < callback.urls.size(); i++) {
            String str = callback.urls.get(i);
            if (!isAbsURL(str)) {
                callback.urls.set(i, toAbsURL(str, url).toString());
            } else {
            }
        }

        return callback.urls;
    }


    public static boolean isAbsURL(String str) {
        return str.matches("^[a-z0-9]+://.+");
    }

    public static URL toAbsURL(String str, URL ref) throws MalformedURLException {
        URL url = null;
        String prefix = ref.getProtocol() + "://" + ref.getHost();
//        //TODO ADD delete /
//        if (prefix.endsWith("/")) {
//            prefix = prefix.substring(0, prefix.length() - 1);
//        }
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


    public static String getReal(String url) {
        try {
            HttpClient client = new HttpClient();
            HttpMethod method = new GetMethod(url);
            HttpParams params = client.getParams();
            params.setParameter(AllClientPNames.HANDLE_REDIRECTS, false);
            client.executeMethod(method);
            String realUrl = method.getURI().getURI();
            return realUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
