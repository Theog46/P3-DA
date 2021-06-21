package com.openclassrooms.entrevoisins;


import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.EventLog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteFavouriteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;


import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class activity_profile_neighbour extends AppCompatActivity {

    @BindView(R.id.firstUsername)
    public TextView mNeighbourName;
    @BindView(R.id.secondUsername)
    public TextView mSecondNeighbourName;
    @BindView(R.id.profileImage)
    public ImageView mNeighbourPicture;
    @BindView(R.id.floatingActionButton)
    public FloatingActionButton mFavouriteButton;
    @BindView(R.id.locationText)
    public TextView mLocation;
    @BindView(R.id.phoneText)
    public TextView mPhone;
    @BindView(R.id.networkText)
    public TextView mNetwork;
    @BindView(R.id.biographie)
    public TextView mBio;


    private NeighbourApiService mApiService = DI.getNeighbourApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_neighbour);
        ButterKnife.bind(this);

        Neighbour neighbour = (Neighbour) getIntent().getSerializableExtra(ListNeighbourActivity.KEY_FOR_NEIGHBOUR_INTENT_EXTRA);

        mNeighbourName.setText(neighbour.getName());
        mSecondNeighbourName.setText(neighbour.getName());
        Glide.with(this)
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.centerCropTransform())
                .into(mNeighbourPicture);

        mLocation.setText(neighbour.getAddress());

        mPhone.setText(neighbour.getPhoneNumber());

        mNetwork.setText("www.facebook.com/" + neighbour.getName().toLowerCase());

        mBio.setText(neighbour.getAboutMe());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getNeighbourApiService();


        if (neighbour.isFavourite()) {
            mFavouriteButton.setImageResource(R.drawable.fav_after);
        } else {
            mFavouriteButton.setImageResource(R.drawable.fav_before);
        }

        mFavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (neighbour.isFavourite()) {
                    mFavouriteButton.setImageResource(R.drawable.fav_before);
                    mApiService.deleteFavouriteNeighbour(neighbour);
                    Toast.makeText(activity_profile_neighbour.this, "Ce voisin de fait plus partie de vos favoris !" + (" \ud83d\ude25"), Toast.LENGTH_SHORT).show();
                    neighbour.setFavourite(false);
                }   else {
                    mFavouriteButton.setImageResource(R.drawable.fav_after);
                    mApiService.addFavouriteNeighbour(neighbour);
                    Toast.makeText(activity_profile_neighbour.this, "Ce voisin fait désormais partie de vos favoris !" + (" \ud83d\ude06"), Toast.LENGTH_SHORT).show();
                    neighbour.setFavourite(true);
                }
                EventBus.getDefault().post(new DeleteFavouriteNeighbourEvent());
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                finish();
                return true;
            }
        }
        return false;
    }
}