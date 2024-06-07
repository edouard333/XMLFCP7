package com.phenix.xmlfcp7;

/**
 * Média généré (dans le logiciel) et pas issu d'un fichier.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class MediaVideoGenere extends MediaVideo {

    /**
     * ID du média vidéo noire.
     */
    public static final int VIDEO_NOIRE = 0;

    /**
     * ID du média calque d'effet.
     */
    public static final int CALQUE_EFFET = 1;

    /**
     * Qu'est-ce qui est généré.
     */
    private final int element_genere;

    /**
     * Définit un élément généré via son ID et un framerate.<br>
     * Pour l'instant, ne gère que les "Vidéo noire".
     *
     * @param element_genere ID de l'élément généré.
     * @param framerate Framerate.
     */
    public MediaVideoGenere(int element_genere, int framerate) {
        super("Vidéo noire", framerate);
        this.type_media = "genere";
        this.element_genere = element_genere;
    }
}
