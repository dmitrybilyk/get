package com.knowledge.get.kotlin.service

data class Product(val id: String, val name: String, val price: Double)

data class CartItem(val product: Product, var quantity: Int)

data class ShoppingCard(val items: MutableList<CartItem>) {
    fun addProduct(product: Product, quantity: Int = 1) {
        items.add(CartItem(product, quantity))
    }

    fun removeProduct(product: Product, quantity: Int = 1) {
        val cartItem = items.find { it.product.id == product.id }
        if (cartItem != null) {
            items.remove(cartItem)
        }
    }

    fun updateQuantity(productId: String, newQuantity: Int) {
        val cartItem = items.find { it.product.id == productId }
        if (cartItem != null) {
            cartItem.quantity = newQuantity
        }
    }

    fun getTotalPrice(): Double {
        return items.sumOf { item -> item.product.price }
    }

    fun listItems(): List<CartItem> {
        return items.toList()
    }
}

