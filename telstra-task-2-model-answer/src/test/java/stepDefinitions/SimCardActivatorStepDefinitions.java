package stepDefinitions;

import au.com.telstra.simcardactivator.component.DatabaseConduit;
import au.com.telstra.simcardactivator.component.SimCardActuationHandler;
import au.com.telstra.simcardactivator.foundation.ActuationResult;
import au.com.telstra.simcardactivator.foundation.SimCard;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

    public class SimCardActivatorStepDefinitions {

        private SimCard simCard;
        private ActuationResult actuationResult;

        @Autowired
        private SimCardActuationHandler simCardActuationHandler;

        @Autowired
        private DatabaseConduit databaseConduit;

        @Given("a sim card with ICCID {string} and customer email {string}")
        public void createSimCard(String iccid, String customerEmail) {
            simCard = new SimCard(iccid, customerEmail, false);
        }

        @When("the sim card is activated")
        public void activateSimCard() {
            actuationResult = simCardActuationHandler.actuate(simCard);
            databaseConduit.save(simCard, actuationResult);
        }

        @Then("the activation result should be successful")
        public void checkSuccessfulActivation() {
            assertEquals(true, actuationResult.getSuccess());
        }

        @Then("the activation result should be unsuccessful")
        public void checkUnsuccessfulActivation() {
            assertEquals(false, actuationResult.getSuccess());
        }



}