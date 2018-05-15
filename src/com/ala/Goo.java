package com.ala;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * this callable accept a url then download the file then returns list of urls from the file
 */
public class Goo implements Callable<List<URL>> {

    private URL url;
    public Goo(URL url) {
        System.out.println("goo created");
        this.url = url;
    }

    @Override
    public List<URL> call() throws Exception {
        System.out.println("running "+ url);
        return Utils.extractUrls(url);

    }
}