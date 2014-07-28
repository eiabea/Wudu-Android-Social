package at.peterzainzinger.wudu.android.social.core;

import android.content.Context;
import android.content.Intent;

/**
 * Created by peterzainzinger on 24/04/14.
 */
public abstract class SocialAuthProvider implements SocialRequestCallbacks,SocialLifeCycleEvents{

    /**
     *
     */

    private Context context;


    /**
     *
     */

    private ProviderType type;


    /**
     *
     */
    private SocialRequestCallbacks callbacks;


    /**
     *
     * @param context
     */
    public SocialAuthProvider(Context context,ProviderType type){

        init(context,type);
    }


    /**
     *
     * @param context
     * @param type
     * @param callbacks
     */
    public SocialAuthProvider(Context context,ProviderType type,SocialRequestCallbacks callbacks){

        init(context,type);
        this.callbacks = callbacks;
    }

    /**
     *
     * @param context
     * @param type
     */
    private void init(Context context,ProviderType type){
        this.context = context;
        this.type = type;

    }


    /**
     *
     * @return if already logged in
     */
    public abstract boolean login();


    /**
     *
     * @return if tbere was a session to logout
     */
    public abstract boolean logout();

    /**
     *
     * @return
     */
    public abstract boolean isLoggedIn();



    /**
     *
     * Method called by SocialAuthManager in the case of an onActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @return
     */

    public void onActivityResult(int requestCode, int resultCode, Intent data) {}

    /**
     *
     * Method is called to find out if the requestCode is handled,
     *
     * NOTE: each onActivityResult is only called once for on Response
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @return
     */

    public abstract boolean doesHandleRequest(int requestCode,int resultCode,Intent data);


    /**
     *
     */
    public void onCreate(Intent intent){


    }

    /**
     *
     */
    public void onResume(Intent intent){}

    /**
     *
     */
    public void onNewIntent(Intent intent){}

    /**
     *
     */
    @Override
    public void onStart() {}

    @Override
    public void onStop() {}

    /**
     *
     * @param token
     */
    @Override
    public void onAuthenticationSuccess(SocialToken token) {

        if(null!=callbacks){

            callbacks.onAuthenticationSuccess(token);
        }

    }

    /**
     *
     * @param error
     */
    @Override
    public void onAuthenticationError(SocialError error) {

        if(null!=callbacks){

            callbacks.onAuthenticationError(error);
        }

    }

    /**
     *
     * @param context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     *
     * @return
     */

    public Context getContext() {
        return context;
    }


    /**
     *
     * @return
     */
    public ProviderType getType() {
        return type;
    }


    /**
     *
     * @param type
     */
    public void setType(ProviderType type) {
        this.type = type;
    }
}
