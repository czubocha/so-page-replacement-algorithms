public class Strona {
    
    int wartosc;
    boolean znacznik;

    Strona(int wartosc) {
	this.wartosc = wartosc;
	znacznik = false;
    }

    public String toString() {
	return wartosc + " " + znacznik;
    }
}
