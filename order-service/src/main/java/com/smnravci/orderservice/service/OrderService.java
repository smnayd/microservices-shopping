package com.smnravci.orderservice.service;

import com.smnravci.orderservice.dto.OrderLineItemsDto;
import com.smnravci.orderservice.dto.OrderRequest;
import com.smnravci.orderservice.model.Order;
import com.smnravci.orderservice.model.OrderLineItems;
import com.smnravci.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        order.setOrderLineItemsList(orderLineItems);
        orderRepository.save(order);
    }
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public Optional<Order> getOrderById(int id){
        return orderRepository.findById(id);
    }

    public Order updateOrder(int id, OrderRequest orderRequest){
        Optional<Order> existingOrderOptional = orderRepository.findById(id);
        if (existingOrderOptional.isPresent()) {
            Order existingOrder = existingOrderOptional.get();
            existingOrder.getOrderLineItemsList().clear();
            for (OrderLineItemsDto lineItemDto : orderRequest.getOrderLineItemsDtoList()) {
                OrderLineItems newLineItem = new OrderLineItems();
                newLineItem.setSkuCode(lineItemDto.getSkuCode());
                newLineItem.setPrice(lineItemDto.getPrice());
                newLineItem.setQuantity(lineItemDto.getQuantity());
                existingOrder.getOrderLineItemsList().add(newLineItem);
            }
            return orderRepository.save(existingOrder);
        } else {
            return null;
        }
    }
    public void deleteOrder(int id){
        orderRepository.deleteById(id);
    }
}





