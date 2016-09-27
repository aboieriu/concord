package concord.http;

/**
 * Created by aboieriu on 9/15/16.
 */
public class NoConnectionContext extends RuntimeException {
    public NoConnectionContext(){
        super("Unable to obtain connection context");
    }
}
