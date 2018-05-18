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

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the url");
        String urlString = reader.readLine(); // read  URL
        System.out.println("Enter number of threads");
        int N = Integer.parseInt(reader.readLine().trim()); //number of threads
        reader.close();
        URL url = new URL(urlString);

        Main m = new Main();
        List<URL> urls = m.downloadAndGetURLs(url);
        m.distributeUrls(urls,N);

    }

    /**
     * this method distributes the urls to N threads
     * @param listOfURL : the urls
     * @param noOfThreads : number of Threads
     * @throws Exception
     */
    private void distributeUrls(List<URL> listOfURL,int noOfThreads) throws Exception
    {
            mController.addUrls(listOfURL);

        do { //keep fetching URLs and distribute it to N threads

            List<URL> someUrls = mController.fetchUrls(noOfThreads); // block of URLs

            if(someUrls != null && !someUrls.isEmpty()){
                int size = someUrls.size();
                ExecutorService executor = Executors.newFixedThreadPool(size);
                List<Callable<List<URL>>> callables = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    callables.add(new Goo(someUrls.get(i)));
                }
                List<Future<List<URL>>> futures = executor.invokeAll(callables);

                for (Future<List<URL>> f :
                        futures) {
                    mController.addUrls(f.get());
                }

                executor.shutdown();
            }
        }while (!mController.isEmpty());

    }
    private List<URL> downloadAndGetURLs(URL url) {
        System.out.println("start");
            return Utils.downloadAndExtractUrls(url);
    }

}
