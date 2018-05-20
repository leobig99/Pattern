package it.csal.entity;
import it.csal.entity.DBEntity;
import java.util.ArrayList;

/**
 *
 * @author Carlo
 */
public class Genere implements DBEntity {
    private int codice;
    private String descrizione;

    public Genere(int codice,  String descrizione) {
        this.codice = codice;
        this.descrizione = descrizione;
    }

    public Genere() {
    }  
    
    @Override
    public boolean setByDB(ArrayList a) {
        try {
            codice = (Integer) a.get(0);
            descrizione = (String) a.get(1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public void setDesrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getCodice() {
        return codice;
    }

    
}
