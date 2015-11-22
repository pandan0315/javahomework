package bank;

/**
 * Created by danpan on 22/11/15.
 */
@SuppressWarnings("serial")
public class RejectedException extends Exception {

    private static final long serialVersionUID = 7548201295632925914L;

    public RejectedException(String reason) {
        super(reason);


    }
}
