package hk.edu.hkbu.comp.search_engine.crawler;

import hk.edu.hkbu.comp.search_engine.model.ConnectionPack;
import hk.edu.hkbu.comp.search_engine.model.WordTable;
import hk.edu.hkbu.comp.search_engine.model.Page;
import hk.edu.hkbu.comp.search_engine.parsing.SplitWord;
import hk.edu.hkbu.comp.search_engine.utils.Utils;

import javax.swing.text.html.parser.ParserDelegator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *   pull data process flow
 * */
public class Crawler {

    WordTable wordTable;
    //maximum number of web pages that crawled
    final int Y = 100;
    final int X = 10;
    final String[] URLException = {".pdf", "..", ".gif", ".png", ".jpg", ".ico", "javascript", "mailto",
            ".css", "adobe", "turnitin"};

    private int x, y;
    private String seed;

    public Crawler() {
    }

    public Crawler(WordTable _wordTable, String seed, int x, int y) throws IOException {
        wordTable = _wordTable;
        UrlQueue.addToUrlPool(seed);
        this.x = x;
        this.y = y;
    }


    public Set<String> crawling() throws IOException {
        UrlFilter urlFilter = new UrlFilter();
        while (UrlQueue.getProcessedUrlPoolSize() < y && UrlQueue.getUrlPoolSize() > 0) {
            //Retrieve and remove an URL from URL Pool
            String visitUrl = (String) UrlQueue.urlPoolDeQueue();
            //Transform to redirected url
//            URL urlVisit = toRedirectedUrl(visitUrl);
//            visitUrl = urlVisit.toString();

            //TODO extract all words from this web pages. if not listed in the given blacklist and ignore list
            //Extract all words from this web page.
            //For each word, if it is not listed in the given blacklist and ignore list, store the following items:
            // i) the word,
            // ii) URL with its title, and
            // iii) number of the word containing in the page.

            ConnectionPack connectionPack = getConnectionPack(visitUrl);
            Page page = getPage(connectionPack);
            if (connectionPack == null || page == null) {
                UrlQueue.addToDeadpool(visitUrl);
                System.out.println("Add to deadpool: " + visitUrl);
                continue;
            }



            //TODO store the words, URL with its title, the number of word containing in the page.
            //get the corresponding web page.

            List<String> links = getURLs(connectionPack);

            for (String url : links) {
                if (UrlQueue.getUrlPoolSize() < x && urlFilter.accept(url)) {
                    UrlQueue.addToUrlPool(url);
                }
            }

            UrlQueue.printUrlPool();

            //add this URL to Processed URL Pool
            UrlQueue.addProcessedUrlPool(visitUrl);
        }
        //print the processedUrl pool
        UrlQueue.printProcessedUrlPool();

        return UrlQueue.getProcessedUrlPool();
    }

    public static Page getPage(ConnectionPack cP) throws IOException {
        Page page = new Page();
        try {
            ParserDelegator parser = new ParserDelegator();
            HTMLParser callback = new HTMLParser();
            parser.parse(cP.getReader(), callback, true);

            page.setTitle(callback.title);
            page.setUrl(cP.getUrl().toString());

            page.setOriginalWords(SplitWord.getSplitWord(cP.getUrl().toString()));
            page.setWordCount(page.getOriginalWords().size());
            page.setHash(Utils.getSHA256(page.getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return page;
    }

    public static ConnectionPack getConnectionPack(String srcPage) throws IOException {
        ConnectionPack cP = new ConnectionPack();
        try {

            cP.setUrl(new URL(srcPage));

            HttpURLConnection httpURLConnection = (HttpURLConnection) cP.getUrl().openConnection();
            httpURLConnection.setInstanceFollowRedirects(false);

            cP.setCode(httpURLConnection.getResponseCode());
            //content of the html
            String content = loadWebContent(srcPage);
            if (content == null) return null;
            cP.setContentString(loadWebContent(srcPage));
            cP.setConnection(httpURLConnection);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return cP;
    }

    public List<String> getURLs(ConnectionPack cP) throws IOException {

        List<String> list = new ArrayList<>();

        // url regex
        Pattern pattern = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(cP.getContentString());
        //
        if (cP.getCode() == 200) {
            //if url is found, keep looping
            while (matcher.find()) {
                //TODO: Filter place here

                //remove the url double quote
                String urlStr = matcher.group(1).replaceAll("\"|\'", "");
                //if not contain # and not in the except list of url
                if (!urlStr.contains("#") && !isInExceptUrl(urlStr, URLException)) {
                    if (isAbsURL(urlStr)) {
                        list.add(urlStr);
                    } else {
                        String absoluteUrl = "";
                        absoluteUrl = cP.getUrl().getProtocol() + "://" + cP.getUrl().getHost() + urlStr;
                        list.add(absoluteUrl);
                    }
                }
            }
        }

        //if the visit url is redirected to another url.
        //such as "http://www.comp.hkbu.edu.hk/" to "http://www.comp.hkbu.edu.hk/v1/"
        if (cP.getCode() == 302) {
            cP.setUrl(toRedirectedUrl(cP.getUrl().toString()));
            while (matcher.find()) {
                //TODO: Filter place here

                //remove the url double quote
                String urlStr = matcher.group(1).replaceAll("\"|\'", "");
                //if not contain # and not in the except list of url
                if (!urlStr.contains("#") && !isInExceptUrl(urlStr, URLException)) {
                    if (isAbsURL(urlStr)) {
                        list.add(urlStr);
                    } else {
                        String absoluteUrl = "";
                        // /v1/v1/ repeated problem
                        if (urlStr.contains(cP.getUrl().getPath())) {
                            absoluteUrl = cP.getUrl().getProtocol() + "://" + cP.getUrl().getHost() + urlStr.replaceAll(cP.getUrl().getPath(), cP.getUrl().getPath());
                        } else {
                            absoluteUrl = cP.getUrl().getProtocol() + "://" + cP.getUrl().getHost() + cP.getUrl().getPath() + urlStr;
                        }
                        list.add(absoluteUrl);
                    }
                }
            }
        }

        return list;
    }

    private static boolean isInExceptUrl(String str, String[] exceptWords) {
        for (int i = 0; i < exceptWords.length; i++) {
            if (str.contains(exceptWords[i])) {
                return true;
            }
        }
        return false;
    }

    public static URL toRedirectedUrl(String srcUrl) throws IOException {
        URLConnection con = new URL(srcUrl).openConnection();
        con.connect();
        InputStream is = con.getInputStream();
        URL url = con.getURL();
        return url;
    }

    public static boolean isAbsURL(String str) {
        return str.matches("^[a-z0-9]+://.+");
    }

    public static String loadWebContent(String urlStr) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        String result = "";
        try {
            url = new URL(urlStr);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
            return null;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
                return null;
            }
        }

        return result;
    }

}
