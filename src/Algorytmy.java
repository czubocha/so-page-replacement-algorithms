import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Algorytmy {
    private int pamiecWirtualna;
    private int pamiecFizyczna;
    private int odwolania;
    private int lokalnosc;
    static ArrayList<Integer> listaOdwolan = new ArrayList<Integer>();
    Random r = new Random();

    public Algorytmy(int pamiecWirtualna, int pamiecFizyczna, int odwolania, ArrayList<Integer> lista) {
	this.pamiecWirtualna = pamiecWirtualna;
	this.pamiecFizyczna = pamiecFizyczna;
	this.odwolania = odwolania;
	listaOdwolan = lista;
    }

    public Algorytmy() {
	Scanner scanner = new Scanner(System.in);

	System.out.println("Podaj iloœæ stron (pamiêæ wirtualna) :");
	pamiecWirtualna = scanner.nextInt();
	System.out.println("Podaj iloœæ odwo³añ :");
	odwolania = scanner.nextInt();
	System.out.println("Podaj iloœæ ramek (pamiêæ fizyczna) :");
	pamiecFizyczna = scanner.nextInt();
	System.out.println("Podaj przedzia³ lokalnoœæi :");
	lokalnosc = scanner.nextInt();

	scanner.close();
	
	generujOdwolania();
    }

    void generujOdwolania() {
	int odwolanie = r.nextInt(pamiecWirtualna);
	listaOdwolan.add(odwolanie);
	int poprzednieOdwolanie = listaOdwolan.get(0);
	int noweOdwolanie = r.nextInt(pamiecWirtualna);
	for (int i = 0; i < odwolania - 1; i++) {
	    while (Math.abs(poprzednieOdwolanie - noweOdwolanie) > lokalnosc)
		noweOdwolanie = r.nextInt(pamiecWirtualna);
	    listaOdwolan.add(noweOdwolanie);
	    poprzednieOdwolanie = noweOdwolanie;
	    noweOdwolanie = r.nextInt(pamiecWirtualna);
	}
    }
    
    public void wypelnijRamki(ArrayList<Integer> listaRamek) {
	for (int i = 0; listaRamek.size() < pamiecFizyczna; i++) {
	    if (!listaRamek.contains(listaOdwolan.get(i)))
		listaRamek.add(listaOdwolan.get(i));
	}
    }

    public void wypelnijRamkiZnacznik(ArrayList<Strona> listaRamek) {
	for (int i = 0; listaRamek.size() < pamiecFizyczna; i++) {
	    boolean czyZnaleziono = false;
	    for (int j = 0; j < listaRamek.size() && !czyZnaleziono; j++)
		if (listaRamek.get(j).wartosc == listaOdwolan.get(i))
		    czyZnaleziono = true;
	    if (!czyZnaleziono) {
		listaRamek.add(new Strona(listaOdwolan.get(i)));
	    }
	}
    }

    public int FIFO() {
	int bledy = 0;
	ArrayList<Integer> listaRamek = new ArrayList<Integer>();
	wypelnijRamki(listaRamek);

	for (int i = 0; i < listaOdwolan.size(); i++) {
//	    System.out.print("Ramki : " + listaRamek + " Nastêpne odwo³anie: " + listaOdwolan.get(i));
	    if (!listaRamek.contains(listaOdwolan.get(i))) {
		bledy++;
//		System.out.print(" Usuniêto: " + listaRamek.get(0));
		listaRamek.remove(0);
		listaRamek.add(listaOdwolan.get(i));
	    }
//	    System.out.println();
	}
//	System.out.println(bledy + "\n");
	return bledy;
    }

    public int OPT() {
	int bledy = 0;
	ArrayList<Integer> listaRamek = new ArrayList<Integer>();
	wypelnijRamki(listaRamek);
	ArrayList<Integer> kopiaOdwolan = new ArrayList<Integer>(listaOdwolan);
	
	while (kopiaOdwolan.size() > 0) {
//	    System.out.print("Ramki : " + listaRamek);
//	    System.out.print(" Nastêpne odwo³anie: " + kopiaOdwolan.get(0));
	    if (!listaRamek.contains(kopiaOdwolan.get(0))) {
		bledy++;
		boolean znaleziono = false;
		// szukanie pierwszej niepotrzebnej juz strony
		for (int j = 0; j < listaRamek.size() && !znaleziono; j++) {
		    if (!kopiaOdwolan.contains(listaRamek.get(j))) {
//			System.out.print(" Usuniêto: " + listaRamek.get(j));
			listaRamek.remove(j);
			znaleziono = true;
		    }
		}
		// szukanie najdluzej nieuzywanej
		if (!znaleziono) {
		    ArrayList<Integer> kopiaRamek = new ArrayList<Integer>(listaRamek);
		    Integer ostatni = null;
		    for (int j = 0; j < kopiaOdwolan.size() && kopiaRamek.size() > 0; j++) {
			if (kopiaRamek.contains(kopiaOdwolan.get(j))) {
			    ostatni = kopiaOdwolan.get(j);
			    // System.out.print(" Ostatni: "+ostatni+" "+kopiaRamek);
			    kopiaRamek.remove(kopiaOdwolan.get(j));
			}
		    }
//		    System.out.print(" Usuniêto: " + ostatni);
		    listaRamek.remove(ostatni);
		}
		listaRamek.add(kopiaOdwolan.get(0));

	    }
	    kopiaOdwolan.remove(0);
//	    System.out.println();
	}
//	System.out.println(bledy + "\n");
	return bledy;
    }

    public int LRU() {
	int bledy = 0;
	ArrayList<Integer> listaRamek = new ArrayList<Integer>();
	wypelnijRamki(listaRamek);
	
	for (int i = 0; i < listaOdwolan.size(); i++) {
//	    System.out.print("Ramki : " + listaRamek);
//	    System.out.print(" Nastêpne odwo³anie: " + listaOdwolan.get(i));
	    if (!listaRamek.contains(listaOdwolan.get(i))) {
		bledy++;
//		System.out.print(" Usuniêto: " + listaRamek.get(0));
		listaRamek.remove(0);
	    } else {
		// System.out.print(" Usuwamy bez b³êdu: " + listaOdwolan.get(i));
		listaRamek.remove(listaOdwolan.get(i));
	    }
	    listaRamek.add(listaOdwolan.get(i));
//	    System.out.println();
	}
//	System.out.println(bledy + "\n");
	return bledy;
    }

    public int aLRU() {
	int bledy = 0;
	ArrayList<Strona> listaRamek = new ArrayList<Strona>();
	ArrayList<Integer> kopiaOdwolan = new ArrayList<Integer>(listaOdwolan);
	wypelnijRamkiZnacznik(listaRamek);
	
	while (kopiaOdwolan.size() > 0) {
//	    System.out.print("Ramki : " + listaRamek);
//	    System.out.print(" Nastêpne odwo³anie: " + kopiaOdwolan.get(0));
	    boolean czyZawiera = false;
	    for (int i = 0; i < listaRamek.size(); i++)
		if (listaRamek.get(i).wartosc == kopiaOdwolan.get(0)) {
		    int pomocnicza = kopiaOdwolan.get(0);
		    listaRamek.remove(i);
		    listaRamek.add(new Strona(pomocnicza));
		    czyZawiera = true;
		}

	    if (czyZawiera == false) {
		bledy++;
//		int pomocnicza = 0;
		boolean czyUsunieto = false;
		boolean czyUsunietoPrzed = false;
		boolean pomocniczyZnacznik = false;
		for (int k = 0; k < listaRamek.size(); k++)
		    if (listaRamek.get(k).znacznik == true && pomocniczyZnacznik == false) {
//			pomocnicza = listaRamek.get(k).wartosc;
			listaRamek.remove(k);
			czyUsunietoPrzed = true;
			pomocniczyZnacznik = true;
		    }
		if (czyUsunietoPrzed == false) {
		    for (int l = 0; l < listaRamek.size(); l++)
			listaRamek.get(l).znacznik = true;
		    for (int k = 0; k < listaRamek.size(); k++)
			if (listaRamek.get(k).znacznik == true && czyUsunieto == false) {
//			    pomocnicza = listaRamek.get(k).wartosc;
			    listaRamek.remove(k);
			    czyUsunieto = true;
			}
		}
//		System.out.print(" Usuniêto: " + pomocnicza);

		listaRamek.add(new Strona(kopiaOdwolan.get(0)));
	    }
	    kopiaOdwolan.remove(0);
//	    System.out.println();
	}
//	System.out.println(bledy + "\n");
	return bledy;
    }

    public int RAND() {
	int bledy = 0;
	ArrayList<Integer> listaRamek = new ArrayList<Integer>();
	wypelnijRamki(listaRamek);
	
	for (int i = 0; i < listaOdwolan.size(); i++) {
//	    System.out.print("Ramki : " + listaRamek);
//	    System.out.print(" Nastêpne odwo³anie: " + listaOdwolan.get(i));
	    if (!listaRamek.contains(listaOdwolan.get(i))) {
		bledy++;
//		System.out.print(" Usuniêto: " + listaRamek.get(losowy));
		listaRamek.remove(r.nextInt(pamiecFizyczna));
		listaRamek.add(listaOdwolan.get(i));
	    }
//	    System.out.println();
	}
//	System.out.println(bledy + "\n");
	return bledy;
    }

}
