package bank;

/**
 * Created by danpan on 21/11/15.
 */
public class RejectedException extends Exception {
    private static final long serialVersionUID = -314439670131687936L;

    public RejectedException(String reason) {
        super(reason);
    }

}
