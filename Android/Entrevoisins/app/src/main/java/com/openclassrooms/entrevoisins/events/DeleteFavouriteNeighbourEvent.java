package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

public class DeleteFavouriteNeighbourEvent {

    /**
     * Neighbour to delete
     */
    public Neighbour favouriteNeighbour;

    /**
     * Constructor.
     * @param neighbour
     */
    public DeleteFavouriteNeighbourEvent(Neighbour neighbour) {
        this.favouriteNeighbour = neighbour;
    }
}
