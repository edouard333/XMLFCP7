package com.phenix.xmlfcp7;

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
    private String alpha;

    /**
     * Valeur de la couche alpha : aucune.
     */
    public static final String ALPHA_NONE = "none";

    /**
     * Valeur de la couche alpha : oui, valeur utilisée pour
     * classique/infographie.
     */
    public static final String ALPHA_STRAIGHT = "straight";

    /**
     * Rapport de zoom du média. 100% = normal, par défaut.
     */
    private int echelle = 100;

    /**
     * Information si la vidéo est freeze ou non.
     */
    private boolean est_freeze = false;

    /**
     * À quel logiciel est destiné ce média vidéo.<br>
     * Par défaut c'est Adobe Premiere.
     */
    private byte logiciel_destination = XMLFCP7.PREMIERE;

    /**
     * Information pour le média vidéo, mais qu'on ne peut renseigner
     * qu'ici.<br>
     * Pour le cas de Resolve.<br>
     * La valeur est celle pour de la HD/UHD 1.777.
     */
    private float horizontal = 0.585365831851959F;

    /**
     * Information pour le média vidéo, mais qu'on ne peut renseigner
     * qu'ici.<br>
     * Pour le cas de Resolve.<br>
     * La valeur est celle pour de la HD/UHD 1.777.
     */
    private float vertical = 0.5F;

    /**
     * Construit un MediaVideo à partir d'un nom de fichier.
     *
     * @param nom_fichier Nom du fichier.
     */
    public MediaVideo(String nom_fichier) {
        super(nom_fichier);
        this.canaux = 0;
        this.type_media = "video";
        this.par = 1;
        this.alpha = ALPHA_NONE;
    }

    /**
     * Construit un MediaVideo à partir d'un nom de fichier et de son framerate.
     *
     * @param nom_fichier Nom du fichier.
     * @param framerate Framerate.
     */
    public MediaVideo(String nom_fichier, int framerate) {
        super(nom_fichier, framerate);
        this.canaux = 0;
        this.type_media = "video";
        this.par = 1;
        this.alpha = ALPHA_NONE;
    }

    /**
     * Retourne la couche alpha.
     *
     * @return La couche alpha.
     */
    public String getAlpha() {
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
     * Il y a un calcul.<br>
     *
     * @param largeur_timeline Largeur de la timeline.
     * @param hauteur_timeline Hauteur de la timeline.
     * @param par_timeline PAR de la timeline.
     *
     * @return Position X pour Premiere ou Resolve.
     */
    public double getPositionHorizontale(double largeur_timeline, double hauteur_timeline, double par_timeline) {
        // On divise la largeur en 2 pour avoir la partie positive ou négative.
        if (this.logiciel_destination == XMLFCP7.PREMIERE) {
            return ((this.x - (largeur_timeline / 2F)) / this.largeur) * (par_timeline / this.par);
            // return (this.x / largeur_timeline) - 0.5D;
        } // Pour Resolve: 
        else {
            double calcule_x = (this.x / largeur_timeline) - 0.5D;

            // OLD = largeur_timeline / (1.193D / par_timeline) > hauteur_timeline
            if ((largeur_timeline * par_timeline) / hauteur_timeline >= (this.largeur * this.par) / this.hauteur) {
                double calcule_1 = (largeur_timeline / hauteur_timeline) / ((double) this.largeur / (double) this.hauteur) / this.par;
                return calcule_x * calcule_1 * par_timeline;
            } else {
                return calcule_x;
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
     * Il y a un calcul.<br>
     *
     * @param largeur_timeline Largeur de la timeline.
     * @param hauteur_timeline Hauteur de la timeline.
     * @param par_timeline PAR de la timeline.
     *
     * @return Position Y pour Premiere ou Resolve.
     */
    public double getPositionVerticale(double largeur_timeline, double hauteur_timeline, double par_timeline) {
        // On divise la hauteur en 2 pour avoir la partie positive ou négative.
        if (logiciel_destination == XMLFCP7.PREMIERE) {
            return (this.y - (hauteur_timeline / 2F)) / this.hauteur;
            //return ((this.y / hauteur_timeline) - (0.5D)) * (((double) this.largeur / (double) this.hauteur) / (largeur_timeline / hauteur_timeline));
        } // Pour Resolve:
        else {
            double calcule_y = (this.y / hauteur_timeline) - 0.5D;

            // OLD = largeur_timeline / (1.193D / par_timeline) > hauteur_timeline
            if ((largeur_timeline * par_timeline) / hauteur_timeline >= (this.largeur * this.par) / this.hauteur) {
                return calcule_y;
            } else {
                double calcule_3 = ((double) this.largeur / (double) this.hauteur) / (largeur_timeline / hauteur_timeline);
                return calcule_y * calcule_3;
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
        return this.est_freeze;
    }

    /**
     * Définit la couche alpha (ou non) du fichier.
     *
     * @param alpha La couche alpha.
     */
    public void setAlpha(String alpha) {
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
     * @param est_freeze {@code true} si l'image est freezé.
     */
    public void setFreeze(boolean est_freeze) {
        this.est_freeze = est_freeze;
    }

    /**
     * Modifie la position horizontale.
     *
     * @param horizontal Position horizontale.
     */
    public void setHorizontal(float horizontal) {
        this.horizontal = horizontal;
    }

    /**
     * Modifie à quel logiciel est destiné cette vidéo.
     *
     * @param logiciel_destination Logiciel auquel est destiné la timeline.
     */
    public void setLogicielDestination(byte logiciel_destination) {
        this.logiciel_destination = logiciel_destination;
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

    /**
     * Modifie la position verticale de la vidéo.
     *
     * @param vertical Position verticale.
     */
    public void setVertical(float vertical) {
        this.vertical = vertical;
    }
}
