package com.phenix.xmlfcp7.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Indique que la classe qui implément {@link  FCP7XMLConvertible} peut retourner
 * une représentation FC7 XML de l'objet.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public interface FCP7XMLConvertible {

    /**
     * Retourne une représentation XML.<br>
     * Pour l'instant, ne peut pas être vide, car si l'objet existe, c'est qu'il y
     * a des valeurs à convertir en FCP7 XML.
     *
     * @return Représentation XML.
     */
    @NotNull
    @NotBlank
    public String toFCP7XML();
}
