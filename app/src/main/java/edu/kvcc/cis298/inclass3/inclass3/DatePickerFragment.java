package edu.kvcc.cis298.inclass3.inclass3;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by rpeck0914 on 11/16/2015.
 */
public class DatePickerFragment extends DialogFragment {

    //Create A String That Can Be Used As A Key For An Extra On An Intent. Because This Extra Will Be
    //Operating At The Activity Level, It Needs To Be Very Unique And Therefore Contains The Package
    //Name As Part Of The String.
    public static final String EXTRA_DATE = "edu.kvcc.cis298.inclass3.inclass3.date";

    private static final String ARG_DATE = "date";
    //Class Level Variable For The DatePicker
    private DatePicker mDatePicker;

    //Public Static Method That Will Return A Properly Formatted Fragment That Can Be Displayed.
    public static DatePickerFragment newInstance(Date date) {
        //Create A Bundle That Can Be Attached As Data To The Fragment
        Bundle args = new Bundle();
        //Adds The Date As A Serializable To The Bundle Object
        args.putSerializable(ARG_DATE, date);

        //Create A New DatePickerFragment Object
        DatePickerFragment fragment = new DatePickerFragment();
        //Attach The Bundle That Contains The Data To The Object
        fragment.setArguments(args);

        //Return The Completed Fragment
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Use The Arguments To Get The Serializable Date. This Date Will Come Out Of The Arguments As
        //A Serializable Interface Type And Will Therefore Need To Be Cast To A Date Object.
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        //Make A Calendar Object That Can Do The Work Of Translating A Date (Which Is A Timestamp) Into
        //A Format That Is Readable By Humans (Year, Month, Day)
        Calendar calendar = Calendar.getInstance();
        //Set The Time For Calendar To Be The Date That Was Passed In
        calendar.setTime(date);

        //Pull Out The Parts We Need From The Calendar Object
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //Use The LayoutInflater To Inflate The Date Dialog Layout That We Would Like To Use Inside
        //Our Dialog
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        //Get A Reference To The Date Picker Layout
        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);

        //Assign The Calendar Variables That We pulled Out To The DatePicker So That It Has A Default
        //Date.
        mDatePicker.init(year, month, day, null);

        //Return A New Dialog That Is Built From Calling The Builder Method, And Then Subsequent Chained
        //Methods. Each Chained Method Adds More Information About The Dialog To It.
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    //Make A New onClickListener For The Positive button

                    //Setup The OnClick Method
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Get each Part Of The Date Out Of The Date Picker
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();

                        //Create A New Date To Send To Send To The sendResult Method. In Order To Do This,
                        //We Need To Use The GregorianCalendar Class To Create A GregorianCalendar. With The
                        //Calendar created, we can call the getTime Method on its so that a timestamp is returned.
                        //the date object is expecting a timestamp, not a year, month, date or GregorianCalendar
                        //object.
                        Date date = new GregorianCalendar(year, month, day).getTime();
                        //Date the date we just made and send it along with a Result OK to the sendResult
                        //method created at the bottom of this file. SendResult will explicitly call
                        //onActivityResult with this information for the activity that is the target of this dialog
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, Date date) {
        //The Target Fragment Was Set Over In The CrimeFragment Class When We Wired Up The Date Button
        //To Start This Dialog Fragment. Because It Was Set Over There Before Starting This Fragment,
        //This Decision Below Should Always Fail. But Just In Case, We Are Putting In A Check.
        if (getTargetFragment() == null) {
            return;
        }

        //Since We Now Know That There Is In Fact A Target Fragment, We Can Use That Fragment To Do Work
        Intent intent = new Intent();
        // Add The Date We Want To Send Back As An Extra With The putExtra On The Intent Object
        intent.putExtra(EXTRA_DATE, date);

        //Use The getTargetFragment To Ge The Destination Of The Return From This Fragment, And Explicitly
        // Call The onActivityResult Method. Pass Over The Target Request Code That Was Sent When The
        //Target Fragment Was Set, The Result Code That Got Passed Into This Function And The Intent That
        //Has The Data WE Just Put Together.
        //
        //Remember That When An Activity Returns On It's Own, onActivityResult Is Called Implicitly By
        //The Android OS. Since We Are Not Returning From An Activity We Have To Do The Work Calling
        //That Method Explicitly.
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
