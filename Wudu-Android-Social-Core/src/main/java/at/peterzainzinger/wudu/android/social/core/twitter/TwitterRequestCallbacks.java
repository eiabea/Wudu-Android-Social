package at.peterzainzinger.wudu.android.social.core.twitter;

import at.peterzainzinger.wudu.android.social.core.SocialRequestCallbacks;

/**
 * Created by peterzainzinger on 24/04/14.
 */
public interface TwitterRequestCallbacks extends SocialRequestCallbacks{

    void onTwitterAuthenticationSuccess(TwitterToken token);
    void onTwitterAuthenticationError(TwitterError error);
}
