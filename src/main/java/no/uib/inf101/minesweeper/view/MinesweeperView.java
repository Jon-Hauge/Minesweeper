package no.uib.inf101.minesweeper.view;

import javax.swing.JPanel;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.minesweeper.model.GameMode;
import no.uib.inf101.minesweeper.model.GameState;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class MinesweeperView extends JPanel {

    public static final int OUTERMARGIN = 15;
    public static final int CELLMARGIN = 2;
    public static final int PREFERREDSIDESIZE = 40;

    private ViewableMinesweeperModel viewableMinesweeperModel;
    private ColorTheme colorTheme;

    public MinesweeperView(ViewableMinesweeperModel viewableMinesweeperModel) {
        this.viewableMinesweeperModel = viewableMinesweeperModel;

        this.colorTheme = new ColorTheme();
        this.setBackground(colorTheme.getBackgroundColor());

        this.setFocusable(true);
        this.setPreferredSize(getDefaultSize(viewableMinesweeperModel.getDimension()));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawGame(g2);
    }
    
    private void drawGame(Graphics2D g2) {
        Rectangle2D box = new Rectangle2D.Double(
                OUTERMARGIN,
                OUTERMARGIN + 50,
                this.getWidth() - OUTERMARGIN * 2,
                this.getHeight() - OUTERMARGIN * 2 - 50);
        g2.setColor(new Color(0, 200, 0));
        g2.fill(box);

        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(box,
                viewableMinesweeperModel.getDimension(), CELLMARGIN);

        drawCells(g2, viewableMinesweeperModel.getTilesOnBoard(), converter, colorTheme);

        if (viewableMinesweeperModel.getGameState().equals(GameState.WELCOME_SCREEN)) {
            drawWelcomeScreen(g2);
        }
        if (viewableMinesweeperModel.getGameState().equals(GameState.ACTIVE_GAME)) {
            drawInfoBox(g2);
        }
        if (viewableMinesweeperModel.getGameState().equals(GameState.WIN)) {
            drawWinScreen(g2);
            drawInfoBox(g2);
        }
        if (viewableMinesweeperModel.getGameState().equals(GameState.GAME_OVER)) {
            drawGameOver(g2);
            drawInfoBox(g2);
        }
    }

    private void drawCells(Graphics2D g2, Iterable<GridCell> iterable,
            CellPositionToPixelConverter converter, ColorTheme colorTheme) {
        for (GridCell cell : iterable) {
            CellPosition pos = cell.pos();
            Rectangle2D tile = converter.getBoundsForCell(pos);

            if (cell.isRevealed()) {
                if (!cell.hasMine()) {
                    drawRevealedCell(g2, cell, tile);
                } else {
                        drawMineCell(g2, tile);
                    }
                }
            if (cell.isFlagged()) {
                drawFlaggedCell(g2, tile);
            }
            if (!cell.isFlagged() && !cell.isRevealed()) {
                g2.setColor(Color.BLACK);
                g2.fill(tile);
            }
        }
    }

    private static Dimension getDefaultSize(GridDimension gd) {
        int width = (int) (PREFERREDSIDESIZE * gd.cols() + CELLMARGIN * (gd.cols() + 1) + 2 * OUTERMARGIN);
        int height = (int) (PREFERREDSIDESIZE * gd.rows() + CELLMARGIN * (gd.cols() + 1) + 2 * OUTERMARGIN + 50);
        return new Dimension(width, height);
    }

    private void drawWelcomeScreen(Graphics2D g2) {
        Rectangle2D box = new Rectangle2D.Double(
                0,
                0,
                this.getWidth(),
                this.getHeight());
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fill(box);

        double x = getBounds().getBounds2D().getCenterX();

        g2.setColor(new Color(0, 255, 0));
        g2.setFont(new Font("Monospaced", Font.BOLD, 48));
        Inf101Graphics.drawCenteredString(g2, "Welcome to", x, 125);
        Inf101Graphics.drawCenteredString(g2, "Minesweeper", x, 175);
        
        g2.setColor(new Color(0, 215, 0));
        g2.setFont(new Font("Courier New", Font.BOLD, 24));
        Inf101Graphics.drawCenteredString(g2, "Press 'Enter' to Start", x, 275);

        g2.setColor(new Color(0, 235, 0));
        Inf101Graphics.drawCenteredString(g2, "Press 'M' to change difficulty", x, 375);
        Inf101Graphics.drawCenteredString(g2, "Current Difficulty: " + viewableMinesweeperModel.getGameMode(), x, 415);
    }

    private void drawWinScreen(Graphics2D g2) {
        Rectangle2D box = new Rectangle2D.Double(
                0,
                0,
                this.getWidth(),
                this.getHeight());
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fill(box);

        double x = getBounds().getBounds2D().getCenterX();

        g2.setColor(new Color(0, 255, 0));
        g2.setFont(new Font("Monospaced", Font.BOLD, 50));
        Inf101Graphics.drawCenteredString(g2, "You Won!", x, 250);
        g2.setColor(Color.GREEN);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        Inf101Graphics.drawCenteredString(g2, "Press 'R' to Restart", x, 315);
    }

    private void drawGameOver(Graphics2D g2) {
        Rectangle2D box = new Rectangle2D.Double(
                0,
                0,
                this.getWidth(),
                this.getHeight());
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fill(box);

        double x = getBounds().getBounds2D().getCenterX();

        g2.setColor(new Color(0, 255, 0));
        g2.setFont(new Font("Monospaced", Font.BOLD, 50));
        Inf101Graphics.drawCenteredString(g2, "You Lost", x, 250);
        g2.setColor(Color.GREEN);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        Inf101Graphics.drawCenteredString(g2, "Press 'R' to Restart", x, 315);
    }

    /* Formateringen av strengen som inneholder timeren ble hjulpet av OpenAI's ChatGPT.
    * OpenAI - https://chatgpt.com - henta 14.04.25
    */
    private void drawInfoBox(Graphics2D g2) {
        Rectangle2D box = new Rectangle2D.Double(
                OUTERMARGIN,
                OUTERMARGIN,
                this.getWidth() - OUTERMARGIN * 2,
                50);
        g2.setColor(colorTheme.getBackgroundColor());
        g2.fill(box);

        g2.setColor(new Color(0, 150, 0));
        g2.setFont(new Font("Monospaced", Font.BOLD, 24));
        g2.drawString("Flags: " + viewableMinesweeperModel.getFlagCount(), 50, 35);
        int seconds = viewableMinesweeperModel.getElapsedTime();
        String elapsedTime = String.format("%02d:%02d", seconds / 60, seconds % 60);
        g2.drawString(elapsedTime, 415, 35);
    }

    private void drawRevealedCell(Graphics2D g2, GridCell cell, Rectangle2D tile) {
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fill(tile);

        if (cell.getMineCount() != 0) {
            if (cell.getMineCount() == 1) {
                g2.setColor(new Color(63, 162, 0));
            }
            if (cell.getMineCount() == 2) {
                g2.setColor(new Color(23, 141, 47));
            }
            if (cell.getMineCount() == 3) {
                g2.setColor(new Color(0, 255, 56));
            }
            if (cell.getMineCount() > 3) {
                g2.setColor(new Color(0, 100, 0));
            }

            if (viewableMinesweeperModel.getGameMode().equals(GameMode.EASY)) {
                g2.setFont(new Font("Arial", Font.BOLD, 32));
            }
            if (viewableMinesweeperModel.getGameMode().equals(GameMode.MEDIUM)) {
                g2.setFont(new Font("Arial", Font.BOLD, 24));
            }
            if (viewableMinesweeperModel.getGameMode().equals(GameMode.HARD)) {
                g2.setFont(new Font("Arial", Font.BOLD, 16));
            } 
            Inf101Graphics.drawCenteredString(g2, Integer.toString(cell.getMineCount()), tile);
        }
    }

    /* Utformingen av funksjonene drawFlaggedCell og drawMineCell ble hjulpet
     * av OpenAI's ChatGPT. Koden er forstått ihht seg selv og resten av programmet.
     * OpenAI - https://chatgpt.com - henta 13.04.25
     */
    private void drawFlaggedCell(Graphics2D g2, Rectangle2D tile) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fill(tile);
        g2.setColor(new Color(0, 200, 0));
        
        int flagWidth = (int) (tile.getWidth() * 0.6);
        int flagHeight = (int) (tile.getHeight() * 0.4);
        int x = (int) (tile.getX() + (tile.getWidth() - flagWidth) / 2);
        int y = (int) (tile.getY() + (tile.getHeight() - flagHeight) / 2);

        g2.fillRect(x, y, flagWidth, flagHeight);
        g2.setColor(new Color(0, 50, 0));
        
        int poleWidth = 5;
        int poleHeight = (int) (tile.getHeight() / 1.2);
        int poleX = (int) (tile.getX() + (tile.getWidth() - flagWidth) / 2) - poleWidth;
        int poleY = (int) (tile.getY() + tile.getHeight() - poleHeight);

        g2.fillRect(poleX, poleY, poleWidth, poleHeight);
    }

    private void drawMineCell(Graphics2D g2, Rectangle2D tile) {
        double centerX = tile.getCenterX();
        double centerY = tile.getCenterY();
        double radius = Math.min(tile.getWidth(), tile.getHeight()) * 0.35;
        
        g2.setColor(Color.BLACK);
        g2.fill(tile);
        
        g2.setColor(new Color(0, 255, 0));
        g2.fillOval((int)(centerX - radius), (int)(centerY - radius), (int)(radius * 2), (int)(radius * 2));
        
        g2.setColor(new Color(0, 150, 0));
        g2.fillOval((int)(centerX - radius / 2), (int)(centerY - radius / 2), (int)(radius), (int)(radius));
    
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawLine((int)(centerX - radius / 1.5), (int)(centerY - radius / 1.5), (int)(centerX + radius / 1.5), (int)(centerY + radius / 1.5));
        g2.drawLine((int)(centerX + radius / 1.5), (int)(centerY - radius / 1.5), (int)(centerX - radius / 1.5), (int)(centerY + radius / 1.5));
    }

}
