package com.company;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class MyRequest {
    private static Logger log = Logger.getLogger(MyRequest.class.getName());
    private String urlText;

    public MyRequest(String urlText) {
        this.urlText = urlText;
    }

    public void send(String xmlContent) {
        try {
            HttpURLConnection c = (HttpURLConnection) new URL(this.urlText).openConnection();
            c.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(c.getOutputStream(), StandardCharsets.UTF_8);
            writer.write(xmlContent);
            writer.close();
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

}
