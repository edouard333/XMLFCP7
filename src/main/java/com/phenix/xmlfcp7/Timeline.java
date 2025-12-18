package com.phenix.xmlfcp7;

import com.phenix.timecode.Timecode;
import com.phenix.xmlfcp7.XMLFCP7.Logiciel;
import com.phenix.xmlfcp7.effect.Effect;
import com.phenix.xmlfcp7.enums.CouleurMarqueur;
import com.phenix.xmlfcp7.internal.FCP7XMLConvertible;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * Timeline dans un projet d'un NLE.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class Timeline implements FCP7XMLConvertible {

    /**
     * Le nom de la timeline.
     */
    private String nom;

    /**
     * Le timecode de début de la timeline.
     */
    private Timecode startTc;

    /**
     * Framerate de la timeline.
     */
    private int framerate;

    /**
     * Hauteur de la timeline en pixel.
     */
    private int hauteur;

    /**
     * Largeur de la timeline en pixel.
     */
    private int largeur;

    /**
     * PAR (Pixel Aspect Ratio) de la timeline.
     */
    private double par;

    /**
     *
     */
    private static int clipitem = 0;

    /**
     * ID de la timeline instancé.
     */
    private int numeroTimeline;

    /**
     * Cette variable sert à savoir combien de timeline ont été faite.
     */
    public static int nombreTimeline = 0;

    /**
     * Liste des UUID pour rendre unique une timeline.<br>
     * Cela permet de générer dans un projet 14 timelines.
     */
    @NotNull
    @NotBlank
    private static final String[] LISTE_UUID = {
        "0e2897bc-1636-4432-bcf2-07d28e53dc39",
        "165c6655-f6b8-4573-8f51-e8f5ba3a14f8",
        "3f523f59-06b7-4df8-9d75-4b3d2e930afb",
        "4381a977-9cb2-42a7-bfd6-b9ae5e0ede20",
        "57ff1067-1a42-48f7-8a53-be80d3f14505",
        "5d3059ef-dbd3-4d28-8d1f-d49a351fc597",
        "6684a79f-3c34-4633-91d8-73f1071cbb7e",
        "69fa176c-00db-4279-88f3-b8e8a0ffeee6",
        "78ef0806-7f87-4d70-ad55-ba7ad7e2132e",
        "7a24a4a6-d76c-4ad2-b71f-eade1e61a92c",
        "a8f673fa-6c72-4c72-8498-bf85bd1bea4e",
        "ca9e5f10-a53c-468e-8182-2884b3312c83",
        "ee3f4248-fd0b-489f-866d-e0d5b3f765f1",
        "fc6ebd17-1adc-4544-bd75-e1d7b94b557c"
    };

    /**
     * Nombre de canaux audio.
     */
    private int nombreCanaux;

    /**
     * Liste des pistes vidéo dans la timeline.
     */
    private List<Integer> listePisteVideo;

    /**
     * Liste des pistes audio dans la timeline.
     */
    private List<Integer> listePisteAudio;

    /**
     * Liste des médias vidéo dans la timeline.
     */
    private List<Media> listeMediaVideo;

    /**
     * Liste des médias audio dans la timeline.
     */
    private List<Media> listeMediaAudio;

    /**
     * TC start du média vidéo dans la timeline.
     */
    private List<Timecode> listeTcStartVideo;

    /**
     * TC start du média audio dans la timeline.
     */
    private List<Timecode> listeTcStartAudio;

    /**
     * TC end du média vidéo dans la timeline.
     */
    private List<Timecode> listeTcEndVideo;

    /**
     * TC end du média audio dans la timeline.
     */
    private List<Timecode> listeTcEndAudio;

    /**
     * Si le média vidéo est activé dans la timeline.
     */
    private List<Boolean> listeActiveVideo;

    /**
     * Si le média vidéo est activé dans la timeline.
     */
    private List<Boolean> listeActiveAudio;

    /**
     * Liste des marqueurs dans la timeline
     */
    private List<Marqueur> listeMarqueur;

    /**
     * La fin de la piste vidéo.
     */
    private Map<Integer, Integer> finPisteVideo;

    /**
     * La fin de la piste vidéo.
     */
    private Map<Integer, Integer> finPisteAudio;

    /**
     * Liste des pistes vidéos à verrouiller.
     */
    private List<Integer> listePisteVideoVerrouiller;

    /**
     * Liste des pistes audio à verrouiller.
     */
    private List<Integer> listePisteAudioVerrouiller;

    /**
     * Quand on veut verrouiller toutes les pistes vidéos.
     */
    private boolean verrouillerPisteVideo;

    /**
     * Quand on veut verrouiller toutes les pistes audio.
     */
    private boolean verrouillerPisteAudio;

    /**
     * Définit la timeline est pour quel logiciel.
     */
    private Logiciel logicielDestination;

    /**
     * Position du curseur dans la timeline.
     */
    private int positionCurseur;

    /**
     * Crée une timeline avec toutes les données par défaut.
     */
    public Timeline() {
        this("Sans titre", 25, new Timecode("00:00:00:00"));
    }

    /**
     * Crée une timeline avec un nom.
     *
     * @param nom Nom de la timeline.
     */
    public Timeline(String nom) {
        this(nom, 25, new Timecode("00:00:00:00"));
    }

    /**
     * Crée une timeline.
     *
     * @param nom Nom de la timeline.
     * @param framerate Framerate de la timeline.
     */
    public Timeline(String nom, int framerate) {
        this(nom, framerate, new Timecode("00:00:00:00"));
    }

    /**
     * Construit une timeline.
     *
     * @param nom Nom de la timeline.
     * @param framerate Framerate de la timeline.
     * @param startTc Timecode début de la timeline.
     */
    public Timeline(String nom, int framerate, Timecode startTc) {
        this.nom = nom;
        this.framerate = framerate;
        this.startTc = startTc;
        this.par = 1;
        this.positionCurseur = 0; // Par défaut, le curseur est au début de la timeline.

        this.nombreCanaux = 2;

        this.verrouillerPisteVideo = false;
        this.verrouillerPisteAudio = false;

        // Par défaut, c'est pour Adobe Premiere.
        this.logicielDestination = XMLFCP7.Logiciel.PREMIERE;

        // Initialise les valeurs dans le constructeur et non dans la classe.
        this.listePisteVideo = new ArrayList<Integer>();
        this.listePisteAudio = new ArrayList<Integer>();
        this.listeMediaVideo = new ArrayList<Media>();
        this.listeMediaAudio = new ArrayList<Media>();
        this.listeTcStartVideo = new ArrayList<Timecode>();
        this.listeTcStartAudio = new ArrayList<Timecode>();
        this.listeTcEndVideo = new ArrayList<Timecode>();
        this.listeTcEndAudio = new ArrayList<Timecode>();
        this.listeActiveVideo = new ArrayList<Boolean>();
        this.listeActiveAudio = new ArrayList<Boolean>();
        this.listeMarqueur = new ArrayList<Marqueur>();
        this.finPisteVideo = new HashMap<Integer, Integer>();
        this.finPisteAudio = new HashMap<Integer, Integer>();
        this.listePisteVideoVerrouiller = new ArrayList<Integer>(10);
        this.listePisteAudioVerrouiller = new ArrayList<Integer>(4);

        this.numeroTimeline = nombreTimeline;
        nombreTimeline++;
    }

    /**
     * Ajoute un marqueur sur la timeline.
     *
     * @param marqueur Le marqueur.
     */
    public void addMarqueur(Marqueur marqueur) {
        this.listeMarqueur.add(marqueur);
    }

    /**
     * Retourne le code XML d'un marqueur dans une timeline pour un projet Adobe
     * Premiere.
     *
     * @param marqueur Le marqueur.
     * @return Code XML.
     */
    private String addMarqueurTimeline(Marqueur marqueur) {
        String xml = "\t\t\t<marker>\n";
        xml += "\t\t\t\t<name>" + marqueur.getNom() + "</name>\n";
        xml += "\t\t\t\t<comment>" + marqueur.getNote() + "</comment>\n";
        xml += "\t\t\t\t<in>" + (marqueur.getIn().toImage() - this.startTc.toImage()) + "</in>\n";
        xml += "\t\t\t\t<out>" + ((marqueur.getOut() == null || marqueur.getIn().toString().equals(marqueur.getOut().toString())) ? "-1" : (marqueur.getOut().toImage() - this.startTc.toImage() + 1)) + "</out>\n";

        if (marqueur.getCouleur() != null) {
            CouleurMarqueur couleur = marqueur.getCouleur();

            if (this.logicielDestination == XMLFCP7.Logiciel.PREMIERE) {
                // Le vert étant le par défaut, on ne l'affiche pas.
                if (couleur != CouleurMarqueur.VERT) {
                    xml += "\t\t\t\t<pproColor>" + couleur.couleurPremiere + "</pproColor>\n";
                }
            } else {
                xml += "\t\t\t\t<color>\n";
                xml += "\t\t\t\t\t<alpha>" + couleur.canalAlpha + "</alpha>\n";
                xml += "\t\t\t\t\t<red>" + couleur.canalRouge + "</red>\n";
                xml += "\t\t\t\t\t<green>" + couleur.canalVert + "</green>\n";
                xml += "\t\t\t\t\t<blue>" + couleur.canalBleu + "</blue>\n";
                xml += "\t\t\t\t</color>\n";
            }
        }

        xml += "\t\t\t</marker>\n";
        return xml;
    }

    /**
     * Ajoute un média à la timeline.
     *
     * @param media Le média.
     */
    public void addMedia(Media media) {
        this.addMedia(1, media, this.startTc, new Timecode(this.startTc.toImage() + media.getDuree().toImage(), media.getFramerate()), true);
    }

    /**
     * Ajoute un média à la timeline en spécifiant une piste.
     *
     * @param piste Le numéro de piste.
     * @param media Le média.
     */
    public void addMedia(int piste, Media media) {
        this.addMedia(piste, media, media.getIn(), media.getOut(), true);
    }

    /**
     * Ajoute un média à la timeline en précisant la piste, point in et point
     * out.
     *
     * @param piste Le numéro de piste.
     * @param media Le média.
     * @param in Point in du média.
     * @param out Point out du média.
     */
    public void addMedia(int piste, Media media, Timecode in, Timecode out) {
        // Média activé par défaut.
        this.addMedia(piste, media, in, out, true);
    }

    /**
     * Ajoute un média à la timeline en précisant la piste, point in et point
     * out.
     *
     * @param piste Le numéro de piste.
     * @param media Le média.
     * @param in Point in du média.
     * @param out Point out du média.
     * @param active Si le média est activé dans la timeline.
     */
    public void addMedia(int piste, Media media, Timecode in, Timecode out, boolean active) {
        // Pour l'image :
        if (media instanceof MediaVideo mediaVideo) {
            // Pas de superposition (seulement si trié) :
            while (this.finPisteVideo.containsKey(piste) && (in.toImage() <= this.finPisteVideo.get(piste))) {
                piste++;
            }

            this.listePisteVideo.add(piste);
            this.listeActiveVideo.add(active);

            // S'il y a une nouvelle piste, on l'ajoute :
            if (!this.finPisteVideo.containsKey(piste)) {
                this.finPisteVideo.put(piste, out.toImage());
            } // Sinon, on met à jour le Tc end :
            else {
                // Et si le TC out et plus grand que le tc de fin de la piste :
                if (this.finPisteVideo.get(piste) < out.toImage()) {
                    this.finPisteVideo.replace(piste, out.toImage());
                }
            }

            this.listeMediaVideo.add(media);

            if (this.framerate != 0) {
                in.setFramerate(this.framerate);
            } // Sinon, on affecte le framerate du timecode (s'il en a un) à média.
            else {
                this.framerate = (int) in.getFramerate();
            }

            if (this.framerate != 0) {
                out.setFramerate(this.framerate);
            } // Sinon, on affecte le framerate du timecode (s'il en a un) à média.
            else {
                this.framerate = (int) out.getFramerate();
            }

            this.listeTcStartVideo.add(in);
            this.listeTcEndVideo.add(out);

            conformiteMedia(mediaVideo);
        } // Pour les audios :
        else {
            // Pas de superposition (seulement si trié) :
            while (this.finPisteAudio.containsKey(piste) && (in.toImage() <= this.finPisteAudio.get(piste))) {
                piste++;
            }

            this.listePisteAudio.add(piste);
            this.listeActiveAudio.add(active);

            // S'il y a une nouvelle piste, on l'ajoute :
            if (!this.finPisteAudio.containsKey(piste)) {
                this.finPisteAudio.put(piste, out.toImage());
            } // Sinon, on met à jour le Tc end :
            else {
                // Et si le TC out et plus grand que le tc de fin de la piste :
                if (this.finPisteAudio.get(piste) < out.toImage()) {
                    this.finPisteAudio.replace(piste, out.toImage());
                }
            }

            this.listeMediaAudio.add(media);

            if (this.framerate != 0) {
                in.setFramerate(this.framerate);
            } // Sinon, on affecte le framerate du timecode (s'il en a un) à média.
            else {
                this.framerate = (int) in.getFramerate();
            }

            if (this.framerate != 0) {
                out.setFramerate(this.framerate);
            } // Sinon, on affecte le framerate du timecode (s'il en a un) à média.
            else {
                this.framerate = (int) out.getFramerate();
            }

            this.listeTcStartAudio.add(in);
            this.listeTcEndAudio.add(out);
        }
    }

    /**
     * Ajoute un clip vidéo à la timeline.
     *
     * @param m Le média vidéo à ajouter.
     * @param start Où commence la vidéo dans la timeline.
     * @param active Si le média est activé dans la timeline.
     *
     * @return Code XML du projet Adobe Premiere.
     */
    private String addItemClipVideo(MediaVideo m, Timecode start, boolean active) {
        clipitem++;

        // On définit à quel logiciel est destiné ce média vidéo.
        m.setLogicielDestination(logicielDestination);

        String nomFichier = new File(m.getNomFichier().replace("\\", "/")).getName();

        String xml = "\t\t\t\t\t<clipitem id=\"clipitem-" + clipitem + "\">\n"
                + "\t\t\t\t\t\t<masterclipid>masterclip-" + m.getId() + "</masterclipid>\n"
                + "\t\t\t\t\t\t<name>" + (m.getNom() != null ? m.getNom() : nomFichier) + "</name>\n"
                + "\t\t\t\t\t\t<enabled>" + ((active) ? "TRUE" : "FALSE") + "</enabled>\n"
                + "\t\t\t\t\t\t<duration>" + m.getDuree().toImage() + "</duration>\n"
                + "\t\t\t\t\t\t<rate>\n"
                + "\t\t\t\t\t\t\t<timebase>" + m.getFramerate() + "</timebase>\n"
                + "\t\t\t\t\t\t\t<ntsc>FALSE</ntsc>\n"
                + "\t\t\t\t\t\t</rate>\n"
                + "\t\t\t\t\t\t<start>" + (start.toImage() - this.startTc.toImage()) + "</start>\n"
                + "\t\t\t\t\t\t<end>" + ((start.toImage() - this.startTc.toImage() + m.getDuree().toImage()) - ((m.getDuree().toImage() > 1) ? 1 : 0)) + "</end>\n"
                + // "-1" car sinon cela ajoute une frame.
                "\t\t\t\t\t\t<in>" + m.getIn().toImage() + "</in>\n"
                + "\t\t\t\t\t\t<out>" + (m.getOut().toImage()) + "</out>\n"
                + "\t\t\t\t\t\t<pproTicksIn>0</pproTicksIn>\n"
                + // Je sais plus.
                "\t\t\t\t\t\t<pproTicksOut>" + (m.getOut().toImage() * 254016000000L / this.framerate) + "</pproTicksOut>\n"; // Je sais plus.
        /*if (!(m instanceof MediaTexte)) {
            xml += "\t\t\t\t\t\t<alphatype>"+m.getAlpha().toFCP7XML()+"</alphatype>\n";
        } else {
            xml += "\t\t\t\t\t\t<alphatype>straight</alphatype>\n";
        }*/
        xml += "\t\t\t\t\t\t<alphatype>" + m.getAlpha().toFCP7XML() + "</alphatype>\n"
                + "\t\t\t\t\t\t<pixelaspectratio>square</pixelaspectratio>\n"
                + "\t\t\t\t\t\t<anamorphic>FALSE</anamorphic>\n";

        // Si le fichier est généré.
        if (!m.getTypeMedia().equals("genere")) {
            if (!m.dejaUtilise()) {
                // Si le logiciel est Adobe Premiere :
                if (this.logicielDestination == Logiciel.PREMIERE) {
                    xml += "\t\t\t\t\t\t<file id=\"file-" + m.getId() + "\">\n"
                            + "\t\t\t\t\t\t\t<name>" + nomFichier + "</name>\n"
                            + "\t\t\t\t\t\t\t<pathurl>" + m.getLocalisation() + "</pathurl>\n"
                            + // Où se trouve le fichier.
                            "\t\t\t\t\t\t\t<rate>\n"
                            + "\t\t\t\t\t\t\t\t<timebase>" + m.getFramerate() + "</timebase>\n"
                            + // Framerate du média.
                            "\t\t\t\t\t\t\t\t<ntsc>FALSE</ntsc>\n"
                            + "\t\t\t\t\t\t\t</rate>\n"
                            + "\t\t\t\t\t\t\t<duration>" + m.getDureeFichier().toImage() + "</duration>\n"
                            + // Durée du média.
                            "\t\t\t\t\t\t\t<timecode>\n"
                            + "\t\t\t\t\t\t\t\t<rate>\n"
                            + "\t\t\t\t\t\t\t\t\t<timebase>" + m.getFramerate() + "</timebase>\n"
                            + // Framerate du média.
                            "\t\t\t\t\t\t\t\t\t<ntsc>FALSE</ntsc>\n"
                            + "\t\t\t\t\t\t\t\t</rate>\n"
                            + "\t\t\t\t\t\t\t\t<string>" + m.getStart() + "</string>\n"
                            + // Timecode de début du média.
                            "\t\t\t\t\t\t\t\t<frame>" + m.getStart().toImage() + "</frame>\n"
                            + // Début du média en nombre d'images.
                            "\t\t\t\t\t\t\t\t<displayformat>NDF</displayformat>\n";

                    if (m.getNomBobine() != null) {
                        xml += "\t\t\t\t\t\t\t\t<reel>\n"
                                + "\t\t\t\t\t\t\t\t\t<name>" + m.getNomBobine() + "</name>\n"
                                + "\t\t\t\t\t\t\t\t</reel>\n";
                    }
                    xml += "\t\t\t\t\t\t\t</timecode>\n"
                            + "\t\t\t\t\t\t\t<media>\n"
                            + "\t\t\t\t\t\t\t\t<video>\n"
                            + "\t\t\t\t\t\t\t\t\t<samplecharacteristics>\n"
                            + "\t\t\t\t\t\t\t\t\t\t<rate>\n"
                            // Frame rate du média.
                            + "\t\t\t\t\t\t\t\t\t\t\t<timebase>" + m.getFramerate() + "</timebase>\n"
                            + "\t\t\t\t\t\t\t\t\t\t\t<ntsc>FALSE</ntsc>\n"
                            + "\t\t\t\t\t\t\t\t\t\t</rate>\n"
                            // largeur du média.
                            + "\t\t\t\t\t\t\t\t\t\t<width>" + m.getLargeur() + "</width>\n"
                            // hauteur du média.
                            + "\t\t\t\t\t\t\t\t\t\t<height>" + m.getHauteur() + "</height>\n"
                            + "\t\t\t\t\t\t\t\t\t\t<anamorphic>FALSE</anamorphic>\n"
                            + "\t\t\t\t\t\t\t\t\t\t<pixelaspectratio>square</pixelaspectratio>\n"
                            + "\t\t\t\t\t\t\t\t\t\t<fielddominance>" + ((MediaVideo) m).getTrame().toFCP7XML() + "</fielddominance>\n"
                            + "\t\t\t\t\t\t\t\t\t</samplecharacteristics>\n"
                            + "\t\t\t\t\t\t\t\t</video>\n";
                    //xml += "\t</media>\n"
                    //       + "</file>";
                } // Cas de Resolve:
                else {
                    xml += "\t\t\t\t\t\t<file id=\"file-" + m.getId() + "\">\n"
                            + "\t\t\t\t\t\t\t<duration>" + m.getDureeFichier().toImage() + "</duration>\n"
                            + "\t\t\t\t\t\t\t<rate>\n"
                            + "\t\t\t\t\t\t\t\t<timebase>" + m.getFramerate() + "</timebase>\n"
                            + "\t\t\t\t\t\t\t\t<ntsc>false</ntsc>\n"
                            + "\t\t\t\t\t\t\t</rate>\n"
                            + "\t\t\t\t\t\t\t<name>" + nomFichier + "</name>\n"
                            + "\t\t\t\t\t\t\t<pathurl>" + /*new File(m.getLocalisation().replace("\\", "/")).getName()*/ m.getLocalisation() + "</pathurl>\n"
                            + "\t\t\t\t\t\t\t<timecode>\n"
                            + "\t\t\t\t\t\t\t\t<string>" + m.getStart() + "</string>\n"
                            + "\t\t\t\t\t\t\t\t<displayformat>NDF</displayformat>\n"
                            + "\t\t\t\t\t\t\t\t<rate>\n"
                            + "\t\t\t\t\t\t\t\t\t<timebase>" + m.getFramerate() + "</timebase>\n"
                            + "\t\t\t\t\t\t\t\t\t<ntsc>false</ntsc>\n"
                            + "\t\t\t\t\t\t\t\t</rate>\n";

                    if (m.getNomBobine() != null) {
                        xml += "\t\t\t\t\t\t\t\t<reel>\n"
                                + "\t\t\t\t\t\t\t\t\t<name>" + m.getNomBobine() + "</name>\n"
                                + "\t\t\t\t\t\t\t\t</reel>\n";
                    }

                    xml += "\t\t\t\t\t\t\t</timecode>\n"
                            + "\t\t\t\t\t\t\t<media>\n"
                            + "\t\t\t\t\t\t\t\t<video>\n"
                            + "\t\t\t\t\t\t\t\t\t<duration>" + m.getDureeFichier().toImage() + "</duration>\n"
                            + "\t\t\t\t\t\t\t\t\t<samplecharacteristics>\n"
                            + "\t\t\t\t\t\t\t\t\t\t<width>" + m.getLargeur() + "</width>\n"
                            + "\t\t\t\t\t\t\t\t\t\t<height>" + m.getHauteur() + "</height>\n"
                            + "\t\t\t\t\t\t\t\t\t</samplecharacteristics>\n"
                            + "\t\t\t\t\t\t\t\t</video>\n";

                    /*
                    // S'il y a des pistes audios.
                    if()
                    xml += "";
                     */
                }

                if (((MediaVideo) m).getCanaux() > 0) {
                    xml += canauxClip(((MediaVideo) m).getCanaux(), 48000, 16);
                }

                xml += "\t\t\t\t\t\t\t</media>\n"
                        + "\t\t\t\t\t\t</file>\n";
            } else {
                xml += "\t\t\t\t\t\t<file id=\"file-" + m.getId() + "\"/>\n";
            }
        } // Cas où c'est un élément généré (mire, noir, calque d'effet, etc) :
        else {
            xml += "\t\t\t\t\t\t<file id=\"genere-" + m.getId() + "\">\n"
                    + "\t\t\t\t\t\t\t<name>" + m.getNomFichier() + "</name>\n";

            MediaTexte mTexte;
            if (m instanceof MediaTexte mediaText) {
                mTexte = mediaText;
            } else {
                mTexte = null;
            }

            if (m instanceof MediaTexte) {
                xml += "\t\t\t\t\t\t\t<mediaSource>" + mTexte.getMediaSource() + "</mediaSource>\n";
            }

            xml += "\t\t\t\t\t\t\t<rate>\n"
                    + "\t\t\t\t\t\t\t\t<timebase>30</timebase>\n"
                    + "\t\t\t\t\t\t\t\t<ntsc>TRUE</ntsc>\n"
                    + "\t\t\t\t\t\t\t</rate>\n"
                    + "\t\t\t\t\t\t\t<timecode>\n"
                    + "\t\t\t\t\t\t\t\t<rate>\n"
                    + "\t\t\t\t\t\t\t\t\t<timebase>30</timebase>\n"
                    + "\t\t\t\t\t\t\t\t\t<ntsc>TRUE</ntsc>\n"
                    + "\t\t\t\t\t\t\t\t</rate>\n"
                    + "\t\t\t\t\t\t\t\t<string>00;00;00;00</string>\n"
                    + "\t\t\t\t\t\t\t\t<frame>0</frame>\n"
                    + "\t\t\t\t\t\t\t\t<displayformat>DF</displayformat>\n";

            if (!(m instanceof MediaTexte)) {
                xml += "\t\t\t\t\t\t\t\t<reel>\n"
                        + "\t\t\t\t\t\t\t\t\t<name></name>\n"
                        + "\t\t\t\t\t\t\t\t</reel>\n";
            }
            xml += "\t\t\t\t\t\t\t</timecode>\n"
                    + "\t\t\t\t\t\t\t<media>\n"
                    + "\t\t\t\t\t\t\t\t<video>\n"
                    + "\t\t\t\t\t\t\t\t\t<samplecharacteristics>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<rate>\n"
                    + "\t\t\t\t\t\t\t\t\t\t\t<timebase>30</timebase>\n"
                    + "\t\t\t\t\t\t\t\t\t\t\t<ntsc>TRUE</ntsc>\n"
                    + "\t\t\t\t\t\t\t\t\t\t</rate>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<width>" + m.getLargeur() + "</width>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<height>" + m.getHauteur() + "</height>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<anamorphic>FALSE</anamorphic>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<pixelaspectratio>square</pixelaspectratio>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<fielddominance>none</fielddominance>\n"
                    + "\t\t\t\t\t\t\t\t\t</samplecharacteristics>\n"
                    + "\t\t\t\t\t\t\t\t</video>\n"
                    + "\t\t\t\t\t\t\t</media>\n"
                    + "\t\t\t\t\t\t</file>\n";
        }
        m.utiliser();

        // Cas qui n'est pas un élément généré (mire, décompte, etc) :
        if (!m.getTypeMedia().equals("genere")) {
            xml += "\t\t\t\t\t\t<link>\n"
                    + "\t\t\t\t\t\t\t<linkclipref>masterclip-" + m.getId() + "</linkclipref>\n"
                    + "\t\t\t\t\t\t\t<mediatype>video</mediatype>\n"
                    + "\t\t\t\t\t\t\t<trackindex>1</trackindex>\n"
                    + "\t\t\t\t\t\t\t<clipindex>1</clipindex>\n"
                    + "\t\t\t\t\t\t</link>\n";

            // DaVinci Resolve indique le mode composition.
            if (this.logicielDestination == Logiciel.RESOLVE) {
                xml += "\t\t\t\t\t\t<compositemode>normal</compositemode>\n";
            }

            // Ajoute les effets sur le média :
            List<Effect> listeEffet = m.getListeEffect();

            for (Effect effet : listeEffet) {
                xml += effet.toFCP7XML();
            }

            // Cas quand il y a un déplacement :
            xml += "\t\t\t\t\t\t<filter>\n";

            if (this.logicielDestination == Logiciel.RESOLVE) {
                xml += "\t\t\t\t\t\t\t<enabled>TRUE</enabled>\n"
                        + "\t\t\t\t\t\t\t<start>" + m.getStart().toImage() + "</start>\n"
                        + "\t\t\t\t\t\t\t<end>" + m.getStart().toImage() + m.getDuree().toImage() + "</end>\n";
            }

            xml += "\t\t\t\t\t\t\t<effect>\n"
                    + "\t\t\t\t\t\t\t\t<name>Basic Motion</name>\n"
                    + "\t\t\t\t\t\t\t\t<effectid>basic</effectid>\n"
                    + "\t\t\t\t\t\t\t\t<effectcategory>motion</effectcategory>\n"
                    + "\t\t\t\t\t\t\t\t<effecttype>motion</effecttype>\n"
                    + "\t\t\t\t\t\t\t\t<mediatype>video</mediatype>\n"
                    + "\t\t\t\t\t\t\t\t<pproBypass>false</pproBypass>\n"
                    + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                    + "\t\t\t\t\t\t\t\t\t<parameterid>scale</parameterid>\n"
                    + "\t\t\t\t\t\t\t\t\t<name>Scale</name>\n"
                    + "\t\t\t\t\t\t\t\t\t<valuemin>0</valuemin>\n"
                    + "\t\t\t\t\t\t\t\t\t<valuemax>1000</valuemax>\n"
                    + "\t\t\t\t\t\t\t\t\t<value>" + m.getEchelle() + "</value>\n"
                    + "\t\t\t\t\t\t\t\t</parameter>\n"
                    + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                    + "\t\t\t\t\t\t\t\t\t<parameterid>rotation</parameterid>\n"
                    + "\t\t\t\t\t\t\t\t\t<name>Rotation</name>\n"
                    + "\t\t\t\t\t\t\t\t\t<valuemin>-8640</valuemin>\n"
                    + // Rotation ?
                    "\t\t\t\t\t\t\t\t\t<valuemax>8640</valuemax>\n"
                    + // Rotation ?
                    "\t\t\t\t\t\t\t\t\t<value>0</value>\n"
                    + "\t\t\t\t\t\t\t\t</parameter>\n"
                    + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                    + "\t\t\t\t\t\t\t\t\t<parameterid>center</parameterid>\n"
                    + "\t\t\t\t\t\t\t\t\t<name>Center</name>\n"
                    + "\t\t\t\t\t\t\t\t\t<value>\n";
            // Position en X : 0 = centre.
            if (this.logicielDestination == Logiciel.PREMIERE) {
                xml += "\t\t\t\t\t\t\t\t\t\t<horiz>" + new DecimalFormat("#.#########", new DecimalFormatSymbols(Locale.ENGLISH)).format(m.getPositionHorizontale(this.largeur, this.hauteur, this.par)) + "</horiz>\n";
                xml += "\t\t\t\t\t\t\t\t\t\t<vert>" + new DecimalFormat("#.#########", new DecimalFormatSymbols(Locale.ENGLISH)).format(m.getPositionVerticale(this.largeur, this.hauteur, this.par)) + "</vert>\n";
            } else {
                xml += "\t\t\t\t\t\t\t\t\t\t<horiz>" + m.getPositionHorizontale(this.largeur, this.hauteur, this.par) + "</horiz>\n";
                xml += "\t\t\t\t\t\t\t\t\t\t<vert>" + m.getPositionVerticale(this.largeur, this.hauteur, this.par) + "</vert>\n";
            }
            // Position en Y : 0 = centre.
            xml += "\t\t\t\t\t\t\t\t\t</value>\n"
                    + "\t\t\t\t\t\t\t\t</parameter>\n"
                    + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                    + "\t\t\t\t\t\t\t\t\t<parameterid>centerOffset</parameterid>\n"
                    + "\t\t\t\t\t\t\t\t\t<name>Anchor Point</name>\n"
                    + "\t\t\t\t\t\t\t\t\t<value>\n"
                    // Point d'ancrage ?
                    + "\t\t\t\t\t\t\t\t\t\t<horiz>0</horiz>\n"
                    // Point d'ancrage ?
                    + "\t\t\t\t\t\t\t\t\t\t<vert>0</vert>\n"
                    + "\t\t\t\t\t\t\t\t\t</value>\n"
                    + "\t\t\t\t\t\t\t\t</parameter>\n"
                    + "\t\t\t\t\t\t\t</effect>\n"
                    + "\t\t\t\t\t\t</filter>\n"
                    + canauxClip2(((MediaVideo) m).getCanaux());
        } // En cas de fichier généré :
        else {
            // Pour un fichier de texte :
            if (m instanceof MediaTexte mediaText) {
                MediaTexte mTexte = mediaText;

                // Texte d'Adobe CC2023 :
                xml += "\t\t\t\t\t\t<filter>\n"
                        + "\t\t\t\t\t\t\t<effect>\n"
                        + "\t\t\t\t\t\t\t\t<name>" + mTexte.getTexte() + "</name>\n"
                        + "\t\t\t\t\t\t\t\t<effectid>GraphicAndType</effectid>\n"
                        + "\t\t\t\t\t\t\t\t<effectcategory>graphic</effectcategory>\n"
                        + "\t\t\t\t\t\t\t\t<effecttype>filter</effecttype>\n"
                        + "\t\t\t\t\t\t\t\t<mediatype>video</mediatype>\n"
                        + "\t\t\t\t\t\t\t\t<pproBypass>false</pproBypass>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>1</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name>Texte source</name>\n"
                        + "\t\t\t\t\t\t\t\t\t<hash>2d03c7ab-2985-2ec9-45bf-033100000154</hash>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>PAEAAAAAAABEMyIRDAAAAAAABgAKAAQABgAAAGQAAAAAAF4AGAAQAAwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAgAAAAAABcABwBeAAAAAAAAARAAAAAcAAAALAAAAAAAAQBo////bP///3D///90////AQAAAAQAAAAGAAAAVGFob21hAAABAAAADAAAAAgADgAEAAgACAAAAGgAAAA8AAAAAAA2ABQAAAAAAAAAAAAAAAAAEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADAAAAAgABAA2AAAAAgAAAAwAAAAMAAAAAACAQPT////4/////P///wQABAAEAAAACAAAAEJvbmpvdXINAAAAAA==</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>2</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name>Transformation</name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>11</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>false</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>false</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,false,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>3</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name>Position</name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,0.5:0.5,0,0,0,0,0,0,5,4,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>4</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name>Echelle</name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>2</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>0</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>4000</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,100.,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>5</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name>Echelle horizontale</name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>2</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>0</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>4000</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,100.,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>6</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name> </name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>4</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>false</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>true</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,true,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>7</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name>Rotation</name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>3</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>-32768</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>32767</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,0.,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>8</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name>Opacité</name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>2</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>0</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>100</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,100.,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>9</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name>Point d'ancrage</name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,0:0,0,0,0,0,0,0,5,4,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>10</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name></name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>12</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>false</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>false</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,false,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>11</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name> </name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>8</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>0</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>32768</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,0.,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>12</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name> </name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>8</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>0</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>32768</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,0.,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>13</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name>start</name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>8</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>-100</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>1000000000</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,8.,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>14</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name>end</name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>8</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>-100</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>1000000000</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,8.,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>15</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name> </name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>4</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>false</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>true</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,false,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>16</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name> </name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>4</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>false</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>true</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,false,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>17</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name> </name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>4</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>false</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>true</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,false,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>18</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name> </name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>4</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>false</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>true</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,false,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>19</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name>Largeur du parent</name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>2</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>0</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>20000</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,0.,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>20</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name>Hauteur du parent</name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>2</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>0</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>20000</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,0.,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>21</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name>Rotation du parent</name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>3</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>-32768</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>32767</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,0.,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t\t<parameter authoringApp=\"PremierePro\">\n"
                        + "\t\t\t\t\t\t\t\t\t<parameterid>22</parameterid>\n"
                        + "\t\t\t\t\t\t\t\t\t<name> </name>\n"
                        + "\t\t\t\t\t\t\t\t\t<IsTimeVarying>false</IsTimeVarying>\n"
                        + "\t\t\t\t\t\t\t\t\t<ParameterControlType>4</ParameterControlType>\n"
                        + "\t\t\t\t\t\t\t\t\t<LowerBound>false</LowerBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<UpperBound>true</UpperBound>\n"
                        + "\t\t\t\t\t\t\t\t\t<value>-91445760000000000,false,0,0,0,0,0,0</value>\n"
                        + "\t\t\t\t\t\t\t\t</parameter>\n"
                        + "\t\t\t\t\t\t\t</effect>\n"
                        + "\t\t\t\t\t\t</filter>\n";
            }
        }

        // Il se peut que cela soit une image et non une vidéo qu'on doit freezer.
        if (this.logicielDestination == XMLFCP7.Logiciel.RESOLVE && m.isFreeze()) {
            xml += "\t\t\t\t\t\t<filter>\n";
            xml += "\t\t\t\t\t\t\t<enabled>TRUE</enabled>\n";
            xml += "\t\t\t\t\t\t\t<start>-1</start>\n";
            xml += "\t\t\t\t\t\t\t<end>-1</end>\n";
            xml += "\t\t\t\t\t\t\t<effect>\n";
            xml += "\t\t\t\t\t\t\t<name>Time Remap</name>\n";
            xml += "\t\t\t\t\t\t\t<effectid>timeremap</effectid>\n";
            xml += "\t\t\t\t\t\t\t<effecttype>motion</effecttype>\n";
            xml += "\t\t\t\t\t\t\t<mediatype>video</mediatype>\n";
            xml += "\t\t\t\t\t\t\t<effectcategory>motion</effectcategory>\n";
            xml += "\t\t\t\t\t\t\t<parameter>\n";
            xml += "\t\t\t\t\t\t\t<name>speed</name>\n";
            xml += "\t\t\t\t\t\t\t<parameterid>speed</parameterid>\n";
            xml += "\t\t\t\t\t\t\t<value>0</value>\n";
            xml += "\t\t\t\t\t\t\t<valuemin>-10000</valuemin>\n";
            xml += "\t\t\t\t\t\t\t<valuemax>10000</valuemax>\n";
            xml += "\t\t\t\t\t\t\t</parameter>\n";
            xml += "\t\t\t\t\t\t\t<parameter>\n";
            xml += "\t\t\t\t\t\t\t<name>reverse</name>\n";
            xml += "\t\t\t\t\t\t\t<parameterid>reverse</parameterid>\n";
            xml += "\t\t\t\t\t\t\t<value>FALSE</value>\n";
            xml += "\t\t\t\t\t\t\t</parameter>\n";
            xml += "\t\t\t\t\t\t\t<parameter>\n";
            xml += "\t\t\t\t\t\t\t<name>frameblending</name>\n";
            xml += "\t\t\t\t\t\t\t<parameterid>frameblending</parameterid>\n";
            xml += "\t\t\t\t\t\t\t<value>FALSE</value>\n";
            xml += "\t\t\t\t\t\t\t</parameter>\n";
            xml += "\t\t\t\t\t\t\t<parameter>\n";
            xml += "\t\t\t\t\t\t\t<name>variablespeed</name>\n";
            xml += "\t\t\t\t\t\t\t<parameterid>variablespeed</parameterid>\n";
            xml += "\t\t\t\t\t\t\t<value>0</value>\n";
            xml += "\t\t\t\t\t\t\t<valuemin>0</valuemin>\n";
            xml += "\t\t\t\t\t\t\t<valuemax>1</valuemax>\n";
            xml += "\t\t\t\t\t\t\t</parameter>\n";
            xml += "\t\t\t\t\t\t\t<parameter>\n";
            xml += "\t\t\t\t\t\t\t<name>graphdict</name>\n";
            xml += "\t\t\t\t\t\t\t<parameterid>graphdict</parameterid>\n";
            xml += "\t\t\t\t\t\t\t<keyframe>\n";
            xml += "\t\t\t\t\t\t\t<when>0</when>\n";
            xml += "\t\t\t\t\t\t\t<value>0</value>\n";
            xml += "\t\t\t\t\t\t\t<speedvirtualkf>TRUE</speedvirtualkf>\n";
            xml += "\t\t\t\t\t\t\t<speedkfstart>TRUE</speedkfstart>\n";
            xml += "\t\t\t\t\t\t\t</keyframe>\n";
            xml += "\t\t\t\t\t\t\t<keyframe>\n";
            xml += "\t\t\t\t\t\t\t<when>86400</when>\n";
            xml += "\t\t\t\t\t\t\t<value>0</value>\n";
            xml += "\t\t\t\t\t\t\t<speedvirtualkf>TRUE</speedvirtualkf>\n";
            xml += "\t\t\t\t\t\t\t<speedkfin>TRUE</speedkfin>\n";
            xml += "\t\t\t\t\t\t\t</keyframe>\n";
            xml += "\t\t\t\t\t\t\t<keyframe>\n";
            xml += "\t\t\t\t\t\t\t<when>86520</when>\n";
            xml += "\t\t\t\t\t\t\t<value>0</value>\n";
            xml += "\t\t\t\t\t\t\t<speedvirtualkf>TRUE</speedvirtualkf>\n";
            xml += "\t\t\t\t\t\t\t<speedkfout>TRUE</speedkfout>\n";
            xml += "\t\t\t\t\t\t\t</keyframe>\n";
            xml += "\t\t\t\t\t\t\t<keyframe>\n";
            xml += "\t\t\t\t\t\t\t<when>1440001</when>\n";
            xml += "\t\t\t\t\t\t\t<value>1</value>\n";
            xml += "\t\t\t\t\t\t\t<speedvirtualkf>TRUE</speedvirtualkf>\n";
            xml += "\t\t\t\t\t\t\t<speedkfend>TRUE</speedkfend>\n";
            xml += "\t\t\t\t\t\t\t</keyframe>\n";
            xml += "\t\t\t\t\t\t\t<valuemin>0</valuemin>\n";
            xml += "\t\t\t\t\t\t\t<valuemax>0</valuemax>\n";
            xml += "\t\t\t\t\t\t\t<interpolation>\n";
            xml += "\t\t\t\t\t\t\t<name>FCPCurve</name>\n";
            xml += "\t\t\t\t\t\t\t</interpolation>\n";
            xml += "\t\t\t\t\t\t\t</parameter>\n";
            xml += "\t\t\t\t\t\t\t</effect>\n";
            xml += "\t\t\t\t\t\t</filter>\n";
        }

        xml += "\t\t\t\t\t\t<logginginfo>\n"
                + "\t\t\t\t\t\t\t<description></description>\n"
                + "\t\t\t\t\t\t\t<scene></scene>\n"
                + "\t\t\t\t\t\t\t<shottake></shottake>\n"
                + "\t\t\t\t\t\t\t<lognote></lognote>\n"
                + "\t\t\t\t\t\t<good></good>\n"
                + "\t\t\t\t\t\t<originalvideofilename></originalvideofilename>\n"
                + "\t\t\t\t\t\t<originalaudiofilename></originalaudiofilename>\n"
                + "\t\t\t\t\t\t</logginginfo>\n"
                + "\t\t\t\t\t\t<colorinfo>\n"
                + "\t\t\t\t\t\t\t<lut></lut>\n"
                + "\t\t\t\t\t\t\t<lut1></lut1>\n"
                + "\t\t\t\t\t\t\t<asc_sop></asc_sop>\n"
                + "\t\t\t\t\t\t\t<asc_sat></asc_sat>\n"
                + "\t\t\t\t\t\t\t<lut2></lut2>\n"
                + "\t\t\t\t\t\t</colorinfo>\n"
                + "\t\t\t\t\t\t<labels>\n"
                //+ "\t\t\t\t\t\t\t<label>Meilleure prise</label>\n"
                + "\t\t\t\t\t\t\t<label2>" + m.getCouleur().toFCP7XML() + "</label2>\n"
                + "\t\t\t\t\t\t</labels>\n"
                + "\t\t\t\t\t</clipitem>\n";
        return xml;
    }

    /**
     * Ajoute un clip audio dans la timeline.
     *
     * @return Le code XML à ajouter au fichier final.
     */
    private String addItemClipAudio(MediaAudio m, int trackindex, Timecode start, boolean active) {
        clipitem++;
        /*String xml = "\t\t\t\t\t<clipitem id=\"clipitem-" + clipitem + "\" premiereChannelType=\"mono\">\n"
                + "\t\t\t\t\t\t<masterclipid>masterclip-" + m.getId() + "</masterclipid>\n"
                + "\t\t\t\t\t\t<name>" + new File(m.getNomFichier().replace("\\", "/")).getName() + "</name>\n"
                + "\t\t\t\t\t\t<enabled>TRUE</enabled>\n"
                + "\t\t\t\t\t\t<duration>" + m.getDuree().toImage() + "</duration>\n"
                + "\t\t\t\t\t\t<rate>\n"
                + "\t\t\t\t\t\t\t<timebase>" + m.getFramerate() + "</timebase>\n"
                + "\t\t\t\t\t\t\t<ntsc>FALSE</ntsc>\n"
                + "\t\t\t\t\t\t</rate>\n"
                + "\t\t\t\t\t\t<start>" + m.getIn().toImage() + "</start>\n"
                + "\t\t\t\t\t\t<end>" + (m.getIn().toImage() + m.getDuree().toImage()) + "</end>\n"
                + "\t\t\t\t\t\t<in>" + m.getIn().toImage() + "</in>\n"
                + "\t\t\t\t\t\t<out>" + m.getOut().toImage() + "</out>\n"
                + "\t\t\t\t\t\t<pproTicksIn>0</pproTicksIn>\n"
                + "\t\t\t\t\t\t<pproTicksOut>" + (m.getOut().toImage() * 254016000000L / this.framerate) + "</pproTicksOut>\n"
                + "\t\t\t\t\t\t<file id=\"file-1\"/>\n"
                + "\t\t\t\t\t\t<sourcetrack>\n"
                + "\t\t\t\t\t\t\t<mediatype>audio</mediatype>\n"
                + "\t\t\t\t\t\t\t<trackindex>" + trackindex + "</trackindex>\n"
                + "\t\t\t\t\t\t</sourcetrack>\n"
                + "\t\t\t\t\t\t<link>\n"
                + "\t\t\t\t\t\t\t<linkclipref>clipitem-1</linkclipref>\n"
                + "\t\t\t\t\t\t\t<mediatype>video</mediatype>\n"
                + "\t\t\t\t\t\t\t<trackindex>1</trackindex>\n"
                + "\t\t\t\t\t\t\t<clipindex>1</clipindex>\n"
                + "\t\t\t\t\t\t</link>\n"
                + canauxClip2(((MediaVideo) m).getCanaux())
                + "\t\t\t\t\t\t<logginginfo>\n"
                + "\t\t\t\t\t\t\t<description></description>\n"
                + "\t\t\t\t\t\t\t<scene></scene>\n"
                + "\t\t\t\t\t\t\t<shottake></shottake>\n"
                + "\t\t\t\t\t\t\t<lognote></lognote>\n"
                + "\t\t\t\t\t\t</logginginfo>\n"
                + "\t\t\t\t\t\t<labels>\n"
                + "\t\t\t\t\t\t\t<label2>Iris</label2>\n"
                + "\t\t\t\t\t\t</labels>\n"
                + "\t\t\t\t\t</clipitem>\n";*/

        String nomFichier = new File(m.getNomFichier().replace("\\", "/")).getName();

        String xml = "\t\t\t\t\t<clipitem id=\"clipitem-" + clipitem + "\" premiereChannelType=\"mono\">\n"
                + "\t\t\t\t\t\t<masterclipid>masterclip-" + m.getId() + "</masterclipid>\n"
                + "\t\t\t\t\t\t<name>" + new File(m.getNomFichier().replace("\\", "/")).getName() + "</name>\n"
                + "\t\t\t\t\t\t<enabled>" + ((active) ? "TRUE" : "FALSE") + "</enabled>\n"
                + "\t\t\t\t\t\t<duration>" + (m.getDuree().toImage() - 1) + "</duration>\n"
                + "\t\t\t\t\t\t<rate>\n"
                + "\t\t\t\t\t\t\t<timebase>" + m.getFramerate() + "</timebase>\n"
                + "\t\t\t\t\t\t\t<ntsc>FALSE</ntsc>\n"
                + "\t\t\t\t\t\t</rate>\n"
                + "\t\t\t\t\t\t<start>" + start.toImage() + "</start>\n"
                + "\t\t\t\t\t\t<end>" + (start.toImage() + m.getDuree().toImage() - 1) + "</end>\n"
                + "\t\t\t\t\t\t<in>" + m.getIn().toImage() + "</in>\n"
                + "\t\t\t\t\t\t<out>" + m.getOut().toImage() + "</out>\n"
                + "\t\t\t\t\t\t<pproTicksIn>0</pproTicksIn>\n"
                + "\t\t\t\t\t\t<pproTicksOut>" + ((m.getOut().toImage() - 1) * 254016000000L / this.framerate) + "</pproTicksOut>\n"
                + "\t\t\t\t\t\t<file id=\"file-" + m.getId() + "\">\n"
                + "\t\t\t\t\t\t\t<name>" + nomFichier + "</name>\n"
                + "\t\t\t\t\t\t\t<pathurl>" + m.getLocalisation() + "</pathurl>\n"
                + "\t\t\t\t\t\t\t<rate>\n"
                + "\t\t\t\t\t\t\t\t<timebase>" + m.getFramerate() + "</timebase>\n"
                + "\t\t\t\t\t\t\t\t<ntsc>FALSE</ntsc>\n"
                + "\t\t\t\t\t\t\t</rate>\n"
                + "\t\t\t\t\t\t\t<duration>" + m.getDureeFichier().toImage() + "</duration>\n"
                + "\t\t\t\t\t\t\t<timecode>\n"
                + "\t\t\t\t\t\t\t\t<rate>\n"
                + "\t\t\t\t\t\t\t\t\t<timebase>" + m.getFramerate() + "</timebase>\n"
                + "\t\t\t\t\t\t\t\t\t<ntsc>FALSE</ntsc>\n"
                + "\t\t\t\t\t\t\t\t</rate>\n"
                + "\t\t\t\t\t\t\t\t<string>" + m.getStart() + "</string>\n"
                + "\t\t\t\t\t\t\t\t<frame>" + m.getStart().toImage() + "</frame>\n"
                + "\t\t\t\t\t\t\t\t<displayformat>NDF</displayformat>\n"
                + "\t\t\t\t\t\t\t</timecode>\n"
                + "\t\t\t\t\t\t\t<media>\n";

        for (int i = 0; i < m.getNombreCanaux(); i++) {
            xml += "\t\t\t\t\t\t\t\t<audio>\n"
                    + "\t\t\t\t\t\t\t\t\t<samplecharacteristics>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<depth>16</depth>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<samplerate>48000</samplerate>\n"
                    + "\t\t\t\t\t\t\t\t\t</samplecharacteristics>\n"
                    + "\t\t\t\t\t\t\t\t\t<channelcount>1</channelcount>\n"
                    + "\t\t\t\t\t\t\t\t\t<audiochannel>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<sourcechannel>" + (i + 1) + "</sourcechannel>\n"
                    + "\t\t\t\t\t\t\t\t\t</audiochannel>\n"
                    + "\t\t\t\t\t\t\t\t</audio>\n";
        }

        xml += "\t\t\t\t\t\t\t</media>\n"
                + "\t\t\t\t\t\t</file>\n"
                + "\t\t\t\t\t\t<sourcetrack>\n"
                + "\t\t\t\t\t\t\t<mediatype>audio</mediatype>\n"
                + "\t\t\t\t\t\t\t<trackindex>" + m.getNumeroSourceCanal() + "</trackindex>\n"
                + "\t\t\t\t\t\t</sourcetrack>\n"
                /*+ "\t\t\t\t\t\t<link>\n"
                + "\t\t\t\t\t\t\t<linkclipref>clipitem-53</linkclipref>\n"
                + "\t\t\t\t\t\t\t<mediatype>audio</mediatype>\n"
                + "\t\t\t\t\t\t\t<trackindex>1</trackindex>\n"
                + "\t\t\t\t\t\t\t<clipindex>1</clipindex>\n"
                + "\t\t\t\t\t\t\t<groupindex>1</groupindex>\n"
                + "\t\t\t\t\t\t</link>\n"*/
                + "\t\t\t\t\t\t<logginginfo>\n"
                + "\t\t\t\t\t\t\t<description></description>\n"
                + "\t\t\t\t\t\t\t<scene></scene>\n"
                + "\t\t\t\t\t\t\t<shottake></shottake>\n"
                + "\t\t\t\t\t\t\t<lognote></lognote>\n"
                + "\t\t\t\t\t\t\t<good></good>\n"
                + "\t\t\t\t\t\t\t<originalvideofilename></originalvideofilename>\n"
                + "\t\t\t\t\t\t\t<originalaudiofilename></originalaudiofilename>\n"
                + "\t\t\t\t\t\t</logginginfo>\n"
                + "\t\t\t\t\t\t<colorinfo>\n"
                + "\t\t\t\t\t\t\t<lut></lut>\n"
                + "\t\t\t\t\t\t\t<lut1></lut1>\n"
                + "\t\t\t\t\t\t\t<asc_sop></asc_sop>\n"
                + "\t\t\t\t\t\t\t<asc_sat></asc_sat>\n"
                + "\t\t\t\t\t\t\t<lut2></lut2>\n"
                + "\t\t\t\t\t\t</colorinfo>\n"
                + "\t\t\t\t\t\t<labels>\n"
                + "\t\t\t\t\t\t\t<label2>Caribbean</label2>\n"
                + "\t\t\t\t\t\t</labels>\n"
                + "\t\t\t\t\t</clipitem>\n";
        return xml;
    }

    /**
     * Retourne le code XML.
     *
     * @param nb
     * @return Le code XML.
     */
    private String canauxClip2(int nb) {
        String xml = "";

        for (int i = 1; i <= nb; i++) {
            xml += "\t\t\t\t\t\t<link>\n"
                    + "\t\t\t\t\t\t\t<linkclipref>clipitem-" + (1 + i) + "</linkclipref>\n"
                    + "\t\t\t\t\t\t\t<mediatype>audio</mediatype>\n"
                    + "\t\t\t\t\t\t\t<trackindex>" + i + "</trackindex>\n"
                    + "\t\t\t\t\t\t\t<clipindex>1</clipindex>\n"
                    + "\t\t\t\t\t\t</link>\n";
        }

        return xml;
    }

    /**
     *
     * @param nb
     * @param echantillon
     * @param bit
     * @return
     */
    private String canauxClip(int nb, int echantillon, int bit) {
        String xml;

        if (nb == 2) {
            xml = "\t\t\t\t\t\t\t\t<audio>\n"
                    + "\t\t\t\t\t\t\t\t\t<samplecharacteristics>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<depth>" + bit + "</depth>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<samplerate>" + echantillon + "</samplerate>\n"
                    + "\t\t\t\t\t\t\t\t\t</samplecharacteristics>\n"
                    + "\t\t\t\t\t\t\t\t\t<channelcount>1</channelcount>\n"
                    + "\t\t\t\t\t\t\t\t\t<layout>stereo</layout>\n"
                    + "\t\t\t\t\t\t\t\t\t<audiochannel>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<sourcechannel>1</sourcechannel>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<channellabel>left</channellabel>\n"
                    + "\t\t\t\t\t\t\t\t\t</audiochannel>\n"
                    + "\t\t\t\t\t\t\t\t</audio>\n"
                    + "\t\t\t\t\t\t\t\t<audio>\n"
                    + "\t\t\t\t\t\t\t\t\t<samplecharacteristics>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<depth>" + bit + "</depth>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<samplerate>" + echantillon + "</samplerate>\n"
                    + "\t\t\t\t\t\t\t\t\t</samplecharacteristics>\n"
                    + "\t\t\t\t\t\t\t\t\t<channelcount>1</channelcount>\n"
                    + "\t\t\t\t\t\t\t\t\t<layout>stereo</layout>\n"
                    + "\t\t\t\t\t\t\t\t\t<audiochannel>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<sourcechannel>2</sourcechannel>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<channellabel>right</channellabel>\n"
                    + "\t\t\t\t\t\t\t\t\t</audiochannel>\n"
                    + "\t\t\t\t\t\t\t\t</audio>\n";
        } else {
            xml = "\t\t\t\t\t\t\t\t<audio>\n"
                    + "\t\t\t\t\t\t\t\t\t<samplecharacteristics>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<depth>" + bit + "</depth>\n"
                    + "\t\t\t\t\t\t\t\t\t\t<samplerate>" + echantillon + "</samplerate>\n"
                    + "\t\t\t\t\t\t\t\t\t</samplecharacteristics>\n"
                    + "\t\t\t\t\t\t\t\t\t<channelcount>" + nb + "</channelcount>\n"
                    + "\t\t\t\t\t\t\t\t</audio>\n";
        }
        return xml;
    }

    /**
     * TODO : Vérifie que le média ajouté est conforme à la timeline.
     *
     * @param media Média à comparer avec la timeline.
     */
    private void conformiteMedia(MediaVideo media) {
        // On traite une vidéo :
        /* if (media instanceof MediaVideo) {
            if (media.getHauteur() != this.hauteur || media.getLargeur() != this.largeur)
                System.out.println("Alert : Les dimensions du média sont différents de la timeline.");

            if (media.getFramerate() != this.framerate)
                System.out.println("Alert : Le framerate du média est différent de celui de la timeline.");
        }*/
    }

    /**
     * Retourne le framerate de la timeline.
     *
     * @return Framerate.
     */
    public int getFramerate() {
        return this.framerate;
    }

    /**
     * Retourne la hauteur en pixel de la timeline.
     *
     * @return Hauteur en pixel.
     */
    public int getHauteur() {
        return this.hauteur;
    }

    /**
     * Retourne la largeur en pixel de la timeline.
     *
     * @return Largeur en pixel.
     */
    public int getLargeur() {
        return this.largeur;
    }

    /**
     * Retourne le PAR de la timeline.
     *
     * @return Le PAR.
     */
    public double getPAR() {
        return this.par;
    }

    /**
     * Retourne le timecode début de la timeline.
     *
     * @return Timecode début.
     */
    public Timecode getStartTc() {
        return this.startTc;
    }

    /**
     * Code XML pour générer le projet Adobe Premiere.
     *
     * @return Code XML à ajouter dans le fichier projet Adobe Premiere.
     */
    @NotNull
    @NotBlank
    @Override
    public String toFCP7XML() {
        // Informations générales :
        String xml = "\t<sequence id=\"sequence-" + (this.numeroTimeline + 1) + "\" TL.SQAudioVisibleBase=\"0\" TL.SQVideoVisibleBase=\"0\" ";
        xml += "TL.SQVisibleBaseTime=\"0\" TL.SQAVDividerPosition=\"0.5\" TL.SQHideShyTracks=\"0\" ";
        xml += "TL.SQHeaderWidth=\"236\" Monitor.ProgramZoomOut=\"1461057696000000\" ";
        xml += "Monitor.ProgramZoomIn=\"0\" TL.SQTimePerPixel=\"5.6763479296991362\" ";
        xml += "MZ.EditLine=\"" + (this.positionCurseur * 254016000000L / this.framerate) + "\" MZ.Sequence.PreviewFrameSizeHeight=\"" + this.hauteur + "\" "; // MZ.EditLine = où doit se trouver le marqueur.
        xml += "MZ.Sequence.PreviewFrameSizeWidth=\"" + this.largeur + "\" MZ.Sequence.AudioTimeDisplayFormat=\"200\" ";
        xml += "MZ.Sequence.PreviewRenderingClassID=\"1061109567\" MZ.Sequence.PreviewRenderingPresetCodec=\"1096172337\" ";
        xml += "MZ.Sequence.PreviewRenderingPresetPath=\"EncoderPresets\\SequencePreview\\9678af98-a7b7-4bdb-b477-7ac9c8df4a4e\\I-Frame Only MPEG.epr\" ";
        xml += "MZ.Sequence.PreviewUseMaxRenderQuality=\"false\" MZ.Sequence.PreviewUseMaxBitDepth=\"false\" ";
        xml += "MZ.Sequence.EditingModeGUID=\"9678af98-a7b7-4bdb-b477-7ac9c8df4a4e\" ";
        xml += "MZ.Sequence.VideoTimeDisplayFormat=\"100\" MZ.WorkOutPoint=\"1461057696000000\" MZ.WorkInPoint=\"0\" ";
        xml += "MZ.ZeroPoint=\"" + (this.startTc.toImage() * 254016000000L / this.framerate) + "\" explodedTracks=\"true\">\n";
        xml += "\t\t<uuid>" + /*LISTE_UUID[numeroTimeline]*/ UUID.randomUUID().toString() + "</uuid>\n";
        xml += "\t\t<duration>" + this.listeMediaVideo.get(0).getDuree().toImage() + "</duration>\n";
        xml += "\t\t<rate>\n";
        xml += "\t\t\t<timebase>" + this.framerate + "</timebase>\n";
        xml += "\t\t\t<ntsc>FALSE</ntsc>\n";
        xml += "\t\t</rate>\n";
        xml += "\t\t<name>" + this.nom + "</name>\n";

        // Les médias dans la timeline.
        xml += "\t\t<media>\n";

        // La partie image de la timeline:
        xml += "\t\t\t<video>\n"
                + "\t\t\t\t<format>\n"
                + "\t\t\t\t\t<samplecharacteristics>\n"
                + "\t\t\t\t\t\t<rate>\n"
                + "\t\t\t\t\t\t\t<timebase>" + this.framerate + "</timebase>\n"
                + "\t\t\t\t\t\t\t<ntsc>FALSE</ntsc>\n"
                + "\t\t\t\t\t\t</rate>\n"
                + "\t\t\t\t\t\t<codec>\n"
                + "\t\t\t\t\t\t\t<name>Apple ProRes 422</name>\n"
                + "\t\t\t\t\t\t\t<appspecificdata>\n"
                + "\t\t\t\t\t\t\t\t<appname>Final Cut Pro</appname>\n"
                + "\t\t\t\t\t\t\t\t<appmanufacturer>Apple Inc.</appmanufacturer>\n"
                + "\t\t\t\t\t\t\t\t<appversion>7.0</appversion>\n"
                + "\t\t\t\t\t\t\t\t<data>\n"
                + "\t\t\t\t\t\t\t\t\t<qtcodec>\n"
                + "\t\t\t\t\t\t\t\t\t\t<codecname>Apple ProRes 422</codecname>\n"
                + "\t\t\t\t\t\t\t\t\t\t<codectypename>Apple ProRes 422</codectypename>\n"
                + "\t\t\t\t\t\t\t\t\t\t<codectypecode>apcn</codectypecode>\n"
                + "\t\t\t\t\t\t\t\t\t\t<codecvendorcode>appl</codecvendorcode>\n"
                + "\t\t\t\t\t\t\t\t\t\t<spatialquality>1024</spatialquality>\n"
                + "\t\t\t\t\t\t\t\t\t\t<temporalquality>0</temporalquality>\n"
                + "\t\t\t\t\t\t\t\t\t\t<keyframerate>0</keyframerate>\n"
                + "\t\t\t\t\t\t\t\t\t\t<datarate>0</datarate>\n"
                + "\t\t\t\t\t\t\t\t\t</qtcodec>\n"
                + "\t\t\t\t\t\t\t\t</data>\n"
                + "\t\t\t\t\t\t\t</appspecificdata>\n"
                + "\t\t\t\t\t\t</codec>\n"
                + "\t\t\t\t\t\t<width>" + this.largeur + "</width>\n"
                + "\t\t\t\t\t\t<height>" + this.hauteur + "</height>\n"
                + "\t\t\t\t\t\t<anamorphic>FALSE</anamorphic>\n"
                + "\t\t\t\t\t\t<pixelaspectratio>square</pixelaspectratio>\n"
                + "\t\t\t\t\t\t<fielddominance>none</fielddominance>\n"
                + "\t\t\t\t\t\t<colordepth>24</colordepth>\n"
                + "\t\t\t\t\t</samplecharacteristics>\n"
                + "\t\t\t\t</format>\n";

        List<Integer> numPiste = new ArrayList<>();
        int max = 0;

        for (Integer pisteVideo : this.listePisteVideo) {
            if (!numPiste.contains(pisteVideo)) {
                numPiste.add(pisteVideo);

                // Piste max:
                if (pisteVideo > max) {
                    max = pisteVideo;
                }
            }
        }

        // Crée chaque piste vidéo (Track / piste 1) :
        for (int i = 1; i <= max; i++) {
            xml += "\t\t\t\t<track TL.SQTrackShy=\"0\" TL.SQTrackExpandedHeight=\"25\" TL.SQTrackExpanded=\"0\" MZ.TrackTargeted=\"1\">\n";

            for (int j = 0; j < this.listePisteVideo.size(); j++) {
                // Piste actuelle :
                if (this.listePisteVideo.get(j) == i) {
                    if (this.listeMediaVideo.get(j) instanceof MediaVideo mediaVideo) {
                        xml += this.addItemClipVideo(mediaVideo, this.listeTcStartVideo.get(j), this.listeActiveVideo.get(j));
                    }
                }
            }

            xml += "\t\t\t\t\t<enabled>TRUE</enabled>\n"
                    // Si on verrouille la piste vidéo.
                    + "\t\t\t\t\t<locked>" + ((this.listePisteVideoVerrouiller.contains(i) || this.verrouillerPisteVideo) ? "TRUE" : "FALSE") + "</locked>\n"
                    + "\t\t\t\t</track>\n";
        }

        xml += "\t\t\t</video>\n"
                + // La partie audio de la timeline :
                "\t\t\t<audio>\n"
                + "\t\t\t\t<numOutputChannels>" + this.nombreCanaux + "</numOutputChannels>\n"
                + "\t\t\t\t<format>\n"
                + "\t\t\t\t\t<samplecharacteristics>\n"
                + "\t\t\t\t\t\t<depth>16</depth>\n"
                + "\t\t\t\t\t\t<samplerate>48000</samplerate>\n"
                + "\t\t\t\t\t</samplecharacteristics>\n"
                + "\t\t\t\t</format>\n"
                + "\t\t\t\t<outputs>\n"
                + this.outputGroupe(this.nombreCanaux)
                + "\t\t\t\t</outputs>\n";

        List<Integer> numPisteAudio = new ArrayList<>();
        int maxAudio = 0;

        for (Integer pisteAudio : this.listePisteAudio) {
            if (!numPisteAudio.contains(pisteAudio)) {
                numPisteAudio.add(pisteAudio);

                // Piste max:
                if (pisteAudio > maxAudio) {
                    maxAudio = pisteAudio;
                }
            }
        }

        if (maxAudio < this.nombreCanaux) {
            maxAudio = this.nombreCanaux;
        }

        // Crée chaque piste audio :
        for (int i = 1; i <= maxAudio; i++) {
            if (i % 2 == 1) {
                xml += "\t\t\t\t<track monotrack=\"TRUE\" TL.SQTrackAudioKeyframeStyle=\"0\" TL.SQTrackShy=\"0\" TL.SQTrackExpandedHeight=\"25\" TL.SQTrackExpanded=\"0\" MZ.TrackTargeted=\"1\" PannerCurrentValue=\"0\" PannerIsInverted=\"true\" PannerStartKeyframe=\"-91445760000000000,0.,0,0,0,0,0,0\" PannerName=\"Pan\" currentExplodedTrackIndex=\"0\" totalExplodedTrackCount=\"1\" premiereTrackType=\"Mono\">\n";

            } else {
                xml += "\t\t\t\t<track monotrack=\"TRUE\" TL.SQTrackAudioKeyframeStyle=\"0\" TL.SQTrackShy=\"0\" TL.SQTrackExpandedHeight=\"25\" TL.SQTrackExpanded=\"0\" MZ.TrackTargeted=\"1\" PannerIsInverted=\"true\" PannerName=\"Pan\" currentExplodedTrackIndex=\"0\" totalExplodedTrackCount=\"1\" premiereTrackType=\"Mono\">\n";
            }

            for (int j = 0; j < this.listePisteAudio.size(); j++) {
                // Piste actuelle :
                if (this.listePisteAudio.get(j) == i) {
                    if (this.listeMediaAudio.get(j) instanceof MediaAudio mediaAudio) {
                        xml += this.addItemClipAudio(mediaAudio, i, this.listeTcStartAudio.get(j), this.listeActiveAudio.get(j));
                    }
                }
            }

            xml += "\t\t\t\t\t<enabled>TRUE</enabled>\n"
                    + "\t\t\t\t\t<locked>" + ((this.listePisteAudioVerrouiller.contains(i) || this.verrouillerPisteAudio) ? "TRUE" : "FALSE") + "</locked>\n"
                    + "\t\t\t\t\t<outputchannelindex>" + i + "</outputchannelindex>\n"
                    + "\t\t\t\t</track>\n";
        }

        /*+ "\t\t\t\t<track monotrack=\"TRUE\" TL.SQTrackAudioKeyframeStyle=\"0\" TL.SQTrackShy=\"0\" TL.SQTrackExpandedHeight=\"25\" TL.SQTrackExpanded=\"0\" MZ.TrackTargeted=\"1\" PannerCurrentValue=\"0\" PannerIsInverted=\"true\" PannerStartKeyframe=\"-91445760000000000,0.,0,0,0,0,0,0\" PannerName=\"Pan\" currentExplodedTrackIndex=\"0\" totalExplodedTrackCount=\"1\" premiereTrackType=\"Mono\">\n"
                + // Si le fichier vidéo a une piste audio, on l'ajoute:
                ((((MediaVideo) this.listeMediaVideo.get(0)).getCanaux() >= 1) ? addItemClipAudio((MediaVideo) this.listeMediaVideo.get(0), 1) : "")
                + "\t\t\t\t\t<enabled>TRUE</enabled>\n"
                + "\t\t\t\t\t<locked>" + ((this.listePisteAudioVerrouiller.contains(1) || this.verrouillerPisteAudio) ? "TRUE" : "FALSE") + "</locked>\n"
                + "\t\t\t\t\t<outputchannelindex>1</outputchannelindex>\n"
                + "\t\t\t\t</track>\n"
                + "\t\t\t\t<track monotrack=\"TRUE\" TL.SQTrackAudioKeyframeStyle=\"0\" TL.SQTrackShy=\"0\" TL.SQTrackExpandedHeight=\"25\" TL.SQTrackExpanded=\"0\" MZ.TrackTargeted=\"1\" PannerIsInverted=\"true\" PannerName=\"Pan\" currentExplodedTrackIndex=\"0\" totalExplodedTrackCount=\"1\" premiereTrackType=\"Mono\">\n"
                + // Si le fichier vidéo a au moins 2 pistes audio, on l'ajoute:
                ((((MediaVideo) this.listeMediaVideo.get(0)).getCanaux() >= 2) ? addItemClipAudio((MediaVideo) this.listeMediaVideo.get(0), 2) : "")
                + "\t\t\t\t\t<enabled>TRUE</enabled>\n"
                + "\t\t\t\t\t<locked>" + ((this.listePisteAudioVerrouiller.contains(2) || this.verrouillerPisteAudio) ? "TRUE" : "FALSE") + "</locked>\n"
                + "\t\t\t\t\t<outputchannelindex>2</outputchannelindex>\n"
                + "\t\t\t\t</track>\n"
                + "\t\t\t\t<track monotrack=\"TRUE\" TL.SQTrackAudioKeyframeStyle=\"0\" TL.SQTrackShy=\"0\" TL.SQTrackExpandedHeight=\"25\" TL.SQTrackExpanded=\"0\" MZ.TrackTargeted=\"1\" PannerCurrentValue=\"0\" PannerIsInverted=\"true\" PannerStartKeyframe=\"-91445760000000000,0.,0,0,0,0,0,0\" PannerName=\"Pan\" currentExplodedTrackIndex=\"0\" totalExplodedTrackCount=\"1\" premiereTrackType=\"Mono\">\n"
                + // Si le fichier vidéo a au moins 3 pistes audio, on l'ajoute:
                ((((MediaVideo) this.listeMediaVideo.get(0)).getCanaux() >= 3) ? addItemClipAudio((MediaVideo) this.listeMediaVideo.get(0), 3) : "")
                + "\t\t\t\t\t<enabled>TRUE</enabled>\n"
                + "\t\t\t\t\t<locked>" + ((this.listePisteAudioVerrouiller.contains(3) || this.verrouillerPisteAudio) ? "TRUE" : "FALSE") + "</locked>\n"
                + "\t\t\t\t\t<outputchannelindex>3</outputchannelindex>\n"
                + "\t\t\t\t</track>\n"
                + "\t\t\t\t<track monotrack=\"TRUE\" TL.SQTrackAudioKeyframeStyle=\"0\" TL.SQTrackShy=\"0\" TL.SQTrackExpandedHeight=\"25\" TL.SQTrackExpanded=\"0\" MZ.TrackTargeted=\"1\" PannerIsInverted=\"true\" PannerName=\"Pan\" currentExplodedTrackIndex=\"0\" totalExplodedTrackCount=\"1\" premiereTrackType=\"Mono\">\n"
                + // Si le fichier vidéo a au moins 4 pistes audio, on l'ajoute:
                ((((MediaVideo) this.listeMediaVideo.get(0)).getCanaux() >= 4) ? addItemClipAudio((MediaVideo) this.listeMediaVideo.get(0), 4) : "")
                + "\t\t\t\t\t<enabled>TRUE</enabled>\n"
                + "\t\t\t\t\t<locked>" + ((this.listePisteAudioVerrouiller.contains(4) || this.verrouillerPisteAudio) ? "TRUE" : "FALSE") + "</locked>\n"
                + "\t\t\t\t\t<outputchannelindex>4</outputchannelindex>\n"
                + "\t\t\t\t</track>\n"*/
        xml += "\t\t\t</audio>\n";

        xml += "\t\t</media>\n";

        // Information de timecode:
        xml += "\t\t<timecode>\n";
        xml += "\t\t\t<rate>\n";
        xml += "\t\t\t\t<timebase>" + this.framerate + "</timebase>\n";
        xml += "\t\t\t\t<ntsc>FALSE</ntsc>\n";
        xml += "\t\t\t</rate>\n";
        xml += "\t\t\t<string>" + this.startTc + "</string>\n";
        xml += "\t\t\t<frame>" + this.startTc.toImage() + "</frame>\n";
        xml += "\t\t\t<displayformat>NDF</displayformat>\n";
        xml += "\t\t</timecode>\n";

        // Les marques :
        for (Marqueur marqueur : this.listeMarqueur) {
            xml += this.addMarqueurTimeline(marqueur);
        }

        xml += "\t\t<labels>\n";
        xml += "\t\t\t<label2>Forest</label2>\n";
        xml += "\t\t</labels>\n";
        xml += "\t</sequence>\n";

        return xml;
    }

    /**
     *
     * @param nb
     * @return
     */
    @NotNull
    private String outputGroupe(int nb) {
        String xml = "";

        for (int i = 1; i <= nb; i++) {
            xml += "\t\t\t\t\t<group>\n"
                    + "\t\t\t\t\t\t<index>" + i + "</index>\n"
                    + "\t\t\t\t\t\t<numchannels>1</numchannels>\n"
                    + "\t\t\t\t\t\t<downmix>0</downmix>\n"
                    + "\t\t\t\t\t\t<channel>\n"
                    + "\t\t\t\t\t\t\t<index>" + i + "</index>\n"
                    + "\t\t\t\t\t\t</channel>\n"
                    + "\t\t\t\t\t</group>\n";
        }

        return xml;
    }

    /**
     * Modifie le nombre de canaux audios.
     *
     * @param nombreCanaux Nombre de canaux audio.
     */
    public void setCanaux(int nombreCanaux) {
        this.nombreCanaux = nombreCanaux;
    }

    /**
     * Définit les dimensions de la timeline.
     *
     * @param largeur La largeur.
     * @param hauteur La hauteur.
     */
    public void setDimension(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    /**
     * Modifie à quel logiciel est destiné la timeline.
     *
     * @param logicielDestination Logiciel auquel est destiné la timeline.
     */
    public void setLogicielDestination(Logiciel logicielDestination) {
        this.logicielDestination = logicielDestination;
    }

    /**
     * Modifie le PAR.
     *
     * @param par PAR.
     */
    public void setPAR(float par) {
        this.par = par;
    }

    /**
     * Définit la position du curseur en image dans la timeline.
     *
     * @param positionCurseur Position en image du curseur.
     */
    public void setPositionCurseur(int positionCurseur) {
        this.positionCurseur = positionCurseur;
    }

    /**
     * Modifie le timecode de début de la timeline.
     *
     * @param startTc Le timecode de début.
     */
    public void setStart(@NotNull Timecode startTc) {
        this.startTc = startTc;

        if (this.framerate != 0) {
            this.startTc.setFramerate(this.framerate);
        } // Sinon, on affecte le framerate du timecode (s'il en a un) à média.
        else {
            this.framerate = (int) this.startTc.getFramerate();
        }
    }

    /**
     * Verrouille toutes les pistes Audio.
     */
    public void verrouillerPisteAudio() {
        this.verrouillerPisteAudio = true;
    }

    /**
     * Verrouille (ou déverrouille) une piste audio.
     *
     * @param numeroPiste Numéro de piste audio.
     */
    public void verrouillerPisteAudio(int numeroPiste) {
        this.verrouillerPisteAudio(numeroPiste, true);
    }

    /**
     * Verrouille (ou déverrouille) une piste audio.
     *
     * @param numeroPiste Numéro de piste audio.
     * @param verrouiller Verrouille la piste audio si {@code true}.
     */
    public void verrouillerPisteAudio(int numeroPiste, boolean verrouiller) {
        if (verrouiller) {
            this.listePisteAudioVerrouiller.add(numeroPiste);
        } else {
            this.listePisteAudioVerrouiller.remove(numeroPiste);
        }
    }

    /**
     * Verrouille toutes les pistes vidéos.
     */
    public void verrouillerPisteVideo() {
        this.verrouillerPisteVideo = true;
    }

    /**
     * Indique un numéro de piste vidéo qu'on veut verrouiller dans le projet du
     * NLE.
     *
     * @param numeroPiste Numéro de piste vidéo.
     */
    public void verrouillerPisteVideo(int numeroPiste) {
        this.verrouillerPisteVideo(numeroPiste, true);
    }

    /**
     * Verrouille (ou déverrouille) une piste vidéo.
     *
     * @param numeroPiste Numéro de piste vidéo.
     * @param verrouiller Verrouille la piste vidéo si {@code true}.
     */
    public void verrouillerPisteVideo(int numeroPiste, boolean verrouiller) {
        if (verrouiller) {
            this.listePisteVideoVerrouiller.add(numeroPiste);
        } else {
            this.listePisteVideoVerrouiller.remove(numeroPiste);
        }
    }
}
