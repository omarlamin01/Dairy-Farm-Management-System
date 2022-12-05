package com.dfms.dairy_farm_management_system.helpers;

import junit.framework.TestCase;

public class HelperTest extends TestCase {

    public void testEncryptPassword() {
        String password = "password";
        String enc_pass = Helper.encryptPassword(password);
        assertTrue("Enrypted successfully", Helper.MD5(enc_pass, password));
    }

    public void testMD5() {
        String password = "password";
        String enc_pass = Helper.encryptPassword(password);
        assertTrue("Enrypted successfully", Helper.MD5(enc_pass, password));
        assertFalse(Helper.MD5(enc_pass, "something"));
    }
}