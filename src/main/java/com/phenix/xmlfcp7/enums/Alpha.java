package com.phenix.xmlfcp7.enums;

/**
 * Les différentes possibilités de couche alpha.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public enum Alpha {
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
    private final String methode;

    /**
     * Définit la méthode de couche alpha?
     *
     * @param methode La méthode.
     */
    private Alpha(String methode) {
        this.methode = methode;
    }

    /**
     * Retourne la méthode de la couche alpha.
     *
     * @return Méthode de couche.
     */
    @Override
    public String toString() {
        return this.methode;
    }
}
