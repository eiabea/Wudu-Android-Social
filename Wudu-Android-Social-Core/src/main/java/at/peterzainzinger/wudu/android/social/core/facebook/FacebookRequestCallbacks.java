package at.peterzainzinger.wudu.android.social.core.facebook;

import at.peterzainzinger.wudu.android.social.core.SocialRequestCallbacks;

/**
 * Created by peterzainzinger on 24/04/14.
 */
public interface FacebookRequestCallbacks extends SocialRequestCallbacks {

    void onFacebookAuthenticationSuccess(FacebookToken token);
    void onFacebookAuthenticationError(FacebookError error);
}
