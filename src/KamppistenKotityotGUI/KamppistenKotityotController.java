package KamppistenKotityotGUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.PrintStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import kamppistenKotityot.*;

import fi.jyu.mit.fxgui.*;


/**
 * Controller tiedosto KamppistenKotityotGUI ohjelmalle.
 * @author Tomi Nyyss�nen & Juho Kajanto
 *   juho_kajanto@hotmail.com
 *   toalnyys@student.jyu.fi
 * @version 4.2.2016
 *
 * TODO: Soitetaan musiikkia, kun kotity� merkit��n tehdyksi
 * Ohjelmassa oleva kuva puuttuu jar-tiedostosta, emmek� tied� kuinka sen saa n�kym��n siin�.
 */
public class KamppistenKotityotController implements Initializable {
	
	private KamppistenKotityot kamppistenKotityot;
	private ObservableList<Asukas> listdataAsukkaat = FXCollections.observableArrayList();
	private ObservableList<Kotityo> listdataKotityot = FXCollections.observableArrayList();
	private ObservableList<Asukas> listdataSeuraavana = FXCollections.observableArrayList();
    private ObservableList<StringBuilder> listdataEdelliset = FXCollections.observableArrayList();
    private ListView<Asukas> listAsukkaat = new ListView<Asukas>();
    private ListView<Kotityo> listKotityot = new ListView<Kotityo>();
    private ListView<Asukas> listSeuraavana = new ListView<Asukas>();
    private ListView<StringBuilder> listEdelliset = new ListView<StringBuilder>();
	private Asukas asukasKohdalla;
	private Kotityo kotityoKohdalla;
	
