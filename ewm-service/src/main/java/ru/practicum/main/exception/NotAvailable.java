package ru.practicum.main.exception;

public class NotAvailable extends RuntimeException {
    public NotAvailable(String message) {
        super(message);
    }
}
