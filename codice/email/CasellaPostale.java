/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author leonardo.bigetti
 */
public class CasellaPostale implements Observer {
	public ArrayList<Email> a;
	public Thread t;
	public Email m;

	public CasellaPostale() {
		this.a=new ArrayList<>();
		this.m=new Email();
		m.addObserver(this);
		t=new Thread(m);
		t.start();
	}
	
	public Email getEmail(int pos){
		if(pos<=a.size()){
			return a.get(pos);
		}
			throw new NullPointerException("email inesistente");
	}
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println(m.getEmail());
	}
	

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		CasellaPostale c =new CasellaPostale();
	}

	
	
}
