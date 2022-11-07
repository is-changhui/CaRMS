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
public class UpdateRentalRateRecordException extends Exception {

    /**
     * Creates a new instance of <code>UpdateRentalRateRecordException</code>
     * without detail message.
     */
    public UpdateRentalRateRecordException() {
    }

    /**
     * Constructs an instance of <code>UpdateRentalRateRecordException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public UpdateRentalRateRecordException(String msg) {
        super(msg);
    }
}
