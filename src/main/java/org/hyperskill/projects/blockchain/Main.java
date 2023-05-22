package org.hyperskill.projects.blockchain;

import org.hyperskill.projects.blockchain.mining.Block;
import org.hyperskill.projects.blockchain.mining.Blockchain;
import org.hyperskill.projects.blockchain.mining.Miner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

    private static final int BLOCKCHAIN_SIZE = 5;
    private static final int NUMBER_OF_MINERS = 5;

    private static final List<Miner> miners = new ArrayList<>();

    public static void main(String[] args) {

        Blockchain blockchain = Blockchain.getInstance();
        Main.startMining(blockchain);
    }

    private static void startMining(Blockchain blockchain) {
        while (blockchain.getChainSize() < BLOCKCHAIN_SIZE) {
            Main.addMinersToPool(NUMBER_OF_MINERS, blockchain);
            Main.mineOneBlock(blockchain, miners);
            System.out.println(blockchain.getLastBlock());
        }
    }

    private static void addMinersToPool(int numberOfMiners, Blockchain blockchain) {
        for (int i = 0; i < numberOfMiners; i++)
            miners.add(new Miner(blockchain, i));
    }

    private static void mineOneBlock(Blockchain blockchain, List<Miner> miners) {
        ExecutorService threadPool = Executors.newFixedThreadPool(miners.size());

        // Shut down all miners once one of them finds a valid block
        try {
            Block resultingBlock = threadPool.invokeAny(miners);
            blockchain.addBlock(resultingBlock);
            threadPool.shutdownNow();
            miners.clear();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}