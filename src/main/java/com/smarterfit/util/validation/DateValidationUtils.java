package com.smarterfit.util.validation;

import com.smarterfit.exception.InvalidDateException;

import java.time.*;

public final class DateValidationUtils {

    private DateValidationUtils() {}

    // VALIDATE LOCALDATE
    public static void validateDateRange(LocalDate start, LocalDate end, boolean allowPast) {
        requireNonNull(start, end, "date");
        checkRange(start, end, "date");
        if (!allowPast) checkFuture(start, "date");
    }

    // VALIDATE LOCALDATETIME
    public static void validateDateTimeRange(LocalDateTime start, LocalDateTime end, boolean allowPast) {
        requireNonNull(start, end, "date-time");
        checkRange(start, end, "date-time");
        if (!allowPast) checkFuture(start, "date-time");
    }

    // VALIDATE TIME (horário)
    public static void validateTimeRange(LocalTime start, LocalTime end) {
        requireNonNull(start, end, "time");
        checkRange(start, end, "time");
    }

    // ========= PRIVATE HELPERS ========= //

    private static <T extends Comparable<T>> void checkRange(T start, T end, String type) {
        if (start.compareTo(end) > 0) {
            throw new InvalidDateException("Start " + type + " must be before end " + type + ".");
        }
    }

    private static void requireNonNull(Object start, Object end, String type) {
        if (start == null || end == null) {
            throw new InvalidDateException("Start " + type + " and end " + type + " must be provided.");
        }
    }

    private static void checkFuture(LocalDate start, String type) {
        if (start.isBefore(LocalDate.now())) {
            throw new InvalidDateException("Start " + type + " must be in the future.");
        }
    }

    private static void checkFuture(LocalDateTime start, String type) {
        if (start.isBefore(LocalDateTime.now())) {
            throw new InvalidDateException("Start " + type + " must be in the future.");
        }
    }
}
