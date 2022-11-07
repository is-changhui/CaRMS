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
public class CarReturnRecordExistException extends Exception {

    /**
     * Creates a new instance of <code>CarReturnRecordExistException</code>
     * without detail message.
     */
    public CarReturnRecordExistException() {
    }

    /**
     * Constructs an instance of <code>CarReturnRecordExistException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CarReturnRecordExistException(String msg) {
        super(msg);
    }
}
