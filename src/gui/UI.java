package gui;

import core.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// Cette classe représente l'UI générale. elle implemente les boutons et leurs
// actions listener (charger joueur, lancer la partie et les 3 vitesses)
public class UI extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static File file1, file2;
	private boolean paused = false;
	Proprio proprio = new Proprio();
	Core core;
	public static int vitesseSlider;
	private JPanel[][] grid;
	private JLabel cycles;
	private JLabel lblMemoireUtilise1;
	private JLabel lblMemoireUtilise2;
	private JLabel instructions1J1;
	private JLabel instructions2J1;
	private JLabel instructions3J1;
	private JLabel instructions4J1;
	private JLabel instructions5J1;
	private JLabel instructions1J2;
	private JLabel instructions2J2;
	private JLabel instructions3J2;
	private JLabel instructions4J2;
	private JLabel instructions5J2;
	JTextArea labelArea;

	/**
	 * Création de la fenêtre
	 */

	public UI() {
		core = new Core();
		setTitle("CoreWar3");

		// Fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(40, 0, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Using this process to invoke the contructor,
		// JFileChooser points to user's default directory
		JFileChooser charger1 = new JFileChooser("./warriors");
		JFileChooser charger2 = new JFileChooser("./warriors");

		// Open the save dialog

		// Configuration du bouton "charger joueurs"
		JButton btnChargerJoueur1 = new JButton("joueur1");
		btnChargerJoueur1.setToolTipText("Permet de charger les joueurs");
		btnChargerJoueur1.setBounds(21, 17, 128, 29);
		btnChargerJoueur1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				charger1.showOpenDialog(null);
				file1 = charger1.getSelectedFile();
				try {
					core.chargerJoueur(file1, core.getPlacementJ1());
				} catch (FileNotFoundException | CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		contentPane.add(btnChargerJoueur1);

		JButton btnChargerJoueur2 = new JButton("joueur2");
		btnChargerJoueur2.setToolTipText("Permet de charger les joueurs");
		btnChargerJoueur2.setBounds(21, 52, 128, 29);
		btnChargerJoueur2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				charger2.showOpenDialog(null);
				file2 = charger2.getSelectedFile();
				try {
					core.chargerJoueur(file2, core.getPlacementJ2());
				} catch (FileNotFoundException | CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		contentPane.add(btnChargerJoueur2);

		// Configuration du bouton "lancer la partie"
		JButton btnLancer = new JButton("Start");
		btnLancer.setToolTipText("Permet de lancer une partie");
		btnLancer.setBounds(235, 17, 140, 29);
		btnLancer.addActionListener(new ActionListener() {

			File imp = new File("./warriors/imp");

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {

					if (file1 == null)
						core.chargerJoueur(imp, core.getPlacementJ1());
					else
						core.chargerJoueur(file1, core.getPlacementJ1());

					if (file2 == null)
						core.chargerJoueur(imp, core.getPlacementJ2());
					else
						core.chargerJoueur(file2, core.getPlacementJ2());

					core.startThread = new Thread(core);
					core.startThread.start();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		});
		contentPane.add(btnLancer);

		JButton btnStop = new JButton("Stop");
		btnStop.setBounds(449, 17, 140, 29);
		btnStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				core.suspend();
				core=new Core();
			}

		});
		contentPane.add(btnStop);

		JButton btnPlay = new JButton("Play/Pause");
		btnPlay.setBounds(235, 52, 140, 29);
		btnPlay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (paused)
					core.resume();
				else
					core.suspend(); // ne marche pas pour l'instant!
				paused = !paused;
			}
		});
		contentPane.add(btnPlay);

		// Configuration du JSlider "vitesse"
		JSlider slider = new JSlider();
		slider.setMinorTickSpacing(50);
		slider.setValue(0);
		slider.setPaintTicks(true);
		slider.setSnapToTicks(true);
		slider.setBounds(700, 45, 190, 29);
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				vitesseSlider = slider.getValue();
				// TODO Auto-generated method stub

			}
		});
		contentPane.add(slider);

		// Jlabels
		JLabel lblVitesse = new JLabel("Vitesse :");
		lblVitesse.setBounds(620, 45, 75, 23);
		contentPane.add(lblVitesse);

		JLabel lblJoueur = new JLabel("<html><div style=\"color:red\">Joueur 1 :</div></html>");
		lblJoueur.setBounds(29, 619, 181, 23);
		contentPane.add(lblJoueur);

		JLabel lblNewLabel = new JLabel("<html><div style=\"color:blue\">Joueur 2 :</div></html>");
		lblNewLabel.setBounds(29, 689, 181, 23);
		contentPane.add(lblNewLabel);

		JLabel lblInstructionsJoueur = new JLabel(
				"<html><div style=\"color:red\">Instructions Joueur 1 :</div></html>");
		lblInstructionsJoueur.setBounds(700, 95, 271, 23);
		contentPane.add(lblInstructionsJoueur);

		JLabel lblInstructionsJoueur_1 = new JLabel(
				"<html><div style=\"color:blue\">Instructions Joueur 2 :</div></html>");
		lblInstructionsJoueur_1.setBounds(700, 424, 271, 23);
		contentPane.add(lblInstructionsJoueur_1);

		initGrid();
		initCycles();
		initInstructionsJ1();
		initInstructionsJ2();
		initMemJ1();
		initMemJ2();
	}

	public void updateGrid(int[][] data) {
		for (int i = 0; i < 64; i++) {
			for (int j = 0; j < 64; j++) {
				if (data[i][j] == 1)
					grid[i][j].setBackground(Color.RED);
				else if (data[i][j] == 2)
					grid[i][j].setBackground(Color.BLUE);
				else if (data[i][j] == 3)
					grid[i][j].setBackground(new Color(150, 150, 150));
				else
					grid[i][j].setBackground(Color.WHITE);
			}
		}

	}

	public void initGrid() {
		JPanel jp = new JPanel();
		jp.setBounds(29, 65, 640, 570);
		jp.setLayout(new GridLayout(64, 64, 0, 0));

		grid = new JPanel[64][64];

		for (int i = 0; i < 64; i++) {
			for (int j = 0; j < 64; j++) {
				grid[i][j] = new JPanel();
				grid[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				jp.add(grid[i][j]);
				contentPane.add(jp);
			}
		}
	}

	public void initCycles() {
		cycles = new JLabel("Cycles restants : ");
		cycles.setBounds(300, 654, 261, 23);
		contentPane.add(cycles);
	}

	public void updateCycles() {
		cycles.setText("Cycles restants : " + core.cyclesToDie);
	}

	public void initMemJ1() {
		lblMemoireUtilise1 = new JLabel();
		lblMemoireUtilise1.setBounds(29, 654, 181, 23);
		contentPane.add(lblMemoireUtilise1);
	}

	public void initMemJ2() {
		lblMemoireUtilise2 = new JLabel();
		lblMemoireUtilise2.setBounds(29, 724, 181, 23);
		contentPane.add(lblMemoireUtilise2);
	}

	public void updateMem(int[] colorGrid) {

		lblMemoireUtilise1.setText("Memoire utilisée : " + core.memJ1 + "bit");
		lblMemoireUtilise2.setText("Memoire utilisée : " + core.memJ2 + "bit");

	}

	public void initInstructionsJ1() {
		instructions1J1 = new JLabel();
		instructions1J1.setBounds(700, 145, 261, 23);
		instructions2J1 = new JLabel();
		instructions2J1.setBounds(700, 180, 261, 23);
		instructions3J1 = new JLabel();
		instructions3J1.setBounds(700, 215, 261, 23);
		instructions4J1 = new JLabel();
		instructions4J1.setBounds(700, 250, 261, 23);
		instructions5J1 = new JLabel();
		instructions5J1.setBounds(700, 285, 261, 23);
		contentPane.add(instructions1J1);
		contentPane.add(instructions2J1);
		contentPane.add(instructions3J1);
		contentPane.add(instructions4J1);
		contentPane.add(instructions5J1);
	}

	public void updateInstructionsJ1() {
		if (core.insJ1.size() >= 1) {
			instructions1J1.setText(core.insJ1.get(core.insJ1.size() - 1));
		}
		if (core.insJ1.size() >= 2) {
			instructions2J1.setText(core.insJ1.get(core.insJ1.size() - 2));
		}
		if (core.insJ1.size() >= 3) {
			instructions3J1.setText(core.insJ1.get(core.insJ1.size() - 3));
		}
		if (core.insJ1.size() >= 4) {
			instructions4J1.setText(core.insJ1.get(core.insJ1.size() - 4));
		}
		if (core.insJ1.size() >= 5) {
			instructions5J1.setText(core.insJ1.get(core.insJ1.size() - 5));
		}
	}

	public void initInstructionsJ2() {
		instructions1J2 = new JLabel();
		instructions1J2.setBounds(700, 470, 261, 23);
		instructions2J2 = new JLabel();
		instructions2J2.setBounds(700, 505, 261, 23);
		instructions3J2 = new JLabel();
		instructions3J2.setBounds(700, 540, 261, 23);
		instructions4J2 = new JLabel();
		instructions4J2.setBounds(700, 575, 261, 23);
		instructions5J2 = new JLabel();
		instructions5J2.setBounds(700, 610, 261, 23);
		contentPane.add(instructions1J2);
		contentPane.add(instructions2J2);
		contentPane.add(instructions3J2);
		contentPane.add(instructions4J2);
		contentPane.add(instructions5J2);
	}

	public void updateInstructionsJ2() {
		if (core.insJ2.size() >= 1) {
			instructions1J2.setText(core.insJ2.get(core.insJ2.size() - 1));
		}
		if (core.insJ2.size() >= 2) {
			instructions2J2.setText(core.insJ2.get(core.insJ2.size() - 2));
		}
		if (core.insJ2.size() >= 3) {
			instructions3J2.setText(core.insJ2.get(core.insJ2.size() - 3));
		}
		if (core.insJ2.size() >= 4) {
			instructions4J2.setText(core.insJ2.get(core.insJ2.size() - 4));
		}
		if (core.insJ2.size() >= 5) {
			instructions5J2.setText(core.insJ2.get(core.insJ2.size() - 5));
		}
	}

	public void run() {

		try {
			// delay de 40 millisecondes (25 fps)
			// à modifier car la boucle se lance à l'ouverture du programme et non au
			// lancement de la partie
			for (int i = 0; i < core.getMaxCycles() * 1000; i++) {
				proprio.coreToGrille(core.colorGrid);
				updateGrid(proprio.tableColoriee);
				updateCycles();
				updateInstructionsJ1();
				updateInstructionsJ2();
				updateMem(core.colorGrid);
				Thread.currentThread().sleep(40);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
