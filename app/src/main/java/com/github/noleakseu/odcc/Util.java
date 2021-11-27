package com.github.noleakseu.odcc;

import android.text.TextUtils;

import com.google.iot.cbor.CborMap;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class Util {
    private static final int DCC_INT = -260;
    private static final String DCC_STRING = "-260";

    public static String getDatesOfBirth(CborMap cborMap) {
        LinkedList<String> list = new LinkedList<>();
        for (Map.Entry<Integer, LinkedHashMap<String, Object>> item : getDCC(cborMap).entrySet()) {
            String dob = (String) item.getValue().get("dob");
            list.add(dob);
        }
        return TextUtils.join(", ", list);
    }

    public static String getNames(CborMap cborMap) {
        LinkedList<String> list = new LinkedList<>();
        for (Map.Entry<Integer, LinkedHashMap<String, Object>> item : getDCC(cborMap).entrySet()) {
            LinkedHashMap<String, String> nam = ((LinkedHashMap<String, String>) item.getValue().get("nam"));
            list.add(nam.get("gn") + " " + nam.get("fn"));
        }
        return TextUtils.join(", ", list);
    }

    private static LinkedHashMap<Integer, LinkedHashMap<String, Object>> getDCC(CborMap cborMap) {
        LinkedHashMap<Integer, LinkedHashMap<String, Object>> dcc = (LinkedHashMap<Integer, LinkedHashMap<String, Object>>) cborMap.toJavaObject().get(DCC_INT);
        if (null == dcc) {
            dcc = (LinkedHashMap<Integer, LinkedHashMap<String, Object>>) cborMap.toJavaObject().get(DCC_STRING);
        }
        return dcc;
    }
}
