package mate.academy.bookstore.dto.order;

import mate.academy.bookstore.model.Order;

public record UpdateOrderDto(Order.Status status) {
}
