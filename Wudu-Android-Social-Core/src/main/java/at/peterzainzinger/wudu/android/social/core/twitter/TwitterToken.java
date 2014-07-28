package at.peterzainzinger.wudu.android.social.core.twitter;

import at.peterzainzinger.wudu.android.social.core.SocialToken;
import twitter4j.auth.AccessToken;

/**
 * Created by peterzainzinger on 24/04/14.
 */
public class TwitterToken implements SocialToken {

    private String token;
    private String tokenSecret;

    public TwitterToken(String token){

        this.token = token;
    }

    public TwitterToken(AccessToken token){

        assert token!=null;
        this.token = token.getToken();
        this.tokenSecret=token.getTokenSecret();


    }


    @Override
    public String getToken() {
        return token;
    }
}
