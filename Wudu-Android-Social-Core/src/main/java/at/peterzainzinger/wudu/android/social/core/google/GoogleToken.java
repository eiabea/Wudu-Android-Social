package at.peterzainzinger.wudu.android.social.core.google;

import at.peterzainzinger.wudu.android.social.core.SocialToken;

/**
 * Created by peterzainzinger on 25/04/14.
 */
public class GoogleToken implements SocialToken {

    String token;

    public GoogleToken(String token){

        super();
        this.token = token;

    }


    @Override
    public String getToken() {
        return token;
    }
}
