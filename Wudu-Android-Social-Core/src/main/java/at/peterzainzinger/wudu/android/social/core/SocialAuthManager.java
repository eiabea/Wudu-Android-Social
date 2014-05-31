package at.peterzainzinger.wudu.android.social.core;

import android.content.Context;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>This Class is Managed usually by {@see SocialAuthManagerFactory}</p>
 *
 * @author Peter Zainzinger
 */
public class SocialAuthManager implements SocialLifeCycleEvents {

    /**
     *
     */
    private Context context;


    /**
     *
     */
    private HashMap<ProviderType, SocialAuthProvider> providers = new HashMap<ProviderType, SocialAuthProvider>();


    /**
     * @param context
     */
    public SocialAuthManager(Context context) {


        assert context != null;
        this.context = context;
        init();
    }


    /**
     *
     */

    private void init() {


    }


    /**
     * @param provider
     * @return
     */

    public boolean registerProvider(SocialAuthProvider provider) {

        assert provider != null;
        boolean isOverWritten = isProviderForTypeRegistered(provider.getType());
        providers.put(provider.getType(), provider);
        return isOverWritten;

    }

    /**
     * @param type
     * @return
     */
    public boolean isProviderForTypeRegistered(ProviderType type) {


        return providers.containsKey(type);

    }


    /**
     * @param type
     * @return
     */
    public SocialAuthProvider getProviderForType(ProviderType type) {


        return providers.get(type);
    }


    /**
     * @param context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * @return
     */
    public Context getContext() {
        return context;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        for (Map.Entry<ProviderType, SocialAuthProvider> entry : providers.entrySet()) {

            if (entry.getValue().doesHandleRequest(requestCode, resultCode, data)) {


                entry.getValue().onActivityResult(requestCode, resultCode, data);

            }


        }
    }

    @Override
    public void onCreate(Intent intent) {

        for (Map.Entry<ProviderType, SocialAuthProvider> entry : providers.entrySet()) {


            entry.getValue().onCreate(intent);


        }

    }

    @Override
    public void onResume(Intent intent) {

        for (Map.Entry<ProviderType, SocialAuthProvider> entry : providers.entrySet()) {


            entry.getValue().onResume(intent);


        }

    }

    @Override
    public void onNewIntent(Intent intent) {


        for (Map.Entry<ProviderType, SocialAuthProvider> entry : providers.entrySet()) {


            entry.getValue().onNewIntent(intent);


        }

    }

    @Override
    public void onStart() {
        for (Map.Entry<ProviderType, SocialAuthProvider> entry : providers.entrySet()) {


            entry.getValue().onStart();


        }

    }

    @Override
    public void onStop() {

        for (Map.Entry<ProviderType, SocialAuthProvider> entry : providers.entrySet()) {


            entry.getValue().onStop();


        }

    }
}
