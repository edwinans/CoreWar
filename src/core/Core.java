package core;

import gui.UI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Core implements Runnable {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	public int[] core; // tableau du core
	public int[] colorGrid;
	public boolean suspended = false;
	public int cyclesDone = 0;
	private int lastAddress = 0;
	private int lastPlayer = 0;
	private int maxCycles = 8000;
	public long memJ1 = 0, memJ2 = 0;
	public int cyclesToDie;
	private int placementJ1; // placement dans la grille du J1 dans le Core
	private int placementJ2;// placement dans la grille du J2 dans le Core
	private int delay;

	Instruction instruction;
	public Thread startThread;
	public ArrayList<String> insJ1 = new ArrayList<>();
	public ArrayList<String> insJ2 = new ArrayList<>();

	public Core() {
		core = new int[4096];
		colorGrid = new int[4096];
		initPlacement();

	}

	public void initPlacement() {
		while (Math.abs(placementJ1 - placementJ2) <= 200) {
			placementJ1 = (int) (Math.random() * 4096);
			placementJ2 = (int) (Math.random() * 4096);
		}
	}

	public void chargerJoueur(File f, int index) throws FileNotFoundException, CoreException {

		Scanner in = new Scanner(f);
		while (in.hasNextLine()) {
			Instruction ins = new Instruction(in.nextLine());
			colorGrid[index] = 3;
			core[index++] = ins.toInt();
			index %= 4096;
		}
		in.close();
	}

	// Cette methode permet de regler le delay (JSlider)
	private void setDelay(int vitesseSlider) {
		if (UI.vitesseSlider == 0) {
			delay = 1000;
		}
		if (UI.vitesseSlider == 50) {
			delay = 475;
		}
		if (UI.vitesseSlider == 100) {
			delay = 50;
		}
	}

//Thread executé une fois que le bouton "lancer" est appuyé
	@SuppressWarnings("deprecation")
	public void run() {
		try {

//			chargerJoueur(UI.file1, placementJ1);
//			chargerJoueur(UI.file2, placementJ2);

			ArrayList<Integer> indexJ1 = new ArrayList<Integer>();
			ArrayList<Integer> indexJ2 = new ArrayList<Integer>();
			indexJ1.add(placementJ1);
			indexJ2.add(placementJ2);

			for (int i = 0; i < maxCycles; i++) {

				setDelay(UI.vitesseSlider);
				for (int ind : indexJ1)
					colorGrid[ind] = 1;
				for (int ind : indexJ2)
					colorGrid[ind] = 2;

				updateMem();

				indexJ1 = Interpreter.execute(core, placementJ1);
				placementJ1 = indexJ1.get(0);
				instruction = new Instruction(core[placementJ1]);
				insJ1.add(instruction.toString());

				indexJ2 = Interpreter.execute(core, placementJ2);
				placementJ2 = indexJ2.get(0);
				instruction = new Instruction(core[placementJ2]);
				insJ2.add(instruction.toString());

				cyclesDone++;
				cyclesToDie = maxCycles - cyclesDone;

				Thread.sleep(delay);

				synchronized (this) {
					while (suspended) {
						wait();
					}
				}

			}
			System.out.println("match nul , egalite entre les joueurs");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void updateMem() {
		memJ1 = 0;
		memJ2 = 0;
		for (int i = 0; i < 4096; i++) {
			if (colorGrid[i] == 1)
				memJ1 += 8;
			else if (colorGrid[i] == 2)
				memJ2 += 8;
		}
	}

	public void suspend() {
		suspended = true;
	}

	public synchronized void resume() {
		suspended = false;
		notify();
	}

	public int getCyclesDone() {
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