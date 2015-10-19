package edu.kvcc.cis298.inclass3.inclass3;

import java.util.UUID;

/**
 * Created by rpeck0914 on 10/19/2015.
 */
public class Crime {

    //Private Variables For Our Models
    private UUID mId;
    private String mTitle;

    //Default Constructor
    public Crime(){
        //Make A New Unique Id For This Particular Crime
        mId = UUID.randomUUID();
    }

    //Getters And Setters
    //Only Need To Get The UUID, No Need To Set It, So No Setter
    public UUID getmId() {
        return mId;
    }

    //Getter And Setter For The Title
    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
