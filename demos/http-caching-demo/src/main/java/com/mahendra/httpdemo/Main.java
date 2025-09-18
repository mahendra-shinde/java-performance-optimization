package com.mahendra.httpdemo;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception {
        // Set up cache directory (10 MB)
        File cacheDir = new File("http-cache");
        Cache cache = new Cache(cacheDir, 10 * 1024 * 1024);

        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        String url = "https://google.com";

        // Sequential requests to show caching
        for (int i = 1; i <= 3; i++) {
            makeRequest(client, url, i);
            try{
            Thread.sleep(1000);
            }catch(InterruptedException ex){
             System.out.println(ex.getMessage());
            }
        }

        // Parallel requests to show connection pooling
        System.out.println("\n--- Parallel requests (connection pooling demo) ---");
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 3; i++) {
            int reqNum = i;
            executor.submit(() -> makeRequest(client, url, reqNum + 3));
        }
        executor.shutdown();
    }

    private static void makeRequest(OkHttpClient client, String url, int reqNum) {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            String source = response.networkResponse() != null ? "NETWORK" : "CACHE";
            System.out.printf("Request #%d: HTTP %d (%s)\n", reqNum, response.code(), source);
        } catch (IOException e) {
            System.out.printf("Request #%d: ERROR %s\n", reqNum, e.getMessage());
        }
    }
}
