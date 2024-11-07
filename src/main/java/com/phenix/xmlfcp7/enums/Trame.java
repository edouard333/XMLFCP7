package com.phenix.xmlfcp7.enums;

/**
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public enum Trame {

    /**
     * Trame impaire.<br>
     *
     * TODO : Trouver la valeur qu'on ne connait pas encore.
     */
    PAIRE("..."),
    /**
     * Trame impaire.
     */
    IMPAIRE("upper"),
    /**
     * Aucune trame, donc balayage progressif.
     */
    AUCUNE("none");

    /**
     * La valeur.
     */
    private final String valeur;

    /**
     * Définit la valeur.
     *
     * @param valeur La valeur.
     */
    private Trame(String valeur) {
        this.valeur = valeur;
    }

    /**
     * Retourne la valeur.
     *
     * @return La valeur.
     */
    @Override
    public String toString() {
        return this.valeur;
    }
}
