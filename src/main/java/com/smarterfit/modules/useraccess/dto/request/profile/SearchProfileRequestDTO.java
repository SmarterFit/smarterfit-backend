package com.smarterfit.modules.useraccess.dto.request.profile;

import java.time.LocalDate;
import java.util.List;

import org.springframework.ai.tool.annotation.ToolParam;

import com.smarterfit.common.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SearchProfileRequestDTO {
   private String fullNameTerm;
   private String cpfTerm;
   private String phoneTerm;
   private LocalDate birthDateFrom;
   private LocalDate birthDateTo;
   List<Gender> gender;
}
