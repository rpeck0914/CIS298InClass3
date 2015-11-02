package edu.kvcc.cis298.inclass3.inclass3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rpeck0914 on 11/2/2015.
 */
public class CrimeListFragment extends Fragment {

    //Class Level Variable To Hold The Recycler View.
    private RecyclerView mCrimeRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Get The View From The Layouts That Will Be Displayed.
        //Use The Inflater to Inflate The Layout To Java Code.
        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);

        //Get A Reference To The Recycler View In The Layout File
        //Remember That We Have To Call findViewById On The View
        //That We Created Above. It Is Not An Automatic Method
        //like It Was For An Activity
        mCrimeRecyclerView = (RecyclerView) v.findViewById(R.id.crime_recycler_view);

        //The Recycler View Requires That It Is Given A Layout
        //Manager. The RecyclerView Is A Fairly New Control, And
        //Is Not Capable Of Displaying The List Items On The Screen.
        //A LinearLayoutManager Is Required To Do That Work.Therefore
        //We Create A new LinearLayoutManager, And PAss It The Context
        //Of Which It Needs To Operate It. The Context Is Passed By Using
        //The getActivity Method. Which Gets The Activity That Is
        //Hosting This Fragment.
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Return The Created View
        return v;
    }
}
