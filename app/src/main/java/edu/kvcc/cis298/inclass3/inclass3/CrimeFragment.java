package edu.kvcc.cis298.inclass3.inclass3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;


public class CrimeFragment extends Fragment {

    //Static String To Be Used As The Key For Parameters We Set And Retrieve From The Bundle
    private static final String ARG_CRIME_ID = "crime_id";
    //Used As A Unique Identifier For The Dialog Fragment. It Will Be Used To Ensure That The Fragment
    //Manager Knows Which Fragment We Are Trying To Use
    private static final String DIALOG_DATE = "DialogDate";

    //Setup A Request Code That Will Be Used When Sending The Result Of The Dialog To The onActivityResult
    //To Know That It Is Coming From The Result Of The Dialog And Not Some Other Activity. That's Why We
    //Need This Code.
    private static final int REQUEST_DATE = 0;

    //Declare A Class Level Variable For A Crime
    private Crime mCrime;

    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckbox;

    //This Is A Static Method That Is Used To Create A New Instance Of A CrimeFragment With The Correct
    //Information Of A Crime Based On A UUID That Is Passed In. Any Activity Including The One We Are
    //Using (Crime Activity) Can Call This Method And Get A Properly Created CrimeFragment. The Method
    //Takes The UUID That Is Passed In, And Then Sets It In An Argument Bundle That Can Be passed Along
    //With The Fragment. Once The Fragment Is Started, The Data In The Bundle Can Be Retrieved And Used.
    public static CrimeFragment newInstance(UUID crimeId) {
        //Create A New Arguments Bundle
        Bundle args = new Bundle();
        //Put The UUID In As A Value To The Bundle
        args.putSerializable(ARG_CRIME_ID, crimeId);

        //Create A New Instance Of This Fragment
        CrimeFragment fragment = new CrimeFragment();
        //Set The Arguments On The Fragment With The Bundle
        fragment.setArguments(args);

        //Finally Return The Fragment That Was Created
        return fragment;
    }

    //This Method Does Not Do The Inflating Of The View
    //Like the onCreate For An Activity Does
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
        //Pulls The UUID From The Crime That The User Selected So We Can Then Search For The Selected Crime
                UUID crimeId = (UUID) getActivity().getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
         */

        //Retrieves The UUID From The Arguments That Was Set
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);

        //Retrieves The Crime Selected By Sending The UUID Over To The CrimeLab To Return The Correct Crime
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    //This Method Is Responsible For Inflating The View
    //And Getting The Content On The Scree.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
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
        updateDate();

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get A Fragment Manager That Will Be Required To Display The Dialog
                FragmentManager manager = getFragmentManager();
                //Ask The DatePickerFragment Class To Supply Us With A New Fragment That Can Be Used
                //To Show The Dialog. The newInstance Method Requires That We Pass A Date, And So We
                //Send It The Crime Date. We Wrote This Method In The DatePickerFragment Class
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());

                //Tell The Dialog What It's Return Target Is. We Know That This Dialog Will Return
                //To The CrimeFragment, And So That Is What We Are Telling It. We Also Pass A Request
                //Code That Is Declared At The Top Of This Class.
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);

                //Call The Show Method On The Dialog Fragment To Show The Dialog On The Screen
                dialog.show(manager, DIALOG_DATE);
            }
        });

        //Get A Handle To The Checkbox
        mSolvedCheckbox = (CheckBox) v.findViewById(R.id.crime_solved);
        //Set The On Check Changed Listener. Checkbox Is A Subclass Of The
        //CompoundButton Class. That Is Why We Use That Class To Setup The
        //New Listener.
        mSolvedCheckbox.setChecked(mCrime.isSolved());
        mSolvedCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //Set The Solved Bool On The Model To The Result Of The Check Changed
                mCrime.setSolved(b);
            }
        });

        return v;
    }

    //This method will be called when the result of an activity returns back to this hosted activity
    //Note that this method is part of the fragment. Fragments have their own 'copy' of on ActivityResult
    //The that will get called when the hosting Activities onActivityResult is called
    //
    //We are not doing any work here from return to this Activity. Instead, we are making an explicit
    //call to this method to get the results of the date picker dialog. over the datePickerFragment
    //class, on the onClickListener for the ok button, we make an explicit call to this method
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Do a check to see if the rsult code is OK. it shoudl always be okay with what we have written
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        //Since we can count on the Result being OK, we need to do the work of checking the request code,
        //and making sure that it matches the request code we sent when we started up the date picker
        //dialog. In other words, we only want to do this work if we are coming back from the date dialog.
        if (requestCode == REQUEST_DATE) {
            //Create a new date by fetching the date extra out of the intent that we used to send
            //data to this method.
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            //Set the date on the Model
            mCrime.setDate(date);
            //Call the method that we extracted with refactor to update the text on the button that starts
            //the date picker.
            updateDate();
        }
    }

    //Method that we extracted from a duplicated line to update the date on the method that launches
    //the date picker
    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }
}
