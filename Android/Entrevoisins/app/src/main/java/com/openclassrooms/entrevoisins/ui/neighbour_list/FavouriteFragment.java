package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteFavouriteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {

    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private RecyclerView mRecyclerView;
    private MyNeighbourRecyclerViewAdapter.OnNeighbourClickListener mOnNeighbourClickListener;

    public static FavouriteFragment newInstance() {
        FavouriteFragment fragment = new FavouriteFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnNeighbourClickListener = (MyNeighbourRecyclerViewAdapter.OnNeighbourClickListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mApiService = DI.getNeighbourApiService();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite_neighbour, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        initList();
        return view;
    }
    
    private void initList() {
        mNeighbours = mApiService.getNeighbours();
        List<Neighbour> favouriteNeighbours = new ArrayList<>();

        for (Neighbour neighbour : mNeighbours) {
            if (neighbour.isFavourite()) {
                favouriteNeighbours.add(neighbour);
            }
        }
        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(favouriteNeighbours, mOnNeighbourClickListener));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        mApiService.deleteNeighbour(event.neighbour);
        initList();
    }

    @Subscribe
    public void onFavouriteStateChange(DeleteFavouriteNeighbourEvent neighbourOnFavouriteChange) {
        initList();
    }
}
