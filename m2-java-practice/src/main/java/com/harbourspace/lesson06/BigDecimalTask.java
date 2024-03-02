package com.harbourspace.lesson06;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalTask {

    public static BigDecimal BigArea(BigDecimal radius, int d){
        return new BigDecimal(Math.PI).multiply(radius).multiply(radius).setScale(d, RoundingMode.HALF_UP);
    }

}

