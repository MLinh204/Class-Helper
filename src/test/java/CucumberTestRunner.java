import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.example.Class_Helper.Tests",
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
}
