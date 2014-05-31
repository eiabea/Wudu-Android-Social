package at.peterzainzinger.wudu.android.social.core.google;

import at.peterzainzinger.wudu.android.social.core.SocialRequestCallbacks;

/**
 * Created by peterzainzinger on 25/04/14.
 */
public interface GoogleRequestCallbacks extends SocialRequestCallbacks {

    void onGoogleAuthenticationSuccess(GoogleToken token);
    void onGoogleAuthenticationError(GoogleError error);
}
