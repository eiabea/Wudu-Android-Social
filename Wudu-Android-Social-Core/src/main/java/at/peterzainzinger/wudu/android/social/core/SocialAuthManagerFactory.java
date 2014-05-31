package at.peterzainzinger.wudu.android.social.core;

import android.content.Context;

/**
 * Created by peterzainzinger on 24/04/14.
 */
public class SocialAuthManagerFactory {

    /**
     *
     */
    private static SocialAuthManager instance;

    /**
     *
     */
    private static Context context;

    /**
     *
     * @param _context
     * @return
     */
    public static SocialAuthManager getInstance(Context _context){

        assert null!=_context;
        context = _context;

        if(null==instance){

            instance = new SocialAuthManager(context);
        }
        return instance;
    }


}








