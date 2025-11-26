package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = { "@target/failedrerun.txt" },
        glue = {"stepDef.common", "stepDef.login", "stepDef.registration", "appHooks"}, // path of step definition
        plugin = {"pretty",
                "rerun:target/failedrerun.txt"},
        monochrome =true
)
public class FailedTestcaseRunner extends AbstractTestNGCucumberTests {
}
