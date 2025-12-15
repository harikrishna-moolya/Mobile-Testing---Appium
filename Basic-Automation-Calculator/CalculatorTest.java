package BASE.base;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CalculatorTest extends Calculator {

    //  DIGITS
    private By digit(int num) {
        return By.id("com.oneplus.calculator:id/digit_" + num);
    }

    //  OPERATORS
    private By add = By.id("com.oneplus.calculator:id/op_add");
    private By sub = By.id("com.oneplus.calculator:id/op_sub");
    private By mul = By.id("com.oneplus.calculator:id/op_mul");
    private By div = By.id("com.oneplus.calculator:id/op_div");
    private By equals = By.id("com.oneplus.calculator:id/eq");

    //  RESULT
    private By result = By.id("com.oneplus.calculator:id/result");

    //  CLEAR
    private By clear = By.id("com.oneplus.calculator:id/clr");

    private void tap(By locator) {
        dr.findElement(locator).click();
    }

    @BeforeMethod
    public void clearCalculator() {
        tap(clear);
    }

    @Test
    public void testAddition() {
        tap(digit(2));
        tap(add);
        tap(digit(3));
        tap(equals);

        Assert.assertEquals(dr.findElement(result).getText(), "5");
    }

    @Test
    public void testSubtraction() {
        tap(digit(8));
        tap(sub);
        tap(digit(3));
        tap(equals);

        Assert.assertEquals(dr.findElement(result).getText(), "5");
    }

    @Test
    public void testMultiplication() {
        tap(digit(4));
        tap(mul);
        tap(digit(5));
        tap(equals);

        Assert.assertEquals(dr.findElement(result).getText(), "20");
    }

    @Test
    public void testDivision() {
        tap(digit(9));
        tap(div);
        tap(digit(3));
        tap(equals);

        Assert.assertEquals(dr.findElement(result).getText(), "3");
    }

    //Screenshot on Failure
    @AfterMethod
    public void captureScreenshotOnFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            takeScreenshot(result.getName());
        }
    }
}
