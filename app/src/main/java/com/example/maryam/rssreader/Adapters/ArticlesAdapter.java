package com.example.maryam.rssreader.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.maryam.rssreader.Models.Articles;
import com.example.maryam.rssreader.R;

import java.util.ArrayList;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesAdapterViewHolder> {
    private ArrayList<Articles> articlesArrayList;
    private Context context;
    public final static String CLICKED_ATRICLE = "clicked_article";
    public final static String SEND_ACTION = "action_send";


    public ArticlesAdapter(Context context, ArrayList<Articles> articlesArrayList) {
        this.context = context;
        this.articlesArrayList = articlesArrayList;
    }

    @NonNull
    @Override
    public ArticlesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_recycler_item, parent, false);
        return new ArticlesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesAdapterViewHolder holder, int position) {

        final Articles currentArticle = articlesArrayList.get(position);
        holder.articleTitle.setText(currentArticle.getTitle());
        Glide.with(context).load(currentArticle.getUrlToImage()).into(holder.articleImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SEND_ACTION);
                intent.putExtra(CLICKED_ATRICLE, currentArticle);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articlesArrayList == null ? 0 : articlesArrayList.size();
    }

    public class ArticlesAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView articleTitle;
        ImageView articleImage;

        public ArticlesAdapterViewHolder(View itemView) {
            super(itemView);
            articleImage = itemView.findViewById(R.id.article_img_view);
            articleTitle = itemView.findViewById(R.id.article_title_tv);
        }
    }

}
