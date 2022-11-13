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
public class PartnerCustomerNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>PartnerCustomerNotFoundException</code>
     * without detail message.
     */
    public PartnerCustomerNotFoundException() {
    }

    /**
     * Constructs an instance of <code>PartnerCustomerNotFoundException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public PartnerCustomerNotFoundException(String msg) {
        super(msg);
    }
}
