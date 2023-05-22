package org.hyperskill.projects.blockchain;
import java.util.Scanner;

public class Main {

    private static final int BLOCKCHAIN_SIZE = 5;
    public static void main(String[] args) {
        int numberOfHashZeros = Main.getIntFromCLI(
                "Enter how many zeros the hash must start with:");
        Block.setNumberofPrefixedHashZeros(numberOfHashZeros);

        Main.createBlocks(BLOCKCHAIN_SIZE);
    }

    private static void createBlocks(int n) {
        Block block = new Block("0");
        System.out.println(block);
        for (int i = 1; i < n; i++) {
            System.out.println();
            block = new Block(block.getHash());
            System.out.println(block);
        }
    }

    private static int getIntFromCLI(String prompt) {
        System.out.println(prompt);
        try (Scanner scanner = new Scanner(System.in)) {
            return scanner.nextInt();
        }
    }
}