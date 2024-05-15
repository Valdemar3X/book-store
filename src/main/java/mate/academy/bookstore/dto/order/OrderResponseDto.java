package mate.academy.bookstore.dto.order;

import lombok.Data;
import mate.academy.bookstore.dto.orderitem.OrderItemDto;
import mate.academy.bookstore.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private Set<OrderItemDto> orderItems;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private Order.Status status;
}