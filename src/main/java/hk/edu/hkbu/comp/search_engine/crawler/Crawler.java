package hk.edu.hkbu.comp.search_engine.crawler;

import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/*
 *   pull data process flow
 * */
public class Crawler {

    String loadWebPage(String urlString) {
        byte[] buffer = new byte[1024];
        String content = new String();
        try {
            URL url = new URL(urlString);
            InputStream in = url.openStream();
            int len;
            while ((len = in.read(buffer)) != -1)
                content += new String(buffer);
        } catch (IOException e) {
            content = "<h1>Unable to download the page</h1>" + urlString;
        }
        return content;
    }
}
