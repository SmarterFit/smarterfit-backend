package com.smarterfit.modules.checkin.domain;

import com.smarterfit.modules.checkin.repository.GymCheckInRepository;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class GymPoints {
    private static final int LOOKBACK_DAYS = 5;
    private static final Set<DayOfWeek> WEEKEND = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    private final GymCheckInRepository repository;

    public GymPoints(GymCheckInRepository repository) {
        this.repository = repository;
    }

    public int calculateDailyConsecutivePoints(UUID userId) {
        LocalDate today = LocalDate.now();
        LocalDateTime endDateTime = today.atTime(LocalTime.MAX);
        LocalDateTime startDateTime = today.minusDays(LOOKBACK_DAYS).atStartOfDay();

        Set<LocalDate> checkInDates = repository
                .findByUserIdAndDateBetween(userId, startDateTime, endDateTime)
                .stream()
                .map(g -> {
                    if (g.getCheckOutTime() != null) {
                        return g.getCheckOutTime().toLocalDate();
                    } else {
                        return g.getCheckInTime().toLocalDate();
                    }
                })
                .filter(d -> !WEEKEND.contains(d.getDayOfWeek()))
                .collect(Collectors.toSet());

        return IntStream.iterate(0, i -> i + 1)
                .limit(LOOKBACK_DAYS + 1)
                .mapToObj(i -> today.minusDays(i))
                .filter(d -> !WEEKEND.contains(d.getDayOfWeek()))
                .takeWhile(checkInDates::contains)
                .mapToInt(d -> dayIndex(d, today))
                .sum();
    }

    private int dayIndex(LocalDate day, LocalDate today) {
        return (int) today.datesUntil(day.plusDays(1))
                .filter(d -> !WEEKEND.contains(d.getDayOfWeek()))
                .count();
    }
}
