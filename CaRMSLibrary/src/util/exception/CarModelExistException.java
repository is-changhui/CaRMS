/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author denzelshemaiah
 */
public class CarModelExistException extends Exception {

    /**
     * Creates a new instance of <code>CarModelExistException</code> without
     * detail message.
     */
    public CarModelExistException() {
    }

    /**
     * Constructs an instance of <code>CarModelExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CarModelExistException(String msg) {
        super(msg);
    }
}