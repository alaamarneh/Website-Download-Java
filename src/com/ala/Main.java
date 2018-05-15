package com.ala;

import java.io.*;
import java.net.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;


public class Main {
    private UrlsController mController;

    public Main() {
        mController = new UrlsController();
    }

    public static void main(String[] args) throws Exception{
        System.out.println("start main");

        URL url = new URL("http://aauj.000webhostapp.com/api_elearn/api_elearn/");
        Main m = new Main();
        m.distributeUrls(m.downloadAndGetURLs(url),5);

    }
    private void distributeUrls(List<URL> listOfURL,int noOfThreads) throws Exception
    {
            mController.addUrls(listOfURL);
            Utils.print(listOfURL);

        do {
            List<URL> someUrls = mController.fetchUrls(noOfThreads);

            if(someUrls != null && !someUrls.isEmpty()){
                int size = someUrls.size();
                ExecutorService executor = Executors.newFixedThreadPool(size);
                List<Callable<List<URL>>> callables = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    callables.add(new Goo(someUrls.get(i)));
                }
                List<Future<List<URL>>> futures = executor.invokeAll(callables);
                int count =0;
                for (Future<List<URL>> f :
                        futures) {
                    Utils.print(f.get());
                    mController.addUrls(f.get());

                }

                executor.shutdown();
            }

            System.out.println("size remaining="+ mController.size());
        }while (!mController.isEmpty());

    }
    private List<URL> downloadAndGetURLs(URL url) throws Exception{
            return Utils.extractUrls(url);
    }

}
