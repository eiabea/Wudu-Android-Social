package at.peterzainzinger.wudu.android.social.core.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peterzainzinger on 24/04/14.
 */
public class FacebookUtils {


    public static List<String> getHashForApiConsole(String youPackage, Context context) throws PackageManager.NameNotFoundException, NoSuchAlgorithmException {


        List<String> hashList = new ArrayList<String>();

        PackageInfo info = context.getPackageManager().getPackageInfo(
                youPackage,
                PackageManager.GET_SIGNATURES);
        for (Signature signature : info.signatures) {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(signature.toByteArray());
            String hash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
            hashList.add(hash);

        }

        return hashList;


    }
}
