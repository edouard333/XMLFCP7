package com.phenix.xmlfcp7;

import com.phenix.timecode.Timecode;
import com.phenix.xmlfcp7.enums.Alpha;

/**
 * Média généré (dans le logiciel) et pas issu d'un fichier.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class MediaTexte extends MediaVideo {

    /**
     * Le média source.
     */
    private final String media_source;

    /**
     * Texte qui sera affiché.
     */
    private String texte;

    /**
     * Définit un élément texte.<br>
     *
     * @param framerate Framerate.
     */
    public MediaTexte(int framerate) {
        super("Image", framerate);
        this.type_media = "genere";
        this.setDureeFichier(new Timecode("99:00:00:00", framerate));
        this.setStart(new Timecode("00:00:00:00", framerate));
        this.setIn(new Timecode("00:00:00:00", framerate));
        this.media_source = "GraphicAndType";
        this.setAlpha(Alpha.STRAIGHT);
    }

    /**
     * Retourne le média source.
     *
     * @return Le média source.
     */
    public String getMediaSource() {
        return this.media_source;
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
