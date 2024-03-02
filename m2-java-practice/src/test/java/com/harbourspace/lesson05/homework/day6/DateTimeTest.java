package com.harbourspace.lesson05.homework.day6;

import com.harbourspace.lesson06.ZonedDateTimeTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DateTimeTest {

    @Test
    public void aheadZonedTimeTest(){
        ZonedDateTimeTask zonedTime = new ZonedDateTimeTask();
        Long diff = zonedTime.getTimeDif("Asia/Tokyo");
        Assertions.assertEquals(120, diff, "expect: " + 120 + " actual: " + diff);
    }

    @Test
    public void beforeZonedTimeTest(){
        ZonedDateTimeTask zonedTime = new ZonedDateTimeTask();
        Long diff = zonedTime.getTimeDif("America/Los_Angeles");
        Assertions.assertEquals(-900, diff, "expect: " + -900 + " actual: " + diff);
    }


}
