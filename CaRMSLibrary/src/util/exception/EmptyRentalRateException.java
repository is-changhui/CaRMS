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
public class EmptyRentalRateException extends Exception {

    /**
     * Creates a new instance of <code>EmptyRentalRateException</code> without
     * detail message.
     */
    public EmptyRentalRateException() {
    }

    /**
     * Constructs an instance of <code>EmptyRentalRateException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EmptyRentalRateException(String msg) {
        super(msg);
    }
}
