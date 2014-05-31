package at.peterzainzinger.wudu.android.social.core.google;

import at.peterzainzinger.wudu.android.social.core.SocialError;

/**
 * Created by peterzainzinger on 25/04/14.
 */
public class GoogleError implements SocialError {


    private String message;

    public GoogleError(String message){

        super();
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
