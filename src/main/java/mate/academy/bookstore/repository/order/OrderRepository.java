package mate.academy.bookstore.repository.order;

import java.util.List;
import mate.academy.bookstore.model.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser_Email(String email, Pageable pageable);
}

