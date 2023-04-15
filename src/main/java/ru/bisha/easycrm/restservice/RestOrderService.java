package ru.bisha.easycrm.restservice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.Item;
import ru.bisha.easycrm.db.entity.Order;
import ru.bisha.easycrm.db.entity.User;
import ru.bisha.easycrm.db.repository.OrderRepository;
import ru.bisha.easycrm.dto.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestOrderService {

    private static final String ORDER_ID = "orderId";

    private final OrderRepository orderRepository;

    public GetOrdersResponse getAll(Integer size, Integer page, Integer statusId) {
        Page<Order> orderPage;

        if (statusId == null || statusId == 0) {
            orderPage = orderRepository.findAll(
                    PageRequest.of(page - 1, size,
                            Sort.by(ORDER_ID)));
        } else {

            if (statusId == -1) {
                orderPage = orderRepository.findAllByExecuteStatusHide(false,
                        PageRequest.of(page - 1, size,
                                Sort.by(ORDER_ID)));
            } else {
                orderPage = orderRepository.findAllByExecuteStatusId(statusId,
                        PageRequest.of(page - 1, size,
                                Sort.by(ORDER_ID)));
            }
        }

        List<OrderDto> orderDtos = orderPage.stream()
                .map(buildOrderDto())
                .collect(Collectors.toList());
        return GetOrdersResponse.builder()
                .orders(orderDtos)
                .pageCount(orderPage.getTotalPages())
                .build();
    }

    public GetSingleOrderResponse getOrder(int id) {
        var order = orderRepository.findById(id)
                .orElseThrow();
        List<ServiceDto> services = order.getListOfServices().stream()
                .map(s -> ServiceDto.builder()
                        .id(String.valueOf(s.getServiceId()))
                        .qty(s.getQty())
                        .description(s.getDescription())
                        .executorId(Optional.ofNullable(s.getExecutor()).map(User::getId).map(String::valueOf).orElse(null))
                        .itemId(Optional.ofNullable(s.getItem()).map(Item::getId).map(String::valueOf).orElse(null))
                        .isCustom(s.getIsCustom())
                        .build())
                .collect(Collectors.toList());
        List<PartDto> parts = order.getListOfParts().stream()
                .map(p -> PartDto.builder()
                        .partId(String.valueOf(p.getPartId()))
                        .name(p.getName())
                        .purchasePrice(BigDecimal.valueOf(p.getPurchasePrice()))
                        .price(BigDecimal.valueOf(p.getPrice()))
                        .qty(p.getQty())
                        .stockId(String.valueOf(p.getStock().getId()))
                        .isStock(p.getIsStock())
                        .build())
                .collect(Collectors.toList());
        return GetSingleOrderResponse.builder()
                .id(String.valueOf(order.getOrderId()))
                .statusId(order.getExecuteStatus().getId().toString())
                .clientId(String.valueOf(order.getClient().getId()))
                .clientName(order.getClient().getName())
                .clientPhone(order.getClient().getPhoneNumber())
                .deviceId(String.valueOf(order.getDevice().getDeviceId()))
                .deviceName(Optional.ofNullable(order.getDevice().getDescription()).orElse("") + " "
                        + Optional.ofNullable(order.getDevice().getDeviceName()).orElse(""))
                .smallDescription(order.getSmallDescription())
                .fullDescription(order.getFullDescription())
                .startedAt(order.getTimestamp().toLocalDateTime().toLocalDate())
                .services(services)
                .parts(parts)
                .build();
    }


    private static Function<Order, OrderDto> buildOrderDto() {
        return o -> OrderDto.builder()
                .id(o.getOrderId())
                .description(o.getSmallDescription())
                .device(o.getDevice().getDeviceName())
                .clientName(o.getClient().getName())
                .status(o.getExecuteStatus())
                .startedAt(o.getTimestamp().toLocalDateTime().toLocalDate())
                .build();
    }
}
