package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();




    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    @Override
    public List<Neighbour> getFavourites() {
        ArrayList<Neighbour> favNeighbours = new ArrayList<>();
        for (Neighbour i: neighbours) {
            if (i.getFavourite()) {
                favNeighbours.add(i);
            }
        }
        return favNeighbours;
    }

    @Override
    public void deleteFavouriteNeighbour(Neighbour neighbour) {
        for (Neighbour mNeighbour : neighbours) {
            if (mNeighbour.equals(neighbour)) {
                mNeighbour.setFavourite(false);
            }
        }
    }
    
    @Override
    public void addFavouriteNeighbour(Neighbour neighbour) {
        for (Neighbour mNeighbour : neighbours) {
            if (mNeighbour.equals(neighbour)) {
                mNeighbour.setFavourite(true);
            }
        }
    }

}