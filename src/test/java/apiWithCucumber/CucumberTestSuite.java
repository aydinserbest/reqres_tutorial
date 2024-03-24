package apiWithCucumber;


import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberSerenityRunner;
import org.junit.runner.RunWith;

@RunWith(CucumberSerenityRunner.class)
@CucumberOptions(features = "src/test/resources/features",
glue = "apiWithCucumber")
public class CucumberTestSuite {
}
//,
//        snippets= CucumberOptions.SnippetType.CAMELCASE
