package org.hyperskill.projects.blockchain.mining;

import java.util.concurrent.Callable;

public class Miner implements Callable<Block> {
    private final Blockchain blockchain;
    private final int minerId;

    public Miner(Blockchain blockchain, int minerId) {
        this.blockchain = blockchain;
        this.minerId = minerId;
    }

    @Override
    public Block call() {
        try {
            return Block.mineBlock(blockchain, minerId);
        } catch (InterruptedException e) {
            throw new RuntimeException("Miner " + this.minerId + " was interrupted.");
        }
    }
}