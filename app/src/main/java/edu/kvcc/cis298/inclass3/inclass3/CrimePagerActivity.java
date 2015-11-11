package edu.kvcc.cis298.inclass3.inclass3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

/**
 * Created by rpeck0914 on 11/11/2015.
 */
public class CrimePagerActivity extends FragmentActivity {

    //Variable To Get Us Access To The ViewPager Widget In The View
    private ViewPager mViewPager;
    //Variable To Hold All Of Our Crime Records That The ViewPager Will Need To Use
    private List<Crime> mCrimes;

    //Setup A String Const For The Key Part Of The Extra
    private static final String EXTRA_CRIME_ID = "edu.kvcc.cis298.inclass3.inclass3.crime_id";

    //This Method Is Public And Static So That ANY Other Activity Or Fragment That Might Want To Start
    //This Activity Can Get A Properly Formatted Intent That Will Allow This Activity To Start Successfully
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        //Make New Intent
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        //Put The Passed crimeId As An Extra Using The Key Declared Above
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        //Return The Intent
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set The Content Of This Activity To The Pager Layout
        setContentView(R.layout.activity_crime_pager);


        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        //Get The View From The Layout
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        //Get A Reference To Our Crime Collection. This Is The Singleton. The Get method Returns The
        //Single Instance Of The Crime Lab That We Want To Use.
        mCrimes = CrimeLab.get(this).getCrimes();

        //Create A New FragmentManager. The Adapter That We Are Going To Use To Wire Up Our Data And
        //The ViewPager Requires Us To Supply A Fragment Manager. it Needs The Manager So That It Can
        //Do The Work Of Swapping Out One CrimeFragment, And Loading A New One. We Did This Ourselves
        //With The Fragments That We Were Loading, However The ViewPager will Do This For Us Automatically
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Set The Adapter For The ViewPager With A Unnamed FragmentStatPagerAdapter. There Are 2 methods
        //That We Have To Override to Finish It out.
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            //Get A Specific Item From The Crimes Collection
            @Override
            public Fragment getItem(int position) {
                //Get The Specific Crime
                Crime crime = mCrimes.get(position);
                //Return A New Instance Of A Crime Fragment Using The Static Method On The CrimeFragment
                //Class That We Created.
                return CrimeFragment.newInstance(crime.getId());
            }

            //Get The Count For The Size Of The Crime Collection
            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        //For Each Crime In The Crimes List
        for(int i = 0; i < mCrimes.size(); i++) {
            //Check To See if The Id Of The Current Crime Matches The One That Was Sent In From The
            //CrimeListActivity That Started This Activity. If So, That Is The One That We Want To Show
            //First.
            if(mCrimes.get(i).getId().equals(crimeId)) {
                //Set The Current Item Of The ViewPager to The One That Was Sent From The List View
                mViewPager.setCurrentItem(i);
                //Since We Found Our Match, No Need To keep Looping. Other Ways This Could Have Been
                //Done Without A Break.
                break;
            }
        }
    }
}
