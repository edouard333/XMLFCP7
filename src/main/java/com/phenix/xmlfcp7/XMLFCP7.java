package com.phenix.xmlfcp7;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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
    private ArrayList<Media> liste_media = new ArrayList<Media>();

    /**
     * Liste des timelines.
     */
    private ArrayList<Timeline> liste_timeline = new ArrayList<Timeline>();

    /**
     * Liste des dossiers.
     */
    private ArrayList<Dossier> liste_dossier = new ArrayList<Dossier>();

    /**
     * L'XML est destiné à quel logiciel.
     */
    private Logiciel logiciel_destination;

    /**
     * Construit un {@code XMLFCP7}.
     *
     * @param fichier Le chemin et nom du fichier.
     * @param mode Si on lit ou écrit l'XML.
     */
    public XMLFCP7(File fichier, Mode mode) {
        this(fichier, mode, Logiciel.PREMIERE);
    }

    /**
     * Construit un {@code XMLFCP7}.
     *
     * @param fichier Le chemin et nom du fichier.
     * @param mode Si on lit ou écrit l'XML.
     * @param logiciel_destination Le XML est destiné à quel logiciel.
     */
    public XMLFCP7(File fichier, Mode mode, Logiciel logiciel_destination) {
        this.fichier = fichier;
        this.mode = mode;
        this.logiciel_destination = logiciel_destination;

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
     * On clôt le fichier dans soit sa lecture soit dans son écriture.
     */
    public void close() {
        // En écriture, on écrit tout.
        if (this.mode == Mode.ECRITURE) {
            try {
                // Si on veut faire de l'UTF8 mais alors on doit vérifier que les Strings reçus sont en UTF8.
                OutputStream os = new FileOutputStream(this.fichier);
                PrintWriter file = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
                //PrintWriter file = new PrintWriter(this.fichier);
                file.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                file.append("<!DOCTYPE xmeml>\n");
                file.append("<xmeml version=\"4\">\n");

                file.append("\t<project>\n");
                file.append("\t\t<name>" + this.titre_projet + "</name>\n");
                file.append("\t\t<children>\n");

                // Liste des dossiers :
                for (Dossier dossier : this.liste_dossier) {
                    file.append(dossier.toString());
                }

                // Liste timeline :
                for (Timeline timeline : this.liste_timeline) {
                    file.append(timeline.toString());
                }

                // Liste des médias :
                for (Media media : this.liste_media) {
                    file.append(media.toString());
                }

                file.append("\t\t</children>\n");
                file.append("\t</project>\n");

                file.append("</xmeml>");
                file.close();
            } catch (FileNotFoundException exception) {
                exception.printStackTrace();
            }
        } // En lecture, on ne fait que lire.
        else {
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
