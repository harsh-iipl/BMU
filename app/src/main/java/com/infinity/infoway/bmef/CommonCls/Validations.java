package com.infinity.infoway.bmef.CommonCls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Patterns;

public class Validations {
    public static Typeface setTypeface(Context ctx) {
//        Typeface font = Typeface.createFromAsset(
//                ctx.getAssets(),
//                "fonts/Helvetica_guj.ttf");
        Typeface font = Typeface.createFromAsset(
                ctx.getAssets(),
                "fonts/PoppinsRegular.otf");

     /*   Typeface font = Typeface.createFromAsset(
                ctx.getAssets(),
                "fonts/HelveticaNeue.ttf");*/

        return font;
    }


    /*VALIDATION FOR EMAIL ADDRESS*/
    public final static boolean isValidEmail(String s) {
        if (s == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(s).matches();
        }
    }
}
