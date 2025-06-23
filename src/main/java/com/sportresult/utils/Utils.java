package com.sportresult.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
public class Utils {

    public static LocalDateTime convertToLocalDateTime(String localDateTime) {
        log.info("START - convertToLocalDateTime: {}", localDateTime);
        if (localDateTime == null) {
            return null;
        }
        ZonedDateTime zonedDateTime;
        try {
            zonedDateTime = ZonedDateTime.parse(localDateTime);
            return zonedDateTime.toLocalDateTime();
        } catch (Exception e) {
            /*
            The data is not always a localDateTime so... ugly I know
             */
            LocalDate localDate = LocalDate.parse(localDateTime);
            zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        }
        return zonedDateTime.toLocalDateTime();
    }
}
