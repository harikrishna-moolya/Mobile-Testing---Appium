package BASE.base;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class W3ETest extends W3E {

    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

    @Test
    public void mobileGesturesTest() {

        WebDriverWait wait = new WebDriverWait(dr, Duration.ofSeconds(25));

        // ---------- TAP: Views ----------
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("Views")
        )).click();

        // ---------- SCROLL & CLICK: Expandable Lists ----------
        dr.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().text(\"Expandable Lists\"));"
        ));
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("Expandable Lists")
        )).click();

        // ---------- SCROLL & CLICK: Custom Adapter ----------
     
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("1. Custom Adapter"))).click();

        // ---------- LONG PRESS: People Names ----------
        WebElement peopleNames = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        AppiumBy.xpath("//android.widget.TextView[@text=\"People Names\"]")
                )
        );

        longPress(peopleNames);

        // ---------- NAVIGATE BACK TO HOME ----------
        dr.navigate().back();
        dr.navigate().back();
        dr.navigate().back();
        dr.navigate().back();

        // ---------- PULL TO REFRESH ----------
        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("Views")
        )).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.accessibilityId("Swipe Refresh")
        )).click();

        Sequence pullToRefresh = new Sequence(finger, 1);
        pullToRefresh.addAction(finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.viewport(),
                540, 400
        ));
        pullToRefresh.addAction(finger.createPointerDown(0));
        pullToRefresh.addAction(finger.createPointerMove(
                Duration.ofMillis(1200),
                PointerInput.Origin.viewport(),
                540, 1600
        ));
        pullToRefresh.addAction(finger.createPointerUp(0));

        dr.perform(List.of(pullToRefresh));
    }

    // ---------- HELPER METHOD: LONG PRESS ---------- 
    private void longPress(WebElement element) {
        Sequence longPress = new Sequence(finger, 1);

        longPress.addAction(finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.fromElement(element),
                0, 0
        ));
        longPress.addAction(finger.createPointerDown(0));
        longPress.addAction(new Pause(finger, Duration.ofSeconds(2)));
        longPress.addAction(finger.createPointerUp(0));

        dr.perform(List.of(longPress));
    }
}
