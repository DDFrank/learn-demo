package com.hust.hui.quicksilver.collection.test.htmlhtml;

import gui.ava.html.image.generator.HtmlImageGenerator;
import org.junit.Test;

import java.awt.image.BufferedImage;

/**
 * Created by yihui on 2017/12/15.
 */
public class HtmlRenderTest {

    @Test
    public void testRenderHtml() {
        String url = "http://shop.mogujie.com/detail/1kailma";
        HtmlImageGenerator generator = new HtmlImageGenerator();
        generator.loadUrl(url);

        BufferedImage img = generator.getBufferedImage();
        System.out.println("---");
    }

}
