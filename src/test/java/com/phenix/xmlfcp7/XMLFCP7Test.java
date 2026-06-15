/*
 * Licence Studio l'Equipe.
 */
package com.phenix.xmlfcp7;

import com.phenix.timecode.Framerate;
import com.phenix.timecode.Timecode;
import com.phenix.xmlfcp7.enums.Alpha;
import com.phenix.xmlfcp7.enums.Balayage;
import com.phenix.xmlfcp7.enums.CouleurMarqueur;
import com.phenix.xmlfcp7.enums.CouleurMedia;
import com.phenix.xmlfcp7.enums.Trame;
import com.phenix.xmlfcp7.exception.XMLFCP7Exception;
import jakarta.validation.constraints.NotNull;
import java.io.File;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests de la classe {@link XMLFCP7}.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class XMLFCP7Test {

    /**
     * Chemin d'où se trouvent les fichiers TXT.
     */
    @NotNull
    private static final Path CHEMIN_DOSSIER_SORTIE_XML_FCP7 = Path.of("target", "sortie", "xml-fcp7");

    /**
     * Dossier où se trouve les fichiers TXT.
     */
    @NotNull
    private static final File DOSSIER_SORTIE_XML_FCP7 = CHEMIN_DOSSIER_SORTIE_XML_FCP7.toFile();

    /**
     * Lance les tests.
     */
    public XMLFCP7Test() {
    }

    /**
     * Ce qui se passe avant tous les tests.
     */
    @BeforeAll
    public static void setUpClass() {
        // Si le dossier de destination existe, on le supprime.
        if (DOSSIER_SORTIE_XML_FCP7.exists()) {
            deleteContent(DOSSIER_SORTIE_XML_FCP7);
        }

        // Crée le dossier du projet.
        boolean resultat = DOSSIER_SORTIE_XML_FCP7.mkdirs();
        
        assertTrue(resultat, "Le dossier de destination n'a pas pu être créé " + DOSSIER_SORTIE_XML_FCP7.getAbsolutePath() + ".");
    }

    /**
     * Ce qui se passe après tous les tests.
     */
    @AfterAll
    public static void tearDownClass() {
    }

    /**
     * Exécuter avant chaque test.
     */
    @BeforeEach
    public void setUp() {
    }

    /**
     * Exécute après chaque test.
     */
    @AfterEach
    public void tearDown() {
    }

    /**
     * Supprime tout le contenu d'un dossier.
     *
     * @param dossier Le dossier à supprimer.
     */
    private static void deleteContent(@NotNull File dossier) {
        for (File fichier : dossier.listFiles()) {
            if (fichier.isDirectory()) {
                deleteContent(fichier);
            } else {
                fichier.delete();
            }
        }

        // Et puis supprime le dossier.
        dossier.delete();
    }

    /**
     * On crée un XML Final Cut Pro 7.
     *
     * @throws XMLFCP7Exception
     */
    @Test
    public void testXMLFCP7() throws XMLFCP7Exception {
        File fichierXml = new File(CHEMIN_DOSSIER_SORTIE_XML_FCP7 + File.separator + "export-XML-empty.xml");
        // Vérifie que le XML  n'existe pas.
        assertFalse(fichierXml.exists(), "L'XML Final Cut Pro 7 ne doit pas exister.");
        
        XMLFCP7 xml = new XMLFCP7(fichierXml);
        xml.save();
    }

    /**
     * On crée un XML Final Cut Pro 7 avec des métadonnées.
     *
     * @throws XMLFCP7Exception
     */
    @Test
    public void testXMLFCP7WithData() throws XMLFCP7Exception {
        File fichierXml = new File(CHEMIN_DOSSIER_SORTIE_XML_FCP7 + File.separator + "export-txt-protools.txt");
        // Vérifie que le XML  n'existe pas.
        assertFalse(fichierXml.exists(), "L'XML Final Cut Pro 7 ne doit pas exister.");
        
        XMLFCP7 xml = new XMLFCP7(fichierXml);
        xml.setTitreProjet("Titre du projet");
        
        Dossier dossier1 = new Dossier("Nom de dossier", Dossier.CouleurAdobe.CERULEEN);
        dossier1.addDossier(new Dossier("Sous-dossier"));
        xml.addDossier(dossier1);
        
        Dossier dossier2 = new Dossier("Dossier");
        dossier2.setNom("Nouveau nom");
        dossier2.setCouleur(Dossier.CouleurAdobe.FORET);
        xml.addDossier(dossier2);
        
        MediaAudio mediaAudio = new MediaAudio("nom-fichier-audio.wav");
        mediaAudio.setStart(new Timecode("01:00:00:00"));
        mediaAudio.setIn(new Timecode("01:00:00:00"));
        mediaAudio.setOut(new Timecode("02:00:00:00"));
        
        xml.addMedia(mediaAudio);
        
        MediaImage mediaImage = new MediaImage("nom-fichier-image.mov");
        mediaImage.setCouleur(CouleurMedia.BLEU);
        mediaImage.setId(589);
        mediaImage.setDureeFichier(new Timecode("01:30:00:00"));
        mediaImage.setIn(new Timecode("01:10:00:00"));
        mediaImage.setOut(new Timecode("01:30:00:00"));
        
        xml.addMedia(mediaImage);
        
        Timeline timeline = new Timeline("Timeline 1");
        timeline.setPAR(1);
        timeline.setStart(new Timecode("01:00:00:00", Framerate.F24));
        timeline.setCanaux(4);
        timeline.setPositionCurseur(0);
        timeline.setDimension(1920, 1080);
        
        timeline.addMarqueur(new Marqueur("Note", new Timecode("01:02:00:00"), null, CouleurMarqueur.BLANC, "Nom"));
        
        Marqueur m = new Marqueur("Note 2", 24);
        m.setIn(new Timecode("02:00:00:00"));
        m.setOut(new Timecode("02:01:00:00"));
        m.setCouleur(CouleurMarqueur.ROUGE);
        m.setFramerate(24);
        m.setNote("It's note.");
        m.setNom("Nom");
        
        timeline.addMarqueur(m);
        
        Marqueur m2 = new Marqueur();
        
        timeline.addMedia(mediaAudio);
        
        MediaVideo mediaVideo = new MediaVideo("nom-fichier-video.mov");
        mediaVideo.setStart(new Timecode("01:30:00:00"));
        mediaVideo.setBalayage(Balayage.PROGRESSIF, Trame.AUCUNE);
        mediaVideo.setCouleur(CouleurMedia.BLEU);
        mediaVideo.setId(589);
        mediaVideo.setDureeFichier(new Timecode("01:30:00:00"));
        mediaVideo.setIn(new Timecode("01:10:00:00"));
        mediaVideo.setOut(new Timecode("01:30:00:00"));
        timeline.addMedia(mediaVideo);
        
        MediaVideo mediaVideo2 = new MediaVideo("nom-fichier-image.mov");
        mediaVideo2.setStart(new Timecode("01:30:00:00"));
        mediaVideo2.setBalayage(Balayage.PROGRESSIF, Trame.AUCUNE);
        mediaVideo2.setDureeFichier(new Timecode("01:30:00:00"));
        mediaVideo2.setIn(new Timecode("01:10:00:00"));
        mediaVideo2.setOut(new Timecode("01:30:00:00"));
        mediaVideo2.setFreeze(true);
        mediaVideo2.setPAR(1);
        mediaVideo2.setEchelle(60);
        mediaVideo2.setCanaux(0);
        mediaVideo2.setAlpha(Alpha.NONE);
        mediaVideo2.setPosition(960, 200);
        mediaVideo2.setDimension(500, 500);
        timeline.addMedia(mediaVideo2);
        
        MediaVideo mediaVideo3 = new MediaVideo("nom-fichier-image2.mov", 24);
        
        xml.addTimeline(timeline);
        
        xml.save();
    }
}
