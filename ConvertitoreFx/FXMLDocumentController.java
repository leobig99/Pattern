/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observer_convertitore;



import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;

/**
 @author andrea.zoccarato
 */
public class FXMLDocumentController implements Initializable {
	 
        private final Subject s;
        private final OctalObserver OctO;
        private final BinaryObserver BinO;
        private final HexaObserver HexO,
        
	@FXML
	private Label Hexa;
        
	@FXML
	private Label Binary;
        
	@FXML
	private Label Octal;
	
	@FXML
	private TextField text;
	
	@FXML
	private void TextWrite(ActionEvent event) {
            // TODO
	}
	
	@FXML
	protected void TextUpdate(ActionEvent event) {
            String str= text.getText();
            int n=Integer.parseInt(str);
            System.out.println(n);                       
            
            HexO.setObservable(s);
            OctO.setObservable(s);
            BinO.setObservable(s);
            
            s.addObserver(HexO);
            s.addObserver(OctO);
            s.addObserver(BinO);
            s.setState(n);
            
            Hexa.setTextAlignment(TextAlignment.CENTER);
            Octal.setTextAlignment(TextAlignment.CENTER);
            Binary.setTextAlignment(TextAlignment.CENTER);
            
            Hexa.setText(Integer.toHexString(s.getState()).toUpperCase());
            Octal.setText(Integer.toOctalString(s.getState()).toUpperCase());
            Binary.setText(Integer.toBinaryString(s.getState()).toUpperCase());
            
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		s = new Subject();
                OctO=new OctalObserver(s);
                BinO=new BinaryObserver(s);
                HexO=new HexaObserver(s);
	}	
	
}