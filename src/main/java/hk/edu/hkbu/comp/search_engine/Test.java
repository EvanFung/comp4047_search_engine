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

import static hk.edu.hkbu.comp.search_engine.crawler.Crawler.getURLs;

public class Test {
    public static void main(String[] args) throws Exception {
//        List<String> list = getURLs( "http://www.comp.hkbu.edu.hk/");
//        for (String s2 : list) {
//            System.out.println(s2);
//        }

//
//
//
//        SensitiveFilterService filter = new SensitiveFilterService(SensitiveType.ignoreWord);
//        String txt = "温家宝今天想去深圳参观，带上了习近平。参观完后还得赶飞机跟日本参与国家发展和改革委员会会议";
//        //如果需要过滤则用“”替换
//        //如果需要屏蔽，则用“*”替换
//        String hou = filter.replaceSensitiveWord(txt, 1, "*");
//        int a = filter.CheckSensitiveWord("温家宝",0,1);
//        System.out.println(a);
//        System.out.println("替换前的文字为：" + txt);
//        System.out.println("替换后的文字为：" + hou);


//        URLConnection con = new URL( "http://www.comp.hkbu.edu.hk/" ).openConnection();
//        System.out.println( "orignal url: " + con.getURL() );
//        con.connect();
//        System.out.println( "connected url: " + con.getURL() );
//        InputStream is = con.getInputStream();
//        System.out.println( "redirected url: " + con.getURL() );
//        is.close();

//        loadKeyWord("https://www.comp.hkbu.edu.hk/v1/");
        Crawler crawler = new Crawler(new String[]{"http://www.comp.hkbu.edu.hk/"});
        crawler.crawling();

        //it can check whether there is a javacript: url
//        Pattern p = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))",Pattern.DOTALL);
    }

    public static boolean isAbsURL(String str) {
        return str.matches("^[a-z0-9]+://.+");
    }

//    public static URL toAbsURL(String str, URL ref) throws MalformedURLException {
//        URL url = null;
//        String prefix = ref.getProtocol() + "://" + ref.getHost() + ref.getPath();
//        //TODO ADD delete /
//        if (prefix.endsWith("/")) {
//            prefix = prefix.substring(0, prefix.length() - 1);
//        }
//        if (ref.getPort() > -1)
//            prefix += ":" + ref.getPort();
//
//        if (!str.startsWith("/")) {
//            int len = ref.getPath().length() - ref.getFile().length();
//            String tmp = "/" + ref.getPath().substring(0, len) + "/";
//            prefix += tmp.replace("//", "/");
//        }
//        url = new URL(prefix + str);
//
//        return url;
//    }


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
