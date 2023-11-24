package test;

import org.junit.jupiter.api.Test;
import util.Utils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for Utils
 */
class UtilsTest {

    @Test
    void getFileNameFromURL() {
        assertEquals(Utils.getFileNameFromURL("img/1.jpg"), "1.jpg");
        assertEquals(Utils.getFileNameFromURL("img/haha/heiehei/1.mov"), "1.mov");
    }

    @Test
    void isImage() {
        assertFalse(Utils.isImage("img/1.mp4"));
        assertTrue(Utils.isImage("D:\\workspace\\test\\test1.jpeg"));
    }
}