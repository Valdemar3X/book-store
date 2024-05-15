package mate.academy.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.order.CreateOrderRequestDto;
import mate.academy.bookstore.dto.order.OrderResponseDto;
import mate.academy.bookstore.dto.order.UpdateOrderDto;
import mate.academy.bookstore.dto.orderitem.OrderItemDto;
import mate.academy.bookstore.dto.shopingcart.ShoppingCartDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.OrderItemMapper;
import mate.academy.bookstore.mapper.OrderMapper;
import mate.academy.bookstore.mapper.ShoppingCartMapper;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.model.CartItem;
import mate.academy.bookstore.model.Order;
import mate.academy.bookstore.model.OrderItem;
import mate.academy.bookstore.model.ShoppingCart;
import mate.academy.bookstore.model.User;
import mate.academy.bookstore.repository.order.OrderRepository;
import mate.academy.bookstore.repository.orderitem.OrderItemRepository;
import mate.academy.bookstore.repository.shopingcart.ShoppingCartRepository;
import mate.academy.bookstore.service.OrderService;
import mate.academy.bookstore.service.ShoppingCartService;
import mate.academy.bookstore.service.UserService;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final ShoppingCartService shoppingCartService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final OrderMapper orderMapper;
    private final ShoppingCartMapper shoppingCartMapper;
    private final OrderItemMapper orderItemMapper;
    private final Order order = new Order();

    @Override
    @Transactional
    public void createUserOrder(Long userId, CreateOrderRequestDto requestDto) {
        ShoppingCartDto dto = shoppingCartService.getShoppingCart(userId);
        ShoppingCart shoppingCart = shoppingCartMapper.toModel(dto);
        User user = userService.findById(userId);
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);
        order.setTotal(countTotal(shoppingCart.getCartItems()));
        order.setTotal(BigDecimal.valueOf(100));
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.shippingAddress());
        Order savedOrder = orderRepository.save(order);
        order.setOrderItems(getOrderItemsFromCartItems(savedOrder,
                shoppingCart.getCartItems()));
        orderRepository.save(savedOrder);
        shoppingCart.getCartItems().clear();
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public List<OrderResponseDto> findAllUserOrders(String email, Pageable pageable) {
        return orderRepository.findByUser_Email(email, pageable).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public void updateOrderStatus(Long id, UpdateOrderDto requestDto) {
        Order orderById = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find order by id: " + id));
        orderById.setStatus(requestDto.status());
        orderRepository.save(orderById);
    }

    @Override
    public List<OrderItemDto> findAllItemsFromOrder(Long orderId, Pageable pageable) {
        return orderItemRepository.findAllByOrder_Id(orderId, pageable).stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto findOrderItemByOrderId(Long orderId, Long itemId) {
        return orderItemMapper.toDto(
                orderItemRepository.findByOrder_IdAndId(orderId, itemId));
    }
y    private BigDecimal countTotal(Set<CartItem> cartItems) {
        return BigDecimal.valueOf(cartItems.stream()
                .mapToDouble(ci ->
                        ci.getQuantity() * ci.getBook().getPrice().doubleValue())
                .sum());
    }

    private Set<OrderItem> getOrderItemsFromCartItems(Order order, Set<CartItem> cartItems) {
        Set<OrderItem> orderItems = new HashSet<>();

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getBook().getPrice());
            orderItems.add(orderItemRepository.save(orderItem));
        }
        return orderItems;
    }

}