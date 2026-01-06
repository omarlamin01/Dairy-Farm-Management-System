package com.dfms.dairy_farm_management_system.helpers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HelperEncryptPasswordTest {

    //Boundary Value Testing for EncryptPassword() method
    @Test
    public void testEncryptPassword() {
        String password = "password";
        String enc_pass = Helper.encryptPassword(password);
        assertTrue(Helper.MD5(enc_pass, password), "Enrypted successfully");
    }

    @Test
    void testEmptyPassword() {
        String hash=Helper.encryptPassword("");
        assertEquals("d41d8cd98f00b204e9800998ecf8427e",hash);
    }

    @Test
    void testSingleCharacterPassword() {
        String hash=Helper.encryptPassword("a");
        assertNotNull(hash);
        assertEquals(32,hash.length());
    }

    @Test
    void testNormalPassword() {
        String hash=Helper.encryptPassword("password");
        assertNotNull(hash);
        assertEquals(32,hash.length());
    }
    @Test
    void testLongPassword() {
        String longPass="a".repeat(1000);
        String hash=Helper.encryptPassword(longPass);
        assertNotNull(hash);
        assertEquals(32,hash.length());
    }
    @Test
    void testSpecialCharacters() {
        String hash = Helper.encryptPassword("!@#$%^&*()_+");
        assertNotNull(hash);
        assertEquals(32, hash.length());
    }
    @Test
    void testUnicodeCharacters() {
        String hash = Helper.encryptPassword("pässwörd✓");
        assertNotNull(hash);
        assertEquals(32, hash.length());
    }
    @Test
    void testNullPassword() {
        assertThrows(NullPointerException.class, () -> Helper.MD5("anyhash", null));
    }

    @Test
    public void testMD5() {
        String password = "password";
        String enc_pass = Helper.encryptPassword(password);
        assertTrue(Helper.MD5(enc_pass, password), "Enrypted successfully");
        assertFalse(Helper.MD5(enc_pass, "something"));
    }

}