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
public class UpdateCarModelException extends Exception {

    /**
     * Creates a new instance of <code>UpdateCarModelException</code> without
     * detail message.
     */
    public UpdateCarModelException() {
    }

    /**
     * Constructs an instance of <code>UpdateCarModelException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UpdateCarModelException(String msg) {
        super(msg);
    }
}
