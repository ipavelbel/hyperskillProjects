package org.hyperskill.projects.blockchain.utils;


import java.util.Random;

public class MineUtil {

    public static long getRandomMagicLong() {
        Random random = new Random();
        return random.nextLong();
    }

    public static int timeSinceInSeconds(long startTime) {
        return Math.toIntExact((System.currentTimeMillis() - startTime) / 1000L);
    }

    public static boolean startsWithValidZeros(String hashToCheck, int numberOfZeros) {
        String regex = "^0{" + numberOfZeros + "}.*";
        return hashToCheck.matches(regex);
    }

    public static void checkIfThreadIsInterrupted() throws InterruptedException {
        if (Thread.currentThread().isInterrupted())
            throw new InterruptedException();
    }

}
