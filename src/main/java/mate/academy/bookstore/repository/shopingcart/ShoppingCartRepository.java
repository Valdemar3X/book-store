package mate.academy.bookstore.repository.shopingcart;

import java.util.Optional;
import mate.academy.bookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @EntityGraph(attributePaths = {"cartItems"})
    Optional<ShoppingCart> findShoppingCartByUserId(Long userId);
}
