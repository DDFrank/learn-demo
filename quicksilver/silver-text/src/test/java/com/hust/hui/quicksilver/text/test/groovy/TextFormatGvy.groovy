package com.hust.hui.quicksilver.text.test.groovy

import com.hust.hui.quicksilver.text.TextFormat

/**
 * Created by yihui on 2017/3/30.
 */


def text =  "{user}, today is {date}! Welcome {user} to {place}!";
def map = [ "user" : "Lucy", "date" : new Date(), "place" : "HangZhou" ]

def ans = TextFormat.formatV2(text, map);
println ans;