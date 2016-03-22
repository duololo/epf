package com.zfer.kit;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * The <code>StrKitTest</code> class is TestClass of StrKit.
 * @author yunshan
 * @version %I%, %G%
 * @since 1.0
 */
public class StrKitTest {
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
        String nullStr = null;
        assertEquals(true,StrKit.isBlank(nullStr));

        assertEquals(false,StrKit.isBlank("xx"));

        assertEquals(true,StrKit.isBlank("xx","","yy"));
        assertEquals(true,StrKit.isBlank("xx",nullStr,"yy"));
        assertEquals(false,StrKit.isBlank("xx","xx","yy"));
    }

    @Test
    public void testNotBlank(){
        assertEquals(false,StrKit.notBlank(" "));
        assertEquals(false,StrKit.notBlank(""));
        String nullStr = null;
        assertEquals(false,StrKit.notBlank(nullStr));

        assertEquals(true,StrKit.notBlank("xx"));

        assertEquals(false,StrKit.notBlank("xx","","yy"));
        assertEquals(false,StrKit.notBlank("xx",nullStr,"yy"));
        assertEquals(true,StrKit.notBlank("xx","xx","yy"));
    }

    @Test
    public void testNotNull(){
    }
}
