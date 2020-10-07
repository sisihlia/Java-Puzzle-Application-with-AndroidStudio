package com.example.sisily.puzzle_15;

/**
 * Created by sisily on 15/02/18.
 */

import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.example.sisily.puzzle_15.HeuristicSearch.Manhattan;
import com.example.sisily.puzzle_15.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class PuzzleTestActivity extends ActivityInstrumentationTestCase2<MainActivity>  {

    private MainActivity mActivity;
    private TextView mView;
    private String resourceString;

    public PuzzleTestActivity() {
        super("com.example.sisily.puzzle_15", MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = this.getActivity();
        mView = (TextView) mActivity.findViewById
                (R.id.moves);

    }

    public void testPreconditions() {
        assertNotNull(mView);
    }

    @Test
    public void testsolutionByManh () {
        String str =  "208635147";
        String goal = "123456780";
        Manhattan man = new Manhattan (str,goal);
        String solution = man.findSolution();
        assertEquals(goal, solution);
    }
}
