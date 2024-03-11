package com.udacity.jwdnd.course1.cloudstorage;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotePage {

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "btn-add-note")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "btn-note-submit")
    private WebElement submitNoteButton;

    @FindBy(id = "btn-delete-note")
    private WebElement deleteNoteButton;

    @FindBy(id = "btn-edit-note")
    private WebElement editNoteButton;

    private WebDriver webDriver;

    public NotePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickNoteTab() {
        this.noteTab.click();
    }
    public void addNote(String noteTitle, String noteDescription) {
        clickNoteTab();
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-add-note")));
        this.addNoteButton.click();

        wait.until(ExpectedConditions.visibilityOf(this.noteTitle));
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.sendKeys(noteDescription);
        this.submitNoteButton.click();
    }

    public void editNote(String noteTitle, String noteDescription) {
        clickNoteTab();
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-edit-note")));
        editNoteButton.click();
        wait.until(ExpectedConditions.visibilityOf(this.noteTitle));
        this.noteTitle.clear();
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.clear();
        this.noteDescription.sendKeys(noteDescription);
        this.submitNoteButton.click();
    }

    public void deleteNote() throws InterruptedException {
        clickNoteTab();
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(deleteNoteButton));
        deleteNoteButton.click();
    }

}
