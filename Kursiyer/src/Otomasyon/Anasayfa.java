package Otomasyon;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Anasayfa {
	
	/*
		 kursiyer.txt                                    
		#5050-Ahmet Ada-23
		*1000-Yüzme
		*1040-Judo
		#6085-Selda Þahin-35
		*1020-Yoga
		#5090-Deniz Arslan-30
		*1000-Yüzme
		*1060-Plates
		#6174-Tarýk Kýlýç-42
		*1010-Fitness
		*1030-Tekvando
		*1050-Boks
		*
		*kurs.txt
		*1000-Yüzme
         1010-Fitness
         1020-Yoga
         1030-Tekvando
         1040-Judo
         1050-Boks
         1060-Plates
	 */
	
	ArrayList<Kursiyer> kursiyerler = new ArrayList<Kursiyer>();
	ArrayList<Kurs> kurslar = new ArrayList<Kurs>();
	
	void KursiyerOku() throws Throwable {//kursiyer.txt dosyamýzý okuyor.
		File f = new File("kursiyer.txt");
		if(!f.exists()) {
			f.createNewFile();
		}
		FileReader fr = new FileReader(f);
		try (BufferedReader br = new BufferedReader(fr)) {
			Kursiyer kursiyer = null;
			do {
				String satir = br.readLine();
				if(satir == null) break;
				if(satir.charAt(0) == '#') {
					kursiyer = new Kursiyer();
					String[] dizi = satir.split("-");
					kursiyer.setKursiyerId(Integer.parseInt(dizi[0].substring(1)));
					kursiyer.setKursiyerAdSoyad(dizi[1]);
					kursiyer.setKursiyerYas(Integer.parseInt(dizi[2]));
					kursiyerler.add(kursiyer);
				}
				else {
					Kurs kurs = new Kurs();
					String[] dizi = satir.split("-");
					kurs.setKursId(Integer.parseInt(dizi[0].substring(1)));
					kurs.setKursAd(dizi[1]);
					kursiyer.alinanKurslar.add(kurs);
				}
			}while(true);
			br.close();
		}
		
	}
	
	void KursOku() throws Throwable {//kurs.txt okuma
		File f = new File("kurs.txt");
		if(!f.exists()) {
			f.createNewFile();
		}
		FileReader fr = new FileReader(f);
		try (BufferedReader br = new BufferedReader(fr)) {
			do {
				String satir = br.readLine();
				if(satir == null) break;
				Kurs kurs = new Kurs();
				String[] dizi = satir.split("-");
				kurs.setKursId(Integer.parseInt(dizi[0]));
				kurs.setKursAd(dizi[1]);
				kurslar.add(kurs);
			}while(true);
			br.close();
		}
	}
	
	
	boolean KursIdKontrol(int id) {
		for (Kurs kurs : kurslar) {
			if(id == kurs.getKursId()) return false;
		}
		return true;
	}
	
	
	
	void KursEkle() {
		int id = verigirisi_int("KursID :");
		if(KursIdKontrol(id)) { //kursýd yi kontrol ediyor bir ustteki fonksiyonda daha sonra ekleme yapýyor
			String kursAdi = verigirisi("Kurs Adý: ");
			Kurs kurs = new Kurs();
			kurs.setKursId(id);
			kurs.setKursAd(kursAdi);
			kurslar.add(kurs);
		}
		else {
			System.out.println("Bu id kayýtlý");
			KursEkle();
		}
	}
	
	
	
	void KursListele() {
		System.out.println("Kurs Id\tKurs Adý");
		for (Kurs kurs : kurslar) {
			System.out.println(kurs.getKursId() +"\t"+ kurs.getKursAd());
		}
	}
	
	
	
	
	boolean KursiyerIdKontrol(int id) {
		for (Kursiyer kursiyer : kursiyerler) {
			if(id == kursiyer.getKursiyerId()) return false;
		}
		return true;
	}
	
	void KursiyerEkle() {
	 	int id = verigirisi_int("KursiyerID :");
		if(KursiyerIdKontrol(id)) {
			Kursiyer kursiyer = new Kursiyer();
			kursiyer.setKursiyerId(id);
			String kursiyerAdiSoyadi = verigirisi("Kursiyer Adý Soyadý: ");
			kursiyer.setKursiyerAdSoyad(kursiyerAdiSoyadi);
			int kursiyerYas = verigirisi_int("Kursiyer Yas: ");
			kursiyer.setKursiyerYas(kursiyerYas);
			boolean durum1=true;
			do {
				ekranayaz("1) Kurs Ekle ", true);
				ekranayaz("2) Kaydý Tamamla", true);
				ekranayaz("3) Menu Ekranýna Dön", true);
				int secim = verigirisi_int("Sec:");
				switch (secim) { 
				case 1:
					for(int i=0;i<kurslar.size();i++) {//kurs secimi icin kayýtlý kurslarý listeler.
						System.out.println((i+1)+"\t"+ kurslar.get(i).getKursId() +"\t" + kurslar.get(i).getKursAd());
	 				}
					System.out.println(kurslar.size()+1 +"\t\tVazgec");
					int kurssecimi = verigirisi_int("Kurs Sec (Secmek istediginiz kursun numarasýný girin):");
					if(kurssecimi <= kurslar.size()) {
						kursiyer.alinanKurslar.add(kurslar.get(kurssecimi-1));
					}
					break;
				case 2:
					kursiyerler.add(kursiyer);
					kursiyer = null;
					break;
				case 3:
					kursiyer = null;
					durum1=false;
					break;
				default:
					durum1=false;
					break;
				}
			}while(durum1);
		}
		else {
			System.out.println("Bu id kayýtlý!");
			KursiyerEkle();
		}
	}
	
	
	
	void KursiyerArama(String adSoyad) {
		int sayi = 0;
		for (Kursiyer kursiyer : kursiyerler) {
			if(kursiyer.getKursiyerAdSoyad().contains(adSoyad)) {
				sayi++;
				System.out.println(kursiyer.getKursiyerId()+" " + kursiyer.getKursiyerAdSoyad() +" "+ kursiyer.getKursiyerYas());
				for (Kurs kurs : kursiyer.alinanKurslar) {
					System.out.println("\t"+ kurs.getKursId() +" " + kurs.getKursAd());
				}
			}
		}
		if(sayi == 0) System.out.println("Bulunamadý!");
	}
	
	
	
	void KursiyerSil(int id) {
		boolean varMi = false;
		for (int i= 0; i< kursiyerler.size(); i++) {
			if(kursiyerler.get(i).getKursiyerId() == id) {
				varMi = true;
				kursiyerler.remove(i);
				
			}
		}
		if(!varMi) System.out.println("Bulunamadý!");
	}
	
	
	void KursiyerListele() {
		System.out.println("Tüm Kursiyerler");
		for (Kursiyer kursiyer : kursiyerler) {
			System.out.println(kursiyer.getKursiyerId()+" " + kursiyer.getKursiyerAdSoyad() +" "+ kursiyer.getKursiyerYas());
		} 
	}
	
	
	void KursiyerAyrintiliListele() {
		System.out.println("Tüm Kursiyerler ve Aldýklarý Kurslar");
		for (Kursiyer kursiyer : kursiyerler) {
			System.out.println(kursiyer.getKursiyerId()+" " + kursiyer.getKursiyerAdSoyad() +" "+ kursiyer.getKursiyerYas());
			for (Kurs kurs : kursiyer.alinanKurslar) {
				System.out.println("\t"+ kurs.getKursId() +" " + kurs.getKursAd());
			}
		}
	}
	
	
	Kursiyer KursiyerArama(int id) {
		boolean varMi = false;
		for (Kursiyer kursiyer : kursiyerler) {
			if(kursiyer.getKursiyerId() == id) {
				varMi = true;
				return kursiyer;
			}
		}
		if(!varMi) System.out.println("Bulunamadý!");
		return null;
	}
	
	
	void KursiyerOdemeTutariHesaplama(int id) {
		Kursiyer kursiyer = KursiyerArama(id);
		if(kursiyer != null) {
			float tutar = 0;
			int sayi = kursiyer.alinanKurslar.size();
			switch (sayi) {
			case 1: //Sadece bir kurs alanlar
				tutar=400;//Aylik ucret hesaplandi
				break;
				
			case 2: // Bu kampanya 2 kurs alan kursiyerler içindir. Bu kursiyerlere ikinci kurs %15 indirimlidir. 
				tutar = 740; //1 aylýk ucret hesaplandý
				
				break;
			case 3: // Bu kampanya 3 kurs alan kursiyerler içindir. Bu kursiyerlere 3.kurs %25 indirimlidir
				tutar = 1100;
				break;
			
			default://  Bu kampanya 3 kurs üstü alan kursiyerler içindir. Bu kursiyerler ise her kurs %10 indirimlidir.
				for(int i=0;i<sayi;i++) {
					tutar += 4*((float)(100*0.9));
				}
				break;
				
			}
			System.out.println("Tutar :"+tutar); 
			
		}
	}
	
	
	void KursiyerYaz() throws IOException{
		File f = new File("kursiyer.txt");
		f.createNewFile();
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);
		for (Kursiyer kursiyer : kursiyerler) {
			bw.write("#");
			bw.write(Integer.toString(kursiyer.getKursiyerId()));
			bw.write("-");
			bw.write(kursiyer.getKursiyerAdSoyad());
			bw.write("-");
			bw.write(Integer.toString(kursiyer.getKursiyerYas()));
			bw.newLine();
			for (Kurs kurs : kursiyer.alinanKurslar) {
				bw.write("*");
				bw.write(Integer.toString(kurs.getKursId()));
				bw.write("-");
				bw.write(kurs.getKursAd());
				bw.newLine();
			}
		}
		bw.close();
	}
	
	void KursYaz() throws IOException{
		File f = new File("kurs.txt");
		f.createNewFile();
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);
		for (Kurs kurs : kurslar) {
			bw.write(Integer.toString(kurs.getKursId()));
			bw.write("-");
			bw.write(kurs.getKursAd());
			bw.newLine();
		}
		bw.close();
	}
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Anasayfa() throws Throwable
	{
		KursiyerOku();
		KursOku();
		boolean durum=true;
		do{
			ekranayaz(".::MENU::.", true);
			ekranayaz("1) Kurs Ekle", true);
			ekranayaz("2) Kurs Listele", true);
			ekranayaz("3) Kursiyer Ekle", true);
			ekranayaz("4) Kursiyer Arama(kursiyerAdSoyad)", true);
			ekranayaz("5) Kursiyer Sil(kursiyerId)", true);
			ekranayaz("6) Kursiyer Listele", true);
			ekranayaz("7) Kursiyer Ayrýntýlý Listele", true);
			ekranayaz("8) Kursiyerin Ödeyeceði Tutar Hesaplama(kursiyerId)", true);
			ekranayaz("9) Bitir", true);
			
			String secim=verigirisi("Seçim Yapýnýz:");
			
			switch (secim) {
			case "1":
				KursEkle();
		 		break;
		 		
			case "2":
				KursListele();
				break;
			
			case "3":	
				KursiyerEkle();
				break;
				
			case "4":
				KursiyerArama(verigirisi("Adý Soyadý:"));
				break;
			
			case "5":
				KursiyerSil(verigirisi_int("Id :"));
				break;
			
			case "6":
				KursiyerListele();
				break;
				
			case "7":
				KursiyerAyrintiliListele();
				break;
				
			case "8":
				KursiyerOdemeTutariHesaplama(verigirisi_int("Id :"));
				break;
			///Burada tüm bilgileri isledikten sonra enson bitir tusuna basýnca dosyaya yazma islemi gerceklestiriyor.
			case "9":
				KursiyerYaz();
				KursYaz();
				durum = false;
				System.exit(0);
				break;
			
			default:
				break;
			}
			
		}while(durum);
		
		
	}
	public static void main(String[] args) throws Throwable {
		new Anasayfa();
	}
	
	//Veri girisi alýyor.
	public String verigirisi(String metin)
	{
		Scanner sc = new Scanner(System.in);
		ekranayaz(metin,false);
		return sc.nextLine();
	}
	
	//int veri giriþi için tanýmlý metot
	public int verigirisi_int(String metin)
	{
		Scanner sc = new Scanner(System.in);
		
		int veri=0;
		
		boolean durum=true;
		do{
			
			try{
				ekranayaz(metin,false);
				veri = sc.nextInt();
				durum=false;
			}catch(Exception ex){
				ekranayaz("Girilen Bilgi Hatalý!", true);
			}
		}
		while(durum);
		return veri; 
	}
	
	
	
	public void ekranayaz(String metin, boolean tur)
	{
		if(tur)
			System.out.println(metin); 
		else
			System.out.print(metin); 
	}
}
