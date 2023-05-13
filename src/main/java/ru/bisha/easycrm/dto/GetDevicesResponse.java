package ru.bisha.easycrm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class GetDevicesResponse {

    private List<DeviceDto> devices;
    private Integer pageCount;

}
