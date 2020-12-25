package com.wobangkj;

import com.wobangkj.utils.PinyinUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);

        String s = "你好, 杭州, tmd";
        System.out.println(PinyinUtils.toPinyin(s, true));
        for (String s1 : PinyinUtils.toPinyinArray(s, false)) {
            System.out.print(s1 + " ");
        }
        System.out.println();
        System.out.println(PinyinUtils.toPinyin("你好"));
    }
}
