package com.vijayrc.supportguy;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SpringSecurityDownloadTest {

    @Test
    public void shouldDownloadAFileAfterAuthentication() throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            String location = "http://naswasmo01:14010//datanet-lite/export/zip?type=DETAIL&performanceLevelId=8&timeRequested=1360782145531";
            authorize(httpclient);
            HttpGet httpget = new HttpGet(location);
            HttpResponse httpResponse = httpclient.execute(httpget);
            InputStream content = httpResponse.getEntity().getContent();
            OutputStream out = new BufferedOutputStream(new FileOutputStream(new File("2.zip")));

            IOUtils.copy(content, out);
            out.close();
            content.close();
            consumeEntity(httpResponse);

        } finally {
            httpclient.getConnectionManager().shutdown();
        }
    }

    private void authorize(DefaultHttpClient httpclient) throws IOException {
        HttpPost httppost = new HttpPost("http://naswasmo01:14010/datanet-lite/j_security_check");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("j_username","t6atchm"));
        nvps.add(new BasicNameValuePair("j_password", "xxx"));
        httppost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

        HttpResponse httpResponse = httpclient.execute(httppost);
        consumeEntity(httpResponse);
    }

    private void consumeEntity(HttpResponse httpResponse) throws IOException {
        HttpEntity entity = httpResponse.getEntity();
        EntityUtils.consume(entity);
    }

    private String getLocationHeader(HttpResponse httpResponse) {
        Header[] allHeaders = httpResponse.getAllHeaders();
        String location = "";
        for (Header header : allHeaders) {
            if("location".equalsIgnoreCase(header.getName())) location = header.getValue();
            httpResponse.addHeader(header.getName(), header.getValue());
        }
        return location;
    }
}
