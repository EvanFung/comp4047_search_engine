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
    //title of the page
    public String title = new String();
    public boolean isScript = false;
    public boolean isStyle = false;
    public boolean isMeta = false;
    public boolean encounterMetaName = false;

    public void handleStartTag(HTML.Tag tag, MutableAttributeSet attrSet, int pos) {
        if (tag == HTML.Tag.SCRIPT) {
            isScript = true;
        } else {
            isScript = false;
        }
        if (tag == HTML.Tag.STYLE) {
            isStyle = true;
        } else {
            isStyle = false;
        }

        if (tag == HTML.Tag.META) {
            isMeta = true;
        } else {
            isMeta = false;
        }
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
    }

    // Override features of the parent's class
    @Override
    public void handleText(char[] data, int pos) {
        if (isStyle) {
//            doSomething(data);
        }
        content += new String(data) + " ";
    }

    public void doSomething(char[] data) {
        System.out.println(data);
    }


    @Override
    public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attrSet, int pos) {
        //title of the page
        String name = (String) attrSet.getAttribute(HTML.Attribute.NAME);
        if (tag == HTML.Tag.META) {
            if (name != null && name.equalsIgnoreCase("keywords")) {
                title = (String) attrSet.getAttribute(HTML.Attribute.CONTENT);
                encounterMetaName = true;
            }
        }

    }
}
