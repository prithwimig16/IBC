package com.prithwiraj.ibc.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Prithwi on 21/07/17.
 */

public class CustomTitleTextView extends android.support.v7.widget.AppCompatTextView {


    public static final int LRTextFieldStyleMandatoryEmail      =       0;          //Default placeholder: 'Email';   Default validation: email validation regular expression
    public static final int LRTextFieldStyleEmail               =       1;          //Default placeholder: 'Email';   Default validation: email validation regular expression
    public static final int LRTextFieldStyleMandatoryPhone      =       2;
    public static final int LRTextFieldStylePhone               =       3;          //Default placeholder: 'Phone';   Default format: '###-###-####'
    public static final int LRTextFieldStylePassword            =       4;       //Default placeholder: 'Password; Default: secure text entry
    public static final int LRTextFieldStylePassword2           =       5;
    public static final int LRTextFieldStyleName                =       6;
    public static final int LRTextFieldStyleNumeric             =       7;
    public static final int LRTextFieldStyleMandatoryNumeric    =       8;
    public static final int LRTextFieldStyleMandatoryText       =       9;
    public static final int LRTextFieldStyleText                =       10;
    public static final int LRTextFieldStyleNone                =       11;           //Default style
    public static final int LRTextFieldStyleHeading             =       12;




    public CustomTitleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        isInEditMode();
    }

    public CustomTitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        isInEditMode();
    }
    public CustomTitleTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        isInEditMode();
    }

    public void setTypeface(Typeface tf, int style) {
        if(!this.isInEditMode()){

            if (style == Typeface.BOLD) {
                super.setTypeface(Config.OpenSans_Bold);
            } else {
                super.setTypeface(Config.OpenSans_Regular);
            }
            super.setTextSize(16);
        }

    }

}