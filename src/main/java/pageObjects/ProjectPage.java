package pageObjects;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.TextBox;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import model.TestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectPage extends Form {

    private final ITextBox testContainer = AqualityServices.getElementFactory().getTextBox(By.className("table"), "Test Container");
    private final IButton homeButton = AqualityServices.getElementFactory().getButton(By.xpath("//ol[contains(@class, 'breadcrumb')]//li[1]"), "Return to Home Page Button");

    public ProjectPage(String name) {
        super(By.xpath("//ol[contains(@class, 'breadcrumb')]//li[2]"), name);
    }

    public boolean isNexageProjectPageDisplayed() {
        return state().waitForDisplayed();
    }

    public List<TestCase> getTestCases() {
        List<ITextBox> elements = testContainer.findChildElements(By.tagName("tr"), ElementType.TEXTBOX);
        List<ITextBox> subElements;
        List<TestCase> tests = new ArrayList<>();

        for (int i = 1; i < elements.size(); i++) {
            subElements = elements.get(i).findChildElements(By.tagName("td"), ElementType.TEXTBOX);
            tests.add(new TestCase(
                    subElements.get(5).getText(),
                    subElements.get(1).getText(),
                    subElements.get(0).getText(),
                    subElements.get(3).getText(),
                    subElements.get(4).getText(),
                    subElements.get(2).getText()
            ));
        }
        return tests;
    }

    public void clickHomeButton() {
        homeButton.click();
    }

    public boolean isTestInList(String methodName) {
        AqualityServices.getConditionalWait().waitFor(ExpectedConditions.invisibilityOf(AqualityServices.getElementFactory().getTextBox(By.cssSelector(".alert.alert-danger"), "Alert").getElement()));
        List<TextBox> tests = testContainer.findChildElements(By.tagName("tr"), ElementType.TEXTBOX);
        List<TextBox> subElements;

        for (int i = 0; i < tests.size(); i++) {
            subElements = tests.get(i).findChildElements(By.tagName("td"), ElementType.TEXTBOX);
            if (subElements.size() != 0) {
                for (TextBox tb : subElements
                ) {
                    if (tb.getText().contains(methodName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
