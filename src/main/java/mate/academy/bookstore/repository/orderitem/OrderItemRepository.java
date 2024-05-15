package mate.academy.bookstore.repository.orderitem;

import mate.academy.bookstore.model.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrder_Id(Long orderId, Pageable pageable);

    OrderItem findByOrder_IdAndId(Long orderId, Long orderItemId);
}