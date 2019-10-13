package hk.edu.hkbu.comp.search_engine;

import hk.edu.hkbu.comp.search_engine.crawler.Crawler;
import hk.edu.hkbu.comp.search_engine.crawler.HTMLParser;


import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Test {
    public static void main(String[] args) throws Exception {
//        List<String> list = getURLs("http://www.comp.hkbu.edu.hk/");
//        for (String s2 : list) {
//            System.out.println(s2);
//        }

//        loadKeyWord("https://www.comp.hkbu.edu.hk/v1/");
        Crawler crawler = new Crawler(new String[]{"https://www.hkbu.edu.hk/eng/main/index.jsp"});
        crawler.crawling();
    }

    public static boolean isAbsURL(String str) {
        return str.matches("^[a-z0-9]+://.+");
    }

    public static URL toAbsURL(String str, URL ref) throws MalformedURLException {
        URL url = null;
        String prefix = ref.getProtocol() + "://" + ref.getHost() + ref.getPath();
        //TODO ADD delete /
        if (prefix.endsWith("/")) {
            prefix = prefix.substring(0, prefix.length() - 1);
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


//    public static List<String> getURLs(String srcPage) throws IOException {
//        String realUrl = getReal(srcPage);
//        URL url = new URL(realUrl);
//        InputStreamReader reader = new InputStreamReader(url.openStream());
//        ParserDelegator parser = new ParserDelegator();
//        HTMLParser callback = new HTMLParser();
//        parser.parse(reader, callback, true);
//
//        for (int i = 0; i < callback.urls.size(); i++) {
//            String str = callback.urls.get(i);
//            if (!isAbsURL(str)) {
//                callback.urls.set(i, toAbsURL(str, url).toString());
//            } else {
//            }
//        }
//
//        return callback.urls;
//    }


//    public static String getReal(String url){
//        try {
//            HttpClient client = new HttpClient();
//            HttpMethod method = new GetMethod(url);
//            HttpParams params = client.getParams();
//            params.setParameter(AllClientPNames.HANDLE_REDIRECTS, false);
//            client.executeMethod(method);
//            String realUrl = method.getURI().getURI();
//            return realUrl;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }


}
