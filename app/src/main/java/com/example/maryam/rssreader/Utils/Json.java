package com.example.maryam.rssreader.Utils;

import com.example.maryam.rssreader.Models.Articles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Json {

    public static ArrayList<Articles> parseArticles(String json) {
        Articles articles;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = new JSONArray(jsonObject.optString("articles", "[\"\"]"));
            ArrayList<Articles> articlesArrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                String currentItem = jsonArray.optString(i, "");
                JSONObject object = new JSONObject(currentItem);
                JSONObject obj = new JSONObject(object.optString("source", "[\"\"]"));
                articles = new Articles(object.optString("author", "Not available"),
                        object.optString("title", "Not available"),
                        object.optString("description", "Not available"),
                        object.optString("url", "Not available"), object.optString("urlToImage", "Not available"),
                        object.optString("publishedAt", "Not available"),
                        object.optString("content", "Not available"), obj.optString("name", "Not available"));

                articlesArrayList.add(articles);
            }

            return articlesArrayList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
