package com.company;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 *
 * @author Leonardo
 */
public class ObserverPatternDemo2 {
 public static void main(String[] args) {
        Random gen = new Random();
        
        //creo il soggetto da osservare
        Subject s = new Subject();
        //creo i tre osservatori
        HexaObserver HexO = new HexaObserver(s);
        OctalObserver OctO = new OctalObserver(s);
        BinaryObserver BinO = new BinaryObserver(s);

        //aggiungo i tre osservatori utilizzando un metodo della classe Observable
        s.addObserver(HexO);
        s.addObserver(OctO);
        s.addObserver(BinO);
        System.out.println("10");
        s.setState(10);
        System.out.println();
        System.out.println("15");
        s.setState(15);
        System.out.println();
        int app=gen.nextInt(100)+1;
        System.out.println(""+app);
        s.setState(app);

    }
}

//quetsa classe estende la classe astratta Observer e viene utilizzata come soggetto da osservare
class Subject extends Observable {

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        if (state != this.state) {
            setChanged();
            this.state = state;
            notifyObservers();
        }
    }
}

//questa classe che implementa Observer viene utilizzata per: osservare i cambiamenti della classe soggetto e convertire il 
//valore ottenuto da intero a esadecimale
class HexaObserver implements Observer {

    Subject s;

    public HexaObserver(Subject s) {
        this.s = s;
    }

    @Override
    public void update(Observable observable, Object o) {
        System.out.println("Hex String: " + Integer.toHexString(s.getState()).toUpperCase());
    }
}

//questa classe che implementa Observer viene utilizzata per: osservare i cambiamenti della classe soggetto e convertire il 
//valore ottenuto da intero a ottale
class OctalObserver implements Observer {

    Subject s;

    public OctalObserver(Subject s) {
        this.s = s;
    }

    @Override
    public void update(Observable observable, Object o) {
        System.out.println("Octal String: " + Integer.toOctalString(s.getState()));
    }
}

//questa classe che implementa Observer viene utilizzata per: osservare i cambiamenti della classe soggetto e convertire il 
//valore ottenuto da intero a binario
class BinaryObserver implements Observer {

    Subject s;

    public BinaryObserver(Subject s) {
        this.s = s;
    }

    @Override
    public void update(Observable observable, Object o) {
        System.out.println("Binary String: " + Integer.toBinaryString(s.getState()));
    }
}
