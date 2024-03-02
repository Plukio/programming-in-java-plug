package com.harbourspace.lesson06;

import java.sql.Time;
import java.time.*;

public class ZonedDateTimeTask {
    public ZonedDateTime zonedDateTimeNow;
    public ZoneId id;
    public ZoneOffset offset;

    // solve your tasks here

    public  ZonedDateTimeTask(){
        this.zonedDateTimeNow = ZonedDateTime.now();
        this.id = this.zonedDateTimeNow.getZone();
        this.offset = this.zonedDateTimeNow.getOffset();
    }
    public void setZonedDateTimeNow() {

        System.out.println("Local Zone: " + zonedDateTimeNow.getZone() + " Offset: " + zonedDateTimeNow.getOffset());
    }



    public Long getTimeDif(String zone){
        ZonedDateTime anotherTime = this.zonedDateTimeNow.withZoneSameInstant(ZoneId.of(zone));
        Long diff = Duration.between ( zonedDateTimeNow, anotherTime.withZoneSameLocal( zonedDateTimeNow.getZone())).toMinutes();
        System.out.println ( "Local Zone: " + zonedDateTimeNow.getZone() + " Offset: " + zonedDateTimeNow.getOffset() );
        System.out.println ( "New Zone: " + anotherTime.getZone() + " Offset: " + anotherTime.getOffset() );
        if (diff > 0){
            System.out.println(zone + " is " + diff + " minutes ahead of " + id );
        } else {
            System.out.println(id + " is " + Math.abs(diff) + " minutes ahead of " + zone);
        }

        return diff;
    }
}



