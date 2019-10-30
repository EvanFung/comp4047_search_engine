package hk.edu.hkbu.comp.search_engine.crawler;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

public class UrlQueue {

    //processed url pool
    private static Set<String> visitedUrl = new HashSet<>();
    //url pool
    private static Queue<String> unVisitedUrl = new LinkedList<>();


    private UrlQueue() {}

    public static synchronized void addVisitedUrl(final String url)
    {
        visitedUrl.add(url);
        System.out.println("now processed url has ：" + UrlQueue.getVisitedUrlNum() + " elements");
    }


    public static synchronized void removeVisitedUrl(final String url)
    {
        visitedUrl.remove(url);
    }

    /**
     * get the number that has been visited.
     *
     * @return The number that has been visited.
     */
    public static synchronized int getVisitedUrlNum()
    {
        return visitedUrl.size();
    }

    /**
     * @return Queue<String>
     */
    public static synchronized Queue<String> getUnVisitedUrl()
    {
        return unVisitedUrl;
}

    /**
     *
     *
     * @return Object
     */
    public static Object unVisitedUrlDeQueue() {

        String visitUrl = null;

        visitUrl = unVisitedUrl.poll();
        System.out.println(visitUrl + " leave queue(url pool)");

        return visitUrl;
    }

    /**
     *
     *
     * @param url url
     */
    public static void addUnvisitedUrl(final String url) {

        if (!visitedUrl.contains(url) && !unVisitedUrl.contains(url))
        {
            unVisitedUrl.add(url);
            System.out.println("addUnvisitedUrl: " + url);
            System.out.println("now url pool has :" + UrlQueue.getUnVisitedUrlNum() + " elements"); //
        }
    }





    /**
     * @return int
     */
    public static int getUnVisitedUrlNum() {
        return unVisitedUrl.size();
    }

    /**
     *
     * @return boolean
     */
    public static synchronized boolean unVisitedUrlsEmpty() {
        return unVisitedUrl.isEmpty();
    }

    public static void printUrlPool() {
        for(String s : unVisitedUrl) {
            System.out.println("[URL pool] : " + s + "element");
        }
    }


}
