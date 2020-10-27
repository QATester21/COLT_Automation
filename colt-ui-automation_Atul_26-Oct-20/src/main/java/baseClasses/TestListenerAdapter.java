package baseClasses;

import org.testng.*;

public class TestListenerAdapter implements IInvokedMethodListener, ITestListener {

	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {}

	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {}

	public void onTestStart(ITestResult iTestResult) {}

	public void onTestSuccess(ITestResult iTestResult) {}

	public void onTestFailure(ITestResult iTestResult) {}

	public void onTestSkipped(ITestResult iTestResult) {}

	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {}

	public void onStart(ITestContext iTestContext) {}

	public void onFinish(ITestContext iTestContext) {}
}
