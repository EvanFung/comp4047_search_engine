package hk.edu.hkbu.comp.search_engine.crawler;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class UrlQueue {

    //processed url pool
    private static Set<String> processedUrlPool = new HashSet<>();
    //url pool
    private static Queue<String> urlPool = new PriorityQueue<>();


    private UrlQueue() {

    }

    public static synchronized void addProcessedUrlPool(final String url) {
        processedUrlPool.add(url);
        System.out.println("now processed url has ：" + UrlQueue.getProcessedUrlPoolSize() + " elements");
    }


    public static synchronized void removeVisitedUrl(final String url) {
        processedUrlPool.remove(url);
    }

    /**
     * get the number that has been visited.
     *
     * @return The number that has been visited.
     */
    public static synchronized int getProcessedUrlPoolSize() {
        return processedUrlPool.size();
    }

    /**
     * @return Queue<String>
     */
    public static synchronized Queue<String> getUrlPool() {
        return urlPool;
    }

    /**
     * @return Object
     */
    public static Object urlPoolDeQueue() {

        String visitUrl = null;

        visitUrl = urlPool.poll();
        System.out.println(visitUrl + " leave queue(url pool)");

        return visitUrl;
    }

    /**
     * @param url url
     */
    public static void addToUrlPool(final String url) {

        if (!processedUrlPool.contains(url) && !urlPool.contains(url)) {
            urlPool.add(url);
            System.out.println("now url pool has :" + UrlQueue.getUrlPoolSize() + " elements"); //
        }
    }

    /**
     * @return int
     */
    public static int getUrlPoolSize() {
        return urlPool.size();
    }

    /**
     * @return boolean
     */
    public static synchronized boolean unVisitedUrlsEmpty() {
        return urlPool.isEmpty();
    }

    public static void printUrlPool() {
        for (String s : urlPool) {
            System.out.println("[URL pool] : " + s);
        }
    }

    /**
     * @return list of processed url pool
     */

    public static void printProcessedUrlPool() {
        for (String s : processedUrlPool) {
            System.out.println("[Processed URL pool] : " + s);
        }
    }


}
