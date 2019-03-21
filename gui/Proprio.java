package gui;

import core.*;

//Cette classe attribue le numéro du joueur à chaque case dans laquelle la dernière modification a été effectuée
//Elle transforme aussi le core en un tableau à 2 dimensions

public class Proprio {

	public int [][] tableColoriee = new int [64][64];
	
	
	
public Proprio(){

}
	
public synchronized void coreToGrille(int [] grilleColoriee) {
	
	for(int i=0; i<grilleColoriee.length; i++) {
		int t = i%4096;
		tableColoriee[t/64][t%64] = grilleColoriee[i];
	}

}
	
private void initTable() {
	for(int i=0; i<tableColoriee[i].length; i++) {
		for(int j=0; j<tableColoriee[j].length; j++) {
			tableColoriee[i][j] = 0;
		}
	}
}

public void afficherGrilleC(int [][] table) {
	
    for(int i = 0 ; i < table.length; i++ ){
        for(int j = 0; j<table[i].length; j++){
   
          System.out.print(" "+table[i][j]);
        }
       System.out.println();
    }
}

//Pour debug
public void afficherC(int[] table) {
	
    for(int i = 0 ; i < table.length; i++ ){
        System.out.print(" "+table[i]);
    }
    System.out.println();
}

//Les variables lastAddress et lastPlayer ne sont pas encore initialisées.
//lastAddress correspond à la dernière adresse où une modification a été effectuée
//lastPlayer correspond au renier joueur a avoir joué (1 ou 2)
public int [] proprio2(int lastAddress, int lastPlayer){
	int [] grilleColoriee = new int [4096];
	grilleColoriee[lastAddress] = lastPlayer;
	return grilleColoriee;
}
	
}
