package no.uib.inf101.tetris.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

import no.uib.inf101.tetris.model.GameState;
import no.uib.inf101.tetris.view.TetrisView;

public class TetrisController implements KeyListener {

    private ControllableTetrisModel model;
    private TetrisView view;

    private Timer timer;

    public TetrisController(ControllableTetrisModel model, TetrisView view) {
        this.model = model;
        this.view = view;
        view.setFocusable(true);
        view.addKeyListener(this);
        this.timer = new Timer(model.getTimeInterval(), this::clockTick);
        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (model.getGameState() == GameState.ACTIVE_GAME) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> model.moveTetromino(0, -1);
                case KeyEvent.VK_RIGHT -> model.moveTetromino(0, 1);
                case KeyEvent.VK_DOWN -> {
                    if (model.moveTetromino(1, 0))
                        timer.restart();
                }
                case KeyEvent.VK_UP -> model.rotateTetromino();
                case KeyEvent.VK_SPACE -> {
                    model.dropTetromino();
                    timer.restart();
                }
            }
        }
        view.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void clockTick(ActionEvent e) {
        if (model.getGameState() == GameState.ACTIVE_GAME) {
            model.clockTick();
            updateTimeDelay();
            view.repaint();
        }
    }

    private void updateTimeDelay() {
        int delay = model.getTimeInterval();
        timer.setDelay(delay);
        timer.setInitialDelay(delay);
    }
}
