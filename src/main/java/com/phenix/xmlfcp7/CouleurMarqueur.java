package com.phenix.xmlfcp7;

/**
 * Définit les couleurs des marqueurs.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public enum CouleurMarqueur {
    /**
     * Couleur rouge.
     */
    ROUGE("4281740498", 0, 255, 0, 0),
    /**
     * Couleur vert, on ne connait pas la valeur de la couleur Adobe Premiere.
     */
    VERT(null, 0, 48, 191, 72),
    /**
     * Couleur bleu, on ne connait pas la valeur de la couleur Adobe Premiere.
     */
    BLEU("4294741314", 0, 0, 0, 255),
    /**
     * Couleur orange, on ne connait pas la valeur de la couleur Adobe Premiere.
     */
    ORANGE("4280578025", 0, 255, 127, 0),
    /**
     * Couleur jaune, on ne connait pas la valeur de la couleur Adobe Premiere.
     */
    JAUNE("4281049552", 0, 0, 0, 0),
    /**
     * Couleur blanc, on ne connait pas la valeur de la couleur Adobe Premiere.
     */
    BLANC("4294967295", 0, 0, 0, 0);

    /**
     * ID de couleur pour Adobe Premiere.
     */
    private final String id_couleur_premiere;

    /**
     * Valeur du canal alpha.
     */
    private final int canal_alpha;

    /**
     * Valeur du canal rouge.
     */
    private final int canal_rouge;

    /**
     * Valeur du canal vert.
     */
    private final int canal_vert;

    /**
     * Valeur du canal bleu.
     */
    private final int canal_bleu;

    /**
     * Définit une valeur de couleur.
     *
     * @param id_couleur_premiere
     * @param canal_alpha
     * @param canal_rouge
     * @param canal_vert
     * @param canal_bleu
     */
    private CouleurMarqueur(String id_couleur_premiere, int canal_alpha, int canal_rouge, int canal_vert, int canal_bleu) {
        this.id_couleur_premiere = id_couleur_premiere;
        this.canal_alpha = canal_alpha;
        this.canal_rouge = canal_rouge;
        this.canal_vert = canal_vert;
        this.canal_bleu = canal_bleu;
    }

    /**
     * Retourne le canal alpha.
     *
     * @return Valeur du canal alpha;
     */
    public int getCanalAlpha() {
        return this.canal_alpha;
    }

    /**
     * Retourne le canal bleu.
     *
     * @return Valeur du canal bleu.
     */
    public int getCanalBleu() {
        return this.canal_bleu;
    }

    /**
     * Retourne le canal rouge.
     *
     * @return Valeur du canal rouge.
     */
    public int getCanalRouge() {
        return this.canal_rouge;
    }

    /**
     * Retourne le canal vert.
     *
     * @return Valeur du canal vert.
     */
    public int getCanalVert() {
        return this.canal_vert;
    }

    /**
     * Retourne couleur pour Adobe Premiere.
     *
     * @return Valeur pour Adobe Premiere.
     */
    public String getCouleurPremiere() {
        return this.id_couleur_premiere;
    }

    /**
     * Retourne une couleur de marqueur selon la couleur d'Adobe Premiere.
     *
     * @param couleur_premiere Couleur Adobe Premiere.
     * @return La couleur.
     */
    public static CouleurMarqueur fromCouleurPremiere(String couleur_premiere) {
        CouleurMarqueur[] liste_couleur = values();

        for (CouleurMarqueur couleur : liste_couleur) {
            if (couleur.id_couleur_premiere == null) {
                if (couleur.id_couleur_premiere == couleur_premiere) {
                    return couleur;
                }
            } else if (couleur.id_couleur_premiere.equals(couleur_premiere)) {
                return couleur;
            }
        }

        return null;
    }

    /**
     * Afficher la couleur c'est afficher la valeur d'Adobe Premiere.
     *
     * @return Valeur de la couleur selon Adobe Premiere.
     */
    @Override
    public String toString() {
        return this.id_couleur_premiere;
    }
}
