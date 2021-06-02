package de.waffel.main;

import javax.swing.SwingUtilities;

import de.waffel.gui.MainWindow;
import de.waffel.gui.screens.IngameScreen;
import de.waffel.logic.TetrisGame;

public class MainController {

	private static MainController singleton;
	
	private TetrisGame game;
	
	private MainWindow mainWindow;

	private AudioPlayer audioPlayer;
	
	public static void main(String[] args) {
		MainController instance = getSingleton();
		SwingUtilities.invokeLater(instance::showMenu);
	}

	public void showMenu() {
		mainWindow = new MainWindow();
	}
	
	public void startGame() {
		game = new TetrisGame();
		mainWindow.setGuiScreen(new IngameScreen());
		game.startGameLoop();
		audioPlayer = new AudioPlayer("/assets/theme.wav");
		audioPlayer.playAudio();
	}

	public boolean isMusic() {
		// TODO 
		return false;
	}

	public void quit() {
		// Just exit for now, no cleanup
		System.exit(0);
	}

	public static MainController getSingleton() {
		if(singleton == null) {
			singleton = new MainController();
		}
		return singleton;
	}

	public TetrisGame getTetrisGame() {
		return this.game;
	}


	public MainWindow getMainWindow() {
		return this.mainWindow;
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}
}
