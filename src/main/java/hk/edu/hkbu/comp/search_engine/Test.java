package hk.edu.hkbu.comp.search_engine;

import hk.edu.hkbu.comp.search_engine.crawler.MyParserCallback;

import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
//        String s = loadPlainText("http://www.comp.hkbu.edu.hk/v1");
        List<String> list = getURLs("http://www.comp.hkbu.edu.hk/v1");
        for (String s2 : list) {
            System.out.println(s2);
        }
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
        MyParserCallback callback = new MyParserCallback();
        ParserDelegator parser = new ParserDelegator();

        URL url = new URL(urlString);
        InputStreamReader reader = new InputStreamReader(url.openStream());
        parser.parse(reader, callback, true); // call MyParserCallback to process the URL stream

        return callback.content;
    }


    public static List<String> getURLs(String srcPage) throws IOException {
        URLConnection con = new URL(srcPage).openConnection();
        con.connect();

        System.out.println("connected url: " + con.getURL());
        String s = con.getURL().toString();
        InputStream is = con.getInputStream();
        System.out.println("redirected url: " + con.getURL());
        System.out.println(is.toString());
        is.close();

        URL url = con.getURL();
        InputStreamReader reader = new InputStreamReader(url.openStream());

        ParserDelegator parser = new ParserDelegator();
        MyParserCallback callback = new MyParserCallback();
        parser.parse(reader, callback, true);

        for (int i = 0; i < callback.urls.size(); i++) {
            String str = callback.urls.get(i);
            if (!isAbsURL(str)) {
                callback.urls.set(i, toAbsURL(str, con.getURL()).toString());
            } else {
            }
        }

        return callback.urls;
    }


}
