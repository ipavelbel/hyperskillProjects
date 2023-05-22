package org.hyperskill.projects.blockchain.mining;

import org.hyperskill.projects.blockchain.utils.MineUtil;
import org.hyperskill.projects.blockchain.utils.StringUtil;

import java.util.Date;


public class Block {

    private final int blockId;
    private final String hash;
    private final String previousHash;
    private final long timeStamp;
    private long magicNumber;
    private final int blockCreationTime;
    private final long minerId;

    private Block(String previousHash, int blockId, long minerId) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        this.minerId = minerId;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.magicNumber = MineUtil.getRandomMagicLong();
        this.hash = calculateValidHash();
        this.blockId = blockId;
        this.blockCreationTime = MineUtil.timeSinceInSeconds(startTime);

    }

    public static Block mineBlock(Blockchain blockchain, int minerId) throws InterruptedException {

        if (blockchain.getChainSize() == 0)
            return new Block("0", 1, minerId);
        else
            return new Block(blockchain.getLastBlock().getHash(), blockchain.getLastBlockId() + 1, minerId);
    }


    private String calculateValidHash() throws InterruptedException {
        String currentHash = this.calculateCurrentHash();
        while (!MineUtil.startsWithValidZeros(currentHash, Blockchain.numberOfHashZeros)) {
            this.magicNumber = MineUtil.getRandomMagicLong();
            currentHash = this.calculateCurrentHash();
            MineUtil.checkIfThreadIsInterrupted();
        }
        return currentHash;
    }

    private String calculateCurrentHash() {
        return StringUtil.applySha256(
                this.blockId +
                        this.previousHash +
                        this.timeStamp +
                        this.magicNumber);
    }

    String getHash() {
        return this.hash;
    }

    String getPreviousHash() {
        return previousHash;
    }

    int getBlockCreationTime() {
        return blockCreationTime;
    }

    int getBlockId() {
        return this.blockId;
    }

    long getMinerId() {
        return this.minerId;
    }

    @Override
    public String toString() {
        return "Block:\n" +
                "Created by miner # " + this.minerId + "\n" +
                "Id: " + this.blockId + "\n" +
                "Timestamp: " + timeStamp + "\n" +
                "Magic number: " + this.magicNumber + "\n" +
                "Hash of the previous block: \n" + previousHash + "\n" +
                "Hash of the block: \n" + hash + "\n" +
                "Block was generating for " + this.blockCreationTime + " seconds";
    }
}