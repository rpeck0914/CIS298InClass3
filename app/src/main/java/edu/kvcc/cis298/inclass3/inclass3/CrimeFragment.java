package edu.kvcc.cis298.inclass3.inclass3;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;


public class CrimeFragment extends Fragment {

    //Declare A Class Level Variable For A Crime
    private Crime mCrime;

    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckbox;

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

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //This Space Intentionally Left Blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //This One Too
            }
        });

        //Find The Date Button
        mDateButton = (Button) v.findViewById(R.id.crime_date);
        //Set The Text On The Date Button To The Date From The Crime
        //Model Converted To A String
        mDateButton.setText(mCrime.getDate().toString());
        //Disable The Button So It Doesn't Do Anything Until We Wire
        //It Up To Do Something
        mDateButton.setEnabled(false);

        //Get A Handle To The Checkbox
        mSolvedCheckbox = (CheckBox) v.findViewById(R.id.crime_solved);
        //Set The On Check Changed Listener. Checkbox Is A Subclass Of The
        //CompoundButton Class. That Is Why We Use That Class To Setup The
        //New Listener.
        mSolvedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //Set The Solved Bool On The Model To The Result Of The Check Changed
                mCrime.setSolved(b);
            }
        });

        return v;
    }
}
