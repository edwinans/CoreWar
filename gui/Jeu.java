package gui;

import java.awt.EventQueue;

import core.*;

// Cette classe permet de lancer le jeu

public class Jeu {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					new Thread(frame).start();
					frame.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
