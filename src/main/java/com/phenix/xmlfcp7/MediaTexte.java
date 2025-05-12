package com.phenix.xmlfcp7;

import com.phenix.timecode.Timecode;
import com.phenix.xmlfcp7.enums.Alpha;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Média généré (dans le logiciel) et pas issu d'un fichier.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class MediaTexte extends MediaVideo {

    /**
     * Le média source.
     */
    @NotNull
    @NotBlank
    private final String mediaSource;

    /**
     * Texte qui sera affiché.
     */
    private String texte;

    /**
     * Définit un élément texte.
     *
     * @param framerate Framerate.
     */
    public MediaTexte(int framerate) {
        super("Image", framerate);
        this.typeMedia = "genere";
        this.setDureeFichier(new Timecode("99:00:00:00", framerate));
        this.setStart(new Timecode("00:00:00:00", framerate));
        this.setIn(new Timecode("00:00:00:00", framerate));
        this.mediaSource = "GraphicAndType";
        this.setAlpha(Alpha.STRAIGHT);
    }

    /**
     * Retourne le média source.
     *
     * @return Le média source.
     */
    @NotNull
    @NotBlank
    public String getMediaSource() {
        return this.mediaSource;
    }

    /**
     * Retourne le texte à afficher.
     *
     * @return Texte à afficher.
     */
    public String getTexte() {
        return this.texte;
    }

    /**
     * Définit le texte à afficher.
     *
     * @param texte Texte à afficher.
     */
    public void setTexte(String texte) {
        this.texte = texte;
    }
}
