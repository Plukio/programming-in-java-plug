package com.harbourspace.lesson09;

import java.util.function.BiFunction;

public class EnhancedInterMagic implements InterMagic {


    @Override
    public <T1, T2, R> R doMagic(T1 input1, T2 input2, BiFunction<T1, T2, R> magic) {
        return magic.apply(input1,input2);
    }
}
