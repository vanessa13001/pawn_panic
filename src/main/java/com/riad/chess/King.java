package com.riad.chess;

public class King extends Piece {

    public King(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String getType() {
        return "king";
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, ChessBoard board) {
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);

        // Le roi peut se déplacer d'une case dans toutes les directions
        if ((dx <= 1) && (dy <= 1)) {
            Piece destinationPiece = board.getPiece(endX, endY);
            // On peut se déplacer sur une case vide ou une case avec une pièce adverse
            return destinationPiece == null || destinationPiece.isWhite() != this.isWhite;
        }

        // TODO: Ajouter le roque plus tard

        return false;
    }

    @Override
    public char getSymbol() {
        return isWhite ? 'K' : 'k';
    }
}
