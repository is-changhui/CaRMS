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
public class TransitDriverOutletMismatchException extends Exception {

    /**
     * Creates a new instance of
     * <code>TransitDriverOutletMismatchException</code> without detail message.
     */
    public TransitDriverOutletMismatchException() {
    }

    /**
     * Constructs an instance of
     * <code>TransitDriverOutletMismatchException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public TransitDriverOutletMismatchException(String msg) {
        super(msg);
    }
}
