package at.peterzainzinger.wudu.android.social.core.facebook;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import at.peterzainzinger.wudu.android.social.core.ProviderType;
import at.peterzainzinger.wudu.android.social.core.SocialAuthProvider;
import at.peterzainzinger.wudu.android.social.core.utils.DateUtils;
import com.facebook.*;
import com.facebook.model.GraphUser;
import de.peterfriese.robolectricdemo.R;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by peterzainzinger on 24/04/14.
 */
public class
        FacebookAuthenticationProvider extends SocialAuthProvider implements FacebookRequestCallbacks {


    public static final String TAG = FacebookAuthenticationProvider.class.getSimpleName();


    /**
     * Message used if Session is null;
     */
    public static final String MESSAGE_NO_SESSION = "NO SESSION";


    /*
     *
     * @see com.facebook.Session.AuthorizationRequest
     */
    public static final int DEFAULT_AUTHORIZE_ACTIVITY_CODE = 0xface;


    /**
     * Id of your Facebook Application, from Dashboard
     */
    private String applicationId;


    /**
     * Activity where the onActivity gets called
     */
    private Activity callbackActivity;


    /**
     * Current Session used by the Provider
     */
    private Session facebookSession;


    /**
     * List of Permissions requested by a Login
     */
    private List<String> permission;


    /**
     *
     */
    private FacebookRequestCallbacks facebookRequestCallbacks;


    /**
     * @param callbackActivity Activity where the onActivity gets called
     * @param applicationId
     */
    public FacebookAuthenticationProvider(Activity callbackActivity, String applicationId) {

        super(callbackActivity, ProviderType.FACEBOOK);
        init(callbackActivity, applicationId);

    }

    /**
     * @param callbackActivity
     * @param applicationId
     * @param callbacks
     */
    public FacebookAuthenticationProvider(Activity callbackActivity, String applicationId, FacebookRequestCallbacks callbacks) {

        super(callbackActivity, ProviderType.FACEBOOK, callbacks);
        init(callbackActivity, applicationId);
        this.facebookRequestCallbacks = callbacks;


    }

    /**
     *
     */
    private void init(Activity callbackActivity, String applicationId) {
        this.applicationId = applicationId;
        this.callbackActivity = callbackActivity;

    }

    /**
     * Facebook Request Code :
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @return
     */
    @Override
    public boolean doesHandleRequest(int requestCode, int resultCode, Intent data) {


        return true;

    }

    /**
     *
     */
    @Override
    public boolean login() {

        Log.d(TAG, "start login");
        Session.openActiveSession(callbackActivity, true, new Session.StatusCallback() {

            // callback when session changes state
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                if (session.isOpened()) {
                    FacebookToken token = FacebookToken.constructTokenFromSession(session);
                    onFacebookAuthenticationSuccess(token);

                } else {

                    onFacebookAuthenticationError(new FacebookError("session not open"));
                }


            }
        });
        return false;
    }


    @Override
    public boolean logout() {


        if (null == Session.getActiveSession() || !Session.getActiveSession().isOpened()) {

            return false;
        } else {

            Session.getActiveSession().closeAndClearTokenInformation();
            return true;

        }


    }

    public boolean checkIfAlreadyLoggedIn() {


        return (null != Session.getActiveSession() && Session.getActiveSession().isOpened());

    }


    /**
     * If the Session Open Request has to be modified, overwrite this
     *
     * @param request
     */

    public void modifySessionOpenRequest(Session.OpenRequest request) {
    }

    /*
     * Facebook LoginActivity is started as an startActivityForResult, so this
     * is called if the request is finished
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        //Check necessary because not only Facebook calls, eg Google+ calls this method, which causes NPE otherwise
        if (null != Session.getActiveSession()) {
            Session.getActiveSession().onActivityResult(callbackActivity, requestCode, resultCode, data);
        }


    }


    @Override
    public void onFacebookAuthenticationSuccess(FacebookToken token) {

        if (null != facebookRequestCallbacks) {

            facebookRequestCallbacks.onFacebookAuthenticationSuccess(token);
        }

        super.onAuthenticationSuccess(token);

    }

    @Override
    public void onFacebookAuthenticationError(FacebookError error) {

        if (null != facebookRequestCallbacks) {

            facebookRequestCallbacks.onFacebookAuthenticationError(error);
        }

        super.onAuthenticationError(error);

    }

    /**
     * @return
     */

    @Override
    public boolean isLoggedIn() {
        return checkIfAlreadyLoggedIn();
    }

    /**
     * @return
     */

    public List<String> getPermission() {
        return permission;
    }


    /**
     * if the list of Permissions is null (e.g if not overwritten) than this is called
     * <p/>
     * <h4>The Default Permissions is/are:</h4>
     * <p/>
     * <li>Email</li>
     *
     * @return
     */

    private List<String> getDefaultPermission() {

        return (Arrays.asList("email"));
    }


    /**
     * @param permission
     */

    public void setPermission(List<String> permission) {
        this.permission = permission;
    }

    /**
     * @return
     */
    public String getApplicationId() {
        return applicationId;
    }


}
