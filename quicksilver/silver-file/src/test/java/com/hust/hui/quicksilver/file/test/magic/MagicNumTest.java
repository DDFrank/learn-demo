package com.hust.hui.quicksilver.file.test.magic;

import com.hust.hui.quicksilver.file.FileReadUtil;
import org.junit.Test;

/**
 * Created by yihui on 2017/6/2.
 */
public class MagicNumTest {
    @Test
    public void testGetMagic() {
        String txt = "info.json";
        String num =  FileReadUtil.getMagicNum(txt);
        System.out.println("json: " + num);
    }

}
