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
public class RentalRateRecordExistException extends Exception {

    /**
     * Creates a new instance of <code>RentalRateRecordExistException</code>
     * without detail message.
     */
    public RentalRateRecordExistException() {
    }

    /**
     * Constructs an instance of <code>RentalRateRecordExistException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public RentalRateRecordExistException(String msg) {
        super(msg);
    }
}
