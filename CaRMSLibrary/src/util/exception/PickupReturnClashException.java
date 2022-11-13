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
public class PickupReturnClashException extends Exception {

    /**
     * Creates a new instance of <code>PickupReturnClashException</code> without
     * detail message.
     */
    public PickupReturnClashException() {
    }

    /**
     * Constructs an instance of <code>PickupReturnClashException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public PickupReturnClashException(String msg) {
        super(msg);
    }
}
