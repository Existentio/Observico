package com.observico.observico.ui.rss;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.observico.observico.R;
import com.observico.observico.model.RssItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Георгий on 07.09.2017.
 */

public class RssItemAdapter extends RecyclerView.Adapter<RssItemAdapter.ViewHolder> {

    private final List<RssItem> mItems = new ArrayList<>();
    private final Context mContext;
    private OnItemClickListener mListener;


    public RssItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setFeed(List<RssItem> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public RssItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rss_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RssItemAdapter.ViewHolder holder, int position) {
        if (mItems.size() <= position) {
            return;
        }
        final RssItem item = mItems.get(position);
        holder.textTitle.setText(item.getTitle());
        holder.textPubDate.setText(item.getPublishDate());

        if(item.getImage() != null) {
            Picasso.with(mContext).load(item.getImage())
                    .fit()
                    .centerCrop()
                    .into(holder.imageThumb);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) mListener.onItemSelected(item);
            }
        });
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public interface OnItemClickListener {
        void onItemSelected(RssItem rssItem);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle;
        private TextView textPubDate;
        private ImageView imageThumb;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            textPubDate = (TextView) itemView.findViewById(R.id.tvPubDate);
            imageThumb = (ImageView) itemView.findViewById(R.id.ivThumb);

        }
    }


}
