package com.surowka.service;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class GuiConsole {

    private final Scanner scanner;

    public GuiConsole(Scanner scanner) {
        this.scanner = scanner;
        Optional.of(scanner).orElseThrow(() -> new IllegalArgumentException("scanner is null"));
    }

    public void print(String text) {
        System.out.println(text);
    }

    public String getUserAnswer() {
        return scanner.nextLine().trim();
    }

    public void destroyGui() {
        scanner.close();
    }
}
