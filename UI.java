package gui;
import core.*;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// Cette classe représente l'UI générale. elle implemente les boutons et leurs
// actions listener (charger joueur, lancer la partie et les 3 vitesses)
public class UI extends JFrame implements Runnable{


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public File file1, file2;
	Proprio proprio = new Proprio();
	Core core = new Core();
	public static int vitesseSlider;

	private JPanel[][] grid;
	
	/**
	 * Création de la fenêtre
	 */
	@SuppressWarnings("serial")

	
	public UI() {
		setTitle("CoreWar3");
		
		//Fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(40, 0, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Using this process to invoke the contructor, 
		// JFileChooser points to user's default directory 
		JFileChooser charger1 = new JFileChooser(); 
		JFileChooser charger2 = new JFileChooser(); 
		

		// Open the save dialog 
		
		//Configuration du bouton "charger joueurs"
		JButton btnChargerJoueur1 = new JButton("joueur1");
		btnChargerJoueur1.setToolTipText("Permet de charger les joueurs");
		btnChargerJoueur1.setBounds(21, 17, 128, 29);
		btnChargerJoueur1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				charger1.showOpenDialog(null); 
				
			}
		});
		file1=charger1.getSelectedFile();
		contentPane.add(btnChargerJoueur1);
		
		JButton btnChargerJoueur2 = new JButton("joueur2");
		btnChargerJoueur2.setToolTipText("Permet de charger les joueurs");
		btnChargerJoueur2.setBounds(21, 52, 128, 29);
		btnChargerJoueur2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				charger2.showOpenDialog(null); 
				
			}
		});
		file2=charger2.getSelectedFile();
		contentPane.add(btnChargerJoueur2);
		


		// Configuration du bouton "lancer la partie"
		JButton btnLancer = new JButton("Lancer");
		btnLancer.setToolTipText("Permet de lancer une partie");
		btnLancer.setBounds(235, 17, 140, 29);
		btnLancer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				core.initJoueurs();
				core.startThread=new Thread(core);
				core.startThread.start();
			}
		});
		contentPane.add(btnLancer);

		
		JButton btnStop=new JButton("Stop");
		btnStop.setBounds(235, 52, 140, 29);
		btnStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					core.startThread.wait(); //ne marche pas pour l'instant!
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		contentPane.add(btnStop);
		
		// Configuration du JSlider "vitesse"
		JSlider slider = new JSlider();
		slider.setMinorTickSpacing(50);
		slider.setValue(0);
		slider.setPaintTicks(true);
		slider.setSnapToTicks(true);
		slider.setBounds(652, 17, 190, 29);
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
		lblVitesse.setBounds(565, 19, 75, 23);
		contentPane.add(lblVitesse);
		
		JLabel lblJoueur = new JLabel("Joueur 1 :");
		lblJoueur.setBounds(29, 619, 181, 23);
		contentPane.add(lblJoueur);
		
		JLabel lblNewLabel = new JLabel("Joueur 2 :");
		lblNewLabel.setBounds(29, 689, 181, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblCycles = new JLabel("Cycles to die :");
		lblCycles.setBounds(300, 619, 261, 23);
		contentPane.add(lblCycles);
		
		JLabel lblMemoireUtilise = new JLabel("Mémoire utilisée :");
		lblMemoireUtilise.setBounds(29, 654, 181, 23);
		contentPane.add(lblMemoireUtilise);
		
		JLabel lblMmoireUtilise = new JLabel("Mémoire utilisée :");
		lblMmoireUtilise.setBounds(29, 724, 181, 23);
		contentPane.add(lblMmoireUtilise);
		
		JLabel lblInstructionsJoueur = new JLabel("Instructions Joueur 1 :");
		lblInstructionsJoueur.setBounds(700, 95, 271, 23);
		contentPane.add(lblInstructionsJoueur);
		
		JLabel lblInstructionsJoueur_1 = new JLabel("Instructions Joueur 2 :");
		lblInstructionsJoueur_1.setBounds(700, 424, 271, 23);
		contentPane.add(lblInstructionsJoueur_1);
		
		


		initGrid();
		//proprio.afficherGrilleC(proprio.tableColoriee);
		
		
		/*
		 * updateGrid(proprio.tableColoriee); //juste pour tester l'affichage! new
		 * Thread(core).start(); System.out.println("aff " + affichage.getState());
		 * System.out.println("core " + core.t.getState());
		 * proprio.afficherGrilleC(proprio.tableColoriee); proprio.afficherC(core.core);
		 * System.out.println("aff " + affichage.getState()); System.out.println("core "
		 * + core.t.getState());
		 */
	}
	
	public void updateGrid(int[][] data) {
		for(int i=0;i<64;i++) {
			for(int j=0;j<64;j++) {
				if(data[i][j]==1)
					grid[i][j].setBackground(Color.RED);
				else if(data[i][j]==2)
					grid[i][j].setBackground(Color.BLUE);
				else
					grid[i][j].setBackground(Color.WHITE);
			}
		}
		
	}
	
	public void initGrid() {
		JPanel jp=new JPanel();
		jp.setBounds(29, 65, 640, 570);
		jp.setLayout(new GridLayout(64,64,0,0));
		
		grid=new JPanel[64][64];
		
		for(int i=0;i<64;i++) {
			for(int j=0;j<64;j++) {		
				grid[i][j]=new JPanel();
				grid[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));				
				jp.add(grid[i][j]);
				contentPane.add(jp);
			}
		}
	}
	
	public void run() {
		//à modifier car la boucle se lance à l'ouverture du programme et non au lancement de la partie

	      try {
	         // delay de 40 millisecondes (25 fps)
	  		for(int i=0; i<core.getMaxCycles()*1000; i++) {
				proprio.coreToGrille(Core.core);
				updateGrid(proprio.tableColoriee);
			}
			
	         Thread.sleep(100);
	      } catch (Exception e) {
	         System.out.println(e);
	      }
	}

  
/*
 * public int[][] randData(int width,int height) { int[][] data=new
 * int[width][height]; for(int i=0;i<(int)(0.2*width*height);i++) { int x=(int)
 * (Math.random()*width); int y=(int) (Math.random()*height); data[x][y]=1; }
 * 
 * for(int i=0;i<(int)(0.1*width*height);i++) { int x=(int)
 * (Math.random()*width); int y=(int) (Math.random()*height); data[x][y]=2; }
 * return data; }
 */
	 
}


