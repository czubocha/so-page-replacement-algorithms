import java.io.IOException;

public class Test {
    
    public static void main(String[] args) throws IOException {

	Algorytmy test = new Algorytmy();
	
	System.out.println("\nOdwolania: " + Algorytmy.listaOdwolan + "\n");

	System.out.println("Ilo�� b��d�w stron dla FIFO: " + test.FIFO());
	System.out.println("Ilo�� b��d�w stron dla OPT: " + test.OPT());
	System.out.println("Ilo�� b��d�w stron dla LRU: " + test.LRU());
	System.out.println("Ilo�� b��d�w stron dla LRU aproksymowanego: " + test.aLRU());
	System.out.println("Ilo�� b��d�w stron dla RANDOM: " + test.RAND());
    }
}
