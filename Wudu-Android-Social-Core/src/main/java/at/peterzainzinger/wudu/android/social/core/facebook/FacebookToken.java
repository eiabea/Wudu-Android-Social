package at.peterzainzinger.wudu.android.social.core.facebook;

import at.peterzainzinger.wudu.android.social.core.SocialToken;
import at.peterzainzinger.wudu.android.social.core.utils.DateUtils;
import com.facebook.Session;

import java.util.Calendar;
import java.util.List;

/**
 * Created by peterzainzinger on 24/04/14.
 */
public class FacebookToken implements SocialToken {

    /**
     *
     */
    private String token;

    /**
     *
     */
    private Calendar expireDate;

    /**
     *
     */
    private List<String> permissions;


    /**
     *
     */
    private Session session;


    /**
     *
     * @param token
     * @param expireDate
     * @param permissions
     */
    public FacebookToken(String token,Calendar expireDate,List<String> permissions,Session session){

        this.token = token;
        this.expireDate = expireDate;
        this.permissions = permissions;
        this.session = session;
    }

    /**
     *
     * @return
     */

    @Override
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }


    /**
     *
     * @return
     */
    public Calendar getExpireDate() {
        return expireDate;
    }

    /**
     *
     * @return
     */
    public List<String> getPermissions() {
        return permissions;
    }

    /**
     *
     * @return
     */
    public Session getSession() {
        return session;
    }

    public static FacebookToken constructTokenFromSession(Session session) {

        String token = session.getAccessToken();
        Calendar expireDate = DateUtils.DateToCalendar(session.getExpirationDate());
        List<String> permissions = session.getPermissions();

        FacebookToken fbToken = new FacebookToken(token, expireDate, permissions,session);

        return fbToken;


    }
}
