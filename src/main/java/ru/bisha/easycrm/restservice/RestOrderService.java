package ru.bisha.easycrm.restservice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.bisha.easycrm.db.entity.*;
import ru.bisha.easycrm.db.repository.*;
import ru.bisha.easycrm.dto.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestOrderService {

    private static final String ORDER_ID = "orderId";

    private final OrderRepository orderRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final StockRepository stockRepository;
    private final ServiceRepository serviceRepository;
    private final PartRepository partRepository;
    private final ClientRepository clientRepository;
    private final DeviceRepository deviceRepository;

    public GetOrdersResponse getAll(Integer size, Integer page, Integer statusId) {
        Page<OrderEntity> orderPage;

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
                .map(RestOrderService::buildOrderDto)
                .collect(Collectors.toList());
        return GetOrdersResponse.builder()
                .orders(orderDtos)
                .pageCount(orderPage.getTotalPages())
                .build();
    }

    public SingleOrderDto getOrder(int id) {
        var order = orderRepository.findById(id).orElseThrow();
        List<ServiceDto> services = mapServicesToDto(order);
        List<PartDto> parts = mapPartsToDto(order);
        return buildOrderDto(order, services, parts);
    }


    public void updateOrder(SingleOrderDto request) {

        var order = orderRepository.findById(Integer.valueOf(request.getId())).orElseThrow();
        var status = statusRepository.findById(Long.valueOf(request.getStatusId())).orElseThrow();

        order.setExecuteStatus(status);
        order.setSmallDescription(request.getSmallDescription());
        order.setFullDescription(request.getFullDescription());


        Set<Integer> newServiceIds = request.getServices().stream()
                .filter(s -> StringUtils.hasLength(s.getId()))
                .filter(s -> !(!Boolean.TRUE.equals(s.getIsCustom()) && "0".equals(s.getItemId())))
                .map(ServiceDto::getId).map(Integer::valueOf).collect(Collectors.toSet());
        order.getListOfServices().stream()
                .filter(s -> !newServiceIds.contains(s.getServiceId()))
                .forEach(t -> serviceRepository.delete(t.getServiceId()));


        Set<Integer> newPartsIds = request.getParts().stream()
                .filter(s -> StringUtils.hasLength(s.getPartId()))
                .filter(p -> !(Boolean.TRUE.equals(p.getIsStock()) && "0".equals(p.getStockId())))
                .map(PartDto::getPartId).map(Integer::valueOf).collect(Collectors.toSet());
        order.getListOfParts().stream()
                .filter(p -> !newPartsIds.contains(p.getPartId()))
                .forEach(p -> partRepository.delete(p.getPartId()));


        List<ServiceEntity> serviceEntities = mapServicesToEntity(request.getServices());
        List<PartEntity> partEntities = mapPartsToEntity(request.getParts());
        order.setListOfServices(serviceEntities);
        order.setListOfParts(partEntities);
        order.setPartsPrice(getPartsPrice(partEntities));
        setSumFromServices(order);
        setOrders(order);
        order.setFullPrice(order.getPartsPrice() + order.getWorkPrice());
        orderRepository.saveAndFlush(order);
    }

    public void closeOrder(SingleOrderDto request) {

        var order = orderRepository.findById(Integer.valueOf(request.getId())).orElseThrow();

        order.setTimeClose(new Date(new java.util.Date().getTime()));
        order.setExecuteStatus(statusRepository.findById(14L).orElseThrow());
        order.setSmallDescription(request.getSmallDescription());
        order.setFullDescription(request.getFullDescription());

        Set<Integer> newServiceIds = request.getServices().stream()
                .filter(s -> StringUtils.hasLength(s.getId()))
                .filter(s -> !(!Boolean.TRUE.equals(s.getIsCustom()) && "0".equals(s.getItemId())))
                .map(ServiceDto::getId).map(Integer::valueOf).collect(Collectors.toSet());
        order.getListOfServices().stream()
                .filter(s -> !newServiceIds.contains(s.getServiceId()))
                .forEach(t -> serviceRepository.delete(t.getServiceId()));


        Set<Integer> newPartsIds = request.getParts().stream()
                .filter(s -> StringUtils.hasLength(s.getPartId()))
                .filter(p -> !(Boolean.TRUE.equals(p.getIsStock()) && "0".equals(p.getStockId())))
                .map(PartDto::getPartId).map(Integer::valueOf).collect(Collectors.toSet());
        order.getListOfParts().stream()
                .filter(p -> !newPartsIds.contains(p.getPartId()))
                .forEach(p -> partRepository.delete(p.getPartId()));

        List<ServiceEntity> serviceEntities = request.getServices().stream()
                .map(s -> ServiceEntity.builder()
                        .serviceId(Optional.ofNullable(s.getId()).map(Integer::valueOf).orElse(null))
                        .qty(s.getQty())
                        .description(s.getDescription())
                        .executor(getExecutor(s.getExecutorId()))
                        .item(getItem(s.getItemId()))
                        .price(Optional.ofNullable(s.getPrice()).map(BigDecimal::doubleValue).orElse(null))
                        .isCustom(s.getIsCustom())
                        .build()
                ).collect(Collectors.toList());
        List<PartEntity> partEntities = request.getParts().stream()
                .map(p -> PartEntity.builder()
                        .partId(Optional.ofNullable(p.getPartId()).map(Integer::valueOf).orElse(null))
                        .qty(p.getQty())
                        .purchasePrice(Optional.ofNullable(p.getPurchasePrice()).map(BigDecimal::doubleValue).orElse(null))
                        .price(Optional.ofNullable(p.getPrice()).map(BigDecimal::doubleValue).orElse(null))
                        .isStock(p.getIsStock())
                        .stock(getStock(p.getStockId()))
                        .build()).collect(Collectors.toList());
        order.setListOfServices(serviceEntities);
        order.setListOfParts(partEntities);
        order.setPartsPrice(getPartsPrice(order.getListOfParts()));
        setSumFromServices(order);
        setOrders(order);
        order.setFullPrice(order.getPartsPrice() + order.getWorkPrice());
        orderRepository.saveAndFlush(order);
    }

    public void createOrder(NewOrderDto request) {
        ClientEnitiy client = getClient(request);
        DeviceEntity device = getDevice(request, client.getId());
        var status = statusRepository.findById(1L).orElseThrow();
        List<ServiceEntity> services = mapServicesToEntity(request.getServices());
        List<PartEntity> parts = mapPartsToEntity(request.getParts());

        OrderEntity order = OrderEntity.builder()
                .client(client)
                .device(device)
                .executeStatus(status)
                .smallDescription(request.getSmallDescription())
                .fullDescription(request.getFullDescription())
                .listOfServices(services)
                .listOfParts(parts)
                .partsPrice(getPartsPrice(parts))
                .build();

        setSumFromServices(order);
        setOrders(order);
        order.setFullPrice(order.getPartsPrice() + order.getWorkPrice());
        orderRepository.saveAndFlush(order);
    }

    private List<PartEntity> mapPartsToEntity(List<PartDto> parts) {
        return parts.stream()
                .filter(p -> !(Boolean.TRUE.equals(p.getIsStock()) && "0".equals(p.getStockId())))
                .map(p -> PartEntity.builder()
                        .partId(Optional.ofNullable(p.getPartId()).map(Integer::valueOf).orElse(null))
                        .qty(p.getQty())
                        .name(p.getName())
                        .purchasePrice(Optional.ofNullable(p.getPurchasePrice()).map(BigDecimal::doubleValue).orElse(null))
                        .price(Optional.ofNullable(p.getPrice()).map(BigDecimal::doubleValue).orElse(null))
                        .isStock(p.getIsStock())
                        .stock(getStock(p.getStockId()))
                        .build()).collect(Collectors.toList());
    }

    private List<ServiceEntity> mapServicesToEntity(List<ServiceDto> services) {
        return services.stream()
                .filter(s -> !(!Boolean.TRUE.equals(s.getIsCustom()) && "0".equals(s.getItemId())))
                .map(s -> ServiceEntity.builder()
                        .serviceId(Optional.ofNullable(s.getId()).map(Integer::valueOf).orElse(null))
                        .qty(s.getQty())
                        .description(s.getDescription())
                        .executor(getExecutor(s.getExecutorId()))
                        .item(getItem(s.getItemId()))
                        .price(Optional.ofNullable(s.getPrice()).map(BigDecimal::doubleValue).orElse(null))
                        .isCustom(s.getIsCustom())
                        .build()
                ).collect(Collectors.toList());
    }

    private DeviceEntity getDevice(NewOrderDto request, Integer ownerId) {
        if (Boolean.TRUE.equals(request.getIsNewDevice())) {
            DeviceEntity newDevice = new DeviceEntity();
            newDevice.setDeviceName(request.getDeviceName());
            newDevice.setDescription(request.getDeviceDescription());
            newDevice.setOwnerId(ownerId);
            return deviceRepository.save(newDevice);
        }
        return deviceRepository.findById(Integer.valueOf(request.getDeviceId()))
                .orElseThrow(() -> new IllegalStateException("Не смогли найти девайс по id"));
    }

    private ClientEnitiy getClient(NewOrderDto request) {
        if (Boolean.TRUE.equals(request.getIsNewClient())) {
            ClientEnitiy newClient = new ClientEnitiy();
            newClient.setName(request.getClientName());
            newClient.setPhoneNumber(request.getClientPhone());
            return clientRepository.save(newClient);
        }
        return clientRepository.findById(Integer.valueOf(request.getClientId()))
                .orElseThrow(() -> new IllegalStateException("Не смогли найти клиента по id"));
    }

    private ItemEntity getItem(String itemId) {
        return itemId == null ? null : itemRepository.findById(Integer.valueOf(itemId)).orElse(null);

    }

    private UserEntity getExecutor(String executorId) {
        return executorId == null ? null : userRepository.findById(Integer.valueOf(executorId)).orElse(null);
    }

    private StockEntity getStock(String stockId) {
        return stockId == null ? null : stockRepository.findById(Integer.valueOf(stockId)).orElse(null);
    }

    private Double getPartsPrice(List<PartEntity> listOfParts) {
        Double partsPrice = listOfParts.stream()
                .filter(part -> !part.getIsStock())
                .mapToDouble(p -> p.getPrice() * p.getQty())
                .sum();
        Double stockPrice = listOfParts.stream()
                .filter(PartEntity::getIsStock)
                .mapToDouble(p -> p.getStock().getPrice() * p.getQty())
                .sum();

        return partsPrice + stockPrice;
    }

    private void setSumFromServices(OrderEntity order) {
        var services = order.getListOfServices();
        var sum = 0.0;
        for (ServiceEntity service : services) {
            Double price = 0.0;
            if (service.getIsCustom()) {
                price = service.getPrice();
            }
            if (!service.getIsCustom()) {
                price = service.getItem().getPrice();
            }

            if (price == null) {
                price = 0.0;
            } else {
                price = price * service.getQty();
                price = applyDiscount(price, order.getClient().getDiscount());
            }

            int percent;
            if (service.getExecutor() == null) {
                percent = 0;
            } else {
                percent = service.getExecutor().getPercent();
            }

            double executorMoney = (price / 100.0) * percent;
            service.setExecutorMoney(executorMoney);
            service.setProfit(price - executorMoney);
            sum += price;
        }
        order.setWorkPrice(sum);
    }

    private double applyDiscount(double sum, Integer discount) {
        if (discount == null) {
            return sum;
        }
        return sum - (sum / 100 * discount);
    }

    private void setOrders(OrderEntity order) {
        for (PartEntity part : order.getListOfParts()) {
            part.setOrder(order);
        }
        for (ServiceEntity service : order.getListOfServices()) {
            service.setOrder(order);
        }
    }

    private static SingleOrderDto buildOrderDto(OrderEntity order, List<ServiceDto> services, List<PartDto> parts) {
        return SingleOrderDto.builder()
                .id(String.valueOf(order.getOrderId()))
                .statusId(order.getExecuteStatus().getId().toString())
                .clientId(String.valueOf(order.getClient().getId()))
                .clientName(order.getClient().getName())
                .clientPhone(order.getClient().getPhoneNumber())
                .clientDiscount(order.getClient().getDiscount())
                .deviceId(String.valueOf(order.getDevice().getDeviceId()))
                .deviceName(Optional.ofNullable(order.getDevice().getDeviceName()).orElse("") + " "
                        + Optional.ofNullable(order.getDevice().getDescription()).orElse(""))
                .smallDescription(order.getSmallDescription())
                .fullDescription(order.getFullDescription())
                .startedAt(order.getTimestamp().toLocalDateTime().toLocalDate())
                .closedAt(Optional.ofNullable(order.getTimeClose()).map(Date::toLocalDate).orElse(null))
                .services(services)
                .parts(parts)
                .build();
    }

    private static List<PartDto> mapPartsToDto(OrderEntity order) {
        return order.getListOfParts().stream()
                .map(p -> PartDto.builder()
                        .partId(String.valueOf(p.getPartId()))
                        .name(p.getName())
                        .purchasePrice(Optional.ofNullable(p.getPurchasePrice()).map(BigDecimal::valueOf).orElse(null))
                        .price(Optional.ofNullable(p.getPrice()).map(BigDecimal::valueOf).orElse(null))
                        .qty(p.getQty())
                        .stockId(String.valueOf(p.getStock().getId()))
                        .isStock(p.getIsStock())
                        .build())
                .collect(Collectors.toList());
    }

    private static List<ServiceDto> mapServicesToDto(OrderEntity order) {
        return order.getListOfServices().stream()
                .map(s -> ServiceDto.builder()
                        .id(String.valueOf(s.getServiceId()))
                        .qty(s.getQty())
                        .description(s.getDescription())
                        .executorId(Optional.ofNullable(s.getExecutor()).map(UserEntity::getId).map(String::valueOf).orElse(null))
                        .itemId(Optional.ofNullable(s.getItem()).map(ItemEntity::getId).map(String::valueOf).orElse(null))
                        .price(Optional.ofNullable(s.getPrice()).map(BigDecimal::valueOf).orElse(null))
                        .isCustom(s.getIsCustom())
                        .build())
                .collect(Collectors.toList());
    }

    private static OrderDto buildOrderDto(final OrderEntity order) {
        return OrderDto.builder()
                .id(order.getOrderId())
                .description(order.getSmallDescription())
                .device(Optional.ofNullable(order.getDevice().getDeviceName()).map(n -> n + " ").orElse("") +
                        Optional.ofNullable(order.getDevice().getDescription()).orElse(""))
                .clientName(order.getClient().getName())
                .status(order.getExecuteStatus())
                .startedAt(order.getTimestamp().toLocalDateTime().toLocalDate())
                .build();
    }

    public List<OrderDto> getByClientId(final Integer clientId) {
        return orderRepository.findAllByClientId(clientId).stream()
                .map(RestOrderService::buildOrderDto)
                .collect(Collectors.toList());
    }
}
