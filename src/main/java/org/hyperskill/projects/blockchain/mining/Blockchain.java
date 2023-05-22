package org.hyperskill.projects.blockchain.mining;

import org.hyperskill.projects.blockchain.utils.MineUtil;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Blockchain {
    private static Blockchain instance;
    private Deque<Block> chain = new ConcurrentLinkedDeque<>();
    static int numberOfHashZeros = 0;

    public static Blockchain getInstance() {
        if (Blockchain.instance == null)
            Blockchain.instance = new Blockchain();
        return Blockchain.instance;
    }

    public synchronized void addBlock(Block block) {
        if (!isValidNextBlock(block))
            System.err.println("Miner " + block.getMinerId() + " tried to add an invalid block to the chain.");
        else {
            this.chain.add(block);
            System.out.println(block);
            setNumberOfHashZeros();
        }
    }

    private boolean isValidNextBlock(Block block) {
        if (block == null)
            return false;
        else if (this.getChainSize() == 0)
            return block.getPreviousHash().equals("0");
        else
            return (this.getLastBlock().getHash().equals(block.getPreviousHash()) &&
                    MineUtil.startsWithValidZeros(block.getHash(), Blockchain.numberOfHashZeros));
    }

    public Block getLastBlock() {
        if (this.getChainSize() == 0)
            throw new IllegalStateException("Blockchain is empty.");
        else
            return this.chain.getLast();
    }

    String getLastBlockHash() {
        if (this.getChainSize() == 0)
            return "0";
        else
            return this.getLastBlock().getHash();
    }

    int getLastBlockId() {
        if (this.getChainSize() == 0)
            return 0;
        else
            return this.getLastBlock().getBlockId();
    }

    private void setNumberOfHashZeros() { // TODO Requirements unclear, guessing something reasonable...
        if (this.getLastBlock().getBlockCreationTime() < 10) {
            Blockchain.numberOfHashZeros++;
            System.out.println("N was increased to " + Blockchain.numberOfHashZeros);
        } else if (this.getLastBlock().getBlockCreationTime() < 60) {
            System.out.println("N stays the same");
        } else {
            Blockchain.numberOfHashZeros--;
            System.out.println("N was decreased by 1");
        }
        System.out.println();
    }

    public int getChainSize() {
        return this.chain.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Block block : this.chain)
            sb.append(block.toString()).append("\n");
        return sb.toString();
    }
}