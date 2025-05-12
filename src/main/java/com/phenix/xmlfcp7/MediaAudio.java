package com.phenix.xmlfcp7;

/**
 * Média de type audio.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class MediaAudio extends Media {

    /**
     * Nombre de canaux du fichier audio.
     */
    private int nombreCanaux;

    /**
     * Quel canal du fichier on utilise.
     */
    private int numeroSourceCanal;

    /**
     * Construit un média audio.
     *
     * @param nomFichier Nom du fichier.
     */
    public MediaAudio(String nomFichier) {
        super(nomFichier);
        this.typeMedia = "audio";
        this.numeroSourceCanal = 1;
    }

    /**
     * Retourne le nombre de canaux.
     *
     * @return Nombre de canaux.
     */
    public int getNombreCanaux() {
        return this.nombreCanaux;
    }

    /**
     * Retourne le numéro du canal source utilisé.
     *
     * @return Numéro du canal source utilisé.
     */
    public int getNumeroSourceCanal() {
        return this.numeroSourceCanal;
    }

    /**
     * Définit le nombre de canaux.
     *
     * @param nombreCanaux Nombre de canaux.
     */
    public void setNombreCanaux(int nombreCanaux) {
        this.nombreCanaux = nombreCanaux;
    }

    /**
     * Définit le numéro de canal à utiliser du fichier audio.
     *
     * @param numero Numéro de canal.
     */
    public void setNumeroSourceCanal(int numero) {
        this.numeroSourceCanal = numero;
    }
}
