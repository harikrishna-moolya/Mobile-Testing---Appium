package BASE.base;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class BaseAppTest extends BaseApp {

    @Test
    public void flipkartEndToEndTest() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

        // ================= LOGIN =================
        try {
            wait.until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.accessibilityId("test-Username"))).sendKeys("standard_user");

            wait.until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.accessibilityId("test-Password"))).sendKeys("secret_sauce");

            wait.until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.accessibilityId("test-LOGIN"))).click();

            System.out.println("✅ Login successful");
        } catch (Exception e) {
            System.out.println("ℹ️ Already logged in or login not required");
        }

        // ================= SELECT FIRST PRODUCT =================
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"Sauce Labs Backpack\")"))).click();

        // ================= SCROLL TO ADD TO CART =================
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().description(\"test-ADD TO CART\"))"
                ))).click();

        System.out.println("✅ Product added to cart");

        // ================= OPEN CART =================
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Cart\"]/android.view.ViewGroup/android.widget.ImageView"))).click();

        // ================= CHECKOUT =================
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.xpath("//android.widget.TextView[@text=\"CHECKOUT\"]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"CANCEL\")"))).click();

        System.out.println("✅ Checkout page reached successfully");
    }
}
