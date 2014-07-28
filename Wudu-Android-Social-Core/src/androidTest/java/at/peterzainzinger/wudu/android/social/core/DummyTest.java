package at.peterzainzinger.wudu.android.social.core;

import at.peterzainzinger.wudu.android.social.core.facebook.FacebookError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

/**
 * Created by peterzainzinger on 04/07/14.
 */
@Config(emulateSdk = 18) //Robolectric support API level 18,17, 16, but not 19
@RunWith(RobolectricTestRunner.class)
public class DummyTest {

    private FacebookError error;
    @Before
    public void setup() {
        error = new FacebookError("Error");
    }

    @Test
    public void testWhoppingComplex() {

        assertEquals("Error",error.getMessage());
    }
}
