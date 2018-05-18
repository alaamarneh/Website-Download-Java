package com.ala;

import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * this callable accept a url then download the file then returns list of urls from the file
 */
public class Goo implements Callable<List<URL>> {

    private URL url;
    public Goo(URL url) {
        this.url = url;
    }

    @Override
    public List<URL> call() throws Exception {
        return Utils.downloadAndExtractUrls(url);

    }
}