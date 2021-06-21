package com.openclassrooms.entrevoisins.neighbour_profil;

import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NeighboursProfilTest {

    NeighbourApiService mNeighbourApiService = DI.getNewInstanceApiService();

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mListNeighbourActivity = new ActivityTestRule<>(ListNeighbourActivity.class);

    @Test
    public void
        usernameOnProfil() {

        onView(allOf(withId(R.id.list_neighbours), withParent(withId(R.id.container)))).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.firstUsername)).check(matches(withText("Caroline")));

    }

    @Test
    public void listOfFavouritesUsers() {

        onView(withId(R.id.list_neighbours)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.floatingActionButton)).perform(click());
        pressBack();

        onView(withId(R.id.list_neighbours)).perform(actionOnItemAtPosition(1, click()));
        onView(withId(R.id.floatingActionButton)).perform(click());
        pressBack();

        onView((allOf(childAtPosition(childAtPosition(withId(R.id.tabs), 0), 1)))).perform(click());

        onView(allOf(withId(R.id.container), childAtPosition(allOf(withId(R.id.main_content), childAtPosition(withId(android.R.id.content), 0)), 1), isDisplayed())).perform(swipeLeft());

        onView(allOf(withId(R.id.favourite_neighbours_list))).perform(actionOnItemAtPosition(0, click()));
        onView(allOf(withId(R.id.firstUsername))).check(matches(withText("Caroline")));
        pressBack();

        onView(allOf(withId(R.id.favourite_neighbours_list))).perform(actionOnItemAtPosition(1, click()));
        onView(allOf(withId(R.id.firstUsername))).check(matches(withText("Jack")));
        pressBack();



    }

    private static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                ViewParent mParent = item.getParent();
                return mParent instanceof ViewGroup && parentMatcher.matches(mParent)
                        && item.equals(((ViewGroup) mParent).getChildAt(position));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Child on position " + position + "in parent");
                parentMatcher.describeTo(description);
            }
        };

    }

}
