package at.peterzainzinger.wudu.android.social.core.twitter;

import at.peterzainzinger.wudu.android.social.core.SocialError;

/**
 * Created by peterzainzinger on 24/04/14.
 */
public class TwitterError implements SocialError {


    private String message;

    public TwitterError(String message){

        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
