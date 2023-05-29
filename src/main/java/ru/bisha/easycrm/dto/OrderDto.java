package ru.bisha.easycrm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import ru.bisha.easycrm.db.entity.StatusEntity;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
public class OrderDto {

    private Integer id;

    private String clientName;

    private String device;

    private String description;

    private StatusEntity status;

    private LocalDate startedAt;
}