	@FXML private ListChooser chooserAsukkaat;
	@FXML private ListChooser chooserKotityot;
    @FXML private ListChooser chooserSeuraavana;
    @FXML private ListChooser chooserEdelliset;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		alusta();
	}
	
	/**
     * Luokka, jolla hoidellaan miten asukas n�ytet��n listassa 
     */
    public static class CellAsukas extends ListCell<Asukas> {
        @Override protected void updateItem(Asukas item, boolean empty) {
            super.updateItem(item, empty); // ilman t�t� ei n�y valinta
            setText(empty ? "" : item.getNimi());
        }
    }
    
    
    /**
     * Luokka, jolla hoidellaan miten kotity� n�ytet��n listassa 
     */
    public static class CellKotityo extends ListCell<Kotityo> {
        @Override protected void updateItem(Kotityo item, boolean empty) {
            super.updateItem(item, empty); // ilman t�t� ei n�y valinta
            setText(empty ? "" : item.getNimi());
        }
    }    
    
    @FXML private void handleLisaaAsukas() {
        lisaaAsukas();
        try {
			kamppistenKotityot.talleta();
		} catch (SailoException e) {
			Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + e.getMessage());
		}
    }

    @FXML private void handleLisaaKotityo() {
        lisaaKotityo();
        try {
			kamppistenKotityot.talleta();
		} catch (SailoException e) {
			Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + e.getMessage());
		}
    }

    @FXML private void handleMerkitseTehdyksi() {
        merkitseTehdyksi();
        try {
			kamppistenKotityot.talleta();
		} catch (SailoException e) {
			Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + e.getMessage());
		}
    }

    @FXML private void handleMerkitseTekijaksi() {
        merkitseTekijaksi();
        try {
			kamppistenKotityot.talleta();
		} catch (SailoException e) {
			Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + e.getMessage());
		}
    }

    @FXML private void handlePoistaAsukas() throws SailoException {
        poistaAsukas();
        try {
			kamppistenKotityot.talleta();
		} catch (SailoException e) {
			Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + e.getMessage());
		}
    }

    @FXML private void handlePoistaKotityo() {
        poistaKotityo();
        try {
			kamppistenKotityot.talleta();
		} catch (SailoException e) {
			Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + e.getMessage());
		}
    }
    
    @FXML private void handleNaytaAsukkaanKotityot() {
        NaytaAsukkaanKotityotController nAKCtrl = NaytaAsukkaanKotityotController.tulosta(null);
        tulostaValittu(nAKCtrl.getTextArea());
    }
    
    
    /**
     * Alustetaan p��ikkuna
     */
    protected void alusta() {
        BorderPane parentAs = (BorderPane)chooserAsukkaat.getParent();
        parentAs.setCenter(listAsukkaat);
        GridPane parentKtGrid = (GridPane)chooserKotityot.getParent();
        parentKtGrid.add(listKotityot,0,1);
        parentKtGrid.add(listSeuraavana,1,1);
        parentKtGrid.add(listEdelliset,2,1);
        listAsukkaat.setCellFactory( p -> new ListCell<Asukas>() {
            @Override protected void updateItem(Asukas item, boolean empty) {
                super.updateItem(item, empty); 
                setText(empty ? "" : item.getNimi());
            }
        });
        listKotityot.setCellFactory( q -> new ListCell<Kotityo>() {
            @Override protected void updateItem(Kotityo item, boolean empty) {
                super.updateItem(item, empty); 
                setText(empty ? "" : item.getNimi());
            }
        });
        listSeuraavana.setCellFactory( p -> new ListCell<Asukas>() {
            @Override protected void updateItem(Asukas item, boolean empty) {
                super.updateItem(item, empty); 
                setText(empty ? "" : item.getNimi());
            }
        });
        listEdelliset.setCellFactory( p -> new ListCell<StringBuilder>() {
            @Override protected void updateItem(StringBuilder item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.toString());
            }
        });
    }

    
    /**
     * Asetetaan kotity�
     * @param kampKot k�ytett�v� k�mppisten kotity�
     */
    public void setKamppistenKotityot(KamppistenKotityot kampKot) {
    	this.kamppistenKotityot = kampKot;
    }
    
    
    /**
     * Luetaan asukkaiden ja kotit�iden tiedot tiedostosta
     * @param nimi tiedosto josta tiedot luetaan
     * @return jos onnistuu, muuten tulostetaan virhe.
     */
    protected String lueTiedosto(String nimi) {
    	try {
    		kamppistenKotityot.lueTiedosto(nimi);
    		haeAsukas(0);
    		haeKotityo(0);
    		haeSeuraavanaTiedostosta();
    		haeEdelliset();
    		return null;
    	} catch (SailoException e) {
    		String virhe = e.getMessage();
    		if (virhe != null) Dialogs.showMessageDialog(virhe);
    		return virhe;
    	}
    }
    
    
    /**
     * Avaa ikkunan asukkaan lis��miseksi.
     */
    protected void lisaaAsukas() {
        String lisaaAsukas = Dialogs.showInputDialog("Lis�tt�v�n asukkaan nimi", "");
        if (lisaaAsukas == null) return;
        if (lisaaAsukas.contains("|")) {
            Dialogs.showMessageDialog("Virheellinen sy�te! Nimi ei saa sis�lt�� merkki� '|'");
            return;
        }
        Asukas uusi = new Asukas();
        uusi.lisaaId();
        uusi.setNimi(lisaaAsukas);
        try {
        	kamppistenKotityot.lisaaAsukas(uusi);
        }
        catch (SailoException e) {
        	Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
        	return;
        }
        haeAsukas(uusi.getId());
    }
    
    
    /**
     * Avaa ikkunan kotity�n lis��miseksi.
     */
    protected void lisaaKotityo() {
        String lisaaKotityo = Dialogs.showInputDialog("Lis�tt�v� kotityo", "");
        if (lisaaKotityo == null) return;
        if (lisaaKotityo.contains("|")) {
            Dialogs.showMessageDialog("Virheellinen sy�te! Nimi ei saa sis�lt�� merkki� '|'");
            return;
        }
        Kotityo kt = new Kotityo(lisaaKotityo);
        kamppistenKotityot.lisaaKotityo(kt);
        haeKotityo(kt.getId());
    }
    
    
    /**
     * Siirt�� valitun ty�n edellisiin kertoihin ja siirt�� edellisill� kerroilla 
     * tehtyj� t�it� yhden taaksep�in.
     */
    private void merkitseTehdyksi() {
    	kotityoKohdalla = listKotityot.getSelectionModel().getSelectedItem();
    	if (kotityoKohdalla == null || kotityoKohdalla.getSeuraavanaVuorossa() == -1) return;
    	kamppistenKotityot.asetaTehdyksi(kotityoKohdalla);
    	haeSeuraavanaTiedostosta();
    	haeEdelliset();
    }

    
    /**
     * Merkitsee valitun asukkaan valitun kotity�n tekij�ksi.
     */
    private void merkitseTekijaksi() {
        asukasKohdalla = listAsukkaat.getSelectionModel().getSelectedItem();
        kotityoKohdalla = listKotityot.getSelectionModel().getSelectedItem();
        if (asukasKohdalla == null || kotityoKohdalla == null) return;
        kamppistenKotityot.asetaTekija(asukasKohdalla, kotityoKohdalla);
        haeSeuraavana(asukasKohdalla.getId(), kotityoKohdalla.getId());
    }
    
    
    /**
     * Avaa ikkunan valitun asukkaan poiston varmistamiseksi. 
     * @throws SailoException 
     */
    private void poistaAsukas() throws SailoException {
    	asukasKohdalla = listAsukkaat.getSelectionModel().getSelectedItem();
    	if (asukasKohdalla == null) return;
    	boolean vastaus = Dialogs.showQuestionDialog("Poiston varmennus",
                "Haluatko varmasti poistaa asukkaan " + asukasKohdalla.getNimi(), "Kyll�", "Peruuta");
        if (vastaus) 
        	kamppistenKotityot.poistaAsukas(asukasKohdalla);
        List<Kotityo> kotityot = kamppistenKotityot.annaKotityot(); 
        for (int i = 0; i<kotityot.size(); i++) {
        	if (kotityot.get(i).getSeuraavanaVuorossa() == asukasKohdalla.getId()) kotityot.get(i).setSeuraavanaVuorossa(-1);
        	if (kotityot.get(i).getEdellinenKerta() == asukasKohdalla.getId()) kotityot.get(i).setEdellinenKerta(-1);
        	if (kotityot.get(i).getSitaEdeltava() == asukasKohdalla.getId()) kotityot.get(i).setSitaEdeltava(-1);
        	if (kotityot.get(i).getToistaEdeltava() == asukasKohdalla.getId()) kotityot.get(i).setToistaEdeltava(-1);
        }
        haeAsukas(0);
    	haeSeuraavanaTiedostosta();
    	haeEdelliset();
    }
    
    
    /**
     * Avaa ikkunan valitun kotity�n poiston varmistamiseksi.
     */
    private void poistaKotityo() {
    	kotityoKohdalla = listKotityot.getSelectionModel().getSelectedItem();
    	if (kotityoKohdalla == null) return;
    	boolean vastaus = Dialogs.showQuestionDialog("Poiston varmennus",
                "Haluatko varmasti poistaa kotity�n " + kotityoKohdalla.getNimi(), "Kyll�", "Peruuta");
        if (vastaus)
        	kamppistenKotityot.poistaKotityo(kotityoKohdalla);
        haeKotityo(0);
        haeSeuraavanaTiedostosta();
        haeEdelliset();
    }
    
    /**
     * Hakee asukkaan tiedot listaan
     * @param id asukkaan viitenumero
     */
    protected void haeAsukas(int id) {
    	listdataAsukkaat.clear();
    	listAsukkaat.setItems(listdataAsukkaat);
    	
    	int index = 0;
    	for (int i = 0; i < kamppistenKotityot.getAsukkaita(); i++) {
    		Asukas asukas = kamppistenKotityot.annaAsukas(i);
    		if (asukas.getId() == id) index = i;
    		listdataAsukkaat.add(asukas);
    	}
    	listAsukkaat.getSelectionModel().select(index);
    }
    
    
    /**
     * Hakee seuraavana vuorossa olevan asukkaan tiedot listaan seuraavanaVuorossa
     * @param idAsukas asukkaan viitenumero
     * @param idKotityo kotityon viitenumero
     */
    protected void haeSeuraavana(int idAsukas, int idKotityo) {
    	listSeuraavana.setItems(listdataSeuraavana);
        
        for (int i = 0; i<kamppistenKotityot.getKotitoita(); i++) {
            if (kamppistenKotityot.getKotitoita() > listdataSeuraavana.size()) {
                Asukas apu = new Asukas();
                listdataSeuraavana.add(apu);
            }
            if (kamppistenKotityot.moneskoKotityo(idKotityo) == i) {
            	try {
					listdataSeuraavana.set(i, kamppistenKotityot.annaAsukas(
							kamppistenKotityot.etsiAsukkaanPaikka(idAsukas)));
				} catch (IndexOutOfBoundsException e) {
					Dialogs.showMessageDialog(e.getMessage());
				} catch (SailoException e) {
					Dialogs.showMessageDialog("Asukasta ei l�ydy! " + e.getMessage());
				}
            }
        }        
    }    
    
    
    /**
     * Hakee kotity�n listaan
     * @param id kotity�n id
     */
    protected void haeKotityo(int id) {
        listdataKotityot.clear();
        listKotityot.setItems(listdataKotityot);
        
        int index = 0;
        for ( int i = 0; i < kamppistenKotityot.getKotitoita(); i++) {
            Kotityo kt = kamppistenKotityot.annaKotityot().get(i);
            if (kt.getId() == id) index = i;
            listdataKotityot.add(kt);
        }
        listKotityot.getSelectionModel().select(index);
    }
    
    
    /**
     * Hakee edelliset tekij�t listaan
     */
    protected void haeEdelliset() {
    	listdataEdelliset.clear();
    	listEdelliset.setItems(listdataEdelliset);
    
    	for (int i = 0; i<kamppistenKotityot.getKotitoita(); i++) {
    		Kotityo kt = kamppistenKotityot.annaKotityot().get(i);
    		StringBuilder nimet;
    		String nimi1 = "";
    		String nimi2 = "";
    		String nimi3 = "";
			try {
				nimi1 = kamppistenKotityot.annaAsukas(
	    				kamppistenKotityot.etsiAsukkaanPaikka(
						kt.getEdellinenKerta())).getNimi();
			} catch (IndexOutOfBoundsException e) {
				Dialogs.showMessageDialog(e.getMessage());
			} catch (SailoException e) {
				nimi1 = "";
			} 
			
			try {
				nimi2 = kamppistenKotityot.annaAsukas(
	    				kamppistenKotityot.etsiAsukkaanPaikka(
						kt.getSitaEdeltava())).getNimi();
			} catch (IndexOutOfBoundsException e) {
				Dialogs.showMessageDialog(e.getMessage());
			} catch (SailoException e) {
				nimi2 = "";
			}
			
			try {
				nimi3 = kamppistenKotityot.annaAsukas(
	    				kamppistenKotityot.etsiAsukkaanPaikka(
						kt.getToistaEdeltava())).getNimi();
			} catch (IndexOutOfBoundsException e) {
				Dialogs.showMessageDialog(e.getMessage());
			} catch (SailoException e) {
				nimi3 = "";
			}
			nimet = new StringBuilder(nimi1 + "    " + nimi2 + "    " + nimi3);
			listdataEdelliset.add(nimet);
		}
    }
    
    
    /**
     * Hakee seuraavat tekij�t listaan ohjelmaa k�ynnistett�ess�
     */
    protected void haeSeuraavanaTiedostosta() {
    	listdataSeuraavana.clear();
    	int x, ktId;
    	if (kamppistenKotityot.getKotitoita() < 1) return; 
    	ktId = kamppistenKotityot.kotityonId(0);
    	for(int i = ktId; i < ktId + kamppistenKotityot.getKotitoita(); i++) {
    		try {
				x = kamppistenKotityot.annaKotityo(i).getSeuraavanaVuorossa();
				if ( x > 0 )
	    			haeSeuraavana(x,i);
					
			} catch (SailoException e) {
				Dialogs.showMessageDialog("Kotity�t� ei l�ydy! " + e.getMessage());
			}
    	}
    }
    
    
    /**
     * Tulostaa asukkaalle m��ritetyt kotity�t tekstialueeseen
     * @param text alue johon tulostetaan
     */
    public void tulostaValittu(TextArea text) {
        boolean toita = false;
    	try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            asukasKohdalla = listAsukkaat.getSelectionModel().getSelectedItem();
            os.println("Asukkaan " + asukasKohdalla.getNimi() + " kotity�t ");
            os.println("-------------------------------------------------");
            for (Kotityo kt:listdataKotityot) {
                if (kt.getSeuraavanaVuorossa() == asukasKohdalla.getId()) {
                    os.println(kt.getNimi());
                    toita = true;
                }
            }
            if (!toita)
            	os.println("Menisit t�ihin!");
        }
    }
}			