package com.example.maryam.rssreader.Views;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.maryam.rssreader.Adapters.MySourcesAdapter;
import com.example.maryam.rssreader.Executors;
import com.example.maryam.rssreader.Models.AppDatabase;
import com.example.maryam.rssreader.Models.URLs;
import com.example.maryam.rssreader.R;
import com.example.maryam.rssreader.Utils.Network;
import com.example.maryam.rssreader.ViewModel;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    @BindView(R.id.spinner)
    SearchableSpinner spinner;
    @BindView(R.id.add_button)
    Button button;
    @BindView(R.id.rescours_rv)
    RecyclerView recyclerView;
    private AppDatabase mDb;
    private List<URLs> urlsList = new ArrayList<>();
    private MySourcesAdapter mySourcesAdapter;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDb = AppDatabase.getInstance(getActivity().getApplicationContext());
        //insert data into database
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedItem = spinner.getSelectedItem().toString();
                String uri = Network.BuildURI(selectedItem);
                final URLs urLs = new URLs(uri, selectedItem);
                Executors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.urlsDao().insertUrl(urLs);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, new HomeFragment()).commit();
                            }
                        });
                    }

                });
            }
        });

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter(MySourcesAdapter.DELETE_ACTION));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setViewModel();
    }

    //delete data from database when button is clicked in recylcerview
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final URLs deletedUrl = (URLs) intent.getSerializableExtra(MySourcesAdapter.DELETE_ITEM);
            Executors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.urlsDao().deleteUrl(deletedUrl);
                }
            });
        }
    };


    public void setViewModel() {

        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getUrls().observe(this, new Observer<List<URLs>>() {
            @Override
            public void onChanged(@Nullable List<URLs> url) {

                if (url.size() > 0) {
                    urlsList.clear();
                    urlsList = url;
                    mySourcesAdapter = new MySourcesAdapter(urlsList, getActivity());
                    recyclerView.setAdapter(mySourcesAdapter);

                }

            }
        });

    }

}


