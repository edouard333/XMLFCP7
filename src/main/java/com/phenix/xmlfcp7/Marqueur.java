package com.phenix.xmlfcp7;

import com.phenix.timecode.Timecode;
import com.phenix.xmlfcp7.enums.CouleurMarqueur;

/**
 * Marqueur dans un projet d'un NLE.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class Marqueur {

    /**
     * Nom du marqueur.
     */
    private String nom;

    /**
     * La description liée au marqueur.
     */
    private String note;

    /**
     * Timecode in.
     */
    private Timecode in;

    /**
     * Timecode out.
     */
    private Timecode out;

    /**
     * Couleur du marqueur.
     */
    private CouleurMarqueur couleur;

    /**
     * Framerate (lié au projet).
     */
    private int framerate;

    /**
     * Durée du marqueur.
     */
    private Timecode duree;

    /**
     * Construit un marqueur au début.
     */
    public Marqueur() {
        this("", new Timecode("00:00:00:00"), new Timecode(), null, "");
    }

    /**
     * Construit un marqueur avec une description.
     *
     * @param note Description.
     */
    public Marqueur(String note) {
        this(note, new Timecode("00:00:00:00"), new Timecode(), null, "");
    }

    /**
     * Construit un marqueur.
     *
     * @param note Description.
     * @param framerate Framerate du projet.
     */
    public Marqueur(String note, int framerate) {
        this.note = note;
        this.in = new Timecode("00:00:00:00");
        this.out = new Timecode();
        this.couleur = null;
        this.nom = "";
        this.framerate = framerate;
    }

    /**
     * Construit un marqueur.
     *
     * @param note Description.
     * @param in Timecode in.
     */
    public Marqueur(String note, Timecode in) {
        this(note, in, new Timecode(), null, "");
    }

    /**
     * Construit un marqueur.
     *
     * @param note Description.
     * @param in Timecode in.
     * @param out Timecode out.
     */
    public Marqueur(String note, Timecode in, Timecode out) {
        this(note, in, out, null, "");
    }

    /**
     * Construit un marqueur.
     *
     * @param note Description.
     * @param in Timecode in.
     * @param out Timecode out.
     * @param couleur Couleur.
     */
    public Marqueur(String note, Timecode in, Timecode out, CouleurMarqueur couleur) {
        this(note, in, out, couleur, "");
    }

    /**
     * Construit un marqueur.
     *
     * @param note Description.
     * @param in Timecode in.
     * @param out Timecode out.
     * @param couleur Couleur.
     * @param nom Nom du marqueur.
     */
    public Marqueur(String note, Timecode in, Timecode out, CouleurMarqueur couleur, String nom) {
        this.note = note;
        this.in = in;
        this.out = out;
        this.couleur = couleur;
        this.nom = nom;
    }

    /**
     * Retourne la couleur.
     *
     * @return couleur.
     */
    public CouleurMarqueur getCouleur() {
        return this.couleur;
    }

    /**
     * Retourne la durée du marqueur.
     *
     * @return Durée.
     */
    public Timecode getDuree() {
        this.duree = new Timecode((out.toImage() - in.toImage() + 1), this.framerate);
        return this.duree;
    }

    /**
     * Retourne le framerate du marqueur.
     *
     * @return Framerate.
     */
    public int getFramerate() {
        return this.framerate;
    }

    /**
     * Retourne le timecode in.
     *
     * @return Timecode in.
     */
    public Timecode getIn() {
        return this.in;
    }

    /**
     * Retourne le nom du marqueur.
     *
     * @return Nom.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Retourne la note du marqueur.
     *
     * @return La note du marqueur.
     */
    public String getNote() {
        return this.note;
    }

    /**
     * Retourne le timecode out.
     *
     * @return Timecode out.
     */
    public Timecode getOut() {
        return this.out;
    }

    /**
     * Définit la couleur du marqueur.
     *
     * @param couleur Couleur.
     */
    public void setCouleur(CouleurMarqueur couleur) {
        this.couleur = couleur;
    }

    /**
     * Modifie le framerate.
     *
     * @param framerate Nouveau framerate.
     */
    public void setFramerate(int framerate) {
        this.framerate = framerate;
    }

    /**
     * Modifie le timecode in.
     *
     * @param in Timecode in.
     */
    public void setIn(Timecode in) {
        this.in = in;
        if (this.framerate != 0) {
            this.in.setFramerate(this.framerate);
        } // Sinon, on affecte le framerate du timecode (s'il en a un) à média.
        else {
            this.framerate = (int) this.in.getFramerate();
        }
    }

    /**
     * Modifie le nom du marqueur.
     *
     * @param nom Nom.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Modifie la note du marqueur.
     *
     * @param note Note.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Modifie le timecode out.
     *
     * @param out Timecode out.
     */
    public void setOut(Timecode out) {
        this.out = out;

        if (this.framerate != 0) {
            this.out.setFramerate(this.framerate);
        } // Sinon, on affecte le framerate du timecode (s'il en a un) à média.
        else {
            this.framerate = (int) this.out.getFramerate();
        }
    }
}
