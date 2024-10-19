package hnt.com.base.mapper;

import hnt.com.base.dto.embedded.OrderDto;
import hnt.com.base.entities.embedded.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toOrderDto(Order order);
    Order toOrder(OrderDto orderDto);

    List<OrderDto> toOrderDtoListBlaBla(List<Order> orders);
    List<Order> toOrderList(List<OrderDto> orderDtos);
}
