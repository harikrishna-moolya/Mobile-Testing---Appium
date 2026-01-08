package BASE.base;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.*;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class Mobile_Specific_Actions {

    AndroidDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() throws Exception {

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName("OnePlus CPH2381")
                .setPlatformVersion("14")
                .setApp("C:\\Users\\Hari Krishna\\Downloads\\ApiDemos-debug.apk")
                .setAppPackage("io.appium.android.apis")
                .setAppActivity("io.appium.android.apis.ApiDemos")
                .setIgnoreHiddenApiPolicyError(true)

                // CRITICAL FIX
                .setNoReset(true);

        driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723"),
                options
        );

        wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    @Test
    public void deviceInteractionsTest() {

        //  Verify install
        System.out.println("App installed: " +
                driver.isAppInstalled("io.appium.android.apis"));

        //  Handle permissions
        handlePermissions();
        
        // Click Views
        WebElement views = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        AppiumBy.androidUIAutomator(
                                "new UiSelector().text(\"Views\")"
                        )
                )
        );
        views.click();
        System.out.println("Clicked Views");

        //  Background / Foreground
        System.out.println("Sending app to background");
        driver.runAppInBackground(Duration.ofSeconds(3));

        // Orientation validation (SAFE)
     // Rotate to landscape
        driver.rotate(ScreenOrientation.LANDSCAPE);
        

        // Rotate back to portrait
        driver.rotate(ScreenOrientation.PORTRAIT);


        //  Screenshot
        takeScreenshot("ApiDemos_Test");
        //push notification validation
        pushNotificationValidationTest();
        //  Uninstall
        driver.removeApp("io.appium.android.apis");
        System.out.println("App installed after uninstall: " +
                driver.isAppInstalled("io.appium.android.apis"));
    }

    public void validatePushNotificationHandling() {
    
        // Open notification panel
        driver.openNotifications();
        System.out.println("Notification panel opened");
    
        WebDriverWait notificationWait =
                new WebDriverWait(driver, Duration.ofSeconds(15));
    
        // Locate notification by partial text
        WebElement notification =
                notificationWait.until(
                        ExpectedConditions.presenceOfElementLocated(
                                AppiumBy.androidUIAutomator(
                                        "new UiSelector().textContains(\"Api\")"
                                )
                        )
                );
    
        // Validate notification content
        String notificationText = notification.getText();
        System.out.println("Notification received: " + notificationText);
    
        Assert.assertTrue(notificationText.length() > 0,
                "Notification content is empty");
    
        // Tap on notification
        notification.click();
        System.out.println("Notification clicked");
    
        // Validate app behavior after tapping notification
        WebElement views =
                notificationWait.until(
                        ExpectedConditions.presenceOfElementLocated(
                                AppiumBy.androidUIAutomator(
                                        "new UiSelector().text(\"Views\")"
                                )
                        )
                );
    
        Assert.assertTrue(views.isDisplayed(),
                "App did not navigate correctly after notification click");
    }


    private void handlePermissions() {
        try {
            List<WebElement> buttons =
                    driver.findElements(AppiumBy.className("android.widget.Button"));

            for (WebElement btn : buttons) {
                String text = btn.getText().toLowerCase();
                if (text.contains("allow") || text.contains("ok")) {
                    btn.click();
                    break;
                }
            }
        } catch (Exception ignored) {}
    }

    private void takeScreenshot(String name) {
        try {
            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);
            File dest = new File("screenshots/" + name + ".png");
            dest.getParentFile().mkdirs();
            FileHandler.copy(src, dest);
            System.out.println("Screenshot saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}


