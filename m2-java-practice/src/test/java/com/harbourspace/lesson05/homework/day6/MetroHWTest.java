package com.harbourspace.lesson05.homework.day6;

import org.junit.jupiter.api.Test;
import java.time.ZoneId;
import com.harbourspace.lesson06.homework.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetroHWTest {

    @Test
    public void testCalculateDailyStats() {
        ZoneId bangkokZoneId = ZoneId.of("Asia/Bangkok");

        List<MeteoData> dailyStats = MetroHW.calculateDailyStats(SampleMeteoData.DATA, bangkokZoneId);

        assertEquals(32, dailyStats.size(), "expect: " + 32 + " actual: " + dailyStats.size());

        MeteoData firstDayStats = dailyStats.get(0);
        assertEquals(27.17, firstDayStats.temperature(), 0.01, "expect: " + 27.17 + " actual: " + firstDayStats.temperature());
        assertEquals(58.08, firstDayStats.humidity(), 0.01, "expect: " + 58.08 + " actual: " + firstDayStats.humidity());
        assertEquals(1.5, firstDayStats.precipitation(), 0.01, "expect: " + 1.5 + " actual: " + firstDayStats.precipitation());

    }
}
