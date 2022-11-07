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
public class CarPickupRecordExistException extends Exception {

    /**
     * Creates a new instance of <code>CarPickupRecordExistException</code>
     * without detail message.
     */
    public CarPickupRecordExistException() {
    }

    /**
     * Constructs an instance of <code>CarPickupRecordExistException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CarPickupRecordExistException(String msg) {
        super(msg);
    }
}
