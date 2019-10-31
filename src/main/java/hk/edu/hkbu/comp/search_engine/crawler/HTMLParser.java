package hk.edu.hkbu.comp.search_engine.crawler;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

public class HTMLParser extends HTMLEditorKit.ParserCallback {
    public ArrayList<String> urls = new ArrayList<String>();
    public String content = new String();
    public String keywordContent = new String();
    public String bodyContent = new String();
    //title of the page
    public String title = new String();
    public boolean isScript = false;
    public boolean isStyle = false;
    public boolean isMeta = false;
    public boolean isTitle = false;
    public static boolean isBody = false;
    public boolean encounterMetaName = false;

    public void handleStartTag(HTML.Tag tag, MutableAttributeSet attrSet, int pos) {
        if (tag.equals(HTML.Tag.BODY)) {
            isBody = true;
        }
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

        if (tag.equals(HTML.Tag.TITLE)) {
            isTitle = true;
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
        if (isTitle) {
            title = new String(data) + " ";
        }
        if(isBody) {
            bodyContent += new String(data) + " ";
        }

        content += new String(data) + " ";
    }


    @Override
    public void handleEndTag(HTML.Tag t, int pos) {
        if(t.equals(HTML.Tag.BODY)) {
            isBody = false;
        }

        if(t.equals(HTML.Tag.TITLE)) {
            isTitle = false;
        }
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


    public static String loadBodyText(String urlString) throws IOException {
        URL url = new URL(urlString);
        InputStreamReader reader = new InputStreamReader(url.openStream());

        ParserDelegator parser = new ParserDelegator();
        HTMLParser callback = new HTMLParser();
        parser.parse(reader, callback, true);

        return callback.bodyContent;
    }


    /**
     *
     * @param urlString url of the web page
     * @return title of the web page
     * @throws IOException
     */
    public static String loadTitleText(String urlString) throws IOException {
        HTMLParser callback = new HTMLParser();
        ParserDelegator parser = new ParserDelegator();

        URL url = new URL(urlString);
        InputStreamReader reader = new InputStreamReader(url.openStream());
        parser.parse(reader, callback, true); // call MyParserCallback to process the URL stream

        return callback.title;
    }
}
