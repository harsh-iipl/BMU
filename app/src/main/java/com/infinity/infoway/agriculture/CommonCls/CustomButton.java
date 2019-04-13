package com.infinity.infoway.agriculture.CommonCls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by ADMIN on 30-04-2018.
 */

public class CustomButton extends Button {

    public CustomButton(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public CustomButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        applyCustomFont(context);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context)
    {
        //  Typeface customFont = FontCache.getTypeface("SourceSansPro-Regular.ttf", context);
        Typeface font = Typeface.createFromAsset(
                context.getAssets(),
                "fonts/PoppinsRegular.otf");
        setTypeface(font);
     //   setTextColor(Color.WHITE);
     //   setHintTextColor(Color.GRAY);

    }
}