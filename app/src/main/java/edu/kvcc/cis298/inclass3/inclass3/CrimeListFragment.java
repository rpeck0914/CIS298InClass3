package edu.kvcc.cis298.inclass3.inclass3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivateKey;
import java.util.List;

/**
 * Created by rpeck0914 on 11/2/2015.
 */
public class CrimeListFragment extends Fragment {

    //Class Level Variable To Hold The Recycler View.
    private RecyclerView mCrimeRecyclerView;

    //Variable To Hold An Instance Of The Adapter
    private CrimeAdapter mAdapter;

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

        //Calls The updateUI Method To Do The Work Of Getting The Data From The CrimeLab, setting
        //It Up With The Adapter, And Then Adding The Adapter To The Recycler View.
        updateUI();

        //Return The Created View
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        //Get The Collection Of Data From The crimeLab Singleton. The Get Method Constructor Requires
        //A Context Is Passed In, So We Send It The Hosting Activity Of This Fragment.
        CrimeLab crimeLab = CrimeLab.get(getActivity());

        //Get The Actual List Of Crimes From The CrimeLab Class
        List<Crime> crimes = crimeLab.getCrimes();

        //If The Adapter Hasn't Been Created Yet, We Want To Create It And Set The Adapter For The
        //Recycler View
        if(mAdapter == null) {
            //Create A New CrimeAdapter And Send It Over The List Of Crimes. Crime Adapter Needs The List
            //Of Crimes So That It Can Work With The RecyclerView To Display Them.
            mAdapter = new CrimeAdapter(crimes);

            //Takes The RecyclerView And Sets It's Adapter To The Adapter We Just Created.
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {    //Else, The Adapter Already Exists, SO We Just Need to Notify That The Data Set
                    //Might Have Changed. This Will Automatically Update Any Data Changes For Us.
            mAdapter.notifyDataSetChanged();
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Create A Class Level Variable To Hold The View For
        //This Holder.
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        private Crime mCrime;

        //Constructor That Takes In A View. The Parent Constructor
        //Is Called, And Then Passed In View Is Assigned
        //To The Class Level Version.
        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            //Do Assignment To Class Level Variables. Use The findViewById method To Get Access
            //To The Various Controls We Want To Do Work With
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }

        //Method To Take In An Instance Of A Crime, And Assign It To The Class Level Version. Then
        //Use The Class Level Version To Take Properties From The Crime And Assign Them To The
        //Various View Controls.
        public void bindCrime(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        //This Method Must Be Implemented Because We Have This Class Implementing The onClickListener
        //Interface. This method Will Do The Work Toasting The Title Of The Crime That Was Clicked On.
        @Override
        public void onClick(View view) {
            //Ask CrimeActivity For An Intent That Will Get The CrimeActivity Started. The Method Requires
            //Us To Pass The Context, Which We Can Get From Calling getActivity(), And The Id Of The Crime
            //We Want To Start The Activity With. Once We Have The Intent, We Call startActivity To Start It.
            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        //Class Level Variable To Hold The 'Data' Of Our App.
        //This Will Be The List Of Crimes
        private List<Crime> mCrimes;

        //Constructor That Takes In A List Of Crimes, And
        //Then Assigns Them To The Class Level Variable.
        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //Get A Layout inflater
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            //Inflate The View That We Would Like To Use To Display A Single List Item.
            //Right Now, It Is A Built In Android Layout Called Simple_List_Item_1
            View v = layoutInflater.inflate(R.layout.list_item_crime, parent, false);

            //Return A New CrimeHolder With The View Passed In As A Parameter
            return new CrimeHolder(v);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            //Get The Crime That Is At The Index Declared By The Variable Position
            //And Assign It To A Local Crime Variable
            Crime crime = mCrimes.get(position);

            //Send The Crime Over To The bindCrime method That We Wrote On The CrimeHolder Class.
            //That Method Does The Work Of Setting The Properties Of The Crime To The Layout
            //Controls In The Custom Layout We made.
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            //Returns The Size Of The Crime List
            return mCrimes.size();
        }
    }
}
