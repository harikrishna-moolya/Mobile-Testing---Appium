package BASE.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.*;

import java.net.URL;
import java.time.Duration;
import java.util.Set;

public class Mobile_Web_Test {

    AndroidDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() throws Exception {

        UiAutomator2Options options = new UiAutomator2Options();

        // ✅ Mandatory
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("OnePlus CPH2381");
        options.setPlatformVersion("14");

        // ✅ Google Chrome (DO NOT CHANGE)
        options.setCapability("Browser", "Chrome");

        // 🔥 Android 14 FIX (DO NOT CHANGE)
        options.setNoReset(true);
        options.setCapability("fullReset", false);
        options.setCapability("skipChromeReset", true);
        options.setCapability("skipUnlock", true);

        // 🔥 Chromedriver auto handling (DO NOT CHANGE)
        options.setCapability("chromedriverAutodownload", true);
        options.setCapability(
                "chromedriverChromeMappingFile",
                "https://googlechromelabs.github.io/chrome-for-testing/known-good-versions-with-downloads.json"
        );

        // Android 14 requirement
        options.setIgnoreHiddenApiPolicyError(true);

        driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723"),
                options
        );

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void mobileWebTest() {

        driver.get("https://www.wikipedia.org");

        WebElement searchBox = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.EditText[@resource-id=\"searchInput\"]"))
        );

        searchBox.sendKeys("Appium");
        driver.findElement(By.xpath("//android.widget.Button[@text=\"Search\"]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@resource-id=\"firstHeading\"]"))).equals("Appium");
        System.out.println("True");
 

    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}