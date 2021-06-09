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
    private Context content;
    private boolean fav;

    final String ID = "ID";
    final String HASHCODE = "HASHCODE";
    final String AVATAR = "image";
    final String USERNAME = "username";
    final String ADRESS = "adress";
    final String PHONE = "phone";
    final String BIO = "bio";

    public MyNeighbourRecyclerViewAdapter(Context context, List<Neighbour> items, boolean fav) {
        mNeighbours = items;
        this.content = context;
        this.fav = fav;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_neighbour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fav) {
                    EventBus.getDefault().post(new DeleteFavouriteNeighbourEvent(neighbour));
                    Log.d("CONSOLE LOG","delete  OK");
                }   else {
                    EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));
                    Log.d("CONSOLE LOG","delete NON");
                }
            }
        });

        // Ouvrir la profile_neighbour on click sur un voisin //
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CONSOLE LOG","Ouverture de detail activity OK");
                Intent intent = new Intent(v.getContext(), activity_profile_neighbour.class);
                intent.putExtra(ID, neighbour.getId());
                intent.putExtra(HASHCODE, neighbour.hashCode());
                intent.putExtra(AVATAR, neighbour.getAvatarUrl());
                intent.putExtra(USERNAME, neighbour.getName());
                intent.putExtra(ADRESS, neighbour.getAddress());
                intent.putExtra(PHONE, neighbour.getPhoneNumber());
                intent.putExtra(BIO, neighbour.getAboutMe());

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
