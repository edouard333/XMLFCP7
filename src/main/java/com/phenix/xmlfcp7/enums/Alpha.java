package com.phenix.xmlfcp7.enums;

import com.phenix.xmlfcp7.internal.FCP7XMLConvertible;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Les différentes possibilités de couche alpha.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public enum Alpha implements FCP7XMLConvertible {
    /**
     * Valeur de la couche alpha : aucune.
     */
    NONE("none"),
    /**
     * Valeur de la couche alpha : oui, valeur utilisée pour
     * classique/infographie.
     */
    STRAIGHT("straight");

    /**
     * Méthode de la couleur alpha.
     */
    @NotNull
    @NotBlank
    private final String methode;

    /**
     * Définit la méthode de couche alpha.
     *
     * @param methode La méthode.
     */
    private Alpha(@NotNull @NotBlank String methode) {
        this.methode = methode;
    }

    @NotNull
    @NotBlank
    @Override
    public String toFCP7XML() {
        return this.methode;
    }
}
