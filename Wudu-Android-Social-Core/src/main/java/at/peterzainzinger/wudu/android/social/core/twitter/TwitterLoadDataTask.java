package at.peterzainzinger.wudu.android.social.core.twitter;

import android.os.AsyncTask;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

/**
 * Created by peterzainzinger on 25/04/14.
 */
public class TwitterLoadDataTask extends AsyncTask<TwitterLoadDataTaskInputModel,Void,TwitterToken> {


    /**
     * @param twitterLoadDataTaskInputModels
     * @return Token or if an Error occured, null
     */

    @Override
    protected TwitterToken doInBackground(TwitterLoadDataTaskInputModel... twitterLoadDataTaskInputModels) {


        assert (twitterLoadDataTaskInputModels.length == 1);

        TwitterLoadDataTaskInputModel input = twitterLoadDataTaskInputModels[0];


        try {

            AccessToken accessToken = input.getTwitter()
                    .getOAuthAccessToken(input.getRequestToken(),
                            input.getVerifier());


            return new TwitterToken(accessToken);




        }catch (TwitterException e){

            e.printStackTrace();
        }

        return null;

    }

    @Override
    protected void onPostExecute(TwitterToken token) {
        super.onPostExecute(token);
    }
}
