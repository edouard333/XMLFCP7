package com.phenix.xmlfcp7.enums;

import com.phenix.xmlfcp7.internal.FCP7XMLConvertible;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;

/**
 * Définit les couleurs des marqueurs.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public enum CouleurMarqueur implements FCP7XMLConvertible {
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
    @Null
    @NotBlank
    public final String couleurPremiere;

    /**
     * Valeur du canal alpha.
     */
    public final int canalAlpha;

    /**
     * Valeur du canal rouge.
     */
    public final int canalRouge;

    /**
     * Valeur du canal vert.
     */
    public final int canalVert;

    /**
     * Valeur du canal bleu.
     */
    public final int canalBleu;

    /**
     * Définit une valeur de couleur.
     *
     * @param couleurpremiere
     * @param canalalpha
     * @param canalrouge
     * @param canalvert
     * @param canalbleu
     */
    private CouleurMarqueur(@Null String couleurPremiere, int canalAlpha, int canalRouge, int canalVert, int canalBleu) {
        this.couleurPremiere = couleurPremiere;
        this.canalAlpha = canalAlpha;
        this.canalRouge = canalRouge;
        this.canalVert = canalVert;
        this.canalBleu = canalBleu;
    }

    /**
     * Retourne une couleur de marqueur selon la couleur d'Adobe Premiere.
     *
     * @param couleurPremiere Couleur Adobe Premiere.
     * @return La couleur.
     */
    @Null
    public static CouleurMarqueur fromCouleurPremiere(String couleurPremiere) {
        for (CouleurMarqueur couleur : values()) {
            if (couleur.couleurPremiere == null) {
                if (couleur.couleurPremiere == couleurPremiere) {
                    return couleur;
                }
            } else if (couleur.couleurPremiere.equals(couleurPremiere)) {
                return couleur;
            }
        }

        return null;
    }

    @Null
    @NotBlank
    @Override
    public String toFCP7XML() {
        return this.couleurPremiere;
    }
}
