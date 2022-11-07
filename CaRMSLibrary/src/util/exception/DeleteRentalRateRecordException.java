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
public class DeleteRentalRateRecordException extends Exception {

    /**
     * Creates a new instance of <code>DeleteRentalRateRecordException</code>
     * without detail message.
     */
    public DeleteRentalRateRecordException() {
    }

    /**
     * Constructs an instance of <code>DeleteRentalRateRecordException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public DeleteRentalRateRecordException(String msg) {
        super(msg);
    }
}
