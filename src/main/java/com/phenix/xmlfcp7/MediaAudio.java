package com.phenix.xmlfcp7;

/**
 * Média de type audio.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class MediaAudio extends Media {

    /**
     * Nombre de canaux du fichier audio.
     */
    private int nombre_canaux;

    /**
     * Quel canal du fichier on utilise.
     */
    private int numero_source_canal;

    /**
     * Construit un média audio.
     *
     * @param nom_fichier Nom du fichier.
     */
    public MediaAudio(String nom_fichier) {
        super(nom_fichier);
        this.type_media = "audio";
        this.numero_source_canal = 1;
    }

    /**
     * Retourne le nombre de canaux.
     *
     * @return Nombre de canaux.
     */
    public int getNombreCanaux() {
        return this.nombre_canaux;
    }

    /**
     * Retourne le numéro du canal source utilisé.
     *
     * @return Numéro du canal source utilisé.
     */
    public int getNumeroSourceCanal() {
        return this.numero_source_canal;
    }

    /**
     * Définit le nombre de canaux.
     *
     * @param nombre_canaux Nombre de canaux.
     */
    public void setNombreCanaux(int nombre_canaux) {
        this.nombre_canaux = nombre_canaux;
    }

    /**
     * Définit le numéro de canal à utiliser du fichier audio.
     *
     * @param numero Numéro de canal.
     */
    public void setNumeroSourceCanal(int numero) {
        this.numero_source_canal = numero;
    }
}
