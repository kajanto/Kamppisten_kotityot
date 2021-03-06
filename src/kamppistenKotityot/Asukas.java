package kamppistenKotityot;

import java.io.*;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Asukas, joka vastaa omasta nimest��n ja viitenumerostaan 
 * @author Juho Kajanto & Tomi Nyyss�nen
 *   juho_kajanto@hotmail.com
 *   toalnyys@student.jyu.fi
 * @version 28.3.2016
 */
public class Asukas {
	private int id;
	private String nimi = "";
	
	private static int seuraavaId = 1;
	
	/**
	 * Apumetodi testiohjelman ajamiseksi
	 */
	public void vastaaTupu() {
		nimi = "Tupu";
	}
	
	/**
	 * Palautetaan asukkaan nimi
	 * @return asukkaan nimi
	 */
	public String getNimi() {
		return nimi;
	}
	
	/**
	 * Asetetaan asukkaalle nimi
	 * @param nimi asukkaan nimi
	 */
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}
	
	/**
	 * Palautetaan asukkaan viitenumero
	 * @return asukkaan viitenumero
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Annetaan asukkaalle seuraava viitenumero
	 * @return uusi viitenumero
	 * @example
	 * <pre name="test">
	 * Asukas as1 = new Asukas();
	 * as1.getId() === 0;
	 * as1.lisaaId();
	 * Asukas as2 = new Asukas();
	 * as2.lisaaId();
	 * int a1 = as1.getId();
	 * int a2 = as2.getId();
	 * a1 === a2-1;
	 * </pre>
	 */
	public int lisaaId() {
		id = seuraavaId;
		seuraavaId++;
		return id;
	}
	
	/**
     * Asettaa tunnusnumeron ja samalla varmistaa ett�
     * seuraava numero on aina suurempi kuin t�h�n menness� suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setId(int nr) {
        id = nr;
        if (id >= seuraavaId) seuraavaId = id + 1;
    }
	
	/**
	 * Tulostetaan asukkaan tiedot
	 * @param out tietovirta johon tulostetaan
	 */
	public void tulosta(PrintStream out) {
		out.println(id + "|" + nimi);
	}
	
	/**
	 * Tulostetaan asukkaan tiedot
	 * @param os tietovirta johon tulostetaan
	 */
	public void tulosta(OutputStream os) {
		tulosta(new PrintStream(os));
	}
	
	/**
	 * Palauttaa asukkaan tiedot merkkijonona, joka voidaan
	 * tannentaa tiedostoon
	 * @return asukkaan tiedot tolppaerotuksella
	 * @example
	 * <pre name="test">
	 * Asukas asukas = new Asukas();
	 * asukas.parse("  2  |   Tupu  ");
	 * asukas.toString().equals("2|Tupu");
	 * </pre>
	 */
	@Override
	public String toString() {
		return getId() + "|" + nimi;
	}
	
	/**
	 * Selvitt�� asukkaan tiedot tolppaerotellusta merkkijonosta
	 * @param rivi josta asukkaan tiedot otetaan
	 * @example
	 * <pre name="test">
	 * Asukas asukas = new Asukas();
	 * asukas.parse("  2  |   Tupu  ");
	 * asukas.toString().equals("2|Tupu");
	 * asukas.lisaaId();
	 * int n = asukas.getId();
	 * asukas.parse(""+(n+20));
	 * asukas.lisaaId();
	 * asukas.getId() === n+20+1;
	 * </pre>
	 */
	public void parse(String rivi) {
		StringBuffer sb = new StringBuffer(rivi);
		setId(Mjonot.erota(sb, '|', getId()));
		nimi = Mjonot.erota(sb, '|', nimi);
	}
	
	/**
	 * Testiohjelma Asukkaalle
	 * @param args ei k�yt�ss�
	 */
	public static void main(String[] args) {
		
	    Asukas as = new Asukas();
		as.lisaaId();
		as.vastaaTupu();
		as.tulosta(System.out);
	}

}
