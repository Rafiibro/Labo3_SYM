package com.example.labo3_sym;
import java.util.EventListener;

public interface NFCListener extends EventListener {
    boolean handleNcfResponse(boolean response);
}
