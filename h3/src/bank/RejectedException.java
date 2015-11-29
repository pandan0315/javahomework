/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.sql.SQLException;

/**
 *
 * @author danpan
 */
public class RejectedException extends Exception{
    
    private static final long serialVersionUID = 7548201295632925914L;
    public RejectedException(String reason) {
        super(reason);
    
}

    public RejectedException(String reason, Throwable rootCause) {
        super(reason, rootCause);
    }
    
}
    
