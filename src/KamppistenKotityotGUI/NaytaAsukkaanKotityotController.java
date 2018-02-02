package KamppistenKotityotGUI;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Tulostusikkunan k�sittely.
 * @author Tomi Nyyss�nen & Juho Kajanto
 *   juho_kajanto@hotmail.com
 *   toalnyys@student.jyu.fi
 * @version 19.4.2016
 *
 */
public class NaytaAsukkaanKotityotController implements ModalControllerInterface<String> {
    @FXML
    private TextArea tekstialue;

    @FXML
    void handleOK() {
        ModalController.closeStage(tekstialue);
    }
    
    
    /**
     * @return alue johon tulostetaan
     */
    public TextArea getTextArea() {
        return tekstialue;
    }
    
    
    /**
     * N�ytt�� tulostusalueessa tekstin
     * @param tulostus tulostettava teskti
     * @return kontrolleri, jolta voidaan pyyt�� lis�� tietoa
     */
    public static NaytaAsukkaanKotityotController tulosta(String tulostus) {
        NaytaAsukkaanKotityotController nAKCtrl = 
        (NaytaAsukkaanKotityotController) ModalController.showModeless(
                NaytaAsukkaanKotityotController.class.getResource("KamppistenKotityotViewNaytaAsukkaanKotityot.fxml"),
                "Asukkaan kotity�t", tulostus);
        return nAKCtrl;
    }


    @Override
    public String getResult() {
        return null;
    }


    /**
     * Mit� tehd��n kun dialogi on n�ytetty
     */
    @Override
    public void handleShown() {
        //
    }


    @Override
    public void setDefault(String oletus) {
        tekstialue.setText(oletus);
    }
}
