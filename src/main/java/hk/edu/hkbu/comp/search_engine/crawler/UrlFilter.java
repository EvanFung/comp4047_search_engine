package hk.edu.hkbu.comp.search_engine.crawler;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlFilter extends FilterTool {

    public UrlFilter() {
    }

    @Override
    public boolean accept(String url) {
        Set<String> blackListUrls = readIgnoreWordFile("./src/main/java/hk/edu/hkbu/comp/search_engine/constant/blacklist_of_urls.txt");
        try {
            URL evaluatedUrl = new URL(url);

            for (String s : blackListUrls) {
                //if case : http://www.weibo.com/*, we only check whether the host is same
                if(s.contains("*")) {
                    //remove the * , get the validated url object
                   String newURL =  s.replaceAll("\\*","");
                   String evaluatedURLStr = evaluatedUrl.getProtocol() + "://" + evaluatedUrl.getHost() + evaluatedUrl.getFile();
                    URL blackList = new URL(newURL);
                    newURL = blackList.getProtocol() + "://" + blackList.getHost() + blackList.getFile();
                    // http://www.sci.hkbu.edu.hk/eng/about-us/deans-message/ to http://www.sci.hkbu.edu.hk/eng/about-us/deans-message
                    // cut down a / to the newURLStr,the evaluated url would be like this  http://www.sci.hkbu.edu.hk/eng/about-us/deans-message/abc
                    // we ensured that the evaluated url definitely longer than the blacklist url(name after newURL here)
                    // therefore I use contains to determine the case http://www.sci.hkbu.edu.hk/eng/about-us/deans-message/*
                    // the conner case, both are  http://www.sci.hkbu.edu.hk/eng/about-us/deans-message which is considered.
                    newURL = newURL.substring(0,newURL.length()-1);
                    if(evaluatedURLStr.contains(newURL)) {
                        return false;
                    }
                } else {
                    URL newURL = new URL(s);
                    //if case : http://www.comp.hkbu.edu.hk/v1/?page=people
                    String newURLStr = newURL.getProtocol() + "://" + newURL.getHost() + newURL.getFile();
                    String evaluatedURLStr = evaluatedUrl.getProtocol() + "://" + evaluatedUrl.getHost() +evaluatedUrl.getFile();
                    if(newURLStr.equalsIgnoreCase(evaluatedURLStr)) {
                        return false;
                    }
                }

            }

            return true;

        } catch (MalformedURLException e) {

        }
        return true;
    }
}
