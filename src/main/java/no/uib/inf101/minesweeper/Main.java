package no.uib.inf101.minesweeper;

import javax.swing.JFrame;
import no.uib.inf101.minesweeper.controller.MinesweeperController;
import no.uib.inf101.minesweeper.model.MinesweeperModel;
import no.uib.inf101.minesweeper.view.MinesweeperView;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Main {
	
	public static final String WINDOW_TITLE = "INF101 Minesweeper";

	public static void main(String[] args) {
		MinesweeperModel model = new MinesweeperModel();
		MinesweeperView view = new MinesweeperView(model);
		new MinesweeperController(model, view);

		JFrame frame = new JFrame(WINDOW_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(view);
		frame.pack();
		frame.setVisible(true);
		// Open GUI in the middle of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
	}

}
