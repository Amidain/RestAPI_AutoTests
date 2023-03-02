package util;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.net.InetAddress;

import static io.restassured.RestAssured.given;

public class ApiRequests {
    private static final ISettingsFile CONFIG_READER = new JsonSettingsFile("config.json");
    private static final ISettingsFile END_POINTS_READER = new JsonSettingsFile("end_points.json");
    private static RequestSpecification httpRequest = given();
    private static final String BASE_URL = CONFIG_READER.getValue("/base_URL").toString();

    public static Response getAuthToken() {
        String paramValue = CONFIG_READER.getValue("/task_variant").toString();
        String endPoint = END_POINTS_READER.getValue("/get_token").toString();

        return httpRequest
                .baseUri(BASE_URL)
                .given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("variant", paramValue)
                .when()
                .post(endPoint);
    }

    public static Response getTestsListJSON() {
        String projectId = CONFIG_READER.getValue("/project_ids/nexage").toString();
        String endPoint = END_POINTS_READER.getValue("/get_tests_list").toString();

        return httpRequest
                .baseUri(BASE_URL)
                .given()
                .log()
                .all()
                .contentType("application/x-www-form-urlencoded")
                .formParam("projectId", projectId)
                .when()
                .post(endPoint);
    }

    public static Response addTest(String testName, String methodName) throws IOException {
        String sessionId = AqualityServices.getBrowser().getDriver().getSessionId().toString();
        String projectName = CONFIG_READER.getValue("/current_project_name").toString();
        String hostname = InetAddress.getLocalHost().getHostName();
        String endPoint = END_POINTS_READER.getValue("/put_test").toString();

        return httpRequest
                .baseUri(BASE_URL)
                .given()
                .log()
                .all()
                .contentType("application/x-www-form-urlencoded")
                .formParam("SID", sessionId)
                .formParam("projectName", projectName)
                .formParam("testName", testName)
                .formParam("methodName", methodName)
                .formParam("env", hostname)
                .when()
                .post(endPoint);
    }

    public static Response addTestLog(String testId, String content) {
        String endPoint = END_POINTS_READER.getValue("/put_test_log").toString();

        return httpRequest
                .baseUri(BASE_URL)
                .given()
                .log()
                .all()
                .contentType("application/x-www-form-urlencoded")
                .formParam("testId", testId)
                .formParam("content", content)
                .when()
                .post(endPoint);
    }

    public static Response addAttachment(String testId, String content, String contentType) {
        String endPoint = END_POINTS_READER.getValue("/put_test_attachment").toString();

        return httpRequest
                .baseUri(BASE_URL)
                .given()
                .log()
                .all()
                .contentType("application/x-www-form-urlencoded")
                .formParam("testId", testId)
                .formParam("content", content)
                .formParam("contentType", contentType)
                .when()
                .post(endPoint);
    }
}
