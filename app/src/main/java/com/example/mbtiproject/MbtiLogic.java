package com.example.mbtiproject;

import java.util.HashMap;
import java.util.Map;

public final class MbtiLogic {
    private MbtiLogic(){}

    public static String computeMbti(int e, int i, int n, int s, int f, int t, int p, int j) {
        char a = (e>=i)?'E':'I';
        char b = (n>=s)?'N':'S';
        char c = (f>=t)?'F':'T';
        char d = (p>=j)?'P':'J';
        return "" + a + b + c + d;
    }

    public static String partnerFor(String userMbti) {
        if (userMbti == null) return null;
        userMbti = userMbti.toUpperCase();
        Map<String,String> m = new HashMap<>();
        m.put("ENTJ","ISFP"); m.put("ENTP","ISTJ"); m.put("INTJ","ESFP"); m.put("INTP","ESFJ");
        m.put("ESTJ","INFP"); m.put("ESFJ","INTP"); m.put("ISTJ","ENFP"); m.put("ISFJ","ENTP");
        m.put("ENFJ","ISTP"); m.put("ENFP","ISTJ"); m.put("INFJ","ESTJ"); m.put("ESTP","INFJ");
        m.put("ESFP","INTJ"); m.put("ISTP","ENFJ"); m.put("ISFP","ENTJ"); m.put("INFP","ESTJ");
        return m.get(userMbti);
    }
}
