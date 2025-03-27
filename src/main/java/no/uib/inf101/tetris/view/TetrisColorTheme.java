package no.uib.inf101.tetris.view;

import java.awt.Color;

public class TetrisColorTheme extends ColorTheme {

    @Override
    public Color getCellColor(Character c) {
        Color color = switch (c) {
            case 'L' -> new Color(255, 165, 0); // Orange
            case 'J' -> new Color(0, 0, 255);   // Blue
            case 'S' -> new Color(0, 255, 0);   // Green
            case 'Z' -> new Color(255, 0, 0);   // Red
            case 'T' -> new Color(128, 0, 128); // Purple
            case 'I' -> new Color(0, 255, 255); // Cyan
            case 'O' -> new Color(255, 255, 0); // Yellow

            default -> super.getCellColor(c);
        };
        return color;
    }
}