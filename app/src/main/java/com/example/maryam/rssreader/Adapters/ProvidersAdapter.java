package com.example.maryam.rssreader.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maryam.rssreader.Models.Articles;
import com.example.maryam.rssreader.Models.Providers;
import com.example.maryam.rssreader.R;

import java.util.ArrayList;

public class ProvidersAdapter extends RecyclerView.Adapter<ProvidersAdapter.ProvidersAdapterViewHolder> {
    Context context;
    ArrayList<Providers> providersArrayList;

    public ProvidersAdapter(Context context, ArrayList<Providers> providersArrayList) {
        this.context = context;
        this.providersArrayList = providersArrayList;
    }

    @NonNull
    @Override
    public ProvidersAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.providers_recycler_item, parent, false);
        return new ProvidersAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProvidersAdapterViewHolder holder, int position) {
        //get object of provider
        Providers providers = providersArrayList.get(position);
        String providerName = providers.getProviderName();
        holder.provider.setText(providerName);
        ArrayList<Articles> signleItem = providers.getArticlesList();
        ArticlesAdapter articlesAdapter = new ArticlesAdapter(context, signleItem);
        holder.articlesRecyclerView.setHasFixedSize(true);
        holder.articlesRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.articlesRecyclerView.setAdapter(articlesAdapter);

    }


    @Override
    public int getItemCount() {
        return providersArrayList == null ? 0 : providersArrayList.size();
    }

    public class ProvidersAdapterViewHolder extends RecyclerView.ViewHolder {
        RecyclerView articlesRecyclerView;
        TextView provider;


        public ProvidersAdapterViewHolder(View itemView) {
            super(itemView);
            provider = itemView.findViewById(R.id.provider_textview);
            articlesRecyclerView = itemView.findViewById(R.id.article_rv);
        }
    }
}
