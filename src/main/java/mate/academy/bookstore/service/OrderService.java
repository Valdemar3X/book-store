package mate.academy.bookstore.service;

import java.util.List;
import mate.academy.bookstore.dto.order.CreateOrderRequestDto;
import mate.academy.bookstore.dto.order.OrderResponseDto;
import mate.academy.bookstore.dto.order.UpdateOrderDto;
import mate.academy.bookstore.dto.orderitem.OrderItemDto;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    void createUserOrder(Long userId, CreateOrderRequestDto requestDto);

    List<OrderResponseDto> findAllUserOrders(String email, Pageable pageable);

    void updateOrderStatus(Long id, UpdateOrderDto requestDto);

    List<OrderItemDto> findAllItemsFromOrder(Long orderId, Pageable pageable);

    OrderItemDto findOrderItemByOrderId(Long orderId, Long itemId);
}
