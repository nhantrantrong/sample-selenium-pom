package com.herokuapp.theinternet.page;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePageObject {

    private By userNameLocator = By.id("username");
    private By passwordLocator = By.id("password");
    private By loginButtonLocator = By.tagName("button");
    private By errorMessageLocator = By.id("flash");

    public LoginPage(WebDriver drive, Logger log) {
        super(drive, log);
    }

    public void login(String username, String password) {
        log.info("Login with username [" + username + "] and password [" + password + "]");
        type(userNameLocator, username);
        type(passwordLocator, password);
        click(loginButtonLocator);
    }

    public SecureAreaPage loginSuccess(String username, String password) {
        login(username, password);
        return new SecureAreaPage(driver, log);
    }

    public void waitForErrorMessage() {
        waitForVisibilityOf(errorMessageLocator, 5);
    }

    public String getErrorMessageText() {
        return find(errorMessageLocator).getText();
    }
}
