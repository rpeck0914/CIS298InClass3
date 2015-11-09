package edu.kvcc.cis298.inclass3.inclass3;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    //Setup A String Const For The Key Part Of The Extra
    private static final String EXTRA_CRIME_ID = "edu.kvcc.cis298.inclass3.inclass3.crime_id";

    //This Method Is Public And Static So That ANY Other Activity Or Fragment That Might Want To Start
    //This Activity Can Get A Properly Formatted Intent That Will Allow This Activity To Start Successfully
    public static Intent newIntent(Context packageContext, UUID crimeId) {
        //Make New Intent
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        //Put The Passed crimeId As An Extra Using The Key Declared Above
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        //Return The Intent
        return intent;
    }

    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return new CrimeFragment().newInstance(crimeId);
    }
}
