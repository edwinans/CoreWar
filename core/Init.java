package core;



// Cette classe correspond à l'initialisation de chaque guerrier dans le core

public class Init {

	public  int initJ1;//initialisation dans la grille du J1
	public  int initJ2=randInt(0,4095); //initialisation dans la grille du J2
  
public void init() {
		initJ1= randInt(0,4095); 
		initJ2=randInt(0,4095);
		ecartJoueurs();
	}

public void ecartJoueurs() {

boolean test = false;

while(!test) {
	if(initJ1 - initJ2 <= 192 || initJ2 - initJ1 <= 192) {
		initJ1 = randInt(0,4095);
		initJ2 = randInt(0,4095);
	}
	else return;

}

}

public static int randInt(int min, int max) {
return min + (int)(Math.random() * ((max - min) + 1));
}
}