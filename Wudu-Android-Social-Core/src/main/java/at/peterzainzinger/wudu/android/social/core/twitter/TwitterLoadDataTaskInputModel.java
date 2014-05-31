package at.peterzainzinger.wudu.android.social.core.twitter;

import twitter4j.Twitter;
import twitter4j.auth.RequestToken;

/**
 * Created by peterzainzinger on 25/04/14.
 */
public class TwitterLoadDataTaskInputModel {

    /**
     *
     */
    private Twitter twitter;


    /**
     *
     */
    private RequestToken requestToken;


    /**
     *
     */

    private String verifier;


    /**
     *
     * @param twitter
     * @param token
     */
    public TwitterLoadDataTaskInputModel(Twitter twitter,RequestToken token,String verifier) {
        this.twitter = twitter;
        this.requestToken = token;
        this.verifier = verifier;
    }

    public RequestToken getRequestToken() {
        return requestToken;
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public String getVerifier() {
        return verifier;
    }
}
