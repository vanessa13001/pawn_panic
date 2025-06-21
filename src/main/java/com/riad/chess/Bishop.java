package com.riad.chess;

public class Bishop extends Piece {

    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String getType() {
        return "bishop";
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, ChessBoard board) {
        // Le fou se déplace en diagonale : la différence absolue des X et des Y doit être égale
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);
        if (dx != dy) {
            return false;
        }

        // Vérifier qu'il n'y a pas d'obstacle sur le chemin
        int stepX = (endX > startX) ? 1 : -1;
        int stepY = (endY > startY) ? 1 : -1;

        int x = startX + stepX;
        int y = startY + stepY;
        while (x != endX && y != endY) {
            if (board.getPiece(x, y) != null) {
                return false; // obstacle détecté
            }
            x += stepX;
            y += stepY;
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
        return isWhite ? 'B' : 'b';
    }
}
