package org.wordpress.android.ui.reader.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import org.wordpress.android.fluxc.model.ReaderFeedModel;
import org.wordpress.android.ui.reader.views.ReaderSiteSearchResultView;

import java.util.ArrayList;
import java.util.List;

/*
 * adapter which shows the results of a reader site search
 */
public class ReaderSiteSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public interface SiteClickListener {
        void onSiteClicked(ReaderFeedModel site);
    }

    private SiteClickListener mClickListener;
    private final List<ReaderFeedModel> mSites = new ArrayList<>();

    public ReaderSiteSearchAdapter() {
        super();
        setHasStableIds(true);
    }

    public void setSiteClickListener(SiteClickListener listener) {
        mClickListener = listener;
    }

    public void setFeedList(@NonNull List<ReaderFeedModel> feeds) {
        mSites.clear();
        mSites.addAll(feeds);
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return (getItemCount() == 0);
    }

    private boolean isValidPosition(int position) {
        return position >= 0 && position < getItemCount();
    }

    @Override
    public int getItemCount() {
        return mSites.size();
    }

    @Override
    public long getItemId(int position) {
        return mSites.get(position).getFeedId();
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReaderSiteSearchResultView view = new ReaderSiteSearchResultView(parent.getContext());
        return new SiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (!isValidPosition(position)) {
            return;
        }
        SiteViewHolder siteHolder = (SiteViewHolder) holder;
        siteHolder.mSearchResultView.setSite(mSites.get(position));
    }

    class SiteViewHolder extends RecyclerView.ViewHolder {
        private final ReaderSiteSearchResultView mSearchResultView;

        SiteViewHolder(View view) {
            super(view);
            mSearchResultView = (ReaderSiteSearchResultView) view;
            view.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (isValidPosition(position) && mClickListener != null) {
                        ReaderFeedModel site = mSites.get(position);
                        mClickListener.onSiteClicked(site);
                    }
                }
            });
        }
    }
}
