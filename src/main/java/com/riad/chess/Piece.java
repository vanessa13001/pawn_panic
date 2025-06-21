package com.riad.chess;

public abstract class Piece {
    protected boolean isWhite;
    private boolean hasMoved;  // ajouté

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
        this.hasMoved = false;  // initialisé à false
    }

    /**
     * Indique si la pièce est blanche.
     * @return true si blanche, false sinon.
     */
    public boolean isWhite() {
        return isWhite;
    }

    /**
     * Indique si la pièce a déjà bougé (utile pour roque, promotion, etc.).
     * @return true si la pièce a bougé, false sinon.
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Définit si la pièce a bougé.
     * @param moved true si déplacée, false sinon.
     */
    public void setHasMoved(boolean moved) {
        this.hasMoved = moved;
    }

    /**
     * Renvoie le type de la pièce (king, queen, bishop, knight, rook, pawn).
     * Chaque sous-classe doit implémenter cette méthode.
     * @return String type de la pièce.
     */
    public abstract String getType();

    /**
     * Vérifie si un déplacement est valide pour cette pièce.
     * @param startX position X de départ (0 à 7)
     * @param startY position Y de départ (0 à 7)
     * @param endX position X d'arrivée (0 à 7)
     * @param endY position Y d'arrivée (0 à 7)
     * @param board l'échiquier complet
     * @return true si déplacement valide, false sinon
     */
    public abstract boolean isValidMove(int startX, int startY, int endX, int endY, ChessBoard board);

    /**
     * Retourne le symbole de la pièce (exemple : 'P' ou 'p').
     * @return char symbole.
     */
    public abstract char getSymbol();
}
