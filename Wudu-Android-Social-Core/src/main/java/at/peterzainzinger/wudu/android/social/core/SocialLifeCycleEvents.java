package at.peterzainzinger.wudu.android.social.core;

import android.content.Intent;

/**
 * Created by peterzainzinger on 24/04/14.
 */
public interface SocialLifeCycleEvents {

    public void onActivityResult(int requestCode, int resultCode, Intent data);
    public void onCreate(Intent intent);
    public void onResume(Intent intent);
    public void onNewIntent(Intent intent);
    public void onStart();
    public void onStop();

}