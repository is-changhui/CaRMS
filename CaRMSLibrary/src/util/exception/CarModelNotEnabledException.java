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
public class CarModelNotEnabledException extends Exception {

    /**
     * Creates a new instance of <code>CarModelNotEnabledException</code>
     * without detail message.
     */
    public CarModelNotEnabledException() {
    }

    /**
     * Constructs an instance of <code>CarModelNotEnabledException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CarModelNotEnabledException(String msg) {
        super(msg);
    }
}
