package org.example;

import org.example.ui.View;


public class Main {
    public static void main(String[] args) {
        View view = new View();
        try {
            view.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}