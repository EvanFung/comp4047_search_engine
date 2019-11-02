package hk.edu.hkbu.comp.search_engine.model;

import hk.edu.hkbu.comp.search_engine.crawler.HTMLParser;
import hk.edu.hkbu.comp.search_engine.parsing.SplitWord;
import hk.edu.hkbu.comp.search_engine.utils.Utils;

import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Page implements Serializable {
    private String url;
    private String title;
    private String originalContent;
    private String hash;

    private int wordCount;

    public Page() {
    }

    public Page(String url, String title, String originalContent) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.url = url;
        this.title = title;
        this.originalContent = originalContent;
        this.wordCount = new StringTokenizer(originalContent).countTokens();
        this.hash = Utils.getSHA256(this.url);
    }

    public String getOriginalContent() {
        return originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public void incrementWord() {
        wordCount++;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean setPage(ConnectionPack cP) throws IOException {
        try {
            ParserDelegator parser = new ParserDelegator();
            HTMLParser callback = new HTMLParser();
            parser.parse(cP.getReader(), callback, true);

            setTitle(callback.title);
            setUrl(cP.getUrl().toString());

            //remove all comment String
            String contentText = cP.getContentString().replaceAll("//(.*?)\n", "")                    .replaceAll("\n", "")
                    .replaceAll("(?i)<style(.*?)>(.*?)</style>", "")
                    .replaceAll("(?i)<script(.*?)>(.*?)</script>", "")
                    .replaceAll("<!--(.*?)-->", "")
                    .replaceAll("/\\*(.*?)\\*/", "");
//            contentText = contentText.replaceAll("\\<(meta|iframe|frame|span|tbody|layer)[^>]*>|<\\/(iframe|frame|meta|span|tbody|layer)>",
//                    "");
//            contentText = contentText.replaceAll("(?i:<script[\\s\\s]+</script *>)", "");
//            contentText = contentText.replaceAll("<SCRIPT[\\s\\s]+</SCRIPT *>", "");
//            contentText = contentText.replaceAll("<!--(.*?)-->", "");
//            contentText = contentText.replaceAll("\\<(style|strong)[^>]*>|<\\/(style|strong)>", "");
//            contentText = contentText.replaceAll("\\<(meta|iframe|frame|span|tbody|layer)[^>]*>|<\\/(iframe|frame|meta|span|tbody|layer)>",
//                    "");
//            contentText = contentText.replaceAll("<script[\\s\\s]+</script *>", "");
//            contentText = contentText.replaceAll("\\s", "");
            String bodyText = HTMLParser.loadBodyText(contentText);

            setOriginalContent(bodyText);
            setWordCount(getOriginalContent().length());
            setHash(Utils.getSHA256(getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
