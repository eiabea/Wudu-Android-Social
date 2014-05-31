

package at.peterzainzinger.wudu.android.social.core.google;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import at.peterzainzinger.wudu.android.social.core.ProviderType;
import at.peterzainzinger.wudu.android.social.core.SocialAuthProvider;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;

import java.io.IOException;


/**
 * Created by peterzainzinger on 25/04/14.
 */
public class GoogleAuthenticationProvider extends SocialAuthProvider implements
        ConnectionCallbacks, OnConnectionFailedListener, ResultCallback<People.LoadPeopleResult>, GoogleRequestCallbacks {

    public static final String TAG = GoogleAuthenticationProvider.class.getSimpleName();
    private Activity callbackActivity;


    private static final int STATE_DEFAULT = 0;
    private static final int STATE_SIGN_IN = 1;
    private static final int STATE_IN_PROGRESS = 2;

    private static final int RC_SIGN_IN = 0;

    private static final int DIALOG_PLAY_SERVICES_ERROR = 0;

    private static final String SAVED_PROGRESS = "sign_in_progress";

    // GoogleApiClient wraps our service connection to Google Play services and
    // provides access to the users sign in state and Google's APIs.
    private GoogleApiClient mGoogleApiClient;

    // We use mSignInProgress to track whether user has clicked sign in.
    // mSignInProgress can be one of three values:
    //
    //       STATE_DEFAULT: The default state of the application before the user
    //                      has clicked 'sign in', or after they have clicked
    //                      'sign out'.  In this state we will not attempt to
    //                      resolve sign in errors and so will display our
    //                      Activity in a signed out state.
    //       STATE_SIGN_IN: This state indicates that the user has clicked 'sign
    //                      in', so resolve successive errors preventing sign in
    //                      until the user has successfully authorized an account
    //                      for our app.
    //   STATE_IN_PROGRESS: This state indicates that we have started an intent to
    //                      resolve an error, and so we should not start further
    //                      intents until the current intent completes.
    private int mSignInProgress;

    // Used to store the PendingIntent most recently returned by Google Play
    // services until the user clicks 'sign in'.
    private PendingIntent mSignInIntent;

    // Used to store the error code most recently returned by Google Play services
    // until the user clicks 'sign in'.
    private int mSignInError;

    private GoogleRequestCallbacks googleRequestCallbacks;

    public GoogleAuthenticationProvider that;

    public GoogleAuthenticationProvider(Activity callbackActivity, GoogleRequestCallbacks googleRequestCallbacks) {

        super(callbackActivity, ProviderType.GOOGLE);
        this.callbackActivity = callbackActivity;
        this.googleRequestCallbacks = googleRequestCallbacks;
        that = this;


    }


    @Override
    public void onCreate(Intent intent) {
        super.onCreate(intent);
        Log.d(TAG, "onCreate");
        mGoogleApiClient = buildGoogleApiClient();
    }

    private GoogleApiClient buildGoogleApiClient() {


        Log.d(TAG, "buildGoogleApiClient");
        // When we build the GoogleApiClient we specify where connected and
        // connection failed callbacks should be returned, which Google APIs our
        // app uses and which OAuth 2.0 scopes our app requests.
        return new GoogleApiClient.Builder(callbackActivity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, null)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart -> connect");
        mGoogleApiClient.connect();

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");

        if (mGoogleApiClient.isConnected()) {
            Log.d(TAG, "onStop -> is connected");

            mGoogleApiClient.disconnect();
        } else {

            Log.d(TAG, "onStop -> is not connected");

        }
    }

    @Override
    public boolean login() {

        Log.d(TAG, "login");

        if (!mGoogleApiClient.isConnecting()) {
            Log.d(TAG, "login -> is not connecting");
            resolveSignInError();
        } else {

            Log.d(TAG, "login -> is connecting");
        }
        return false;
    }

    @Override
    public boolean logout() {
        Log.d(TAG, "logout");

        if (mGoogleApiClient.isConnected()) {
            Log.d(TAG, "logout -> is connected");
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            return true;
        }

        Log.d(TAG, "logout -> is not connected");
        return false;
    }

    public boolean revokeAccess() {


        if (mGoogleApiClient.isConnected()) {
            // After we revoke permissions for the user with a GoogleApiClient
            // instance, we must discard it and create a new one.
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            // Our sample has caches no user data from Google+, however we
            // would normally register a callback on revokeAccessAndDisconnect
            // to delete user data so that we comply with Google developer
            // policies.
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient);
            mGoogleApiClient = buildGoogleApiClient();
            mGoogleApiClient.connect();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isLoggedIn() {
        Log.d(TAG, "isLoggedIn");
        return mGoogleApiClient.isConnected();
    }

    @Override
    public boolean doesHandleRequest(int requestCode, int resultCode, Intent data) {

        Log.d(TAG, "doesHandleRequest");
        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "onConnected");

        // Retrieve some profile information to personalize our app for the user.
        Person currentUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

        loadToken();
        Plus.PeopleApi.loadVisible(mGoogleApiClient, null)
                .setResultCallback(this);

        // Indicate that the sign in process is complete.
        mSignInProgress = STATE_DEFAULT;

    }

    public void loadToken() {


        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object... params) {
                String scope = "oauth2:" + Scopes.PLUS_LOGIN;
                try {
                    // We can retrieve the token to check via
                    // tokeninfo or to pass to a service-side
                    // application.
                    String token = GoogleAuthUtil.getToken(callbackActivity,
                            Plus.AccountApi.getAccountName(mGoogleApiClient)
                            , scope);

                    onGoogleAuthenticationSuccess(new GoogleToken(token));
                    return null;
                } catch (UserRecoverableAuthException e) {
                    // This error is recoverable, so we could fix this
                    // by displaying the intent to the user.
                    e.printStackTrace();
                    onAuthenticationError(new GoogleError(e.getMessage()));

                } catch (IOException e) {
                    onAuthenticationError(new GoogleError(e.getMessage()));
                    e.printStackTrace();
                } catch (GoogleAuthException e) {
                    onAuthenticationError(new GoogleError(e.getMessage()));
                    e.printStackTrace();
                }
                return null;
            }
        };
        task.execute((Void) null);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnectionSuspended " + i);

        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {

        // Refer to the javadoc for ConnectionResult to see what error codes might

        String error = "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode();
        // be returned in onConnectionFailed.
        Log.i(TAG, error);

        if (mSignInProgress != STATE_IN_PROGRESS) {
            // We do not have an intent in progress so we should store the latest
            // error resolution intent for use when the sign in button is clicked.

            Log.d(TAG, "STATE NOT IN PROGRESS");
            mSignInIntent = result.getResolution();
            mSignInError = result.getErrorCode();

            if (mSignInProgress == STATE_SIGN_IN) {

                Log.d(TAG, "STATE SIGN IN");
                // STATE_SIGN_IN indicates the user already clicked the sign in button
                // so we should continue processing errors until the user is signed in
                // or they click cancel.
                resolveSignInError();
            } else {

                onGoogleAuthenticationError(new GoogleError("Error"));
            }
        }

        // In this sample we consider the user signed out whenever they do not have
        // a connection to Google Play services.
        onSignedOut();

    }

    /* Starts an appropriate intent or dialog for user interaction to resolve
   * the current error preventing the user from being signed in.  This could
   * be a dialog allowing the user to select an account, an activity allowing
   * the user to consent to the permissions being requested by your app, a
   * setting to enable device networking, etc.
   */
    private void resolveSignInError() {


        Log.d(TAG, "resolveSignInError");
        if (mSignInIntent != null) {

            Log.d(TAG, "resolveSignInError -> mSignInIntent != null");

            // We have an intent which will allow our user to sign in or
            // resolve an error.  For example if the user needs to
            // select an account to sign in with, or if they need to consent
            // to the permissions your app is requesting.

            try {
                // Send the pending intent that we stored on the most recent
                // OnConnectionFailed callback.  This will allow the user to
                // resolve the error currently preventing our connection to
                // Google Play services.
                mSignInProgress = STATE_IN_PROGRESS;
                callbackActivity.startIntentSenderForResult(mSignInIntent.getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
                Log.d(TAG, "resolveSignInError -> without Exception");

            } catch (SendIntentException e) {
                Log.i(TAG, "Sign in intent could not be sent: "
                        + e.getLocalizedMessage());
                // The intent was canceled before it was sent.  Attempt to connect to
                // get an updated ConnectionResult.
                mSignInProgress = STATE_SIGN_IN;
                mGoogleApiClient.connect();
            }
        } else {

            Log.d(TAG, "resolveSignInError -> mSignInIntent == null");


        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(TAG, String.format("requestcode: %d, resultcode: %d ", requestCode, resultCode));

        switch (requestCode) {
            case RC_SIGN_IN:
                if (resultCode == Activity.RESULT_OK) {
                    // If the error resolution was successful we should continue
                    // processing errors.

                    Log.d(TAG, "Result ok");
                    mSignInProgress = STATE_SIGN_IN;
                } else {
                    // If the error resolution was not successful or the user canceled,
                    // we should stop processing errors.

                    Log.d(TAG, "Result not ok");
                    mSignInProgress = STATE_DEFAULT;
                }

                if (!mGoogleApiClient.isConnecting()) {
                    // If Google Play services resolved the issue with a dialog then
                    // onStart is not called so we need to re-attempt connection here.
                    Log.d(TAG, "is not connecting, reconnect");
                    mGoogleApiClient.connect();
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onResult(LoadPeopleResult peopleData) {
        Log.d(TAG, "onResult");
        if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
            PersonBuffer personBuffer = peopleData.getPersonBuffer();

        } else {
            Log.e(TAG, "Error requesting visible circles: " + peopleData.getStatus());
        }
    }

    private void onSignedOut() {

    }

    @Override
    public void onGoogleAuthenticationSuccess(final GoogleToken token) {


        callbackActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (null != googleRequestCallbacks) {

                    googleRequestCallbacks.onGoogleAuthenticationSuccess(token);
                }
                that.onAuthenticationSuccess(token);
            }
        });

    }

    @Override
    public void onGoogleAuthenticationError(final GoogleError error) {


        callbackActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (null != googleRequestCallbacks) {

                    googleRequestCallbacks.onGoogleAuthenticationError(error);
                }

                that.onAuthenticationError(error);
            }
        });


    }
}
