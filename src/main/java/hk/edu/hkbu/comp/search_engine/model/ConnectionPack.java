package hk.edu.hkbu.comp.search_engine.model;

import hk.edu.hkbu.comp.search_engine.crawler.Crawler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ConnectionPack
{
    private URL url;
    private int code;
    private String contentString;
    private URLConnection connection;

    public URL getUrl() {
        return url;
    }

    public int getCode() {
        return code;
    }

    public String getContentString() {
        return contentString;
    }

    public InputStreamReader getReader() throws IOException {
        return new InputStreamReader(connection.getInputStream());
    }

    public URLConnection getConnection() {
        return connection;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setContentString(String contentString) {
        this.contentString = contentString;
    }

    public void setConnection(URLConnection connection) {
        this.connection = connection;
    }

    public boolean setConnectionPack(String srcPage) throws IOException {
        try {
            setUrl(new URL(srcPage));
            HttpURLConnection httpURLConnection = (HttpURLConnection) getUrl().openConnection();
            httpURLConnection.setInstanceFollowRedirects(false);

            setCode(httpURLConnection.getResponseCode());

            if(getCode() == 302)
            {
                if(Crawler.isInExceptUrl(Crawler.toRedirectedUrl(getUrl().toString()).toString(), Crawler.URLException)) return false;
                System.out.println("what is the redirected url " + Crawler.toRedirectedUrl(getUrl().toString()).toString());
            }

            //content of the html
            String content = Crawler.loadWebContent(srcPage);
            if (content == null) return false;
            setContentString(content);
            setConnection(httpURLConnection);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
