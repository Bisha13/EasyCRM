package ru.bisha.easycrm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewOrderDto {

    private String clientId;
    private String clientName;
    private String clientPhone;
    private Boolean isNewClient;
    private String deviceId;
    private String deviceName;
    private String deviceDescription;
    private Boolean isNewDevice;
    private String smallDescription;
    private String fullDescription;
    private List<ServiceDto> services;
    private List<PartDto> parts;
}
