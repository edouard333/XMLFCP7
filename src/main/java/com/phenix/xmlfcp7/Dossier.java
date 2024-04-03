package com.phenix.xmlfcp7;

import java.util.ArrayList;

/**
 * Dossier du projet.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class Dossier {

    /**
     * Liste des médias dans le dossier.
     */
    private ArrayList<Media> liste_media = new ArrayList<Media>();

    /**
     * Liste des timelines dans le dossier.
     */
    private ArrayList<Timeline> liste_timeline = new ArrayList<Timeline>();

    /**
     * Liste des dossiers dans le dossier.
     */
    private ArrayList<Dossier> liste_dossier = new ArrayList<Dossier>();

    /**
     * Nom du dossier.
     */
    private String nom;

    /**
     * La couleur du dossier.
     */
    private String couleur;

    /**
     * Couleur orange pour Adobe.
     */
    public static final String COULEUR_ORANGE = "Mango";

    /**
     * Couleur rose pour Adobe.
     */
    public static final String COULEUR_ROSE = "Rose";

    /**
     * Couleur vert pour Adobe.
     */
    public static final String COULEUR_FORET = "Forest";

    /**
     * Couleur bleue pour Adobe.
     */
    public static final String COULEUR_CERULEEN = "Cerulean";

    /**
     * Couleur mauve pour Adobe.
     */
    public static final String COULEUR_LAVANDE = "Lavender";

    /**
     * Couleur XXX pour Adobe.
     */
    public static final String COULEUR_CARAIBE = "Caribbean";

    /**
     * Couleur XXX pour Adobe.
     */
    public static final String COULEUR_IRIS = "Iris";

    /**
     * Couleur violet pour Adobe.
     */
    public static final String COULEUR_VIOLET = "Violet";

    /**
     * Construit un objet Dossier.
     *
     * @param nom Nom du dossier
     */
    public Dossier(String nom) {
        this.nom = nom;
        this.couleur = COULEUR_ORANGE;
    }

    /**
     * Construit un objet Dossier.
     *
     * @param nom Nom du dossier.
     * @param couleur Couleur du dossier.
     */
    public Dossier(String nom, String couleur) {
        this.nom = nom;
        this.couleur = couleur;
    }

    /**
     * Ajoute un sous-dossier au dossier.
     *
     * @param dossier Dossier à ajouter.
     */
    public void addDossier(Dossier dossier) {
        this.liste_dossier.add(dossier);
    }

    /**
     * Ajoute un média au dossier.
     *
     * @param media Média à ajouter.
     */
    public void addMedia(Media media) {
        this.liste_media.add(media);
    }

    /**
     * Ajoute une timeline au dossier.
     *
     * @param timeline La timeline.
     */
    public void addTimeline(Timeline timeline) {
        this.liste_timeline.add(timeline);
    }

    /**
     * Retourne le nom du dossier.
     *
     * @return Nom du dossier.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Modifie la couleur du dossier.
     *
     * @param couleur La couleur.
     */
    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    /**
     * Modifie le nom du dossier.
     *
     * @param nom Le nom.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne l'XML généré pour créer un projet d'un NLE.
     *
     * @return Code XML.
     */
    @Override
    public String toString() {
        String xml = "<bin>\n"
                + "<name>" + this.nom + "</name>\n"
                + "<labels>\n"
                + "<label2>" + this.couleur + "</label2>\n"
                + "</labels>\n";

        xml += "<children>\n";

        // Ajout des sous-dossiers :
        for (int i = 0; i < this.liste_dossier.size(); i++) {
            xml += this.liste_dossier.get(i).toString();
        }

        // Ajout des médias :
        for (int i = 0; i < this.liste_media.size(); i++) {
            xml += ((Media) this.liste_media.get(i)).toString();
        }

        // Ajout des séquences :
        for (int i = 0; i < this.liste_timeline.size(); i++) {
            xml += this.liste_timeline.get(i).toString();
        }

        xml += "</children>\n"
                + "</bin>\n";

        return xml;
    }
}
