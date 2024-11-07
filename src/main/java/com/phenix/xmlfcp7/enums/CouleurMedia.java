package com.phenix.xmlfcp7.enums;

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
    private final String couleur_premiere;

    /**
     * Définit une couleur média.
     *
     * @param couleur_premiere Couleur pour Adobe Premiere.
     */
    private CouleurMedia(String couleur_premiere) {
        this.couleur_premiere = couleur_premiere;
    }

    /**
     * Retourne la valeur de la couleur pour Adobe Premiere.
     *
     * @return Valeur de la couleur pour Adobe Premiere.
     */
    @Override
    public String toString() {
        return this.couleur_premiere;
    }
}
