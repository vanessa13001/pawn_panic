package com.riad.chess;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Lancer l'interface graphique dans le thread de l'UI
        SwingUtilities.invokeLater(() -> new ChessGUI());
    }
}
