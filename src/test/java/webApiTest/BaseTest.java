package webApiTest;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public abstract class BaseTest {
    private final ISettingsFile CONFIG_READER = new JsonSettingsFile("config.json");
    private final String USERNAME = CONFIG_READER.getValue("/credentials/username").toString();
    private final String PASSWORD = CONFIG_READER.getValue("/credentials/password").toString();
    private final String BASE_URL = CONFIG_READER.getValue("/auth_URL").toString();
    private final String URL = String.format(BASE_URL, USERNAME, PASSWORD);

    @BeforeMethod
    void setup(Method method) {
        AqualityServices.getBrowser().goTo(URL);
        AqualityServices.getBrowser().maximize();
    }

    @AfterMethod
    void tearDown() {
        if (AqualityServices.isBrowserStarted()) {
            AqualityServices.getBrowser().quit();
        }
    }
}
