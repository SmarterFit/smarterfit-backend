package com.smarterfit.common.mapper;

import org.springframework.beans.BeanUtils;

public class GenericMapper {
   private GenericMapper() {
      // Prevent instantiation
   }

   public static <T, U> U map(T source, U target) {
      if (source == null) {
         return null;
      }

      try {
         BeanUtils.copyProperties(source, target);
         return target;
      } catch (Exception e) {
         throw new RuntimeException("Mapping failed", e);
      }
   }

   public static <T, U> U map(T source, Class<U> targetClass) {
      try {
         U target = targetClass.getDeclaredConstructor().newInstance();
         return map(source, target);
      } catch (Exception e) {
         throw new RuntimeException("Mapping failed", e);
      }
   }
}
