package com.smarterfit.modules.classgroup.dto.request.classgroup;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SearchClassGroupRequestDTO {

    private String titleTerm;
    private Integer minCapacity;
    private Integer maxCapacity;
    private String modality;
    private Integer minTotalMembers;
    private Integer maxTotalMembers;
    private List<DayOfWeek> daysOfWeek;

    @JsonFormat(pattern = "dd-MM-yyyy") private LocalDate startDateFrom;
    @JsonFormat(pattern = "dd-MM-yyyy") private LocalDate startDateTo;
    @JsonFormat(pattern = "dd-MM-yyyy") private LocalDate endDateFrom;
    @JsonFormat(pattern = "dd-MM-yyyy") private LocalDate endDateTo;
    private Boolean isEvent;

}
