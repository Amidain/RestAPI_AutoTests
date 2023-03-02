package pageObjects;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class AddProjectPage extends Form {
    private static final ISettingsFile CONFIG_READER = new JsonSettingsFile("config.json");
    private final ITextBox projectNameTextbox = AqualityServices.getElementFactory().getTextBox(By.id("projectName"), "Enter Project Name Text Box");
    private final IButton saveProjectButton = AqualityServices.getElementFactory().getButton(By.cssSelector(".btn.btn-primary"), "Save Project Button");
    private final ITextBox successAlertTextBox = AqualityServices.getElementFactory().getTextBox(By.cssSelector(".alert.alert-success"), "Success Alert Text Box");

    public AddProjectPage() {
        super(By.id("addProjectForm"), "Add Project Page");
    }

    public boolean isAddProjectPageDisplayed() {
        return state().isDisplayed();
    }

    public void addNewProject() {
        String projectName = CONFIG_READER.getValue("/current_project_name").toString();
        projectNameTextbox.sendKeys(projectName);
        saveProjectButton.click();
    }

    public boolean isProjectAddedSuccessfully(){
        return successAlertTextBox.getElement().isDisplayed();
    }
}

