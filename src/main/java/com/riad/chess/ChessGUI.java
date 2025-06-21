package com.riad.chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class ChessGUI extends JFrame {
    private static final int ICON_SIZE = 64; // <- Taille uniforme des icônes ici

    private JButton[][] squares = new JButton[8][8];
    private ChessBoard board;
    private int selectedX = -1, selectedY = -1;

    // Map des icônes pour chaque type de pièce + couleur
    private HashMap<String, ImageIcon> icons = new HashMap<>();

    public ChessGUI() {
        board = new ChessBoard();

        setTitle("Jeu d'échecs - Pawn Panic");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        

        JPanel chessPanel = new JPanel(new GridLayout(8, 8));
        initializeIcons(); // Chargement des images
        initializeBoard(chessPanel);

        add(chessPanel, BorderLayout.CENTER);

        JLabel infoLabel = new JLabel("Tour du joueur blanc", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(infoLabel, BorderLayout.SOUTH);

        refreshBoard();

        setVisible(true);
    }

    private void initializeIcons() {
        loadIcon("king_white");
        loadIcon("king_black");
        loadIcon("queen_white");
        loadIcon("queen_black");
        loadIcon("rook_white");
        loadIcon("rook_black");
        loadIcon("bishop_white");
        loadIcon("bishop_black");
        loadIcon("knight_white");
        loadIcon("knight_black");
        loadIcon("pawn_white");
        loadIcon("pawn_black");
    }

    private void loadIcon(String name) {
        String path = "/images/" + name + ".png";
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image img = icon.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);
            icons.put(name, new ImageIcon(img));
        } else {
            System.err.println("Image non trouvée : " + path);
        }
    }

    private void initializeBoard(JPanel panel) {
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                JButton btn = new JButton();
                btn.setMargin(new Insets(0, 0, 0, 0));
                btn.setBackground(getSquareColor(x, y));
                final int fx = x;
                final int fy = y;
                btn.addActionListener(e -> onSquareClicked(fx, fy));
                squares[x][y] = btn;
                panel.add(btn);
            }
        }
    }

    private Color getSquareColor(int x, int y) {
    return ((x + y) % 2 == 0) ? new Color(255, 255, 255) : new Color(180, 160, 210);
}


    private void onSquareClicked(int x, int y) {
        Piece clickedPiece = board.getPiece(x, y);
        if (selectedX == -1) {
            if (clickedPiece != null) {
                selectedX = x;
                selectedY = y;
                highlightSelectedSquare();
            }
        } else {
            boolean moved = board.movePiece(selectedX, selectedY, x, y);
            if (!moved) {
                JOptionPane.showMessageDialog(this, "Déplacement invalide !");
            }
            clearHighlights();
            selectedX = -1;
            selectedY = -1;
            refreshBoard();
        }
    }

    private void highlightSelectedSquare() {
        clearHighlights();
        squares[selectedX][selectedY].setBackground(Color.YELLOW);
    }

    private void clearHighlights() {
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                squares[x][y].setBackground(getSquareColor(x, y));
            }
        }
    }

    private void refreshBoard() {
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                Piece piece = board.getPiece(x, y);
                JButton btn = squares[x][y];
                if (piece == null) {
                    btn.setIcon(null);
                    btn.setText("");
                } else {
                    String key = piece.getType().toLowerCase() + "_" + (piece.isWhite() ? "white" : "black");
                    btn.setIcon(icons.get(key));
                    btn.setText("");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGUI::new);
    }
}
