package at.peterzainzinger.wudu.android.social.core;

/**
 * Created by peterzainzinger on 24/04/14.
 */
public interface SocialRequestCallbacks {

    /**
     *
     * @param token
     */
    void onAuthenticationSuccess(SocialToken token);

    /**
     *
     * @param error
     */
    void onAuthenticationError(SocialError error);


}
