package com.riad.chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.awt.image.BufferedImage;

public class ChessGUI extends JFrame {
    private static final int ICON_SIZE = 64;
    private JButton[][] squares = new JButton[8][8];
    private ChessBoard board;
    private int selectedX = -1, selectedY = -1;
    private HashMap<String, ImageIcon> icons = new HashMap<>();
    private ImageIcon backgroundImage;

    public ChessGUI() {
        board = new ChessBoard();
        setTitle("Jeu d'échecs - Pawn Panic");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel chessPanel = new JPanel(new GridLayout(8, 8));
        initializeIcons();
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

        String bgPath = "/images/blue3.jpg";
        java.net.URL bgImgURL = getClass().getResource(bgPath);
        if (bgImgURL != null) {
            Image img = new ImageIcon(bgImgURL).getImage().getScaledInstance(8 * ICON_SIZE, 8 * ICON_SIZE, Image.SCALE_SMOOTH);
            backgroundImage = new ImageIcon(img);
        } else {
            System.err.println("Image de fond non trouvée : " + bgPath);
        }
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
                // Ne pas définir d'icône ici pour éviter doublons
                btn.setOpaque(false);
                btn.setContentAreaFilled(false);
                btn.setBorderPainted(false);
                btn.setFocusPainted(false);
                final int fx = x;
                final int fy = y;
                btn.addActionListener(e -> onSquareClicked(fx, fy));
                squares[x][y] = btn;
                panel.add(btn);
            }
        }
    }

    private ImageIcon createSubImage(ImageIcon originalIcon, int x, int y) {
    Image originalImage = originalIcon.getImage();
    int subImageWidth = originalImage.getWidth(null) / 8;
    int subImageHeight = originalImage.getHeight(null) / 8;

    BufferedImage subImage = new BufferedImage(subImageWidth, subImageHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = subImage.createGraphics();
    g2d.drawImage(originalImage,
        0, 0, subImageWidth, subImageHeight,
        x * subImageWidth, y * subImageHeight, (x + 1) * subImageWidth, (y + 1) * subImageHeight,
        null);
    g2d.dispose();
    return new ImageIcon(subImage);
}


    private Icon createCompositeIcon(ImageIcon backgroundIcon, ImageIcon pieceIcon, int x, int y) {
    BufferedImage compositeImage = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = compositeImage.createGraphics();

    // Dessiner l'image de fond
    ImageIcon subImageIcon = createSubImage(backgroundIcon, x, y);
    Image bgImg = subImageIcon.getImage();
    g2d.drawImage(bgImg, 0, 0, ICON_SIZE, ICON_SIZE, null);

    // Dessiner la pièce au-dessus, si elle existe
    if (pieceIcon != null) {
        Image pieceImg = pieceIcon.getImage();
        g2d.drawImage(pieceImg, 0, 0, ICON_SIZE, ICON_SIZE, null);
    }

    g2d.dispose();
    return new ImageIcon(compositeImage);
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
        squares[selectedX][selectedY].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
    }

    private void clearHighlights() {
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                squares[x][y].setBorder(BorderFactory.createEmptyBorder());
            }
        }
    }

    private void refreshBoard() {
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                Piece piece = board.getPiece(x, y);
                JButton btn = squares[x][y];
                if (piece == null) {
                    btn.setIcon(createSubImage(backgroundImage, x, y));
                    btn.setText("");
                } else {
                    String key = piece.getType().toLowerCase() + "_" + (piece.isWhite() ? "white" : "black");
                    ImageIcon pieceIcon = icons.get(key);
                    btn.setIcon(createCompositeIcon(backgroundImage, pieceIcon, x, y));
                    btn.setText("");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGUI::new);
    }
}
