package com.phenix.xmlfcp7.enums;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
    @NotNull
    @NotBlank
    private final String valeur;

    /**
     * Définit la valeur.
     *
     * @param valeur La valeur.
     */
    private Trame(@NotNull @NotBlank String valeur) {
        this.valeur = valeur;
    }

    /**
     * Retourne la valeur.
     *
     * @return La valeur.
     */
    @NotNull
    @NotBlank
    @Override
    public String toString() {
        return this.valeur;
    }
}
