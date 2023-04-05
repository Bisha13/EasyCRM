package ru.bisha.easycrm.restservice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.Order;
import ru.bisha.easycrm.db.repository.OrderRepository;
import ru.bisha.easycrm.dto.GetOrdersResponse;
import ru.bisha.easycrm.dto.OrderDto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestOrderService {

    private static final String ORDER_ID = "orderId";

    private final OrderRepository orderRepository;

    public GetOrdersResponse getAll(Integer size, Integer page, Integer statusId) {
        Page<Order> orderPage;

        if (statusId == null) {
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
