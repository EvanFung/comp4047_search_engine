package hk.edu.hkbu.comp.search_engine.crawler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;

import java.io.*;
import java.net.URL;

/*
 *   download file IO
 * */
public class DownloadFile {

    private void saveFile(InputStream data, String filePath) {
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            BufferedInputStream in = new BufferedInputStream(data);
            int r;
            while ((r = in.read()) != -1) {
                out.write((byte) r);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileNameByUrl(String url) {
        url = url.substring(7); //remove http://
        url = url.replaceAll("[\\?/:*|<>\"]", "_"); //将特殊字符替换，以生成合法的本地文件名
        return url;
    }

    public String downloadFile(String url) {
        String filePath = "";
        HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
                if (i > 5) {
                    //if retry more than 5 times.
                    return false;
                }
                return true;
            }
        };

        CloseableHttpClient httpClient = HttpClients.custom().setRetryHandler(requestRetryHandler).build();
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(5000).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + statusLine);
                filePath = null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream entityContent = entity.getContent();
                filePath = "../html/" + getFileNameByUrl(url);
                saveFile(entityContent, filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("successfully download file " + filePath + " to local");
        return filePath;
    }
}
