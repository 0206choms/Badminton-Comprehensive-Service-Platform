package com.kiwi.exception;

public class OutOfStockException  extends RuntimeException{
    public OutOfStockException(String message) {
        super(message);
    }
}
