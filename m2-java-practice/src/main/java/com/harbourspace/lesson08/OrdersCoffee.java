package com.harbourspace.lesson08;

import java.util.concurrent.atomic.AtomicInteger;

public class OrdersCoffee implements Runnable {
    private static AtomicInteger orders = new AtomicInteger(5);

    @Override
    public void run() {
        while (orders.get() > 0) {
            try {
                System.out.println("Barista is making coffee. Orders left: " + orders);
                Thread.sleep(5000);
                System.out.println("Your coffee is ready. Enjoy! Orders left: " + orders.decrementAndGet());
            } catch (InterruptedException e) {
                System.out.println("The coffee making process was interrupted.");
            }
        }
    }

    public static void main(String[] args) {
        Thread baristaThread = new Thread(new OrdersCoffee());
        baristaThread.start();
    }
}
