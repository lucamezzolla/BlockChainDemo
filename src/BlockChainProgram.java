import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BlockChainProgram {

    private ArrayList<Block> blockChain = new ArrayList<>();
    private static Scanner stringScanner = new Scanner(System.in);
    private static Scanner intScanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BlockChainProgram bcp = new BlockChainProgram();
        while(true) {
            bcp.printMenu();
        }
    }

    private void printMenu() {
        int option;
        System.out.println();
        System.out.println("1) Aggiungi blocco");
        System.out.println("2) Mostra l'intera blockchain");
        System.out.println("3) Controlla integrità blockchain");
        System.out.println("Scegli 0 per uscire");
        try {
            option = intScanner.nextInt();
            if (option == 1) {
                addBlock();
            } else if (option == 2) {
                showBlockChain();
            } else if (option == 3) {
                if (blockChain.isEmpty()) {
                    System.out.println("La blockchain è vuota.");
                } else if (checkBlockChain()) {
                    System.out.println("La blockchain è corretta.");
                } else {
                    System.out.println("La blockchain presenta blocchi corrotti o manipolati.");
                }
            } else if (option == 0) {
                intScanner.close();
                stringScanner.close();
                System.out.println("Arrivederci.");
                System.exit(0);
            }
        } catch(InputMismatchException e) {
            System.out.println("Errore.");
            System.exit(1);
        }
    }

    private void addBlock() {
        System.out.print("Quanti dati vuoi inserire? ");
        int dataIndex = intScanner.nextInt();
        String[] data = new String[dataIndex];
        for(int i = 0; i < dataIndex; i++) {
            System.out.print("Inserisci data: ");
            data[i] = stringScanner.nextLine();
        }
        int previousHash = 0;
        if(!blockChain.isEmpty()) {
            previousHash = ((Block) blockChain.get(blockChain.size() - 1)).getBlockHash();
        }
        blockChain.add(new Block(data, previousHash));
    }

    private void showBlockChain() {
        for(Block block : blockChain) {
            System.out.println(block);
        }
    }

    private boolean checkBlockChain() {
        int corruptBlocks = 0;
        for(int i = 0; i < blockChain.size(); i++) {
            Block b = blockChain.get(i);
            if(i < blockChain.size() - 1) {
                Block bb = blockChain.get(i + 1);
                if (bb.getPreviousBlockHash() != b.getBlockHash()) {
                    ++corruptBlocks;
                }
            }
        }
        return corruptBlocks == 0;
    }

}