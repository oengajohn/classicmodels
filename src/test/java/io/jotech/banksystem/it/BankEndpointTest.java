package io.jotech.banksystem.it;

import java.math.BigDecimal;
import java.net.URL;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import classicmodels.util.ArquillianWarUtils;
import io.jotech.banksystem.BankSystem;
import io.jotech.banksystem.rest.BankEndpoint;
import io.jotech.banksystem.subsystems.CreditRatingService;
import io.jotech.banksystem.subsystems.InterBankPolicyService;
import io.jotech.banksystem.subsystems.RepaymentPayabilityService;
import io.jotech.banksystem.subsystems.RepaymentService;
import io.jotech.banksystem.subsystems.TransferService;
import io.jotech.classicmodels.entity.Customer;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@ExtendWith(ArquillianExtension.class)
@Slf4j
public class BankEndpointTest {
    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive webArchive = ArquillianWarUtils
                .getBasicWebArchive("BankSystemItTest.war")
                .addClasses(
                        CreditRatingService.class,
                        RepaymentPayabilityService.class,
                        InterBankPolicyService.class,
                        TransferService.class,
                        RepaymentService.class,
                        BankSystem.class,
                        BankEndpoint.class

                )
                .addPackages(true,"io.jotech.classicmodels.entity");
        log.info(webArchive.toString(true));
        return webArchive;
    }
    @ArquillianResource
    private URL base;

    @Test
    @DisplayName("processLoanApplication")
    @Disabled
    void processLoanApplication() {

        var customer = Customer.builder()
                .customerNumber(1)
                .customerName("Duncan")
                .city("Nairobi")
                .creditLimit(BigDecimal.valueOf(600_000))
                .build();
        var principle = BigDecimal.valueOf(450_000);
        var income1 = BigDecimal.valueOf(50_000);
        var income2 = BigDecimal.valueOf(300_000);
        var months = 6;

        given().body(customer)
                .header("Content-Type", "application/json")
                .accept("application/json")
                .pathParam("principle",principle)
                .pathParam("income",income1)
                .pathParam("months",months)
                .log().all()
                .when()
                .post(base+"/api/bank/{principle}/{income}/{months}")
                .then()
                .log()
                .all();
//                .assertThat()
//                .body(
//                        "success",equalTo(false)
//                ).
//                statusCode(Response.Status.OK.getStatusCode());

        given().body(customer)
                .header("Content-Type", "application/json")
                .accept("application/json")
                .pathParam("principle",principle)
                .pathParam("income",income2)
                .pathParam("months",months)
                .log().all()
                .when()
                .post(base+"/api/bank/{principle}/{income}/{months}")
                .then()
                .log()
                .all()
                .assertThat()
                .body(
                        "success",equalTo(true)
                ).
                statusCode(Response.Status.OK.getStatusCode());

    }
}