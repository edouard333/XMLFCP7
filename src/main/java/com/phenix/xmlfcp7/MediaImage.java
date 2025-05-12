package com.phenix.xmlfcp7;

/**
 * Média de type image.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class MediaImage extends Media {

    /**
     * Construit un média image.
     *
     * @param nomFichier Nom du fichier.
     */
    public MediaImage(String nomFichier) {
        super(nomFichier);
        this.typeMedia = "image";
    }
}
