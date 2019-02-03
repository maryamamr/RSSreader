package com.example.maryam.rssreader.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.maryam.rssreader.Models.URLs;
import com.example.maryam.rssreader.R;

import java.util.List;

public class MySourcesAdapter extends RecyclerView.Adapter<MySourcesAdapter.SourcesViewHolder> {
    private List<URLs> urLsList;
    private Context mContext;
    public static final String DELETE_ACTION = "action_delete";
    public static final String DELETE_ITEM = "delete_item";


    public MySourcesAdapter(List<URLs> urLsList, Context mContext) {
        this.urLsList = urLsList;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public SourcesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.modify_scource_item, parent, false);
        return new MySourcesAdapter.SourcesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SourcesViewHolder holder, final int position) {
        final URLs url = urLsList.get(position);
        holder.textView.setText(url.getSource());
        //send item to delete
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DELETE_ACTION);
                intent.putExtra(DELETE_ITEM, url);
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return urLsList == null ? 0 : urLsList.size();
    }

    public class SourcesViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageButton imageButton;

        public SourcesViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.scource_tv);
            imageButton = itemView.findViewById(R.id.delete_button);
        }
    }

}
