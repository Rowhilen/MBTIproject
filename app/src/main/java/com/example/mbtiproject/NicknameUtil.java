package com.example.mbtiproject;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

public final class NicknameUtil {
    private NicknameUtil(){}
    private static final String[] CANDIDATES = new String[] {
        "두근거리는시계","말랑이는헤드셋","딱딱한오므라이스","향기로운젤리"
    };
    public static String getOrCreateNickname(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences(AppConstants.PREFS, Context.MODE_PRIVATE);
        String nick = sp.getString(AppConstants.KEY_CURRENT_NICKNAME, null);
        if (nick == null || nick.isEmpty()) {
            nick = CANDIDATES[new Random().nextInt(CANDIDATES.length)];
            sp.edit().putString(AppConstants.KEY_CURRENT_NICKNAME, nick).apply();
        }
        return nick;
    }
}
