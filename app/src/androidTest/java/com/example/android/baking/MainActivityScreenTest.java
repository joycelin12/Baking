package com.example.android.baking;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by joycelin12 on 7/18/18.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    public static final String RECIPE_INGREDIENTS = "2 CUP Graham Cracker crumbs\n" +
            "6 TBLSP unsalted butter, melted\n" +
            "0.5 CUP granulated sugar\n" +
            "1.5 TSP salt\n" +
            "5 TBLSP vanilla\n" +
            "1 K Nutella or other chocolate-hazelnut spread\n" +
            "500 G Mascapone Cheese(room temperature)\n" +
            "1 CUP heavy cream(cold)\n" +
            "4 OZ cream cheese(softened)\n";

    /**
     * The ActivityTestRule is a rule provided by Android used for functional testing of a single
     * activity. The activity that will be tested will be launched before each test that's annotated
     * with @Test and before methods annotated with @Before. The activity will be terminated after
     * the test and methods annotated with @After are complete. This rule allows you to directly
     * access the activity during the test.
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Clicks on a GridView item and checks it opens up the OrderActivity with the correct details.
     */
    @Test
    public void clickGridViewItem_OpensRecipeDetailActivity() {

        // Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific
        // gridview item and clicks it.
        //onData(anything()).inAdapterView(withId()).atPosition(0).perform(click());
        onView(withId(R.id.recipe_main_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Checks that the RecipeDetailActivity opens with the correct recipe displayed
        onView(withId(R.id.ingredients_text_view)).check(matches(withText(RECIPE_INGREDIENTS)));


    }




}
