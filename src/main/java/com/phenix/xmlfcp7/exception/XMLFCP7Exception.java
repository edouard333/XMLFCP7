package com.phenix.xmlfcp7.exception;

import jakarta.validation.constraints.NotNull;

/**
 * Exception de base pour toutes les erreurs survenant dans le projet.<br>
 * <br>
 * Toutes les exceptions spécifiques doivent hériter de cette classe.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class XMLFCP7Exception extends Exception {

    /**
     * Construit une {@link XMLFCP7Exception} avec un messag et une cause.
     *
     * @param message Le message.
     * @param cause La cause.
     */
    public XMLFCP7Exception(String message, @NotNull Throwable cause) {
        super(message, cause);
    }
}
