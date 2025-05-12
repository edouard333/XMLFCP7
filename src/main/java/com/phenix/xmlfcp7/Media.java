package com.phenix.xmlfcp7;

import com.phenix.timecode.Timecode;
import com.phenix.xmlfcp7.effect.Effect;
import com.phenix.xmlfcp7.enums.Balayage;
import com.phenix.xmlfcp7.enums.CouleurMedia;
import com.phenix.xmlfcp7.enums.Trame;
import com.phenix.xmlfcp7.internal.FCP7XMLConvertible;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Media dans un projet.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class Media implements FCP7XMLConvertible {

    /**
     * Durée du fichier qui peut être différent du média dans la timeline.
     */
    private Timecode dureeFichier;

    /**
     * Framerate du média.
     */
    private int framerate;

    /**
     * Timecode in.
     */
    private Timecode in;

    /**
     * Où se trouve le fichier.
     */
    private String localisation;

    /**
     * Nom du média qui peut être différent du fichier qu'il fait référence.
     */
    private String nom;

    /**
     * Nom de la bobine (<em>reel name</em>).
     */
    private String nomBobine;

    /**
     * Nom du fichier.
     */
    private String nomFichier;

    /**
     * Timecode out.
     */
    private Timecode out;

    /**
     * Timecode début du média.
     */
    private Timecode start;

    /**
     * Balayage.
     */
    private Balayage balayage;

    /**
     * Si trame supérieure ou inférieure ou aucune dans le cas progressif.
     */
    private Trame trame;

    /**
     * Id du média. (?)
     */
    private static int idActuelle = 1;

    /**
     * Masterclip id.
     */
    private int id;

    /**
     * Indique si le média a été utilisé dans une timeline.
     */
    private boolean utiliser;

    /**
     * Savoir ce qu'est le fichier : un fichier audio, vidéo, image, etc élément
     * qui existe (png, mov, wav, etc) ou un élément généré (mire, décompte,
     * etc).
     */
    protected String typeMedia = "media";

    /**
     * La couleur du média : bleu par défaut.
     */
    private CouleurMedia couleur = CouleurMedia.BLEU;

    /**
     *
     */
    @NotNull
    private final List<Effect> listeEffet;

    /**
     * Définit un média sur base de son nom de fichier.
     *
     * @param nomFichier Nom du fichier.
     */
    public Media(String nomFichier) {
        this.nomFichier = nomFichier;

        // Par défaut, balayage progressig (donc aucune trame).
        this.balayage = Balayage.PROGRESSIF;
        this.trame = Trame.AUCUNE;
        this.listeEffet = new ArrayList<Effect>();

        id = idActuelle;
        idActuelle++;
    }

    /**
     * Définit un média sur base de son nom de fichier et du framerate.
     *
     * @param nomFichier Nom du fichier.
     * @param framerate Framerate.
     */
    public Media(String nomFichier, int framerate) {
        this(nomFichier);
        this.framerate = framerate;
    }

    /**
     * Ajoute un effet sur le média.
     *
     * @param effect L'effet.
     */
    public void addEffect(Effect effect) {
        this.listeEffet.add(effect);
    }

    /**
     * Savoir si le média a déjà été utilisé.
     *
     * @return {@code true} si le média a été utilisé dans la timeline.
     */
    public boolean dejaUtilise() {
        return this.utiliser;
    }

    /**
     * Retourne la couleur du média.
     *
     * @return La couleur.
     */
    public CouleurMedia getCouleur() {
        return this.couleur;
    }

    /**
     * Retourne la durée du média.
     *
     * @return Durée.
     */
    @NotNull
    public Timecode getDuree() {
        return new Timecode((this.out.toImage() - this.in.toImage() + 1), this.framerate);
    }

    /**
     * Retourne la durée du fichier (peut être différente du média dans la
     * timeline).
     *
     * @return Durée du fichier.
     */
    public Timecode getDureeFichier() {
        if (this.dureeFichier != null) {
            return this.dureeFichier;
        } else {
            return this.getDuree();
        }
    }

    /**
     * Retourne le framerate.
     *
     * @return Framerate.
     */
    public int getFramerate() {
        return this.framerate;
    }

    /**
     * Retourne l'ID du "master-clip".
     *
     * @return ID.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Retourne le point in.
     *
     * @return Point in.
     */
    public Timecode getIn() {
        return this.in;
    }

    /**
     * Retourne la localisation du média.
     *
     * @return Chemin du média.
     */
    public String getLocalisation() {
        return this.localisation;
    }

    /**
     * Retourne le nom qu'on donne au média, il peut être différent du fichier.
     *
     * @return Le nom du média.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Retourne le nom de la bobine.
     *
     * @return Nom de la bobine.
     */
    public String getNomBobine() {
        return this.nomBobine;
    }

    /**
     * Retourne le nom du fichier.
     *
     * @return Nom du fichier.
     */
    public String getNomFichier() {
        return this.nomFichier;
    }

    /**
     * Retourne le point out.
     *
     * @return Point out.
     */
    public Timecode getOut() {
        return this.out;
    }

    /**
     * Retourne le timecode début.
     *
     * @return Timecode début.
     */
    public Timecode getStart() {
        return this.start;
    }

    /**
     * Retourne la trame du média.
     *
     * @return La trame.
     */
    public Trame getTrame() {
        return this.trame;
    }

    /**
     * Retourne le type de média.
     *
     * @return Le type.
     */
    public String getTypeMedia() {
        return this.typeMedia;
    }

    /**
     * Définit le balayage du média.
     *
     * @param balayage Le balayage.
     * @param trame Si entrelacé, on indique la trame, sinon utiliser
     * {@link Trame#AUCUNE Trame.AUCUNE}.
     */
    public void setBalayage(Balayage balayage, Trame trame) {
        this.balayage = balayage;
        this.trame = trame;
    }

    /**
     * Modifie la couleur par défaut du média.
     *
     * @param couleur La nouvelle couleur.
     */
    public void setCouleur(CouleurMedia couleur) {
        this.couleur = couleur;
    }

    /**
     * Définit la durée du fichier.
     *
     * @param dureeFichier Durée du fichier.
     */
    public void setDureeFichier(Timecode dureeFichier) {
        this.dureeFichier = dureeFichier;
    }

    /**
     * On modifie l'ID du master-clip.
     *
     * @param id ID du master-clip.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Définit le point in.
     *
     * @param in Point in.
     */
    public void setIn(@NotNull Timecode in) {
        this.in = in;

        // Si média a un framerate, on l'affecte au timecode reçu.
        if (this.framerate != 0) {
            this.in.setFramerate(this.framerate);
        } // Sinon, on affecte le framerate du timecode (s'il en a un) à média.
        else {
            this.framerate = (int) this.in.getFramerate();
        }
    }

    /**
     * Retourne la liste des effets du média.
     *
     * @return Liste des effets.
     */
    @NotNull
    public List<Effect> getListeEffect() {
        return this.listeEffet;
    }

    /**
     * Définit où se trouve le média.
     *
     * @param localisation Chemin du média.
     */
    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    /**
     * Définit le nom du média.
     *
     * @param nom Nom du média.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Définit le nom de la bobine fichier.<br>
     * C'est le "<em>reel name</em>".
     *
     * @param nomBobine Nom de la bobine.
     */
    public void setNomBobine(String nomBobine) {
        this.nomBobine = nomBobine;
    }

    /**
     * Définit le nom du média fichier.
     *
     * @param nomFichier Nom du média fichier.
     */
    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    /**
     * Définit le point out.
     *
     * @param out Point out.
     */
    public void setOut(@NotNull Timecode out) {
        this.out = out;

        if (this.framerate != 0) {
            this.out.setFramerate(this.framerate);
        } // Sinon, on affecte le framerate du timecode (s'il en a un) à média.
        else {
            this.framerate = (int) this.out.getFramerate();
        }
    }

    /**
     * Définit le timecode de début.
     *
     * @param start Timecode de début.
     */
    public void setStart(@NotNull Timecode start) {
        this.start = start;

        if (this.framerate != 0) {
            this.start.setFramerate(this.framerate);
        } // Sinon, on affecte le framerate du timecode (s'il en a un) à média.
        else {
            this.framerate = (int) this.start.getFramerate();
        }
    }

    /**
     * Code XML pour générer un projet Adobe Premiere.
     *
     * @return Code XML.
     */
    @NotNull
    @NotBlank
    @Override
    public String toFCP7XML() {
        String xml = "<clip id=\"masterclip-5\" explodedTracks=\"true\">\n"
                + "\t\t\t\t\t\t\t\t\t\t<uuid>8712fc3e-6ee7-459f-87d3-1866ff0a68fe</uuid>\n"
                + "\t\t\t\t\t\t\t\t\t\t<masterclipid>masterclip-5</masterclipid>\n"
                + "\t\t\t\t\t\t\t\t\t\t<ismasterclip>TRUE</ismasterclip>\n"
                + "\t\t\t\t\t\t\t\t\t\t<duration>40</duration>\n"
                + "\t\t\t\t\t\t\t\t\t\t<rate>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t<timebase>30</timebase>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t<ntsc>TRUE</ntsc>\n"
                + "\t\t\t\t\t\t\t\t\t\t</rate>\n"
                + "\t\t\t\t\t\t\t\t\t\t<name>tu_memmerdes.mp3</name>\n"
                + "\t\t\t\t\t\t\t\t\t\t<media>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t<audio>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t<track>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t<clipitem id=\"clipitem-5\">\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<masterclipid>masterclip-5</masterclipid>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<name>tu_memmerdes.mp3</name>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<rate>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<timebase>30</timebase>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<ntsc>TRUE</ntsc>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</rate>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<file id=\"file-5\">\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<name>tu_memmerdes.mp3</name>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<pathurl>file://localhost/C%3a/Users/Edouard/Desktop/tu_memmerdes.mp3</pathurl>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<rate>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<timebase>30</timebase>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<ntsc>TRUE</ntsc>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</rate>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<duration>40</duration>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<timecode>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<rate>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<timebase>30</timebase>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<ntsc>TRUE</ntsc>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</rate>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<string>00;00;00;00</string>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<frame>0</frame>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<displayformat>DF</displayformat>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</timecode>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<media>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<audio>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<samplecharacteristics>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<depth>16</depth>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<samplerate>48000</samplerate>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</samplecharacteristics>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<channelcount>1</channelcount>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<layout>stereo</layout>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<audiochannel>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<sourcechannel>1</sourcechannel>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<channellabel>left</channellabel>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</audiochannel>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</audio>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<audio>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<samplecharacteristics>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<depth>16</depth>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<samplerate>48000</samplerate>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</samplecharacteristics>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<channelcount>1</channelcount>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<layout>stereo</layout>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<audiochannel>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<sourcechannel>2</sourcechannel>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<channellabel>right</channellabel>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</audiochannel>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</audio>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</media>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</file>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<sourcetrack>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<mediatype>audio</mediatype>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<trackindex>1</trackindex>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</sourcetrack>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<link>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<linkclipref>clipitem-6</linkclipref>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<mediatype>audio</mediatype>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<trackindex>1</trackindex>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<clipindex>1</clipindex>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</link>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<link>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<linkclipref>clipitem-7</linkclipref>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<mediatype>audio</mediatype>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<trackindex>2</trackindex>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<clipindex>1</clipindex>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</link>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t</clipitem>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t</track>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t<track>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t<clipitem id=\"clipitem-2\">\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<masterclipid>masterclip-1</masterclipid>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<name>tu_memmerdes.mp3</name>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<rate>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<timebase>30</timebase>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<ntsc>TRUE</ntsc>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</rate>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<file id=\"file-5\"/>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<sourcetrack>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<mediatype>audio</mediatype>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<trackindex>2</trackindex>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</sourcetrack>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<link>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<linkclipref>clipitem-6</linkclipref>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<mediatype>audio</mediatype>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<trackindex>1</trackindex>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<clipindex>1</clipindex>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</link>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<link>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<linkclipref>clipitem-7</linkclipref>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<mediatype>audio</mediatype>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<trackindex>2</trackindex>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<clipindex>1</clipindex>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</link>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t\t</clipitem>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t\t</track>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t</audio>\n"
                + "\t\t\t\t\t\t\t\t\t\t</media>\n"
                + "\t\t\t\t\t\t\t\t\t\t<logginginfo>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t<description></description>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t<scene></scene>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t<shottake></shottake>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t<lognote></lognote>\n"
                + "\t\t\t\t\t\t\t\t\t\t</logginginfo>\n"
                + "\t\t\t\t\t\t\t\t\t\t<labels>\n"
                + "\t\t\t\t\t\t\t\t\t\t\t<label2>Caribbean</label2>\n"
                + "\t\t\t\t\t\t\t\t\t\t</labels>\n"
                + "\t\t\t\t\t\t\t\t\t</clip>";
        return xml;
    }

    /**
     * Indique que le média a été utilisé dans une timeline.
     */
    public void utiliser() {
        this.utiliser = true;
    }
}
