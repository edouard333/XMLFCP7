# XMLFCP7
Librairie qui gère l'écriture d'XML Final Cut Pro 7.

# Comment l'utiliser ?
Utiliser la classe [`com.phenix.xmlfcp7.XMLFCP7`](src/main/java/com/phenix/xmlfcp7/XMLFCP7.java).
```java
XMLFCP7 xml = new XMLFCP7(new File("timeline-fcp7.xml"), Mode.LECTURE, Logiciel.RESOLVE);
xml.close();
```