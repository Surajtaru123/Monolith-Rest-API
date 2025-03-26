package com.love.service.cart;

import com.love.model.CartItem;

public interface ICartItemService {
    // In model class we have
    // id, quantity, unitPrice, totalPrice, product, cart

    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, Integer quantity);
    CartItem getCartItem(Long cartId, Long productId);
}
