/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Darie
 */
public class PartnerExistException extends Exception {

    /**
     * Creates a new instance of <code>PartnerExistException</code> without
     * detail message.
     */
    public PartnerExistException() {
    }

    /**
     * Constructs an instance of <code>PartnerExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PartnerExistException(String msg) {
        super(msg);
    }
}
