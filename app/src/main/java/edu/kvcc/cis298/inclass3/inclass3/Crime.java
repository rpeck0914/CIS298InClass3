package edu.kvcc.cis298.inclass3.inclass3;

import java.util.Date;
import java.util.UUID;

/**
 * Created by rpeck0914 on 10/19/2015.
 */
public class Crime {

    //Private Variables For Our Models
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    //Default Constructor
    public Crime(){
        //Make A New Unique Id For This Particular Crime
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Crime(UUID id, String title, Date date, boolean solved) {
        mId = id;
        mTitle = title;
        mDate = date;
        mSolved = solved;
    }

    //Getters And Setters
    //Only Need To Get The UUID, No Need To Set It, So No Setter

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
