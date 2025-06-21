package com.riad.chess;

public class Knight extends Piece {

    public Knight(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String getType() {
        return "knight";
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, ChessBoard board) {
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);

        // Le cavalier bouge en "L" : 2 cases dans une direction et 1 dans l'autre
        boolean validMove = (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
        if (!validMove) {
            return false;
        }

        // La case d'arrivée doit être vide ou contenir une pièce adverse
        Piece destinationPiece = board.getPiece(endX, endY);
        if (destinationPiece == null) {
            return true;
        } else {
            return destinationPiece.isWhite() != this.isWhite;
        }
    }

    @Override
    public char getSymbol() {
        return isWhite ? 'N' : 'n';
    }
}
