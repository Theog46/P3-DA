package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    private List<Neighbour> favouriteNeighbour = DummyNeighbourGenerator.ListFavouriteNeighbours();


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
    public List<Neighbour> getFavouriteNeighbours() {

        return favouriteNeighbour;
    }


    public void deleteFavouriteNeighbour(Neighbour neighbour) {
        favouriteNeighbour.remove(neighbour);
    }

    @Override
    public void addFavouriteNeighbour(Neighbour neighbour) {
        favouriteNeighbour.add(neighbour);
    }
}