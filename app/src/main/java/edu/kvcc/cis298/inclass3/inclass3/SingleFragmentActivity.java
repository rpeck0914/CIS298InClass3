package edu.kvcc.cis298.inclass3.inclass3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by rpeck0914 on 11/2/2015.
 */
public abstract class SingleFragmentActivity extends FragmentActivity {

    //This Method Is Declared As Abstract So That Every Activity
    //That We Create Must Implement This Method. Instead Of Having
    //Every Activity That We Create Inherit From FragmentActivity
    //It Will Inherit From THIS Class, Which Now Provides Some
    //Base Functionality For Us. We Will No Longer Need To Duplicate
    //This Code In Any Child Activities That We Create.
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        //Get A Variable That Represents The Support Version
        //of The Fragment Manager. If The Android OS Version
        //Of Fragment Manager Is Imported, This Statement Will
        //Fail. Must Have Support Version Imported.
        FragmentManager fm = getSupportFragmentManager();

        //Use The Fragment Manager To Get The Fragment That Is Currently
        //In The Fragment That We Created In The crimeFragment.xml
        //On The Start Of The App, This Will Be Null Since There
        //Won't Be Anything There Until We Add It. Once Something is
        //Added, This Method Will Return Whatever Is In It.
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        //Check To See If The Fragment Is Null. On Starting The App, It
        //Will Be. But Once We Add A Fragment To The Frame, It Will Not Be.
        if (fragment == null) {
            //Since It Is Empty, Lets Create A New Instance Of The
            //CrimeFragment.
            fragment = createFragment();
            //Now Go Through The Work Of Adding It To The Frame.
            //This Is Done By Starting A Transaction, Then Adding The
            //Fragment We Would Like To Attach, And Then Committing The
            //Changes.
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
