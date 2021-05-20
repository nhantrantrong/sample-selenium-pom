package com.herokuapp.theinternet.base.testcases;

import com.herokuapp.theinternet.base.TestUtilities;
import com.herokuapp.theinternet.page.LoginPage;
import com.herokuapp.theinternet.page.SecureAreaPage;
import com.herokuapp.theinternet.page.WelcomePage;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest extends TestUtilities {

    @Parameters({"username", "password", "expectedMessage"})
    @Test(priority = 1)
    public void invalidLoginTest(String username, String password, String expectedErrorMessage) {
        log.info("Starting negativeTest");

        log.info("1. Open main page");
        WelcomePage welcomePage = new WelcomePage(driver, log);
        welcomePage.openPage();

        log.info("2. Click on Form Authentication link");
        LoginPage loginPage = welcomePage.clickFormAuthenticationLink();

        log.info("3. execute negative login");
        loginPage.login(username, password);

        log.info("4. wait for error message");
        loginPage.waitForErrorMessage();
        String message = loginPage.getErrorMessageText();

        log.info("Verify that error message should be returned correcrly");
        Assert.assertTrue(message.contains(expectedErrorMessage), "Message doesn't contain expected text.");
    }

    @Test(priority = 1)
    public void validLogInTest() {
        log.info("Starting logIn test");

        log.info("1. Open main page");
        WelcomePage welcomePage = new WelcomePage(driver, log);
        welcomePage.openPage();

        log.info("2. Click on Form Authentication link");
        LoginPage loginPage = welcomePage.clickFormAuthenticationLink();

        log.info("3. Execute log in");
        SecureAreaPage secureAreaPage = loginPage.loginSuccess("tomsmith", "SuperSecretPassword!");

        log.info("Verify that New Page url should be navigated to correctly");
        Assert.assertEquals(secureAreaPage.getPageUrl(), secureAreaPage.getPageUrl());

        log.info("Verify that log out button should be visible");
        Assert.assertTrue(secureAreaPage.isLogOutButtonVisible(), "LogOut Button is not visible.");

        log.info("Verify that Successful log in message should be displayed correctly");
        String expectedSuccessMessage = "You logged into a secure area!";
        String actualSuccessMessage = secureAreaPage.getSuccessMessageText();
        Assert.assertTrue(actualSuccessMessage.contains(expectedSuccessMessage),
                "actualSuccessMessage does not contain expectedSuccessMessage\nexpectedSuccessMessage: "
                        + expectedSuccessMessage + "\nactualSuccessMessage: " + actualSuccessMessage);
    }
}
