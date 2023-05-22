package org.hyperskill.projects.blockchain;


import java.util.Date;
import java.util.Random;

public class Block {

    private static int numberofPrefixedHashZeros;
    private static int id = 1;
    private final int blockId;
    private final String hash;
    private final String previousHash;
    private final long timeStamp;
    private long magicNumber;
    private final int blockCreationTime;

    public Block(String previousHash) {
        long startTime = System.currentTimeMillis();
        this.blockId = id++;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.magicNumber = Block.getRandomMagicLong();
        this.hash = calculateValidHash();
        this.blockCreationTime = Block.timeSinceInSeconds(startTime);
    }

    private static int timeSinceInSeconds(long startTime) {
        return Math.toIntExact((System.currentTimeMillis() - startTime) / 1000L);
    }


    public static void setNumberofPrefixedHashZeros(int mumberofPrefixedHashZeros) {
        Block.numberofPrefixedHashZeros = mumberofPrefixedHashZeros;
    }

    private static long getRandomMagicLong() {
        Random random = new Random();
        return random.nextLong();
    }

    private String calculateValidHash() {
        String currentHash = this.calculateCurrentHash();
        while (!startsWithValidZeros(currentHash)) {
            this.magicNumber = Block.getRandomMagicLong();
            currentHash = this.calculateCurrentHash();
        }
        return currentHash;
    }

    private boolean startsWithValidZeros(String currentHash) {
        String regex = "^0{" + Block.numberofPrefixedHashZeros + "}.*";
        return currentHash.matches(regex);
    }

    private String calculateCurrentHash() {
        return StringUtil.applySha256(
                this.blockId +
                        this.previousHash +
                        this.timeStamp +
                        this.magicNumber);
    }

    public String getHash() {
        return this.hash;
    }

    @Override
    public String toString() {
        String sb = "Block:\n" +
                "Id: " + this.blockId + "\n" +
                "Timestamp: " + timeStamp + "\n" +
                "Magic number: " + this.magicNumber + "\n" +
                "Hash of the previous block: \n" + previousHash + "\n" +
                "Hash of the block: \n" + hash + "\n" +
                "Block was generating for " + this.blockCreationTime + " seconds\n";
        return sb;
    }
}