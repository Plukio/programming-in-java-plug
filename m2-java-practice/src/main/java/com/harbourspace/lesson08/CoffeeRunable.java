package com.harbourspace.lesson08;

public class CoffeeRunable implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("Barista is making your coffee");
            Thread.sleep(5000);
            System.out.println("Your coffee is ready. Enjoy!");
        } catch (InterruptedException e) {
            System.out.println("The coffee making process was interrupted.");
        }
    }

    public static void main(String[] args) {
        Thread baristaThread = new Thread(new CoffeeRunable());
        baristaThread.start();
    }
}

