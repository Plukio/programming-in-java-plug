package com.harbourspace.lesson06.homework;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.round;

public class MetroHW {
    public static List<MeteoData>  calculateDailyStats(List<MeteoData> data, ZoneId zone) {
        Map<ZonedDateTime, List<MeteoData>> dailyData = data.stream()
                .map(meteo -> new MeteoData(
                        meteo.time().withZoneSameLocal(zone),
                        meteo.temperature(),
                        meteo.humidity(),
                        meteo.precipitation())
                ).collect(
                Collectors.groupingBy(meteoData -> meteoData.time().truncatedTo(ChronoUnit.DAYS), Collectors.toList())
        );


        List<MeteoData> dailyStats = new ArrayList<>();
        for (Map.Entry<ZonedDateTime, List<MeteoData>> entry : dailyData.entrySet()) {
            double meanTemp = round(entry.getValue().stream().collect(Collectors.summarizingDouble(MeteoData::temperature)).getAverage() * Math.pow(10,2)) / Math.pow(10,2);
            double meanHumi = round(entry.getValue().stream().collect(Collectors.summarizingDouble(MeteoData::humidity)).getAverage() * Math.pow(10,2)) / Math.pow(10,2);
            double sumPreci = round(entry.getValue().stream().collect(Collectors.summarizingDouble(MeteoData::precipitation)).getSum() * Math.pow(10,2)) / Math.pow(10,2);
            dailyStats.add(new MeteoData(entry.getKey(), meanTemp, meanHumi, sumPreci));
        }
        return dailyStats.stream().sorted(MeteoData::compareTo).toList();
    }

}
