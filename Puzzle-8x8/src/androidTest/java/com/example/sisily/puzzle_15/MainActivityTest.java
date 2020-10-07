package com.example.sisily.puzzle_15;

/**
 * Created by sisily on 15/02/18.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.LinearLayout;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureLinearLayoutIsPresent() throws Exception {
        MainActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.mainLayout);
        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(LinearLayout.class));
       // LinearLayout listView = (LinearLayout) viewById;
       // ListAdapter adapter = listView.getAdapter();
        //assertThat(adapter, instanceOf(ArrayAdapter.class));
        //assertThat(adapter.getCount(), greaterThan(5));

    }
    /* @Test
   public void testIsConnected() throws Exception {
        Context context = rule.getActivity().getBaseContext();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean connected = cm.getActiveNetworkInfo().isConnectedOrConnecting();
        Assert.assertEquals(connected, ConnectionUtils.isConnected(context));
    }*/
}
