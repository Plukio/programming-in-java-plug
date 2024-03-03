package com.harbourspace.lesson08;

public class CoffeeTread {
    public static void main(String[] args) {
        Thread baristaThread = new Thread(() -> {
            try {
                System.out.println("Barista is making your coffee");
                Thread.sleep(5000); // Wait for 5 seconds
                System.out.println("Your coffee is ready. Enjoy!");
            } catch (InterruptedException e) {
                System.out.println("The coffee making process was interrupted.");
            }
        });

        baristaThread.start();
    }
}
