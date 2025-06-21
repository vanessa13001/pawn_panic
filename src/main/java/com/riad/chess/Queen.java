package com.riad.chess;

public class Queen extends Piece {

    public Queen(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String getType() {
        return "queen";
    }

    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, ChessBoard board) {
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);

        // La reine combine les déplacements de la tour et du fou
        if (dx == dy) {
            // Déplacement en diagonale (comme Bishop)
            int stepX = (endX > startX) ? 1 : -1;
            int stepY = (endY > startY) ? 1 : -1;

            int x = startX + stepX;
            int y = startY + stepY;
            while (x != endX && y != endY) {
                if (board.getPiece(x, y) != null) {
                    return false; // obstacle
                }
                x += stepX;
                y += stepY;
            }
        } else if (startX == endX || startY == endY) {
            // Déplacement en ligne droite (comme Rook)
            if (startX == endX) {
                int stepY = (endY > startY) ? 1 : -1;
                for (int y = startY + stepY; y != endY; y += stepY) {
                    if (board.getPiece(startX, y) != null) {
                        return false; // obstacle
                    }
                }
            } else {
                int stepX = (endX > startX) ? 1 : -1;
                for (int x = startX + stepX; x != endX; x += stepX) {
                    if (board.getPiece(x, startY) != null) {
                        return false; // obstacle
                    }
                }
            }
        } else {
            // Déplacement invalide
            return false;
        }

        // Vérifier la case d'arrivée
        Piece destinationPiece = board.getPiece(endX, endY);
        return destinationPiece == null || destinationPiece.isWhite() != this.isWhite;
    }

    @Override
    public char getSymbol() {
        return isWhite ? 'Q' : 'q';
    }
} 