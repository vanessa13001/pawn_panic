package com.riad.chess;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard {
    private Piece[][] board;
    private boolean whiteTurn; // true = tour des blancs, false = tour des noirs

    public ChessBoard() {
        board = new Piece[8][8];
        whiteTurn = true; // Les blancs commencent
        setupPieces();
    }

    private void setupPieces() {
        for (int i = 0; i < 8; i++) {
            board[i][1] = new Pawn(true);
            board[i][6] = new Pawn(false);
        }

        board[0][0] = new Rook(true);
        board[7][0] = new Rook(true);
        board[0][7] = new Rook(false);
        board[7][7] = new Rook(false);

        board[1][0] = new Knight(true);
        board[6][0] = new Knight(true);
        board[1][7] = new Knight(false);
        board[6][7] = new Knight(false);

        board[2][0] = new Bishop(true);
        board[5][0] = new Bishop(true);
        board[2][7] = new Bishop(false);
        board[5][7] = new Bishop(false);

        board[3][0] = new Queen(true);
        board[3][7] = new Queen(false);

        board[4][0] = new King(true);
        board[4][7] = new King(false);
    }

    public Piece getPiece(int x, int y) {
        if (!isInBounds(x, y)) {
            return null;
        }
        return board[x][y];
    }

    /**
     * Tente de déplacer une pièce de (startX, startY) vers (endX, endY).
     * Met à jour l'attribut hasMoved de la pièce si le déplacement est valide.
     * @return true si déplacement effectué, false sinon.
     */
    public boolean movePiece(int startX, int startY, int endX, int endY) {
        if (!isInBounds(startX, startY) || !isInBounds(endX, endY)) {
            System.out.println("Position hors de l'échiquier.");
            return false;
        }

        Piece piece = board[startX][startY];
        if (piece == null) {
            System.out.println("Pas de pièce à la position de départ.");
            return false;
        }

        // Vérifier que c'est bien le tour de la couleur qui joue
        if (piece.isWhite() != whiteTurn) {
            System.out.println("Ce n'est pas le tour de cette couleur.");
            return false;
        }

        if (piece.isValidMove(startX, startY, endX, endY, this)) {
            Piece destinationPiece = board[endX][endY];
            if (destinationPiece != null && destinationPiece.isWhite() == piece.isWhite()) {
                System.out.println("Déplacement impossible : case occupée par une pièce alliée.");
                return false;
            }

            // Effectuer le déplacement
            board[endX][endY] = piece;
            board[startX][startY] = null;

            // Mettre à jour l'état hasMoved de la pièce
            if (!piece.hasMoved()) {
                piece.setHasMoved(true);
            }

            System.out.println("Déplacement effectué : " + piece.getSymbol() + " de (" + startX + "," + startY + ") à (" + endX + "," + endY + ")");

            // TODO : gérer la promotion des pions ici si besoin
            // TODO : gérer le roque ici si besoin

            // Changer de tour
            whiteTurn = !whiteTurn;
            return true;
        } else {
            System.out.println("Déplacement invalide pour " + piece.getSymbol());
            return false;
        }
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public void printBoard() {
        for (int y = 7; y >= 0; y--) {
            System.out.print((y + 1) + " ");
            for (int x = 0; x < 8; x++) {
                Piece piece = board[x][y];
                if (piece == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(piece.getSymbol() + " ");
                }
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
        System.out.println("Tour de " + (whiteTurn ? "Blancs" : "Noirs"));
    }

    /**
     * Retourne une liste de toutes les pièces d'une couleur donnée.
     * @param isWhite true pour pièces blanches, false pour pièces noires
     */
    public List<Piece> getPiecesByColor(boolean isWhite) {
        List<Piece> pieces = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece piece = board[x][y];
                if (piece != null && piece.isWhite() == isWhite) {
                    pieces.add(piece);
                }
            }
        }
        return pieces;
    }

    /**
     * Retourne une copie profonde de l'état actuel de l'échiquier.
     * Utile pour simuler des mouvements sans modifier l'état réel.
     */
    public ChessBoard cloneBoard() {
        ChessBoard clone = new ChessBoard();
        clone.whiteTurn = this.whiteTurn;

        // On vide d'abord le plateau du clone
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                clone.board[x][y] = null;
            }
        }

        // Copier chaque pièce (en créant de nouvelles instances)
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece piece = this.board[x][y];
                if (piece != null) {
                    clone.board[x][y] = clonePiece(piece);
                    // Copier l'état hasMoved
                    clone.board[x][y].setHasMoved(piece.hasMoved());
                }
            }
        }

        return clone;
    }

    // Méthode auxiliaire pour cloner une pièce (à adapter selon tes classes)
    private Piece clonePiece(Piece piece) {
        boolean isWhite = piece.isWhite();
        char symbol = piece.getSymbol();

        switch (Character.toLowerCase(symbol)) {
            case 'p': return new Pawn(isWhite);
            case 'r': return new Rook(isWhite);
            case 'n': return new Knight(isWhite);
            case 'b': return new Bishop(isWhite);
            case 'q': return new Queen(isWhite);
            case 'k': return new King(isWhite);
            default: return null;
        }
    }
}
