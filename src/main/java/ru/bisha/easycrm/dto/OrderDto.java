package ru.bisha.easycrm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.bisha.easycrm.db.entity.StatusEntity;

import java.time.LocalDate;
import java.util.List;

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

    @Setter
    private List<ServiceDto> services;
}
