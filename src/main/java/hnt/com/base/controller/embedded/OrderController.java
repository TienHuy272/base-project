package hnt.com.base.controller.embedded;


import hnt.com.base.dto.embedded.OrderDto;
import hnt.com.base.entities.embedded.Order;
import hnt.com.base.mapper.OrderMapper;
import hnt.com.base.repositories.embedded.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @GetMapping("")
    public List<OrderDto> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orderMapper.toOrderDtoListBlaBla(orders);
    }
}
