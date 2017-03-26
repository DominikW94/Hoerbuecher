// -- @author Jan Schaffland --

package projekt.user;


public class User {
	
	//Klassenvariablen der User-Klasse
	private String vorname;
	private String nachname;
	private int alter;
	private String nutzername;
	private String passwort;
	private String email;
	
	//Konstruktor zum Registrieren eines Nutzers
	public User(String vorname, String nachname, int alter, String nutzername, String passwort, String email) {
		super();
		this.vorname = vorname;
		this.nachname = nachname;
		this.alter = alter;
		this.nutzername = nutzername;
		this.passwort = passwort;
		this.email = email;
	}
	
	//Getter und Setter der Klassenvariablen
	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public int getAlter() {
		return alter;
	}

	public void setAlter(int alter) {
		this.alter = alter;
	}

	public String getNutzername() {
		return nutzername;
	}

	public void setNutzername(String nutzername) {
		this.nutzername = nutzername;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
