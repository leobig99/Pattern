/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import java.util.Observable;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author leonardo.bigetti
 */
public class Email extends Observable implements Runnable {
	private boolean inviata;

	public Email() {
		this.inviata = false;
	}

	private void setEmail() {
		this.inviata = true;
	}

	private void clearEmail() {
		this.inviata = false;
	}

	public void change() {
		this.setChanged();
		this.notifyObservers();
	}

	public void clearChange() {
		this.clearChanged();
	}
	
	public boolean getEmail(){
		return this.inviata;
	}

	@Override
	public void run() {
		while (true) {
			try {
				//random da settare
				this.clearChange();
				TimeUnit.SECONDS.sleep(1);
				this.setEmail();
				this.change();
				TimeUnit.SECONDS.sleep(3);
				this.clearEmail();
			} catch (InterruptedException e) {
				//non entrerà mai
			}
		}

	}
}
