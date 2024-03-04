package com.harbourspace.lesson09;

import java.util.function.BiFunction;

@FunctionalInterface
public interface InterMagic {
    <T1,T2,R> R doMagic(T1 input1, T2 input2, BiFunction<T1,T2, R> magic);
}