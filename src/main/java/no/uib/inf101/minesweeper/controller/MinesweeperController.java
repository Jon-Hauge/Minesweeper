package no.uib.inf101.minesweeper.controller;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.minesweeper.model.GameState;
import no.uib.inf101.minesweeper.view.MinesweeperView;

public class MinesweeperController extends MouseAdapter implements KeyListener {

    private ControllableMinesweeperModel model;
    private MinesweeperView view;
    private Timer timer;
    private int elapsedTime;

    public MinesweeperController(ControllableMinesweeperModel model, MinesweeperView view) {
        this.model = model;
        this.view = view;
        timer = new Timer(1000, e -> {
            elapsedTime++;
            model.setElapsedTime(elapsedTime);
            view.repaint();
        });
        view.setFocusable(true);
        view.addMouseListener(this);   
        view.addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (model.getGameState().equals(GameState.WELCOME_SCREEN)) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                model.startGame();
                elapsedTime = 0;
                timer.start();
                view.repaint();
            }
            if (e.getKeyCode() == KeyEvent.VK_M) {
                model.toggleGameMode();
                view.repaint();
            }
        }

        if (model.getGameState().equals(GameState.WIN) ||
         model.getGameState().equals(GameState.GAME_OVER)) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                model.setElapsedTime(0);
                model.restartGame();
                view.repaint();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (model.getGameState().equals(GameState.ACTIVE_GAME)) {
            for (GridCell cell : model.getBoard()) {
                Rectangle activeBounds = getBoundsForCell(cell.pos());
        
                if (activeBounds.contains(mouseX, mouseY)) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        model.revealCell(cell.pos());
                        view.repaint();
                    }
                    if (SwingUtilities.isRightMouseButton(e)) {
                        model.setFlag(cell.pos());
                        view.repaint();
                    }
                    if (model.getGameState().equals(GameState.GAME_OVER) ||
                    model.getGameState().equals(GameState.WIN)) {    
                        timer.stop();
                        view.repaint();
                    }
                }
            }
        }
    }

    /** Translates position of cell into clickable bounds
     * 
     * @param pos position to get bounds for
     */
    private Rectangle getBoundsForCell(CellPosition pos) {
        int rows = model.getBoard().rows();
        int cols = model.getBoard().cols();
        int width = view.getWidth() / cols;
        int height = (view.getHeight() - 50) / rows;
        int x = pos.col() * width;
        int y = pos.row() * height + 50;

        return new Rectangle(x, y, width, height);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

}
