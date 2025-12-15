package BASE.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.URL;
import java.time.Duration;

public class BaseApp {

    protected AndroidDriver driver;

    @BeforeClass
    public void setup() throws Exception {

        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setDeviceName("AndroidDevice");
        options.setAutomationName("UiAutomator2");

        options.setAppPackage("com.swaglabsmobileapp");
        options.setAppActivity("com.swaglabsmobileapp.MainActivity");

        // ---------- STABILITY OPTIONS ----------
        options.setNoReset(true);
        options.setIgnoreHiddenApiPolicyError(true);

        // ---------- ANDROID 13 / 14 FIXES ----------
        options.setCapability("uiautomator2ServerInstallTimeout", 60000);
        options.setCapability("uiautomator2ServerLaunchTimeout", 60000);
        options.setCapability("adbExecTimeout", 60000);
        options.setCapability("newCommandTimeout", 300);
        options.setCapability("disableWindowAnimation", true);

        // ---------- OPTIONAL BUT RECOMMENDED ----------
        options.setAutoGrantPermissions(true);

        driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723/"),
                options
        );

        // Global implicit wait (small)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
