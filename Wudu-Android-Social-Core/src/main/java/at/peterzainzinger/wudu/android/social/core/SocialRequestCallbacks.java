package at.peterzainzinger.wudu.android.social.core;

/**
 * Created by peterzainzinger on 24/04/14.
 */
public interface SocialRequestCallbacks {

    void onAuthenticationSuccess(SocialToken token);
    void onAuthenticationError(SocialError error);


}
