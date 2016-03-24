package com.zfer.kit;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
/**
 * The <code>StrKitTest</code> class is TestClass of StrKit.
 * @author yunshan
 * @version %I%, %G%
 * @since 1.0
 */
public class StrKitTest {
    String strNull;
    @Before
    public void setUp(){
        strNull = null;
    }


    @Test
    public void testFirstCharToLowerCase(){
        String changedStr = StrKit.firstCharToLowerCase("klsdlD");
        assertEquals("klsdlD",changedStr);

        String changedStr1 = StrKit.firstCharToLowerCase("DlsdlD");
        assertEquals("dlsdlD",changedStr1);

        String changedStr2 = StrKit.firstCharToLowerCase("2DlsdlD");
        assertEquals("2DlsdlD",changedStr2);

        String changedStr3 = StrKit.firstCharToLowerCase("#2DlsdlD");
        assertEquals("#2DlsdlD",changedStr3);
    }

    @Test
    public void testFirstCharToUpperCase(){
        String changedStr = StrKit.firstCharToUpperCase("klsdlD");
        assertEquals("KlsdlD",changedStr);

        String changedStr1 = StrKit.firstCharToUpperCase("DlsdlD");
        assertEquals("DlsdlD",changedStr1);

        String changedStr2 = StrKit.firstCharToUpperCase("2DlsdlD");
        assertEquals("2DlsdlD",changedStr2);

        String changedStr3 = StrKit.firstCharToUpperCase("#2DlsdlD");
        assertEquals("#2DlsdlD",changedStr3);
    }

    @Test
    public void testIsBlank(){
        assertEquals(true,StrKit.isBlank(" "));
        assertEquals(true,StrKit.isBlank(""));
        assertEquals(true,StrKit.isBlank((String)null));

        assertEquals(false,StrKit.isBlank("xx"));

        assertEquals(true,StrKit.isBlank("xx","","yy"));
        assertEquals(true,StrKit.isBlank("xx",null,"yy"));
        assertEquals(false,StrKit.isBlank("xx","xx","yy"));
    }

    @Test
    public void testNotBlank(){
        assertEquals(false,StrKit.notBlank(" "));
        assertEquals(false,StrKit.notBlank(""));
        assertEquals(false,StrKit.notBlank((String) null));

        assertEquals(true,StrKit.notBlank("xx"));

        assertEquals(false,StrKit.notBlank("xx","","yy"));
        assertEquals(false,StrKit.notBlank("xx",strNull,"yy"));
        assertEquals(true,StrKit.notBlank("xx","xx","yy"));
    }

    @Test
    public void testIsNull(){
        assertEquals(false,StrKit.isNull(" "));
        assertEquals(false,StrKit.isNull(""));
        assertEquals(true,StrKit.isNull(strNull));

        assertEquals(false,StrKit.isNull("xx"));

        assertEquals(false,StrKit.isNull("xx","","yy"));
        assertEquals(true,StrKit.isNull("xx",null,"yy"));
        assertEquals(false,StrKit.isNull("xx","xx","yy"));
    }

    @Test
    public void testNotNull(){
        assertEquals(true,StrKit.notNull(" "));
        assertEquals(true,StrKit.notNull(""));
        assertEquals(false,StrKit.notNull(strNull));

        assertEquals(true,StrKit.notNull("xx"));

        assertEquals(true,StrKit.notNull("xx","","yy"));
        assertEquals(false,StrKit.notNull("xx",null,"yy"));
        assertEquals(true,StrKit.notNull("xx","xx","yy"));
    }

    @Test
    public void testToUtf8String(){
        String str = StrKit.toUtf8String("中文");
        assertEquals("%E4%B8%AD%E6%96%87",str);
    }

    @Test
    public void testGetStr(){

        assertEquals("",StrKit.getStr(null));
        assertEquals("uuu",StrKit.getStr("uuu"));
        assertEquals("uuu ",StrKit.getStr("uuu "));

        assertEquals("yyy",StrKit.getStr(null,"yyy"));
        assertEquals("yyy",StrKit.getStr("","yyy"));
        assertEquals("ttt",StrKit.getStr("ttt","yyy"));

        assertEquals("",StrKit.getStrAndTrim(null));
        assertEquals("uuu",StrKit.getStrAndTrim("uuu"));
        assertEquals("uuu",StrKit.getStrAndTrim("uuu "));

        assertEquals("YY",StrKit.getStrAndTrim(" ","YY "));
        assertEquals("YY",StrKit.getStrAndTrim(null,"YY "));
    }

    @Test
    public void testReplace(){
        String str1 = "select code,?,? from aa where num=? and code=?";
        String str11 = StrKit.replace(str1, new String[]{"222","333","444","'555'"});
        assertEquals("select code,222,333 from aa where num=444 and code='555'",str11);

        String str2 = "select code,#,# from aa where num=# and code=#";
        String str22 = StrKit.replace(str2,"#",new String[]{"222","333","444","'555'"});
        assertEquals("select code,222,333 from aa where num=444 and code='555'",str22);
    }

    @Test
    public void testContainsChineseChar(){
        assertEquals(true,StrKit.containsChineseChar("ww我"));
        assertEquals(false,StrKit.containsChineseChar("ww22#"));


        assertEquals("xx",StrKit.getNotNullFirstStr("","","","xx","yyy"));
        assertEquals("xx",StrKit.getNotNullFirstStr("","","","xx","yyy"));

    }

    @Test
    public void testGetSplitStr(){
        List<String> stringList = new ArrayList<String>();
        stringList.add("x1");
        stringList.add("x2");
        stringList.add("x3");
        stringList.add("x4");

        assertEquals("x1;x2;x3;x4",StrKit.getSplitStr(stringList,";"));
        assertEquals("x1-x2-x3-x4",StrKit.getSplitStr(stringList,"-"));
    }

    @Test
    public void testGetStrByObj(){
        java.util.Date date = new Date(1458704405425L);

        String str = StrKit.getStrByObj(date);
        assertEquals("2016-03-23",str);


        assertEquals("300.00",StrKit.getStrByObj(new BigDecimal("300.00"),"yyyy-MM-dd HH"));
    }

}
