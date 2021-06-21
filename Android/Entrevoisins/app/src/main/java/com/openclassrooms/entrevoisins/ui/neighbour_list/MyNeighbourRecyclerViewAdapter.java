package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.activity_profile_neighbour;
import com.openclassrooms.entrevoisins.events.DeleteFavouriteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {


    private final List<Neighbour> mNeighbours;
    private OnNeighbourClickListener mOnNeighbourClickListener;



    public MyNeighbourRecyclerViewAdapter(List<Neighbour> items, OnNeighbourClickListener onNeighbourClickListener) {
        mNeighbours = items;
        mOnNeighbourClickListener = onNeighbourClickListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_neighbour, parent, false);
        return new ViewHolder(view, mOnNeighbourClickListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        private OnNeighbourClickListener mOnNeighbourClickListener;

        public ViewHolder(View view, OnNeighbourClickListener onNeighbourClickListener) {
            super(view);
            ButterKnife.bind(this, view);
            mOnNeighbourClickListener = onNeighbourClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnNeighbourClickListener.onNeighbourClick(mNeighbours.get(getAdapterPosition()));
        }
    }
        public interface OnNeighbourClickListener {
            void onNeighbourClick(Neighbour neighbour);
        }
}
