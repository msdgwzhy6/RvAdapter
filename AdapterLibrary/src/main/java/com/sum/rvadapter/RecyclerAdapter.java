package com.sum.rvadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdl on 2016/8/29.
 * 统一适配器
 */
public class RecyclerAdapter<DataHolder extends RecyclerDataHolder> extends RecyclerView.Adapter<ViewHolder> {

    private List<DataHolder> mHolders;
    private int mCurPosition;

    public RecyclerAdapter() {
        this(null);
        mHolders = new ArrayList<>();
    }

    public RecyclerAdapter(List<DataHolder> holders) {
        if (holders != null) {
            mHolders = new ArrayList<>(holders.size() + 10);
            mHolders.addAll(holders);
        }
        setHasStableIds(true);
    }

    public void setDataHolders(List<DataHolder> holders) {
        if (holders == null)
            mHolders = new ArrayList<>();
        else {
            mHolders = new ArrayList<>(holders.size() + 10);
            mHolders.addAll(holders);
        }
        notifyDataSetChanged();
    }

    public void addDataHolder(DataHolder holder) {
        mHolders.add(holder);
        notifyItemInserted(mHolders.size() - 1);
    }

    public void addDataHolder(int location, DataHolder holder) {
        mHolders.add(location, holder);
        notifyItemInserted(location);
    }

    public void addDataHolder(List<DataHolder> holders) {
        int location = mHolders.size();
        mHolders.addAll(holders);
        notifyItemRangeInserted(location, holders.size());
    }

    public void addDataHolder(int location, List<DataHolder> holders) {
        mHolders.addAll(location, holders);
        notifyItemRangeInserted(location, holders.size());
    }

    public void removeDataHolder(int location) {
        if (location < mHolders.size()) {
            mHolders.remove(location);
            notifyItemRemoved(location);
        }
    }

    public void removeDataHolder(DataHolder holder) {
        int index = mHolders.indexOf(holder);
        if (index != -1) {
            mHolders.remove(index);
            notifyItemRemoved(index);
        }
    }

    public List<DataHolder> getDataHoders() {
        return mHolders;
    }

    private DataHolder queryDataHolder(int location) {
        return mHolders.get(location);
    }

    @Override
    public final int getItemCount() {
        return mHolders.size();
    }

    @Override
    public final long getItemId(int position) {
        return queryDataHolder(position).getId();
    }

    @Override
    public final int getItemViewType(int position) {
        mCurPosition = position;
        return queryDataHolder(position).getType();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DataHolder holder = queryDataHolder(mCurPosition);
        View view = holder.onCreateView(parent.getContext(), parent);
        return holder.onCreateViewHolder(view, mCurPosition);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vHolder, int position) {
        DataHolder holder = queryDataHolder(position);
        holder.onBindViewHolder(position, vHolder, holder.getData());
    }

}
