package com.dfms.dairy_farm_management_system.helpers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HelperEncryptPasswordTest {

    private static void assertValidMd5HashFor(String password) {
        String hash = Helper.encryptPassword(password);
        assertNotNull(hash, "Hash should not be null");
        assertEquals(32, hash.length(), "MD5 hash must be 32 hex chars");
        assertTrue(Helper.MD5(hash, password), "MD5(hash, password) should verify true");
    }

    @Test
    void bvt_length_min_0_emptyString() {
        String hash = Helper.encryptPassword("");
        assertNotNull(hash);
        assertEquals(32, hash.length());
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", hash, "MD5 of empty string is fixed");
        assertTrue(Helper.MD5(hash, ""), "Should verify for empty string too");
    }

    @Test
    void bvt_length_min_plus_1_singleChar() {
        assertValidMd5HashFor("a");
    }

    @Test
    void bvt_length_just_below_policy_min_7() {
        assertValidMd5HashFor("1234567");
    }

    @Test
    void bvt_length_policy_min_8() {
        assertValidMd5HashFor("12345678");
    }

    @Test
    void bvt_length_just_above_policy_min_9() {
        assertValidMd5HashFor("123456789");
    }

    @Test
    void bvt_length_near_upper_63() {
        assertValidMd5HashFor("a".repeat(63));
    }

    @Test
    void bvt_length_upper_64() {
        assertValidMd5HashFor("a".repeat(64));
    }

    @Test
    void bvt_length_upper_plus_1_65() {
        assertValidMd5HashFor("a".repeat(65));
    }

    @Test
    void bvt_null_password_should_throw() {
        assertThrows(NullPointerException.class, () -> Helper.encryptPassword(null));
    }

    @Test
    void md5_should_return_false_for_wrong_password() {
        String password = "12345678";
        String hash = Helper.encryptPassword(password);

        assertTrue(Helper.MD5(hash, password));
        assertFalse(Helper.MD5(hash, "wrongpass"));
    }
}
