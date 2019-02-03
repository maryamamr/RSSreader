package com.example.maryam.rssreader.Views;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.maryam.rssreader.Adapters.ProvidersAdapter;
import com.example.maryam.rssreader.Models.AppDatabase;
import com.example.maryam.rssreader.Models.Articles;
import com.example.maryam.rssreader.Models.Providers;
import com.example.maryam.rssreader.Models.URLs;
import com.example.maryam.rssreader.R;
import com.example.maryam.rssreader.Utils.Json;
import com.example.maryam.rssreader.Utils.Network;
import com.example.maryam.rssreader.ViewModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    @BindView(R.id.provders_rv)
    RecyclerView providerRv;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.empty_list)
    TextView emptyList;
    private ProvidersAdapter providersAdapter;
    private ArrayList<Providers> providersArrayList = new ArrayList<>();
    private List<URLs> urlsList = new ArrayList<>();
    private ArrayList<Articles> articlesArrayList = new ArrayList<>();
    private AppDatabase mDb;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        providerRv.setHasFixedSize(true);
        providerRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        providersAdapter = new ProvidersAdapter(getActivity(), providersArrayList);
        providerRv.setAdapter(providersAdapter);
        mDb = AppDatabase.getInstance(getActivity().getApplicationContext());
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setViewModel();
    }

    public void setViewModel() {

        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getUrls().observe(this, new Observer<List<URLs>>() {
            @Override
            public void onChanged(@Nullable List<URLs> urls) {
                if (urls.size() > 0) {
                    urlsList.clear();
                    urlsList = urls;
                }

                queryData();
            }
        });


    }

    private void queryData() {
        if (urlsList.size() != 0) {
            emptyList.setVisibility(View.GONE);
            for (int i = 0; i < urlsList.size(); i++) {
                URL url = Network.getURL(urlsList.get(i).getUrl());
                new getData().execute(url);
            }
        } else {
            emptyList.setVisibility(View.VISIBLE);
        }

    }

    public class getData extends AsyncTask<URL, Void, String> {


        @Override
        protected String doInBackground(URL... urls) {
            URL searchurl = urls[0];
            String results = null;

            try {
                results = Network.getResponse(searchurl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return results;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            if (s != null && s != "") {
                articlesArrayList = Json.parseArticles(s);
                for (int x = 0; x < 1; x++) {
                    Providers providers = new Providers();
                    providers.setArticlesList(articlesArrayList);
                    providers.setProviderName(articlesArrayList.get(0).getSource());
                    providersArrayList.add(providers);
                }
            }

            providersAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //check the state of the task
        getData task = new getData();
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING)
            task.cancel(true);
    }
}
