package com.example.maryam.rssreader.Views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.maryam.rssreader.Adapters.ArticlesAdapter;
import com.example.maryam.rssreader.Models.Articles;
import com.example.maryam.rssreader.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    android.support.v4.app.Fragment mContent = new HomeFragment();
    @BindView(R.id.no_internet_imgv)
    ImageView imageView;
    @BindView(R.id.fragment_container)
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "myFragmentName");
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(ArticlesAdapter.SEND_ACTION));
        //check for network connection
        if (isNetworkConnected() == false) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //get extra from intent
            Articles articles = (Articles) intent.getSerializableExtra(ArticlesAdapter.CLICKED_ATRICLE);
            mContent = new ArticleFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(ArticlesAdapter.CLICKED_ATRICLE, articles);
            mContent.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mContent).commitAllowingStateLoss();

        }
    };

    //inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        //check menu items
        if (itemId == R.id.action_add) {
            mContent = new SettingsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mContent).commit();
        } else if (itemId == R.id.action_home) {
            mContent = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mContent).commit();
        }

        return super.onOptionsItemSelected(item);
    }

    //save state of fragment
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mContent.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "myFragmentName", mContent);
        }
    }

}

