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
     * Les différents modes.
     */
    public enum Mode {
        /**
         * Si on lit l'XML.
         */
        LECTURE,
        /**
         * Si on écrit l'XML.
         */
        ECRITURE;
    }

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
    private File fichier;

    /**
     * Si écriture ou lecture.
     */
    private Mode mode;

    /**
     * Nom du projet.
     */
    private String titre_projet;

    /**
     * Liste des médias.
     */
    @NotNull
    private final List<Media> liste_media;

    /**
     * Liste des timelines.
     */
    @NotNull
    private final List<Timeline> liste_timeline;

    /**
     * Liste des dossiers.
     */
    @NotNull
    private final List<Dossier> liste_dossier;

    /**
     * L'XML est destiné à quel logiciel.
     */
    private Logiciel logiciel_destination;

    /**
     * Construit un {@link XMLFCP7}.
     *
     * @param fichier Le chemin et nom du fichier.
     * @param mode Si on lit ou écrit l'XML.
     */
    public XMLFCP7(File fichier, Mode mode) {
        this(fichier, mode, Logiciel.PREMIERE);
    }

    /**
     * Construit un {@link XMLFCP7}.
     *
     * @param fichier Le chemin et nom du fichier.
     * @param mode Si on lit ou écrit l'XML.
     * @param logiciel_destination Le XML est destiné à quel logiciel.
     */
    public XMLFCP7(File fichier, Mode mode, Logiciel logiciel_destination) {
        this.fichier = fichier;
        this.mode = mode;
        this.logiciel_destination = logiciel_destination;

        this.liste_media = new ArrayList<Media>();
        this.liste_timeline = new ArrayList<Timeline>();
        this.liste_dossier = new ArrayList<Dossier>();

        // Si on crée un nouvel XML FCP7, alors le nombre de timeline est de 0.
        Timeline.nombre_timeline = 0;
    }

    /**
     * Ajoute un dossier au projet.
     *
     * @param dossier Le dossier.
     */
    public void addDossier(Dossier dossier) {
        this.liste_dossier.add(dossier);
    }

    /**
     * Ajoute un média audio au projet. Si mode écriture (add).
     *
     * @param audio Média audio.
     */
    public void addMediaAudio(MediaAudio audio) {
        this.liste_media.add(audio);
    }

    /**
     * Ajoute une image au projet.
     *
     * @param image L'image a ajouter.
     */
    public void addMediaImage(MediaImage image) {
        this.liste_media.add(image);
    }

    /**
     * Ajoute une vidéo au projet.
     *
     * @param video La vidéo à ajouter.
     */
    public void addMediaVideo(MediaVideo video) {
        this.liste_media.add(video);
    }

    /**
     * Ajoute une timeline au projet.
     *
     * @param timeline La timeline à ajouter.
     */
    public void addTimeline(Timeline timeline) {
        timeline.setLogicielDestination(this.logiciel_destination);
        this.liste_timeline.add(timeline);
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
            writer.append("\t\t<name>" + this.titre_projet + "</name>\n");
            writer.append("\t\t<children>\n");

            // Liste des dossiers :
            for (Dossier dossier : this.liste_dossier) {
                writer.append(dossier.toString());
            }

            // Liste timeline :
            for (Timeline timeline : this.liste_timeline) {
                writer.append(timeline.toString());
            }

            // Liste des médias :
            for (Media media : this.liste_media) {
                writer.append(media.toString());
            }

            writer.append("\t\t</children>\n");
            writer.append("\t</project>\n");

            writer.append("</xmeml>");
        } catch (IOException exception) {
            throw new XMLFCP7Exception(exception.getMessage(), exception);
        }
    }

    /**
     * TODO : Retourne le média audio.
     *
     * @return L'audio.
     */
    public MediaAudio getMediaAudio() {
        return new MediaAudio("XXX.wav");
    }

    /**
     * Suffixe donné aux fichiers générés.
     *
     * @param logiciel_destination En fonction du logiciel de destination le
     * suffixe est différent.
     *
     * @return Suffixe du fichier.
     */
    @NotNull
    @NotBlank
    public static String getSuffixeFichier(Logiciel logiciel_destination) {
        return (logiciel_destination == Logiciel.PREMIERE) ? "PRE" : "RESOLVE";
    }

    /**
     * Modifie le nom du projet.
     *
     * @param titre_projet Le nouveau titre du projet.
     */
    public void setTitreProjet(String titre_projet) {
        this.titre_projet = titre_projet;
    }
}
