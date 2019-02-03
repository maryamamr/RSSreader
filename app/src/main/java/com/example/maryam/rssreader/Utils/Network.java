package com.example.maryam.rssreader.Utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Network {
    private static final String API_KEY = "c8a9f799877b45ff891f3ab695ae81f2";
    private static final String BASE_URL = "https://newsapi.org/v2/everything?";

    public static URL getURL(String text) {
        URL url = null;
        try {
            url = new URL(text);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String BuildURI(String source) {

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("sources", source)
                .appendQueryParameter("apiKey", API_KEY)
                .build();

        return builtUri.toString();

    }

    public static String getResponse(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }


    }


}
