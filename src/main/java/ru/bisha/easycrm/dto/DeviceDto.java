package ru.bisha.easycrm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceDto {

    private String id;
    private String name;
    private String description;
    private String clientId;
    private LocalDateTime createdAt;
}
