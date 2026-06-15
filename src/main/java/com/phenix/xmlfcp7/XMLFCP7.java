package com.phenix.xmlfcp7;

import com.phenix.xmlfcp7.exception.XMLFCP7Exception;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * XML Final Cut Pro 7.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class XMLFCP7 {

    /**
     * Logiciels utilisables pour l'export.
     */
    public enum Logiciel {

        /**
         * Si l'XML est pour Adobe Premiere Pro CC2017.
         */
        PREMIERE,
        /**
         * Si l'XML est pour DaVinci Resolve 16.2.5.015.
         */
        RESOLVE;
    }

    /**
     * Le fichier XML à créer ou lire.
     */
    @NotNull
    private final File fichier;

    /**
     * Nom du projet.
     */
    private String titreProjet;

    /**
     * Liste des médias.
     */
    @NotNull
    private final List<Media> listeMedia;

    /**
     * Liste des timelines.
     */
    @NotNull
    private final List<Timeline> listeTimeline;

    /**
     * Liste des dossiers.
     */
    @NotNull
    private final List<Dossier> listeDossier;

    /**
     * L'XML est destiné à quel logiciel.
     */
    @NotNull
    private Logiciel logicielDestination;

    /**
     * Construit un {@link XMLFCP7}.<br>
     *
     * Par défaut, spécifique pour Adobe Premiere Pro.
     *
     * @param fichier Le chemin et nom du fichier.
     */
    public XMLFCP7(@NotNull File fichier) {
        this(fichier, Logiciel.PREMIERE);
    }

    /**
     * Construit un {@link XMLFCP7}.
     *
     * @param fichier Le chemin et nom du fichier.
     * @param logicielDestination Le XML est destiné à quel logiciel.
     */
    public XMLFCP7(@NotNull File fichier, @NotNull Logiciel logicielDestination) {
        this.fichier = fichier;
        this.logicielDestination = logicielDestination;

        this.listeMedia = new ArrayList<Media>();
        this.listeTimeline = new ArrayList<Timeline>();
        this.listeDossier = new ArrayList<Dossier>();
    }

    /**
     * Ajoute un dossier au projet.
     *
     * @param dossier Le dossier.
     */
    public void addDossier(@NotNull Dossier dossier) {
        this.listeDossier.add(dossier);
    }

    /**
     * Ajoute un média (image, audio, etc) au projet.
     *
     * @param media Média.
     */
    public void addMedia(@NotNull Media media) {
        this.listeMedia.add(media);
    }

    /**
     * Ajoute une timeline au projet.
     *
     * @param timeline La timeline à ajouter.
     */
    public void addTimeline(@NotNull Timeline timeline) {
        timeline.setLogicielDestination(this.logicielDestination);
        this.listeTimeline.add(timeline);
    }

    /**
     * On sauve le fichier.
     *
     * @throws XMLFCP7Exception
     */
    public void save() throws XMLFCP7Exception {
        // Si on veut faire de l'UTF8 mais alors on doit vérifier que les Strings reçus sont en UTF8.
        try (PrintWriter writer = new PrintWriter(this.fichier, StandardCharsets.UTF_8)) {
            writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.append("<!DOCTYPE xmeml>\n");
            writer.append("<xmeml version=\"4\">\n");

            writer.append("\t<project>\n");
            writer.append("\t\t<name>" + this.titreProjet + "</name>\n");
            writer.append("\t\t<children>\n");

            // Liste des dossiers :
            for (Dossier dossier : this.listeDossier) {
                writer.append(dossier.toFCP7XML());
            }

            // Liste timeline :
            for (Timeline timeline : this.listeTimeline) {
                writer.append(timeline.toFCP7XML());
            }

            // Liste des médias :
            for (Media media : this.listeMedia) {
                writer.append(media.toFCP7XML());
            }

            writer.append("\t\t</children>\n");
            writer.append("\t</project>\n");

            writer.append("</xmeml>");
        } catch (IOException exception) {
            throw new XMLFCP7Exception(exception.getMessage(), exception);
        }
    }

    /**
     * Suffixe donné aux fichiers générés.
     *
     * @param logicielDestination En fonction du logiciel de destination le
     * suffixe est différent.
     *
     * @return Suffixe du fichier.
     */
    @NotNull
    @NotBlank
    public static String getSuffixeFichier(Logiciel logicielDestination) {
        return (logicielDestination == Logiciel.PREMIERE) ? "PRE" : "RESOLVE";
    }

    /**
     * Modifie le nom du projet.
     *
     * @param titreProjet Le nouveau titre du projet.
     */
    public void setTitreProjet(String titreProjet) {
        this.titreProjet = titreProjet;
    }
}
