package webApiTest;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import model.TestCase;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AddProjectPage;
import pageObjects.MainPage;
import pageObjects.ProjectPage;
import util.ApiRequests;
import util.JsonValidator;
import util.StringLogGenerator;
import util.TestsVerification;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class WebApiTest extends BaseTest{
    private static final ISettingsFile CONFIG_READER = new JsonSettingsFile("config.json");
    @Test
    void passAuthorizationExtractDataAndAddProject() throws IOException, InterruptedException {
        MainPage mainPage = new MainPage();
        ProjectPage nexageProjectPage = new ProjectPage("Nexage Project Page");
        ProjectPage userCustomProjectPage = new ProjectPage("User Custom Project Page");
        AddProjectPage addProjectPage = new AddProjectPage();

        Response rs = ApiRequests.getTestsListJSON();
        Assert.assertTrue(JsonValidator.isValid(rs.getBody().asString()), "Returned response body do not have JSON format!");
        ObjectMapper objectMapper = new ObjectMapper();
        TestCase[] testCases = objectMapper.readValue(rs.getBody().asString(), TestCase[].class);
        List<TestCase> apiResponseTests = Arrays.asList(testCases);
        AqualityServices.getBrowser().waitForPageToLoad();
        Assert.assertTrue(mainPage.isMainPageDisplayed(), "Main Page has not been displayed!");
        String token = ApiRequests.getAuthToken().getBody().asString();
        AqualityServices.getBrowser().getDriver().manage().addCookie(new Cookie("token", token));
        AqualityServices.getBrowser().refresh();
        AqualityServices.getBrowser().waitForPageToLoad();
        String expectedVersion = String.format("Version: %s", CONFIG_READER.getValue("/task_variant").toString());
        Assert.assertEquals(mainPage.getVersionConfirmationTextBoxValue(), expectedVersion, "Variant version is not as expected!");
        mainPage.clickNexageButton();
        Assert.assertTrue(nexageProjectPage.isNexageProjectPageDisplayed(), "Nexage Project Page has not been displayed!");
        List<TestCase> webPageTests = nexageProjectPage.getTestCases();
        Assert.assertTrue(TestsVerification.areTestCasesSortedDescendingByDate(webPageTests), "Tests are not sorted in descending order!");
        Assert.assertTrue(TestsVerification.doesApiTestListContainWebTestListElements(apiResponseTests, webPageTests), "API tests list does not contain values from the list retrieved from Web!");
        nexageProjectPage.clickHomeButton();
        Assert.assertTrue(mainPage.isMainPageDisplayed(), "Main Page has not been displayed!");
        mainPage.clickAddProjectButton();
        AqualityServices.getBrowser().tabs().switchToLastTab();
        Assert.assertTrue(addProjectPage.isAddProjectPageDisplayed(), "Add Project Page has not been displayed!");
        addProjectPage.addNewProject();
        Assert.assertTrue(addProjectPage.isProjectAddedSuccessfully(), "Project has not been added successfully!");
        AqualityServices.getBrowser().tabs().closeTab();
        AqualityServices.getBrowser().tabs().switchToTab(0);
        AqualityServices.getBrowser().refresh();
        String projectName = CONFIG_READER.getValue("/current_project_name").toString();
        Assert.assertTrue(mainPage.isProjectDisplayed(projectName), "Project has not been added to the list, hence it is not visible!");
        mainPage.openProject(projectName);
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        String className =  this.getClass().getSimpleName();
        Response addProjectRs = ApiRequests.addTest(className, methodName);
        String testId = addProjectRs.getBody().asString();
        final int LOG_LENGTH = 18;
        ApiRequests.addTestLog(testId, StringLogGenerator.generateLog(LOG_LENGTH));
        ApiRequests.addAttachment(testId, AqualityServices.getBrowser().getDriver().getScreenshotAs(OutputType.BASE64), "image/png");
        Assert.assertTrue(userCustomProjectPage.isTestInList(methodName), "New test is not visible on the list!");
    }
}
