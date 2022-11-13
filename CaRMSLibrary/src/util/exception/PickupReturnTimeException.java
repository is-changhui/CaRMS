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
public class PickupReturnTimeException extends Exception {

    /**
     * Creates a new instance of <code>PickupReturnTimeException</code> without
     * detail message.
     */
    public PickupReturnTimeException() {
    }

    /**
     * Constructs an instance of <code>PickupReturnTimeException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PickupReturnTimeException(String msg) {
        super(msg);
    }
}
