package ru.bisha.easycrm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetSingleOrderResponse {

    private String id;
    private String statusId;
    private String clientId;
    private String clientName;
    private String clientPhone;
    private String deviceId;
    private String deviceName;
    private String smallDescription;
    private LocalDate startedAt;
    private List<ServiceDto> services;
}
