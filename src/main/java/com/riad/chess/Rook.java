package com.riad.chess;

public class Rook extends Piece {

    public Rook(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String getType() {
        return "rook";
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, ChessBoard board) {
        // La tour se déplace uniquement en ligne droite (horizontalement ou verticalement)
        if (startX != endX && startY != endY) {
            return false;
        }

        // Vérifier qu'il n'y a pas d'obstacle sur le chemin

        // Déplacement vertical
        if (startX == endX) {
            int step = (endY > startY) ? 1 : -1;
            for (int y = startY + step; y != endY; y += step) {
                if (board.getPiece(startX, y) != null) {
                    return false; // obstacle détecté
                }
            }
        }

        // Déplacement horizontal
        if (startY == endY) {
            int step = (endX > startX) ? 1 : -1;
            for (int x = startX + step; x != endX; x += step) {
                if (board.getPiece(x, startY) != null) {
                    return false; // obstacle détecté
                }
            }
        }

        // Vérifier la case d'arrivée : soit vide soit occupée par une pièce adverse
        Piece destinationPiece = board.getPiece(endX, endY);
        if (destinationPiece == null || destinationPiece.isWhite() != this.isWhite) {
            return true;
        }

        return false;
    }

    @Override
    public char getSymbol() {
        return isWhite ? 'R' : 'r';
    }
}
