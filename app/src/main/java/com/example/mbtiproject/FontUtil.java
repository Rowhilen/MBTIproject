
package com.example.mbtiproject;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FontUtil {
    public static final String PREF = "prefs";
    public static final String KEY_BIG = "big_text";

    public static boolean isBig(Context c){
        return c.getSharedPreferences(PREF, Context.MODE_PRIVATE).getBoolean(KEY_BIG, false);
    }
    public static void setBig(Context c, boolean big){
        c.getSharedPreferences(PREF, Context.MODE_PRIVATE).edit().putBoolean(KEY_BIG, big).apply();
    }
    public static void apply(View root, boolean big){
        applyInternal(root, big);
    }
    private static void applyInternal(View v, boolean big){
        if (v instanceof ViewGroup){
            ViewGroup g = (ViewGroup)v;
            for (int i=0;i<g.getChildCount();i++) applyInternal(g.getChildAt(i), big);
        } else if (v instanceof TextView){
            TextView tv = (TextView)v;
            float base = tv.getTextSize()/tv.getResources().getDisplayMetrics().scaledDensity;
            Object tag = tv.getTag();
            float orig;
            if (tag instanceof String && ((String)tag).startsWith("orig:")){
                try{ orig = Float.parseFloat(((String)tag).substring(5)); } catch(Exception e){ orig = base; }
            } else {
                tv.setTag("orig:"+base);
                orig = base;
            }
            float target = big ? orig + 5f : orig;
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, target);
        }
    }
}
