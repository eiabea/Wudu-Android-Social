package at.peterzainzinger.wudu.android.social.core.twitter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import at.peterzainzinger.wudu.android.social.core.ProviderType;
import at.peterzainzinger.wudu.android.social.core.SocialAuthProvider;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.util.concurrent.ExecutionException;

/**
 * Created by peterzainzinger on 25/04/14.
 * <p/>
 * <p/>
 * <p>This Class is using Twitter4j lib (V 3.0.5)</p>
 */
public class TwitterAuthenticationProvider extends SocialAuthProvider implements TwitterRequestCallbacks {

    /**
     *
     */
    private static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";


    public static final String ERROR_DENIED = "DENIED";


    /**
     *
     */
    private String consumerKey;


    /**
     *
     */
    private String consumerSecret;


    /**
     *
     */
    private TwitterRequestCallbacks twitterRequestCallbacks;


    /**
     * Instance of the Twitter , initialized in {@see prepareTwitter}
     */

    private Twitter twitter;


    /**
     *
     */
    private RequestToken requestToken;


    /**
     *
     */

    private String twitterCallbackUrl;


    /**
     *
     */

    private Activity callbackActivity;

    /**
     * @param callbackActivity
     * @param consumerKey
     * @param consumerSecret
     */

    public TwitterAuthenticationProvider(Activity callbackActivity, String consumerKey, String consumerSecret, TwitterRequestCallbacks twitterRequestCallbacks, String twitterCallbackUrl) {


        super(callbackActivity, ProviderType.TWITTER);


        assert null != consumerKey;
        assert null != consumerSecret;
        assert null != twitterRequestCallbacks;
        assert null != twitterCallbackUrl;

        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.twitterRequestCallbacks = twitterRequestCallbacks;
        this.twitterCallbackUrl = twitterCallbackUrl;
        this.callbackActivity = callbackActivity;


    }


    /**
     * Builds the Twitter Instance
     */

    private void prepareTwitter() {


        TwitterFactory factory = new TwitterFactory(buildConfiguration());
        this.twitter = factory.getInstance();


    }

    /**
     * @return
     */

    @Override
    public boolean login() {


        prepareTwitter();


        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    requestToken = twitter
                            .getOAuthRequestToken(twitterCallbackUrl);

                    callbackActivity.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(requestToken
                                    .getAuthenticationURL())
                    ));
                } catch (TwitterException e) {
                    e.printStackTrace();
                    onTwitterAuthenticationError(new TwitterError(e.getMessage()));
                }

            }
        }).start();


        return false;
    }

    /**
     * @return the Configuration for the Twitter Factory
     */
    public Configuration buildConfiguration() {

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(this.consumerKey);
        builder.setOAuthConsumerSecret(this.consumerSecret);
        Configuration configuration = builder.build();
        return configuration;


    }


    /**
     * Called by the Lifecycle methods
     *
     * @param intent
     */

    public void checkIntent(Intent intent) {

        Uri uri = intent.getData();

        if (null != uri) {


            if (uri != null && uri.toString().startsWith(twitterCallbackUrl)) {


                if (uri.toString().contains("denied")) {

                    onTwitterAuthenticationError(new TwitterError(ERROR_DENIED));
                    return;
                } else {


                    prepareTwitter();


                    final String verifier = uri
                            .getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);


                    TwitterLoadDataTask task = new TwitterLoadDataTask();

                    TwitterLoadDataTaskInputModel input = new TwitterLoadDataTaskInputModel(twitter, requestToken, verifier);

                    try {

                        TwitterToken token = task.execute(input).get();
                        onTwitterAuthenticationSuccess(token);


                    } catch (InterruptedException e) {

                        onTwitterAuthenticationError(new TwitterError(e.getMessage()));


                    } catch (ExecutionException e) {

                        onTwitterAuthenticationError(new TwitterError(e.getMessage()));


                    }


                }
            }


        }


    }


    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public boolean doesHandleRequest(int requestCode, int resultCode, Intent data) {
        return false;
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public void onTwitterAuthenticationSuccess(TwitterToken token) {


        if (null != twitterCallbackUrl) {

            twitterRequestCallbacks.onTwitterAuthenticationSuccess(token);
        }

        super.onAuthenticationSuccess(token);

    }


    @Override
    public void onTwitterAuthenticationError(TwitterError error) {


        if (null != twitterCallbackUrl) {

            twitterRequestCallbacks.onTwitterAuthenticationError(error);
        }


        super.onAuthenticationError(error);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * One of these Methods is Called after Web Login
     *
     * @param intent
     */

    @Override
    public void onCreate(Intent intent) {
        super.onCreate(intent);
        //   checkIntent(intent);
    }

    @Override
    public void onResume(Intent intent) {
        super.onResume(intent);
        checkIntent(intent);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkIntent(intent);
    }
}
