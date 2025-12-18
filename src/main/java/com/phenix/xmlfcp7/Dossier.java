package com.phenix.xmlfcp7;

import com.phenix.xmlfcp7.internal.FCP7XMLConvertible;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Dossier du projet.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class Dossier implements FCP7XMLConvertible {

    /**
     * Liste des médias dans le dossier.
     */
    @NotNull
    private final List<Media> listeMedia;

    /**
     * Liste des timelines dans le dossier.
     */
    @NotNull
    private final List<Timeline> listeTimeline;

    /**
     * Liste des dossiers dans le dossier.
     */
    @NotNull
    private final List<Dossier> listeDossier;

    /**
     * Nom du dossier.
     */
    @NotNull
    private String nom;

    /**
     * La couleur du dossier.
     */
    @NotNull
    private CouleurAdobe couleur;

    /**
     * Couleurs d'Adobe.
     */
    public enum CouleurAdobe implements FCP7XMLConvertible {
        /**
         * Couleur orange pour Adobe.
         */
        ORANGE("Mango"),
        /**
         * Couleur rose pour Adobe.
         */
        ROSE("Rose"),
        /**
         * Couleur verte pour Adobe.
         */
        FORET("Forest"),
        /**
         * Couleur bleue pour Adobe.
         */
        CERULEEN("Cerulean"),
        /**
         * Couleur mauve pour Adobe.
         */
        LAVANDE("Lavender"),
        /**
         * Couleur XXX pour Adobe.
         */
        CARAIBE("Caribbean"),
        /**
         * Couleur XXX pour Adobe.
         */
        IRIS("Iris"),
        /**
         * Couleur violette pour Adobe.
         */
        VIOLET("Violet");

        /**
         *
         */
        @NotNull
        @NotBlank
        public final String valeur;

        /**
         * Une couleur Adobe.
         *
         * @param valeur La valeur.
         */
        private CouleurAdobe(@NotNull @NotBlank String valeur) {
            this.valeur = valeur;
        }

        @NotNull
        @NotBlank
        @Override
        public String toFCP7XML() {
            return this.valeur;
        }
    }

    /**
     * Construit un objet Dossier.
     *
     * @param nom Nom du dossier
     */
    public Dossier(@NotNull String nom) {
        this(nom, CouleurAdobe.ORANGE);
    }

    /**
     * Construit un objet Dossier.
     *
     * @param nom Nom du dossier.
     * @param couleur Couleur du dossier.
     */
    public Dossier(@NotNull String nom, @NotNull CouleurAdobe couleur) {
        this.nom = nom;
        this.couleur = couleur;

        this.listeMedia = new ArrayList<Media>();
        this.listeTimeline = new ArrayList<Timeline>();
        this.listeDossier = new ArrayList<Dossier>();
    }

    /**
     * Ajoute un sous-dossier au dossier.
     *
     * @param dossier Dossier à ajouter.
     */
    public void addDossier(@NotNull Dossier dossier) {
        this.listeDossier.add(dossier);
    }

    /**
     * Ajoute un média au dossier.
     *
     * @param media Média à ajouter.
     */
    public void addMedia(@NotNull Media media) {
        this.listeMedia.add(media);
    }

    /**
     * Ajoute une timeline au dossier.
     *
     * @param timeline La timeline.
     */
    public void addTimeline(@NotNull Timeline timeline) {
        this.listeTimeline.add(timeline);
    }

    /**
     * Retourne le nom du dossier.
     *
     * @return Nom du dossier.
     */
    @NotNull
    public String getNom() {
        return this.nom;
    }

    /**
     * Modifie la couleur du dossier.
     *
     * @param couleur La couleur.
     */
    public void setCouleur(@NotNull CouleurAdobe couleur) {
        this.couleur = couleur;
    }

    /**
     * Modifie le nom du dossier.
     *
     * @param nom Le nom.
     */
    public void setNom(@NotNull String nom) {
        this.nom = nom;
    }

    /**
     * Retourne l'XML généré pour créer un projet d'un NLE.
     *
     * @return Code XML.
     */
    @NotNull
    @NotBlank
    @Override
    public String toFCP7XML() {
        String xml = "<bin>\n"
                + "<name>" + this.nom + "</name>\n"
                + "<labels>\n"
                + "<label2>" + this.couleur.toFCP7XML() + "</label2>\n"
                + "</labels>\n";

        xml += "<children>\n";

        // Ajout des sous-dossiers :
        for (Dossier dossier : this.listeDossier) {
            xml += dossier.toFCP7XML();
        }

        // Ajout des médias :
        for (Media media : this.listeMedia) {
            xml += media.toFCP7XML();
        }

        // Ajout des séquences :
        for (Timeline timeline : this.listeTimeline) {
            xml += timeline.toFCP7XML();
        }

        xml += "</children>\n"
                + "</bin>\n";

        return xml;
    }
}
