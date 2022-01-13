package Otomasyon;

import java.util.ArrayList;

public class Kursiyer {

	private int kursiyerId;
	private String kursiyerAdSoyad;
	private int kursiyerYas;
	public ArrayList<Kurs> alinanKurslar;
	
	public Kursiyer() { 
		alinanKurslar = new ArrayList<Kurs>();
	}

	public int getKursiyerId() {
		return kursiyerId;
	}

	public void setKursiyerId(int kursiyerId) {
		this.kursiyerId = kursiyerId;
	}

	public String getKursiyerAdSoyad() {
		return kursiyerAdSoyad;
	}

	public void setKursiyerAdSoyad(String kursiyerAdSoyad) {
		this.kursiyerAdSoyad = kursiyerAdSoyad;
	}

	public int getKursiyerYas() {
		return kursiyerYas;
	}

	public void setKursiyerYas(int kursiyerYas) {
		this.kursiyerYas = kursiyerYas;
	}
	
	
}
