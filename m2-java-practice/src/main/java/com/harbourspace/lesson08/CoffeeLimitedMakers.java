package com.harbourspace.lesson08;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class CoffeeLimitedMakers implements Runnable{
    private static AtomicInteger orders = new AtomicInteger(3);
    private static Semaphore coffeeMachines = new Semaphore(1);

    @Override
    public void run() {
        while (orders.get() > 0) {
            try {
                coffeeMachines.acquire();
                if (orders.get() > 0) { // Double-check pattern
                    System.out.println(Thread.currentThread().getName() + "'s coffees is being made. Orders left: " + orders);
                    Thread.sleep(5000); // Simulate making coffee
                    System.out.println(Thread.currentThread().getName() + "'s coffee is ready. Enjoy! Orders left: " + orders.decrementAndGet());
                }
                coffeeMachines.release();
            } catch (InterruptedException e) {
                System.out.println("The coffee making process was interrupted.");
            }
        }
    }

    public static void main(String[] args) {
        Thread baristaThread1 = new Thread(new CoffeeLimitedMakers(), "Costumer 1");
        Thread baristaThread2 = new Thread(new CoffeeLimitedMakers(), "Costumer 2");
        baristaThread1.start();
        baristaThread2.start();
    }
}
