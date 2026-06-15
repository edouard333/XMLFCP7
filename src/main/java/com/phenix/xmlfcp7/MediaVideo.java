package com.phenix.xmlfcp7;

import com.phenix.xmlfcp7.XMLFCP7.Logiciel;
import com.phenix.xmlfcp7.enums.Alpha;

/**
 * Média de type vidéo (image + audio).
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class MediaVideo extends Media {

    /**
     * Hauteur de l'image.
     */
    private int hauteur;

    /**
     * Largeur de l'image.
     */
    private int largeur;

    /**
     * Le PAR (Pixel Aspect Ratio).
     */
    private double par;

    /**
     * Position du média en x.
     */
    private int x;

    /**
     * Position du média en y.
     */
    private int y;

    /**
     * Nombre de canaux audio.
     */
    private int canaux;

    /**
     * Si canal alpha.
     */
    private Alpha alpha;

    /**
     * Rapport de zoom du média. 100% = normal, par défaut.
     */
    private int echelle = 100;

    /**
     * Information si la vidéo est freeze ou non.
     */
    private boolean estFreeze = false;

    /**
     * À quel logiciel est destiné ce média vidéo.<br>
     * Par défaut c'est Adobe Premiere.
     */
    private Logiciel logicielDestination = Logiciel.PREMIERE;

    /**
     * Construit un MediaVideo à partir d'un nom de fichier.
     *
     * @param nomFichier Nom du fichier.
     */
    public MediaVideo(String nomFichier) {
        super(nomFichier);
        this.canaux = 0;
        this.typeMedia = "video";
        this.par = 1;
        this.alpha = Alpha.NONE;
    }

    /**
     * Construit un MediaVideo à partir d'un nom de fichier et de son framerate.
     *
     * @param nomFichier Nom du fichier.
     * @param framerate Framerate.
     */
    public MediaVideo(String nomFichier, int framerate) {
        super(nomFichier, framerate);
        this.canaux = 0;
        this.typeMedia = "video";
        this.par = 1;
        this.alpha = Alpha.NONE;
    }

    /**
     * Retourne la couche alpha.
     *
     * @return La couche alpha.
     */
    public Alpha getAlpha() {
        return this.alpha;
    }

    /**
     * Retourne le nombre de canaux audio.
     *
     * @return Nombre de canaux audio.
     */
    public int getCanaux() {
        return this.canaux;
    }

    /**
     * Rapport du zoom.
     *
     * @return 100 = 100%
     */
    public int getEchelle() {
        return this.echelle;
    }

    /**
     * Retourne la hauteur de l'image.
     *
     * @return Hauteur en pixel.
     */
    public int getHauteur() {
        return this.hauteur;
    }

    /**
     * Retourne la largeur de l'image.
     *
     * @return Largeur en pixel.
     */
    public int getLargeur() {
        return this.largeur;
    }

    /**
     * Retourne le PAR.
     *
     * @return Le PAR.
     */
    public double getPAR() {
        return this.par;
    }

    /**
     * Position en X :<br>
     * 0 = centre, max : +/-7.80488 (1080p).<br>
     * <br>
     * Pour Adobe Premiere :<br>
     * Pour n'importe quelque résolution : /123F<br>
     * <br>
     * Pour Resolve : UHD :<br>
     * Il y a un calcul.
     *
     * @param largeurTimeline Largeur de la timeline.
     * @param hauteurTimeline Hauteur de la timeline.
     * @param parTimeline PAR de la timeline.
     *
     * @return Position X pour Premiere ou Resolve.
     */
    public double getPositionHorizontale(double largeurTimeline, double hauteurTimeline, double parTimeline) {
        // On divise la largeur en 2 pour avoir la partie positive ou négative.
        if (this.logicielDestination == Logiciel.PREMIERE) {
            return ((this.x - (largeurTimeline / 2F)) / this.largeur) * (parTimeline / this.par);
            // return (this.x / largeurTimeline) - 0.5D;
        } // Pour Resolve :
        else {
            double calculeX = (this.x / largeurTimeline) - 0.5D;

            // OLD = largeurTimeline / (1.193D / parTimeline) > hauteurTimeline
            if ((largeurTimeline * parTimeline) / hauteurTimeline >= (this.largeur * this.par) / this.hauteur) {
                double calcule1 = (largeurTimeline / hauteurTimeline) / ((double) this.largeur / (double) this.hauteur) / this.par;
                return calculeX * calcule1 * parTimeline;
            } else {
                return calculeX;
            }
        }
    }

    /**
     * Position en Y :<br>
     * 0 = centre, max : +/-6.66667 (1080p).<br>
     * <br>
     * Pour Adobe Premiere :<br>
     * Pour n'importe quelque résolution : /81F<br>
     * <br>
     * Pour Resolve :<br>
     * Il y a un calcul.
     *
     * @param largeurTimeline Largeur de la timeline.
     * @param hauteurTimeline Hauteur de la timeline.
     * @param parTimeline PAR de la timeline.
     *
     * @return Position Y pour Premiere ou Resolve.
     */
    public double getPositionVerticale(double largeurTimeline, double hauteurTimeline, double parTimeline) {
        // On divise la hauteur en 2 pour avoir la partie positive ou négative.
        if (logicielDestination == Logiciel.PREMIERE) {
            return (this.y - (hauteurTimeline / 2F)) / this.hauteur;
            //return ((this.y / hauteurTimeline) - (0.5D)) * (((double) this.largeur / (double) this.hauteur) / (largeurTimeline / hauteurTimeline));
        } // Pour Resolve:
        else {
            double calculeY = (this.y / hauteurTimeline) - 0.5D;

            // OLD = largeurTimeline / (1.193D / parTimeline) > hauteurTimeline
            if ((largeurTimeline * parTimeline) / hauteurTimeline >= (this.largeur * this.par) / this.hauteur) {
                return calculeY;
            } else {
                double calcule3 = ((double) this.largeur / (double) this.hauteur) / (largeurTimeline / hauteurTimeline);
                return calculeY * calcule3;
            }
        }
    }

    /**
     * Position en x du média (en pixel).
     *
     * @return Coordonnée x.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Position en y du média (en pixel).
     *
     * @return Coordonnée y.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Retourne si l'image est freeze ou non.
     *
     * @return {@code true} si l'image est freezé sinon {@code false}.
     */
    public boolean isFreeze() {
        return this.estFreeze;
    }

    /**
     * Définit la couche alpha (ou non) du fichier.
     *
     * @param alpha La couche alpha.
     */
    public void setAlpha(Alpha alpha) {
        this.alpha = alpha;
    }

    /**
     * Définit le nombre de canaux audio qu'a le média vidéo.
     *
     * @param canaux Nombre de canaux audio.
     */
    public void setCanaux(int canaux) {
        this.canaux = canaux;
    }

    /**
     * Définit les dimensions de la vidéo.
     *
     * @param largeur Largeur.
     * @param hauteur Hauteur.
     */
    public void setDimension(int largeur, int hauteur) {
        this.largeur = largeur;
        this.x = largeur / 2;
        this.hauteur = hauteur;
        this.y = hauteur / 2;
    }

    /**
     * Modifie le rapport du zoom.
     *
     * @param echelle 100 = 100%
     */
    public void setEchelle(int echelle) {
        this.echelle = echelle;
    }

    /**
     * Définit si l'image est freezé.
     *
     * @param estFreeze {@code true} si l'image est freezé.
     */
    public void setFreeze(boolean estFreeze) {
        this.estFreeze = estFreeze;
    }

    /**
     * Modifie à quel logiciel est destiné cette vidéo.
     *
     * @param logicielDestination Logiciel auquel est destiné la timeline.
     */
    public void setLogicielDestination(Logiciel logicielDestination) {
        this.logicielDestination = logicielDestination;
    }

    /**
     * Définit le PAR de la vidéo.
     *
     * @param par Le PAR.
     */
    public void setPAR(double par) {
        this.par = par;
    }

    /**
     * Définit la position de l'élément dans l'image.
     *
     * @param x Coordonnée x.
     * @param y Coordonnée y.
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
