package com.harbourspace.lesson09;

import java.util.function.BiFunction;

public class Magic {
    public static String doMagic(String item1, String item2, BiFunction<String,String, String> magic){
        return magic.apply(item1, item2);
    }
}
