package at.peterzainzinger.wudu.android.social.core.twitter;

import at.peterzainzinger.wudu.android.social.core.SocialToken;
import twitter4j.auth.AccessToken;

/**
 * Created by peterzainzinger on 24/04/14.
 */
public class TwitterToken implements SocialToken {

    public String token;

    public TwitterToken(String token){

        this.token = token;
    }

    public TwitterToken(AccessToken token){

        assert token!=null;
        this.token = token.getToken();


    }


    @Override
    public String getToken() {
        return token;
    }
}
