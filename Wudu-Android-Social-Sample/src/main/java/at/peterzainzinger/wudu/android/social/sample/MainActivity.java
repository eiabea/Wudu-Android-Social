package at.peterzainzinger.wudu.android.social.sample;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import at.peterzainzinger.wudu.android.social.core.SocialAuthManagerFactory;
import at.peterzainzinger.wudu.android.social.core.SocialError;
import at.peterzainzinger.wudu.android.social.core.SocialToken;
import at.peterzainzinger.wudu.android.social.core.facebook.FacebookAuthenticationProvider;
import at.peterzainzinger.wudu.android.social.core.facebook.FacebookError;
import at.peterzainzinger.wudu.android.social.core.facebook.FacebookRequestCallbacks;
import at.peterzainzinger.wudu.android.social.core.facebook.FacebookToken;
import at.peterzainzinger.wudu.android.social.core.google.GoogleAuthenticationProvider;
import at.peterzainzinger.wudu.android.social.core.google.GoogleError;
import at.peterzainzinger.wudu.android.social.core.google.GoogleRequestCallbacks;
import at.peterzainzinger.wudu.android.social.core.google.GoogleToken;
import at.peterzainzinger.wudu.android.social.core.twitter.TwitterAuthenticationProvider;
import at.peterzainzinger.wudu.android.social.core.twitter.TwitterError;
import at.peterzainzinger.wudu.android.social.core.twitter.TwitterRequestCallbacks;
import at.peterzainzinger.wudu.android.social.core.twitter.TwitterToken;
import at.peterzainzinger.wudu.android.social.core.utils.FacebookUtils;
import com.google.android.gms.common.SignInButton;

import java.util.List;


public class MainActivity extends FragmentActivity implements GoogleRequestCallbacks,View.OnClickListener,TwitterRequestCallbacks,FacebookRequestCallbacks {

    public static final String TAG = MainActivity.class.getSimpleName();

    private SignInButton mGoogleSignIn;
    private Button mGoogleRevoke;
    private Button mTwitterSignIn;
    private Button mFacebookSignIn;


    public GoogleAuthenticationProvider googleAuthenticationProvider;
    public TwitterAuthenticationProvider twitterAuthenticationProvider;
    public FacebookAuthenticationProvider facebookAuthenticationProvider;



    public static final String TWITTER_CONSUMER_KEY = "K8WvXHOmLTb3soYhMdTaaqqwi";
    public static final String TWITTER_CONSUMER_SECRET = "W0W3zQjIoP7wXErZ0SKTssomiE1kOlLOjvjLxZOvNcvyFybcJT";
    public static final String TWITTER_CALLBACK= "oauth://sample";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAuthProvider();
        SocialAuthManagerFactory.getInstance(this).onCreate(getIntent());

        mGoogleSignIn = (SignInButton)findViewById(R.id.sign_in_button);
        mGoogleSignIn.setOnClickListener(this);

        mTwitterSignIn = (Button)findViewById(R.id.btn_twitter_login);
        mTwitterSignIn.setOnClickListener(this);

        mFacebookSignIn = (Button)findViewById(R.id.btn_facebook_login);
        mFacebookSignIn.setOnClickListener(this);

        mGoogleRevoke = (Button)findViewById(R.id.btn_google_revoke);
        mGoogleRevoke.setOnClickListener(this);


    }

    private void initAuthProvider() {

        googleAuthenticationProvider = new GoogleAuthenticationProvider(this,this);
        SocialAuthManagerFactory.getInstance(getApplicationContext()).registerProvider(googleAuthenticationProvider);

        twitterAuthenticationProvider = new TwitterAuthenticationProvider(this,TWITTER_CONSUMER_KEY,TWITTER_CONSUMER_SECRET,this,TWITTER_CALLBACK);
        SocialAuthManagerFactory.getInstance(getApplicationContext()).registerProvider(twitterAuthenticationProvider);

        facebookAuthenticationProvider = new FacebookAuthenticationProvider(this,"893553804004573",this);
        SocialAuthManagerFactory.getInstance(getApplicationContext()).registerProvider(facebookAuthenticationProvider);

    }

    @Override
    protected void onStart() {
        super.onStart();
        SocialAuthManagerFactory.getInstance(this).onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        SocialAuthManagerFactory.getInstance(this).onStop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SocialAuthManagerFactory.getInstance(this).onNewIntent(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        SocialAuthManagerFactory.getInstance(this).onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onGoogleAuthenticationSuccess(GoogleToken token) {

        log("onGoogleAuthenticationSuccess");

    }

    @Override
    public void onGoogleAuthenticationError(GoogleError error) {

        log("onGoogleAuthenticationError");

    }

    @Override
    public void onAuthenticationSuccess(SocialToken token) {

    }

    @Override
    public void onAuthenticationError(SocialError error) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                googleAuthenticationProvider.login();
                break;

            case R.id.btn_twitter_login:
                twitterAuthenticationProvider.login();
                break;
            case R.id.btn_facebook_login:
                facebookAuthenticationProvider.login();
                break;

            case R.id.btn_google_revoke:
                googleAuthenticationProvider.revokeAccess();
                break;
        }
    }

    private void log(String message) {

        Log.d(TAG, message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onTwitterAuthenticationError(TwitterError error) {

        log("onTwitterAuthenticationError");

    }

    @Override
    public void onTwitterAuthenticationSuccess(TwitterToken token) {

        log("onTwitterAuthenticationSuccess");


    }

    @Override
    public void onFacebookAuthenticationSuccess(FacebookToken token) {
        log("onFacebookAuthenticationSuccess");

    }

    @Override
    public void onFacebookAuthenticationError(FacebookError error) {
        log("onFacebookAuthenticationError");

    }
}
