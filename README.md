# XML FCP7
Librairie qui gère l'écriture d'XML Final Cut Pro 7.

# Comment l'utiliser ?
Instancier la classe :
```java
import com.phenix.xmlfcp7.XMLFCP7;
```

Exemple :
```java
import com.phenix.xmlfcp7.Dossier;
import com.phenix.xmlfcp7.XMLFCP7;
import com.phenix.xmlfcp7.XMLFCP7.Logiciel;
import com.phenix.xmlfcp7.XMLFCP7.Mode;

void main(String[] args) {
    XMLFCP7 xml = new XMLFCP7(new File("timeline-fcp7.xml"), Mode.LECTURE, Logiciel.RESOLVE);
    
    // On peut, par exemple, ajouter un dossier :
    Dossier dossier = new Dossier("DOSSIER", Dossier.CouleurAdobe.CARAIBE);
    xml.addDossier(dossier);

    // A la fin, on ferme pour écrire le fichier.
    xml.close();
}
```