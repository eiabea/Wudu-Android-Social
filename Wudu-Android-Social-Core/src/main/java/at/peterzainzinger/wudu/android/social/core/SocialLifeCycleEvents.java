package at.peterzainzinger.wudu.android.social.core;

import android.content.Intent;

/**
 * Created by peterzainzinger on 24/04/14.
 */
public interface SocialLifeCycleEvents {

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data);

    /**
     *
     * @param intent
     */
    public void onCreate(Intent intent);

    /**
     *
     * @param intent
     */
    public void onResume(Intent intent);

    /**
     *
     * @param intent
     */
    public void onNewIntent(Intent intent);

    /**
     *
     */
    public void onStart();

    /**
     *
     */
    public void onStop();

}