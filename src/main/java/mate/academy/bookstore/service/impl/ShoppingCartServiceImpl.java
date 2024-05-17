package mate.academy.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.cartitem.CartItemRequestDto;
import mate.academy.bookstore.dto.cartitem.CartItemResponseDto;
import mate.academy.bookstore.dto.cartitem.CartItemUpdateRequestDto;
import mate.academy.bookstore.dto.shopingcart.ShoppingCartDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.CartItemMapper;
import mate.academy.bookstore.mapper.ShoppingCartMapper;
import mate.academy.bookstore.model.CartItem;
import mate.academy.bookstore.model.ShoppingCart;
import mate.academy.bookstore.repository.cartitem.CartItemRepository;
import mate.academy.bookstore.repository.shopingcart.ShoppingCartRepository;
import mate.academy.bookstore.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartDto getShoppingCart(Long userId) {
        return shoppingCartMapper.toDto(getShoppingCartModel(userId));
    }

    @Override
    @Transactional
    public void addCartItemToCart(Long userId, CartItemRequestDto requestDto) {
        CartItem cartItem = cartItemMapper.toModel(requestDto);
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "can't find shopping cart by user id : " + userId));
        shoppingCart.getCartItems().stream()
                .filter(item -> item.getBook().getId()
                        .equals(requestDto.getBookId())).findFirst()
                .ifPresentOrElse(
                        item -> item.setQuantity(item.getQuantity() + requestDto.getQuantity()),
                        () -> addItemToCart(cartItem, shoppingCart));
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public CartItemResponseDto updateItem(
            Long userId,
            Long id,
            CartItemUpdateRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "can't find shopping cart by user id : " + userId));
        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "can't find item by id: " + id));
        cartItem.setQuantity(requestDto.getQuantity());
        CartItem savedItem = cartItemRepository.save(cartItem);
        return cartItemMapper.toDto(savedItem);
    }

    @Override
    public void deleteItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public ShoppingCart getShoppingCartModel(Long userId) {
        return shoppingCartRepository.findShoppingCartByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "can't find shopping cart by user id : " + userId));
    }

    private void addItemToCart(CartItem cartItem, ShoppingCart shoppingCart) {
        cartItem.setShoppingCart(shoppingCart);
        cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(cartItem);
    }
}
