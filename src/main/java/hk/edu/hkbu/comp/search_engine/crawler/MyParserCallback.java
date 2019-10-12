package hk.edu.hkbu.comp.search_engine.crawler;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class MyParserCallback extends HTMLEditorKit.ParserCallback {
    public String content = new String();
    public List<String> urls = new ArrayList<String>();

    // Override features of the parent's class
    @Override
    public void handleText(char[] data, int pos) {
        content += new String(data) + " ";
    }


    public void handleStartTag(HTML.Tag tag, MutableAttributeSet attrSet, int pos) {
        if (tag.toString().equals("a")) {
            Enumeration e = attrSet.getAttributeNames();
            while (e.hasMoreElements()) {
                Object aname = e.nextElement();
                if (aname.toString().equals("href")) {
                    String u = (String) attrSet.getAttribute(aname);
                    if (urls.size() < 1024 && !urls.contains(u))
                        urls.add(u);
                }
            }
        }
    }


}




