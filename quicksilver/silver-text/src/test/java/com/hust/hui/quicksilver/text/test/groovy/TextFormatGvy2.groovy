package com.hust.hui.quicksilver.text.test.groovy

import com.hust.hui.quicksilver.text.TextFormat

/**
 * Created by yihui on 2017/3/30.
 */
class TextFormatGvy2 {
    public format() {
        def text =  "{user}, today is {date}! Welcome {user} to {place}!";
        def map = [ "user" : "Lucy", "date" : new Date(), "place" : "HangZhou" ]
        return TextFormat.formatV2(text, map);
    }
}