package util;

import model.TestCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestsVerification {
    public static boolean doesApiTestListContainWebTestListElements(List<TestCase> apiList, List<TestCase> webList) {
        for (TestCase tc : webList
        ) {
            if (!apiList.contains(tc)) {
                return false;
            }
        }
        return true;
    }

    public static boolean areTestCasesSortedDescendingByDate(List<TestCase> testCases) {
        final int MAX_ITERATIONS = testCases.size() - 1;
        int iterator = 1;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:SS");

        try {
            Date dateTime1 = formatter.parse(testCases.get(iterator - 1).getStartTime());
            Date dateTime2 = formatter.parse(testCases.get(iterator).getStartTime());

            while (iterator < MAX_ITERATIONS) {
                if (dateTime1.before(dateTime2)) {
                    return false;
                }
                iterator++;
                dateTime1 = dateTime2;
                dateTime2 = formatter.parse(testCases.get(iterator).getStartTime());
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
