package com.example.osman.grandresturant.classes;

public class Order {
    private RequestsClass item;
    private int quantity;

    public Order() {
    }

    public Order(RequestsClass item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public RequestsClass getItem() {
        return item;
    }

    public void setItem(RequestsClass item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
