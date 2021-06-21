package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;


    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void addNeighbourWithSuccess() {
        Neighbour newNeighbour = new Neighbour(13, "Test", "https://i.pravatar.cc/150?u=a042581f4e29026704d", false, "Adresse test", "0102030405", "Description test");
        service.createNeighbour(newNeighbour);
        assertTrue(service.getNeighbours().contains(newNeighbour));
    }

    @Test
    public void getNeighbourProfilWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(0);
        assertNotNull(neighbour.getId());
        assertNotNull(neighbour.getAvatarUrl());
        assertNotNull(neighbour.getName());
        assertNotNull(neighbour.getPhoneNumber());
        assertNotNull(neighbour.getAddress());
        assertNotNull(neighbour.getAboutMe());
        assertFalse(neighbour.isFavourite());
    }

    @Test
    public void getFavouriteNeighbourWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(3);
        neighbour.setFavourite(true);
        for(Neighbour i: service.getNeighbours()) {
            if (i.getFavourite()) {
                assertTrue(service.getFavourites().contains(i));
            }   else  {
                assertFalse(service.getFavourites().contains(i));
            }
        }
    }

    @Test
    public void addNeighbourToFavouriteWithSuccess() {
        Neighbour NewFavNeighbour = service.getNeighbours().get(2);
        service.addFavouriteNeighbour(NewFavNeighbour);
        assertTrue(service.getFavourites().contains(NewFavNeighbour));
    }

    @Test
    public void removeNeighbourToFavouriteWithSuccess() {
        Neighbour NewFavNeighbour = service.getNeighbours().get(4);
        service.addFavouriteNeighbour(NewFavNeighbour);
        assertTrue(service.getFavourites().contains(NewFavNeighbour));
        service.deleteFavouriteNeighbour(NewFavNeighbour);
        assertFalse(service.getFavourites().contains(NewFavNeighbour));
    }

}
