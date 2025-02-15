package com.example.demo.simulations;




import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class TransactionSimulation extends Simulation {

    // 定义 HTTP 配置
    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080") // 测试的目标 URL
            .acceptHeader("application/json");

    // 定义测试场景
    private ScenarioBuilder scn = scenario("Transaction Load Test")
            .exec(
                    http("Create Transaction")
                            .post("/transactions")
                            .body(StringBody("{\"type\":\"deposit\",\"amount\":100.0}"))
                            .asJson()
                            .check(status().is(200)))
                            .pause(1)
                            .exec(
                                    http("Get All Transactions")
                                            .get("/transactions")
                                            .check(status().is(200)))
                            .pause(1)
                            .exec(
                                    http("Delete Transaction")
                                            .delete("/transactions/1000")
                                            .check(status().is(404)));

    // 设置负载模型
    {
        setUp(
                scn.injectOpen(
                        constantUsersPerSec(10).during(30) // 每秒 10 个用户，持续 30 秒
                ).protocols(httpProtocol));
    }
}