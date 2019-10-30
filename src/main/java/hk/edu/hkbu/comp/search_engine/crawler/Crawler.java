package hk.edu.hkbu.comp.search_engine.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *   pull data process flow
 * */
public class Crawler {
    //maximum number of web pages that crawled
    final int Y = 100;
    final int X = 10;
    final String[] URLException = {".pdf", "..", ".gif", ".png", ".jpg", ".ico", "javascript", "mailto",
            ".css", "adobe", "turnitin"};

    private UrlFilter urlFilter = new UrlFilter();
    private int x, y;



    private String seed;

    public Crawler() {
    }

    public Crawler(String seed, int x, int y) throws IOException {
        UrlQueue.addToUrlPool(seed);
        this.x = x;
        this.y = y;
    }


    public void crawling() throws IOException {
        while (UrlQueue.getProcessedUrlPoolSize() < y) {
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
            //TODO store the words, URL with its title, the number of word containing in the page.
            //get the corresponding web page.

            List<String> links = getURLs(visitUrl);

            for (String url : links) {
                if (UrlQueue.getUrlPoolSize() < x ) {
                    UrlQueue.addToUrlPool(url);
                }
            }


            UrlQueue.printUrlPool();

            //add this URL to Processed URL Pool
            UrlQueue.addProcessedUrlPool(visitUrl);
        }
        //print the processedUrl pool
        UrlQueue.printProcessedUrlPool();

    }


    public List<String> getURLs(String srcPage) throws IOException {
//        URL url = new URL(srcPage);
//        InputStreamReader reader = new InputStreamReader(url.openStream());
//        ParserDelegator parser = new ParserDelegator();
//        HTMLParser callback = new HTMLParser();
//        parser.parse(reader, callback, true);
//
//        for (int i = 0; i < callback.urls.size(); i++) {
//            String str = callback.urls.get(i);
//            if (!isAbsURL(str)) {
//                callback.urls.set(i, toAbsURL(str, url).toString());
//            }
//        }
//
//        return callback.urls;


        List<String> list = new ArrayList<>();
        URL url = new URL(srcPage);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setInstanceFollowRedirects(false);
        int code = httpURLConnection.getResponseCode();
        // url regex
        Pattern pattern = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))", Pattern.DOTALL);
        //content of the html
        String webContent = loadWebConent(srcPage);
        Matcher matcher = pattern.matcher(webContent);
        //
        if (code == 200) {
            //if url is found, keep looping
            while (matcher.find()) {
                //remove the url double quote
                String urlStr = matcher.group(1).replaceAll("\"|\'", "");
                //if not contain # and not in the except list of url
                if (!urlStr.contains("#") && !isInExceptUrl(urlStr, URLException)) {
                    if (isAbsURL(urlStr)) {
                        list.add(urlStr);
                    } else {
                        String absoluteUrl = "";
                        absoluteUrl =  url.getProtocol() + "://" + url.getHost() + urlStr;
                        list.add(absoluteUrl);
                    }
                }
            }
        }

        //if the visit url is redirected to another url.
        //such as "http://www.comp.hkbu.edu.hk/" to "http://www.comp.hkbu.edu.hk/v1/"
        if(code == 302) {
            url = toRedirectedUrl(url.toString());
            while (matcher.find()) {
                //remove the url double quote
                String urlStr = matcher.group(1).replaceAll("\"|\'", "");
                //if not contain # and not in the except list of url
                if (!urlStr.contains("#") && !isInExceptUrl(urlStr, URLException)) {
                    if (isAbsURL(urlStr)) {
                        list.add(urlStr);
                    } else {
                        String absoluteUrl = "";
                        // /v1/v1/ repeated problem
                        if(urlStr.contains(url.getPath())) {
                            absoluteUrl = url.getProtocol() + "://" + url.getHost() +  urlStr.replaceAll(url.getPath(), url.getPath());
                        } else {
                            absoluteUrl =  url.getProtocol() + "://" + url.getHost() + url.getPath() + urlStr;
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


    public URL toRedirectedUrl(String srcUrl) throws IOException {
        URLConnection con = new URL(srcUrl).openConnection();
        con.connect();
        InputStream is = con.getInputStream();
        URL url = con.getURL();
        return url;
    }


    public static boolean isAbsURL(String str) {
        return str.matches("^[a-z0-9]+://.+");
    }

    public static URL toAbsURL(String str, URL ref) throws MalformedURLException {
        URL url = null;
        String prefix = ref.getProtocol() + "://" + ref.getHost() + ref.getPath();
        if (prefix.endsWith("/")) {
            prefix = prefix.substring(0, prefix.length() - 1);
        }
        if (ref.getPort() > -1)
            prefix += ":" + ref.getPort();


        url = new URL(prefix + str);
        return url;
    }


    public static String loadWebConent(String urlStr) {
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
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }

        return result;
    }
}
