package com.example.maryam.rssreader.Models;

import java.util.ArrayList;


public class Providers {
    public String getProviderName() {
        return ProviderName;
    }

    public void setProviderName(String providerName) {
        ProviderName = providerName;
    }

    public ArrayList<Articles> getArticlesList() {
        return articlesList;
    }

    public void setArticlesList(ArrayList<Articles> articlesList) {
        this.articlesList = articlesList;
    }

    String ProviderName;
    ArrayList<Articles> articlesList;
}
