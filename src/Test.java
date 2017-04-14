import java.io.IOException;

public class Test {
    
    public static void main(String[] args) throws IOException {

	Algorytmy test = new Algorytmy();
	
	System.out.println("\nOdwolania: " + Algorytmy.listaOdwolan + "\n");

	System.out.println("Iloœæ b³êdów stron dla FIFO: " + test.FIFO());
	System.out.println("Iloœæ b³êdów stron dla OPT: " + test.OPT());
	System.out.println("Iloœæ b³êdów stron dla LRU: " + test.LRU());
	System.out.println("Iloœæ b³êdów stron dla LRU aproksymowanego: " + test.aLRU());
	System.out.println("Iloœæ b³êdów stron dla RANDOM: " + test.RAND());
    }
}
