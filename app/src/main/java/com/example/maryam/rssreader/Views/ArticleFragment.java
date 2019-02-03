package com.example.maryam.rssreader.Views;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.maryam.rssreader.Adapters.ArticlesAdapter;
import com.example.maryam.rssreader.Models.Articles;
import com.example.maryam.rssreader.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {
    @BindView(R.id.image_view)
    ImageView articleImg;
    @BindView(R.id.title_tv)
    TextView title;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.publish_date_tv)
    TextView publishDate;
    @BindView(R.id.provider_tv)
    TextView provider;
    @BindView(R.id.full_article)
    TextView fullArticle;
    @BindView(R.id.author)
    TextView author;
    private Articles article;

    public ArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            article = (Articles) getArguments().getSerializable(ArticlesAdapter.CLICKED_ATRICLE);
            setData();
        }
        //open google chrome
        fullArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

    public void setData() {
        Glide.with(getActivity()).load(article.getUrlToImage()).into(articleImg);
        title.setText(article.getTitle());
        description.setText(article.getDescription());
        provider.setText(article.getSource());
        publishDate.setText(article.getPublishedAt());
        author.setText(article.getAuthor());

    }

}
