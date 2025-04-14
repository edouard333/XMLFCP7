package com.phenix.xmlfcp7.enums;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Couleur pour un média.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public enum CouleurMedia {

    /**
     * Couleur "iris"/bleu dans Adobe Premiere.
     */
    BLEU("Iris"),
    /**
     * Couleur "mango" (orange) dans Adobe Premiere.
     */
    ORANGE("Mango"),
    /**
     * Couleur "rose" dans Adobe Premiere.
     */
    ROUGE("Rose");

    /**
     * Couleur pour Adobe Premiere.
     */
    @NotNull
    @NotBlank
    private final String couleur_premiere;

    /**
     * Définit une couleur média.
     *
     * @param couleur_premiere Couleur pour Adobe Premiere.
     */
    private CouleurMedia(@NotNull @NotBlank String couleur_premiere) {
        this.couleur_premiere = couleur_premiere;
    }

    /**
     * Retourne la valeur de la couleur pour Adobe Premiere.
     *
     * @return Valeur de la couleur pour Adobe Premiere.
     */
    @NotNull
    @NotBlank
    @Override
    public String toString() {
        return this.couleur_premiere;
    }
}
