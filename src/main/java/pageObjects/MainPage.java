package pageObjects;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.TextBox;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import java.util.List;

public class MainPage extends Form {
    private final IButton nexageButton = AqualityServices.getElementFactory().getButton(By.xpath("//div[@class='list-group']//*[text()='Nexage']"), "Nexage Button");
    private final IButton addProjectButton = AqualityServices.getElementFactory().getButton(By.cssSelector(".btn.btn-xs.btn-primary.pull-right"), "Add Project Button");
    private final ITextBox footerTextBox = AqualityServices.getElementFactory().getTextBox(By.className("container"), "Footer Text Box");
    private final ITextBox projectsContainer = AqualityServices.getElementFactory().getTextBox(By.className("list-group"), "Projects Coontainer");

    public MainPage() {
        super(By.xpath("//ol[contains(@class, 'breadcrumb')]//li[1]"), "Main Page");
    }

    public boolean isMainPageDisplayed() {
        return state().isDisplayed();
    }

    public String getVersionConfirmationTextBoxValue() {
        TextBox versionConfirmation = footerTextBox.findChildElement(By.tagName("span"), ElementType.TEXTBOX);
        return versionConfirmation.getText();
    }

    public void clickNexageButton() {
        nexageButton.click();
    }

    public void clickAddProjectButton() {
        addProjectButton.click();
    }

    public boolean isProjectDisplayed(String projectName){
        List<TextBox> projects = projectsContainer.findChildElements(By.className("list-group-item"), ElementType.TEXTBOX);
        for (TextBox tb: projects
             ) {
            if(tb.getText().equals(projectName)){
                return true;
            }
        }
        return false;
    }

    public void openProject(String projectName){
        String projectPath = String.format("//*[text()='%s']", projectName);
        projectsContainer.findChildElement(By.xpath(projectPath), ElementType.TEXTBOX).click();
    }


}
