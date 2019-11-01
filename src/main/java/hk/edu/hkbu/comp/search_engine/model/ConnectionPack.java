package hk.edu.hkbu.comp.search_engine.model;

import java.io.IOException;
import java.io.InputStreamReader;
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
}
