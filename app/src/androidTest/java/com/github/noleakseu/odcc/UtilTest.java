package com.github.noleakseu.odcc;

import com.google.iot.cbor.CborMap;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UtilTest {
    private CborMap cborMap;

    @Before
    public void setUp() throws Exception {
        JSONObject json = new JSONObject("{\"-260\":{\"1\":{\"t\":[{\"co\":\"BE\",\"is\":\"European Medicines Agency\"}],\"nam\":{\"fnt\":\"MUSTERMANN\",\"fn\":\"MUSTERMANN\",\"gnt\":\"MAX\",\"gn\":\"MAX\"},\"ver\":\"1.3.0\",\"dob\":\"1999-30-12\"}}}");
        cborMap = CborMap.createFromJSONObject(json);
    }

    @Test
    public void getDatesOfBirth() {
        Assert.assertEquals("1999-30-12", Util.getDatesOfBirth(cborMap));
    }

    @Test
    public void getNames() {
        Assert.assertEquals("MAX MUSTERMANN", Util.getNames(cborMap));
    }
}