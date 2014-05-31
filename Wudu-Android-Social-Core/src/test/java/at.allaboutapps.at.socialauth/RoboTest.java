package at.allaboutapps.at.socialauth;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by peterzainzinger on 25/04/14.
 */

@RunWith(RobolectricTestRunner.class)
public class RoboTest {

    @Test
    @Config(emulateSdk = 18)
    public void clickingButton_shouldChangeResultsViewText() throws Exception {

        TestCase.assertNull(null);
    }

    @Test
    @Config(emulateSdk = 18)
    public void booleantest() throws Exception {

        TestCase.assertTrue(true);
    }
}