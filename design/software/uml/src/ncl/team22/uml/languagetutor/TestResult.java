package ncl.team22.uml.languagetutor;


public class TestResult extends Result
{
	int points;
	/**
	 * @uml.property  name="testResultManager"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="testResult:ncl.team22.uml.languagetutor.TestResultManager"
	 */
	private TestResultManager testResultManager = new ncl.team22.uml.languagetutor.TestResultManager();

	/**
	 * Getter of the property <tt>testResultManager</tt>
	 * @return  Returns the testResultManager.
	 * @uml.property  name="testResultManager"
	 */
	public TestResultManager getTestResultManager()
	{
		return testResultManager;
	}

	/**
	 * Setter of the property <tt>testResultManager</tt>
	 * @param testResultManager  The testResultManager to set.
	 * @uml.property  name="testResultManager"
	 */
	public void setTestResultManager(TestResultManager testResultManager)
	{
		this.testResultManager = testResultManager;
	}

}
