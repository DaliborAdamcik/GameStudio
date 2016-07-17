package sk.ness.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Student {

	@Id
	@GeneratedValue
	private int ident;
	
	private String meno;
	
	private String priezvisko;
	
	private int vek;
	
	public Student() {
		// TODO Auto-generated constructor stub
	}

	public String getMeno() {
		return meno;
	}

	public void setMeno(String meno) {
		this.meno = meno;
	}

	public Student(String meno, String priezvisko, int vek) {
		super();
		this.meno = meno;
		this.priezvisko = priezvisko;
		this.vek = vek;
	}

	public String getPriezvisko() {
		return priezvisko;
	}

	public void setPriezvisko(String priezvisko) {
		this.priezvisko = priezvisko;
	}

	public int getVek() {
		return vek;
	}

	public void setVek(int vek) {
		this.vek = vek;
	}

	@Override
	public String toString() {
		return "Student [ident=" + ident + ", meno=" + meno + ", priezvisko=" + priezvisko + ", vek=" + vek + "]";
	}


}
