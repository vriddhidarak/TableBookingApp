// src/main/java/com/vriddhi/tablebookingapp/exception/ReservationConflictException.java
package com.vriddhi.tablebookingapp.exception;

public class ReservationConflictException extends RuntimeException {
    public ReservationConflictException(String message) {
        super(message);
    }
}