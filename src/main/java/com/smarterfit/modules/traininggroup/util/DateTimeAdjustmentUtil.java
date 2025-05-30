package com.smarterfit.modules.traininggroup.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.smarterfit.modules.traininggroup.dto.request.CreateTrainingGroupRequestDTO;
import com.smarterfit.modules.traininggroup.dto.request.UpdateTrainingGroupRequestDTO;

public class DateTimeAdjustmentUtil {
   public static void adjustTrainingGroupDateRange(CreateTrainingGroupRequestDTO dto) {
      LocalDateTime now = LocalDateTime.now();

      if (dto.getStartDate() != null) {
         LocalDate startDate = dto.getStartDate().toLocalDate();
         if (startDate.isEqual(now.toLocalDate()) && dto.getStartDate().isBefore(now)) {
            dto.setStartDate(now);
         }
      }

      if (dto.getEndDate() != null) {
         LocalDate endDate = dto.getEndDate().toLocalDate();
         if (endDate.isEqual(now.toLocalDate()) && (dto.getEndDate().isBefore(now) || dto.getEndDate().isEqual(now))) {
            dto.setEndDate(LocalDateTime.of(endDate, LocalTime.MAX));
         }
      }
   }

   public static void adjustTrainingGroupDateRange(UpdateTrainingGroupRequestDTO dto) {
      LocalDateTime now = LocalDateTime.now();

      if (dto.getStartDate() != null) {
         LocalDate startDate = dto.getStartDate().toLocalDate();
         if (startDate.isEqual(now.toLocalDate()) && dto.getStartDate().isBefore(now)) {
            dto.setStartDate(now);
         }
      }

      if (dto.getEndDate() != null) {
         LocalDate endDate = dto.getEndDate().toLocalDate();
         if (endDate.isEqual(now.toLocalDate()) && (dto.getEndDate().isBefore(now) || dto.getEndDate().isEqual(now))) {
            dto.setEndDate(LocalDateTime.of(endDate, LocalTime.MAX));
         }
      }
   }
}
