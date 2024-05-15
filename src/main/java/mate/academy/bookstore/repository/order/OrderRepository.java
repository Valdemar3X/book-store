package mate.academy.bookstore.repository.order;

import mate.academy.bookstore.model.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_Email(String email, Pageable pageable);
}