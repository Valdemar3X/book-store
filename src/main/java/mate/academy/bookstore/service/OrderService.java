package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.order.CreateOrderRequestDto;
import mate.academy.bookstore.dto.order.OrderResponseDto;
import mate.academy.bookstore.dto.order.UpdateOrderDto;
import mate.academy.bookstore.dto.orderitem.OrderItemDto;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {
    void createUserOrder(Long userId, CreateOrderRequestDto requestDto);

    List<OrderResponseDto> findAllUserOrders(String email, Pageable pageable);

    void updateOrderStatus(Long id, UpdateOrderDto requestDto);

    List<OrderItemDto> findAllItemsFromOrder(Long orderId, Pageable pageable);

    OrderItemDto findOrderItemByOrderId(Long orderId, Long itemId);
}