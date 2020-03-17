package com.sandrocaseiro.apitemplate.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    monochrome = true,
    plugin = {"pretty", "html:target/cucumber"},
    strict = true)
public class CucumberIT {

}
