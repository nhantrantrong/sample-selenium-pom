package com.herokuapp.theinternet.page;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageObject {
    protected WebDriver driver;
    protected Logger log;

    public BasePageObject(WebDriver driver, Logger log) {
        this.driver = driver;
        this.log = log;
    }

    /**
     * Open page with given url
     *
     * @param url
     */
    protected void openUrl(String url) {
        driver.get(url);
    }

    /**
     * Find and return WebElement by given locator
     *
     * @param locator
     */
    protected WebElement find(By locator) {
        return driver.findElement(locator);
    }


    /**
     * Wait for WebElement which is visible, then click on
     *
     * @param locator
     */
    protected void click(By locator) {
        waitForVisibilityOf(locator);
        find(locator).click();
    }

    /**
     * Wait for WebElement which is visible, then type the given text into
     *
     * @param locator
     */
    protected void type(By locator, String text) {
        waitForVisibilityOf(locator);
        find(locator).sendKeys(text);
    }

    /**
     * Wait for given Element Condition within expected timeOutInSeconds
     * @param condition
     * @param timeOutInSeconds
     */
    private void waitFor(ExpectedCondition<WebElement> condition, Integer timeOutInSeconds) {
        timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds : 30;
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(condition);
    }

    /**
     * Wait for WebElement Visible within expected timeOutInSeconds
     *
     * @param locator
     */
    protected void waitForVisibilityOf(By locator, Integer... timeOutInSeconds) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                waitFor(ExpectedConditions.visibilityOfElementLocated(locator),
                        (timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
    }
}
