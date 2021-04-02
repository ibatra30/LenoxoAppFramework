package listeners;


import java.util.Set;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import static org.testng.Reporter.log;

public class TestListener implements ITestListener {
    private static String logFile = null;

    @Override
    public void onFinish(ITestContext context) {
        Set<ITestResult> failedTests = context.getFailedTests().getAllResults();
        for (ITestResult temp : failedTests) {
            ITestNGMethod method = temp.getMethod();
            if (context.getFailedTests().getResults(method).size() > 1) {
                failedTests.remove(temp);
            } else {
                if (context.getPassedTests().getResults(method).size() > 0) {
                    failedTests.remove(temp);
                }
            }
        }
    }

    public void onTestStart(ITestResult result) {
        if (logFile == null) {
            logFile = ".log";
        }

        log("\n---------------------------------- Test '" + result.getName() + "' ----------------------------------\n");
        log("    ***Test Parameters = " + getTestParams(result) + "\n");


    }

    /**
     * getTestParams method
     *
     * @param tr
     * @return String
     */
    public String getTestParams(ITestResult tr) {
        int iLength = tr.getParameters().length;
        String message = "";

        try {
            if (tr.getParameters().length > 0) {
                message = tr.getParameters()[0].toString();

                for (int iCount = 0; iCount < iLength; iCount++) {
                    if (iCount == 0) {
                        message = tr.getParameters()[0].toString();
                    } else {
                        message = message + ", " + tr.getParameters()[iCount].toString();
                    }
                }
            }
        } catch (Exception e) {
            // do nothing...
        }

        return message;
    }

    public void onTestSuccess(ITestResult result) {

    }

    public void onTestFailure(ITestResult result) {

    }

    public void onTestSkipped(ITestResult result) {
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onStart(ITestContext context) {

    }
}  


