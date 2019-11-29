package com.example.labo3_sym;
import java.util.EventListener;

public interface NFCListener extends EventListener {
    public void handleNfcResponse();
}
