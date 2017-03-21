package com.company;

import java.util.Observable;
import java.util.Observer;

public class ObserverPatternDemo {

    public static void main(String[] args) {

        Subject s = new Subject();
        HexaObserver HexO=new HexaObserver(s);
        OctalObserver OctO = new OctalObserver(s);
        BinaryObserver BinO=new BinaryObserver(s);

        s.addObserver(HexO);
        s.addObserver(OctO);
        s.addObserver(BinO);
        System.out.println("10");
        s.setState(10);
        System.out.println();
        System.out.println("15");
        s.setState(15);


    }
}

class Subject extends Observable{
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        if(state!=this.state){
            setChanged();
            this.state = state;
            notifyObservers();
        }
    }
}


class HexaObserver implements Observer{
    Subject s;

    public HexaObserver(Subject s) {
        this.s = s;
    }

    @Override
    public void update(Observable observable, Object o) {
         System.out.println("Hex String: " + Integer.toHexString(s.getState()).toUpperCase());
    }
}

class OctalObserver implements Observer{
    Subject s;

    public OctalObserver(Subject s) {
        this.s = s;
    }

    @Override
    public void update(Observable observable, Object o) {
        System.out.println("Octal String: " + Integer.toOctalString(s.getState()));
    }
}

class BinaryObserver implements Observer{
    Subject s;

    public BinaryObserver(Subject s) {
        this.s = s;
    }

    @Override
    public void update(Observable observable, Object o) {
        System.out.println("Binary String: " + Integer.toBinaryString(s.getState()));
    }
}
