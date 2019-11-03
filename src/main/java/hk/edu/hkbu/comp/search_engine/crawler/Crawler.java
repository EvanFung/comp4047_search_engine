package hk.edu.hkbu.comp.search_engine.crawler;

import hk.edu.hkbu.comp.search_engine.model.ConnectionPack;
import hk.edu.hkbu.comp.search_engine.model.WordTable;
import hk.edu.hkbu.comp.search_engine.model.Page;
import hk.edu.hkbu.comp.search_engine.parsing.SplitWord;
import hk.edu.hkbu.comp.search_engine.utils.Utils;

import javax.swing.text.html.parser.ParserDelegator;
import java.io.*;
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
    public static final String[] URLException = {".pdf", "..", ".gif", ".png", ".jpg", ".ico", "javascript", "mailto",
            ".css", "adobe", "turnitin", ".svg", ".js"};

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

            ConnectionPack connectionPack = new ConnectionPack();
            Page page = new Page();

            if (!connectionPack.setConnectionPack(visitUrl) || !page.setPage(connectionPack)) {
                UrlQueue.addToDeadpool(visitUrl);
                System.out.println("Add to deadpool: " + visitUrl);
                continue;
            }
            ArrayList<String> UniqueWords = SplitWord.splitToUniqueWords(page.getOriginalContent());
            page.setNumOfWords(UniqueWords);
            ArrayList<String> filteredWords = FilterTool.filterWords(UniqueWords);

            wordTable.addPageToWords(filteredWords, page);

            recordPage(page);

            List<String> links = getURLs(connectionPack);


            for (String url : links) {
                if (UrlQueue.getUrlPoolSize() < x && urlFilter.accept(url) && !url.equals(visitUrl)) {
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

    public List<String> getURLs(ConnectionPack cP) throws IOException {

        List<String> list = new ArrayList<>();


        // url regex
        Pattern pattern = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(cP.getContentString());


        if (cP.getCode() == 200) {
            //if url is found, keep looping
            while (matcher.find()) {
                //remove the url double quote
                String urlStr = matcher.group(1).replaceAll("\"|\'", "");
                //if not contain # and not in the except list of url
                if (!urlStr.contains("#") && !isInExceptUrl(urlStr, URLException) && !urlStr.startsWith("//")) {
                    //handle this case: //static.bbc.co.uk //nav.files.bbci.co.uk
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
                //remove the url double quote
                String urlStr = matcher.group(1).replaceAll("\"|\'", "");
                //if not contain # and not in the except list of url
                if (!urlStr.contains("#") && !isInExceptUrl(urlStr, URLException) && !urlStr.startsWith("//")) {
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


    public static boolean isInExceptUrl(String str, String[] exceptWords) {
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
                result += line + "\n";
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

    public static void recordPage(Page page) throws IOException {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        fileOutputStream = new FileOutputStream("./src/main/java/hk/edu/hkbu/comp/search_engine/Record/Pages/" + page.getHash() + ".ser");
        objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(page);
    }

}
