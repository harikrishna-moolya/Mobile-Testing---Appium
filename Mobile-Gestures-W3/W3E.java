package BASE.base;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class W3E {

    protected AndroidDriver dr;

    @BeforeClass
    public void setUp() throws Exception {

        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("OnePlus CPH2381");
        options.setPlatformVersion("14");

        // OnePlus Calculator
        options.setAppPackage("io.appium.android.apis");
        options.setAppActivity(".ApiDemos");

        // IMPORTANT FIX
        options.setNoReset(true);
        options.setCapability("disableHiddenApiPolicy", true);
        options.setCapability("ignoreHiddenApiPolicyError", true);

        dr = new AndroidDriver(
                new URL("http://127.0.0.1:4723"),
                options
        );
    }

    public void takeScreenshot(String testName) {
        try {
            File src = ((TakesScreenshot) dr).getScreenshotAs(OutputType.FILE);
            File dest = new File("screenshots/" + testName + ".png");
            FileHandler.copy(src, dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        if (dr != null) {
            dr.quit();
        }
    }
}
