package core;
import gui.Proprio;
import gui.UI;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class Core implements Runnable{

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	public static int [] core = new int [4096]; //tableau du core
	private static int cyclesDone = 0;
	private int lastAddress = 0;
	private int lastPlayer = 0;
	private int maxCycles = 4096;
	private int placementJ1 ; //placement dans la grille du J1 dans le Core 
	private int placementJ2 ;//placement dans la grille du J2 dans le Core
	private static String instructionJoueur; //dernière instruction scannée - requis pour la classe AffichageData
	private int delay;
	Init init = new Init();
	Proprio proprio = new Proprio();
	public Thread startThread;
public Core(){
	placementJ1=init.initJ1;
	placementJ2=init.initJ2;
	//Scan(UI.file1);
	
	}

public void initJoueurs() {
	for(int i = 0; i<core.length; i++) {
		core[placementJ1] = 1;
		core[placementJ2] = 2;
	}
}

//Cette methode permet de lire un fichier texte
private static String Scan(File f) throws FileNotFoundException{
	@SuppressWarnings("resource")
	Scanner sc = new Scanner(f);
	String ligne1 = sc.nextLine();
	instructionJoueur = ligne1;
	return ligne1;
}

  
// Cette methode permet de regler le delay (JSlider)
private void delay(int vitesseSlider) {
	if(UI.vitesseSlider == 0) {
		delay = 1000;
	}
	if(UI.vitesseSlider == 50) {
			delay = 475;
	}
	if(UI.vitesseSlider == 100) {
		delay = 50;
}
}


//Thread executé une fois que le bouton "lancer" est appuyé
public void run() {
	for(int i=0; i<maxCycles; i++) {
		delay(UI.vitesseSlider);
		core[placementJ1 + 1] = 1; 
		core[placementJ2 + 1] = 2;
		//proprio.coreToGrille(core);
		placementJ1 = placementJ1+1;
		placementJ2 = placementJ2+1;
		//proprio.afficherC(core);

		
      try {
         // delay de x millisecondes
         Thread.sleep(delay);
      } catch (Exception e) {
         System.out.println(e.getMessage());
      }
	}
}



public int getCyclesDone()
{
  return cyclesDone;
}

public int getLastAddress() {
	return lastAddress;
}

public int getLastPlayer() {
	return lastPlayer;
}

public int getPlacementJ1() {
	return placementJ1;
}

public int getPlacementJ2() {
	return placementJ2;
}

public int getMaxCycles() {
	return maxCycles;
}
}

