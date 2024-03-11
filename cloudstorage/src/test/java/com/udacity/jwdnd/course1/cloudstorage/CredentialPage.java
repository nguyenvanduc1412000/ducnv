package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialPage {
    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(id = "btn-add-credential")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement url;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "btn-credential-save")
    private WebElement submitCredentialButton;

    private WebDriver webDriver;


    public CredentialPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickCredentialTab() {
        this.credentialTab.click();
    }

    public void addCredential(String url, String username, String password) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        clickCredentialTab();
        wait.until(ExpectedConditions.elementToBeClickable(this.addCredentialButton));
        this.addCredentialButton.click();
        wait.until(ExpectedConditions.visibilityOf(this.url));
        this.url.sendKeys(url);
        this.credentialUsername.sendKeys(username);
        this.credentialPassword.sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(this.submitCredentialButton));
        this.submitCredentialButton.click();
    }


    public void editCredential(String url, String username, String password) throws InterruptedException {
        this.credentialTab.click();
        WebElement editButton = this.webDriver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[1]/button"));
        WebDriverWait wait = new WebDriverWait(webDriver, 5);

        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        editButton.click();

        wait.until(ExpectedConditions.visibilityOf(this.url));
        this.url.clear();
        this.url.sendKeys(url);

        credentialUsername.clear();
        credentialUsername.sendKeys(username);
        credentialPassword.clear();
        credentialPassword.sendKeys(password);

        this.submitCredentialButton.click();
    }

    public void deleteCredential() throws InterruptedException {
        this.credentialTab.click();
        WebElement deleteButton = this.webDriver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[1]/a"));
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        deleteButton.click();
    }
}
