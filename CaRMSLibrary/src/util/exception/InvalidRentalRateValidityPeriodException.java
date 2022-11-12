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
public class InvalidRentalRateValidityPeriodException extends Exception {

    /**
     * Creates a new instance of
     * <code>InvalidRentalRateValidityPeriodException</code> without detail
     * message.
     */
    public InvalidRentalRateValidityPeriodException() {
    }

    /**
     * Constructs an instance of
     * <code>InvalidRentalRateValidityPeriodException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public InvalidRentalRateValidityPeriodException(String msg) {
        super(msg);
    }
}
