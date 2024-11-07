package com.phenix.xmlfcp7.effect;

/**
 * Effet Lumetri.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class Lumetri extends Effect {

    /**
     * Valeur du blanc.
     */
    private int blanc;

    /**
     * Construit l'effet {@code Lumetri}.
     */
    public Lumetri() {
        this.blanc = 0;
    }

    /**
     * Retourne le blanc.
     *
     * @return Le blanc.
     */
    public int getBlanc() {
        return this.blanc;
    }

    /**
     * Définit le blanc.
     *
     * @param blanc Valeur du blanc.
     */
    public void setBlanc(int blanc) {
        this.blanc = blanc;
    }

    @Override
    public String toString() {
        return "<filter>\n"
                + "<effect>\n"
                + "	<name></name>\n"
                + "	<effectid>Lumetri</effectid>\n"
                + "	<effecttype>filter</effecttype>\n"
                + "	<mediatype>video</mediatype>\n"
                + "	<pproBypass>false</pproBypass>\n"
                + "	<parameter authoringApp=\"PremierePro\">\n"
                + "		<parameterid>1</parameterid>\n"
                + "		<name>Blob</name>\n"
                + "		<hash>63866a15-9327-e4cb-ff31-c9ea0000000e</hash>\n"
                + "		<value>/v4=</value>\n"
                + "	</parameter>\n"
                + "	<parameter authoringApp=\"PremierePro\">\n"
                + "		<parameterid>15</parameterid>\n"
                + "		<name>Blancs</name>\n"
                + "		<IsTimeVarying>false</IsTimeVarying>\n"
                + "		<ParameterControlType>8</ParameterControlType>\n"
                + "		<LowerBound>-150</LowerBound>\n"
                + "		<UpperBound>150</UpperBound>\n"
                + "		<LowerUIBound>-100</LowerUIBound>\n"
                + "		<UpperUIBound>100</UpperUIBound>\n"
                + "		<value>-91445760000000000," + blanc + ".,0,0,0,0,0,0</value>\n"
                + "	</parameter>\n"
                + "</effect>\n"
                + "</filter>\n";
    }
}
