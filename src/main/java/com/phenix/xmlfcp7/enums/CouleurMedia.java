package com.phenix.xmlfcp7.enums;

import com.phenix.xmlfcp7.internal.FCP7XMLConvertible;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Couleur pour un média.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public enum CouleurMedia implements FCP7XMLConvertible {

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
    public final String couleurPremiere;

    /**
     * Définit une couleur média.
     *
     * @param couleurPremiere Couleur pour Adobe Premiere.
     */
    private CouleurMedia(@NotNull @NotBlank String couleurPremiere) {
        this.couleurPremiere = couleurPremiere;
    }

    /**
     * Retourne la valeur de la couleur pour Adobe Premiere.
     *
     * @return Valeur de la couleur pour Adobe Premiere.
     */
    @NotNull
    @NotBlank
    @Override
    public String toFCP7XML() {
        return this.couleurPremiere;
    }
}
