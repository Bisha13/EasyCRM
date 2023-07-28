package ru.bisha.easycrm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleOrderDto {

    private String id;
    private String statusId;
    private String clientId;
    private String clientName;
    private String clientPhone;
    private Integer clientDiscount;
    private String deviceId;
    private String deviceName;
    private String smallDescription;
    private String fullDescription;
    private LocalDateTime startedAt;
    private LocalDate closedAt;
    private List<ServiceDto> services;
    private List<PartDto> parts;
}
