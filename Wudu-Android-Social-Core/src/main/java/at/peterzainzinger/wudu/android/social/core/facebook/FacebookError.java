package at.peterzainzinger.wudu.android.social.core.facebook;

import at.peterzainzinger.wudu.android.social.core.SocialError;

/**
 * Created by peterzainzinger on 24/04/14.
 */
public class FacebookError implements SocialError {

    /**
     *
     */
    private String message;


    public FacebookError(String message){

        this.message = message;
    }

    /**
     *
     * @return
     */
    @Override
    public String getMessage() {
        return message;
    }
}
