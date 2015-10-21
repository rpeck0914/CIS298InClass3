package edu.kvcc.cis298.inclass3.inclass3;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CrimeFragment extends Fragment {

    //Declare A Class Level Variable For A Crime
    private Crime mCrime;

    //This Method Does Not Do The Inflating Of The View
    //Like the onCreate For An Activity Does
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Create A New Instance Of A Crime
        mCrime = new Crime();
    }

    //This Method Is Responsible For Inflating The View
    //And Getting The Content On The Scree.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        return v;
    }
}
