package gui;

//Cette classe attribue le numéro du joueur à chaque case dans laquelle la dernière modification a été effectuée
//Elle transforme aussi le core en un tableau à 2 dimensions

public class Proprio {

	public int[][] tableColoriee;

	public Proprio() {
		tableColoriee = new int[64][64];
	}

	public synchronized void coreToGrille(int[] grilleColoriee) {

		for (int i = 0; i < grilleColoriee.length; i++) {
			int t = i % 4096;
			tableColoriee[t / 64][t % 64] = grilleColoriee[i];
		}

	}

//Les variables lastAddress et lastPlayer ne sont pas encore initialisées.
//lastAddress correspond à la dernière adresse où une modification a été effectuée
//lastPlayer correspond au renier joueur a avoir joué (1 ou 2)
	public int[] proprio2(int lastAddress, int lastPlayer) {
		int[] grilleColoriee = new int[4096];
		grilleColoriee[lastAddress] = lastPlayer;
		return grilleColoriee;
	}

}
