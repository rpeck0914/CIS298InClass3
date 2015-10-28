package edu.kvcc.cis298.inclass3.inclass3;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rpeck0914 on 10/28/2015.
 */
public class CrimeLab {

    //Static Variable To Hold The Instance Of CrimeLab
    //Rather Than Returning A New Instance Of CrimeLab
    //We Will Return This Variable That Holds Our Instance
    private static CrimeLab sCrimeLab;

    //A Variable Of TYPE List, Which Is An Interface, To Hold
    //A List Of TYPE Crime.
    private List<Crime> mCrimes;

    //This Is The Method That Will Be used To Get An Instance Of
    //CrimeLab. It Will Check To See If The Current Instance In The
    //Variable Is Null, And If It Is, It Will Create A New One Using
    //The Private Constructor. If It Is Not Null, It Will Just Return
    //The Instance That Exists.
    public static CrimeLab get(Context context) {
        if(sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    //This Is The Constructor. It Is Private Rather Than Public
    //It Is Private Because We Don't Want People To Be Able To
    //Create A New Instance From Outside Classes. If They Want
    //To Make An Instance, We Want Them To Use The Get Method
    //Declared Right Above Here
    private CrimeLab(Context context){
        //Instantiates A New ArrayList, Which Is A Child Class That
        //Implements The Interface List. Because ArrayList Is A Child
        //Of List, We Can Store It In The mCrimes Variable Which Is Of
        //Type List, And Not ArrayList. (Polymorphism)
        mCrimes = new ArrayList<>();

        //For Loop To Populate Our ArrayList With Some Dummy Data.
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
    }

    //Getter To Get The Crimes
    public List<Crime> getCrimes() {
        return mCrimes;
    }

    //Method To Get A Specific Crime Based on The
    //UUID That Is Passed In.
    public Crime getCrime(UUID id) {
        //This Is A ForEach Loop To Go Through All Of The Crimes
        //At Each Iteration
        for(Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}
