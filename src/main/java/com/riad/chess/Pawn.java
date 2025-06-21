package com.riad.chess;

public class Pawn extends Piece {

    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String getType() {
        return "pawn";
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, ChessBoard board) {
        int direction = isWhite ? 1 : -1; // Blanc avance vers y croissant, noir vers y décroissant

        // Déplacement vertical simple d'une case
        if (startX == endX && endY - startY == direction && board.getPiece(endX, endY) == null) {
            return true;
        }

        // Déplacement de 2 cases en avant si premier mouvement du pion
        if (startX == endX 
            && ((isWhite && startY == 1) || (!isWhite && startY == 6))
            && endY - startY == 2 * direction
            && board.getPiece(endX, endY) == null
            && board.getPiece(endX, startY + direction) == null) {
            return true;
        }

        // Capture en diagonale
        if (Math.abs(endX - startX) == 1 && endY - startY == direction) {
            Piece target = board.getPiece(endX, endY);
            if (target != null && target.isWhite() != this.isWhite) {
                return true;
            }
        }

        return false;
    }

    @Override
    public char getSymbol() {
        return isWhite ? 'P' : 'p';
    }
}
