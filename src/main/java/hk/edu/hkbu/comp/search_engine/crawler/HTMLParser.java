package hk.edu.hkbu.comp.search_engine.crawler;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import java.util.ArrayList;
import java.util.Enumeration;


public class HTMLParser extends HTMLEditorKit.ParserCallback {
    public ArrayList<String> urls = new ArrayList<String>();
    public String content = new String();
    public String keywordContent = new String();
    public String title = new String();

    public void handleStartTag(HTML.Tag tag, MutableAttributeSet attrSet, int pos) {
        //get a tag
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


        //get title tag
        if (tag.toString().equals("title")) {

        }
    }

    // Override features of the parent's class
    @Override
    public void handleText(char[] data, int pos) {
        content += new String(data) + " ";
    }

    @Override
    public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attrSet, int pos) {

    }
}
