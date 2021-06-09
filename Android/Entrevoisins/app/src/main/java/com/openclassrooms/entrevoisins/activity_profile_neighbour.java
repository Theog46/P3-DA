package com.openclassrooms.entrevoisins;


import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;


import java.io.Serializable;
import java.util.List;

public class activity_profile_neighbour extends AppCompatActivity {

    private NeighbourApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_neighbour);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getNeighbourApiService();

        FloatingActionButton favouriteAdd = findViewById(R.id.floatingActionButton);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();

        Long neighbourID = intent.getLongExtra("ID", 0);
        int neighbourHash = intent.getIntExtra("HASHCODE", 0);
        String neighbourImage = intent.getStringExtra("image");
        String neighbourName = intent.getStringExtra("username");
        String neighbourAdress = intent.getStringExtra("adress");
        String neighbourPhone = intent.getStringExtra("phone");
        String neighbourBio = intent.getStringExtra("bio");

        if (intent != null) {
            if (extra.containsKey("image")) {
                Glide.with(this)
                        .load(neighbourImage)
                        .into((ImageView) findViewById(R.id.profileImage));
            }
            if (intent.hasExtra("username")) {
                TextView nameUser = (TextView) findViewById(R.id.firstUsername);
                nameUser.setText(neighbourName);
                TextView nameUserSecond = (TextView) findViewById(R.id.secondUsername);
                nameUserSecond.setText(neighbourName);
                TextView userFacebook = (TextView) findViewById(R.id.networkText);
                userFacebook.setText("www.facebook.com/" + neighbourName);
            }

            if (intent.hasExtra("adress")) {
                TextView userAdress = (TextView) findViewById(R.id.locationText);
                userAdress.setText(neighbourAdress);
            }
            if (intent.hasExtra("phone")) {
                TextView userPhone = (TextView) findViewById(R.id.phoneText);
                userPhone.setText(neighbourPhone);
            }

            if (intent.hasExtra("bio")) {
                TextView userBio = (TextView) findViewById(R.id.biographie);
                userBio.setText(neighbourBio);
            }
        }

        favouriteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Neighbour> listNeighbours = mApiService.getFavouriteNeighbours();

                Neighbour fNeighbour = new Neighbour(neighbourID, neighbourName, neighbourImage, neighbourAdress, neighbourPhone, neighbourBio);

                boolean NeighbourId = true;

                for (Neighbour n: listNeighbours) {
                    if (n.hashCode() == neighbourHash) {
                        NeighbourId = false;
                    }
                }
                if (NeighbourId) {
                    mApiService.addFavouriteNeighbour(fNeighbour);
                    Toast.makeText(activity_profile_neighbour.this, neighbourName + " a bien été ajouté aux favoris !", Toast.LENGTH_SHORT).show();
                    favouriteAdd.setImageResource(R.drawable.fav_after);

                }   else {
                    mApiService.deleteFavouriteNeighbour(fNeighbour);
                    Toast.makeText(activity_profile_neighbour.this, neighbourName + " a bien été retiré de vos favoris !", Toast.LENGTH_SHORT).show();
                    favouriteAdd.setImageResource(R.drawable.fav_before);
                }
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