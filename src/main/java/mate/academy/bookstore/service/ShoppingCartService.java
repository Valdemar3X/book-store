package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.cartitem.CartItemRequestDto;
import mate.academy.bookstore.dto.cartitem.CartItemResponseDto;
import mate.academy.bookstore.dto.cartitem.CartItemUpdateRequestDto;
import mate.academy.bookstore.dto.shopingcart.ShoppingCartDto;
import mate.academy.bookstore.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart(Long userId);

    void addCartItemToCart(Long userId, CartItemRequestDto requestDto);

    CartItemResponseDto updateItem(
            Long userId,
            Long id,
            CartItemUpdateRequestDto requestDto);

    void deleteItem(Long id);

    public ShoppingCart getShoppingCartModel(Long userId);
}

