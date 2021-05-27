package petstore.acceptancetests.publicapi.pet

import io.restassured.RestAssured.`given`
import org.scalatest.matchers.should.Matchers
import org.mockito.scalatest.MockitoSugar
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import io.restassured.response.Response
import org.scalatest.compatible.Assertion
import org.scalatest.funspec.AnyFunSpec
import spray.json._
import scala.jdk.CollectionConverters.MapHasAsJava
import scala.util.Try


import petstore.acceptancetests.PublicApiAcceptanceTests
import org.scalatest.funspec.AnyFunSpec

class StoreSpec extends AnyFunSpec with PublicApiAcceptanceTests with Matchers {
  override val apiVersion = "v2"
  val baseUrl = s"$publicApiUrl/store"

  describe("Petstore Public APIs v0") {
    describe("GET /store/inventory") {
      val url = s"$baseUrl/inventory"
      it("should return 200 and return the inventory") {

        val InventoryResult = given().get(url)

        InventoryResult.Then().assertThat().statusCode(200)

      }
    }
  }

  describe("Petstore Public APIs v0") {
    describe("POST /store/order") {
      val url = s"$baseUrl/order"
      it("should return 200 and place the order") {
        val idOrder = "9223372036854775807"
        val jsonRequest =
          s"""
             |{
             |  "id": 0,
             |  "petId": 0,
             |  "quantity": 0,
             |  "shipDate": "2021-05-27T17:10:40.676Z",
             |  "status": "placed",
             |  "complete": true
             |}
             |""".stripMargin

        val InventoryResult = given()
          .contentType("application/json")
          .when
          .body(jsonRequest)
          .post(url)
          .Then()
          .assertThat().statusCode(200)
          .extract().response().body().jsonPath().getString("id") shouldBe idOrder

      }
    }
  }

  describe("Petstore Public APIs v0") {
    describe("GET /store/order/{orderid}") {
      val idOrder = "9"
      val idOrderWrong = "1234567890"
      val idPet = "123"

      it("should return purchase order and return 200") {

        val url = s"$baseUrl/order/$idOrder"

        val orderResponse = given().get(url)
        val responseBody = orderResponse.body()

        orderResponse
          .Then()
          .assertThat()
          .statusCode(200)

        responseBody
          .jsonPath().getString("id") shouldBe idOrder

        responseBody
          .jsonPath().getString("petId") shouldBe idPet
      }

      it("should return 404 if no id valid") {
        val url = s"$baseUrl/order/$idOrderWrong"

        val orderResponse = given().when.get(url)
        val responseBody = orderResponse.body()

        orderResponse
          .Then()
          .assertThat()
          .statusCode(404)

        responseBody
          .jsonPath().getString("message") shouldBe ("Order not found")

      }

      it("should return 405 if no id provide at all") {
        val url = s"$baseUrl/order/"

        val orderResponse = given().when.get(url)

        orderResponse
          .Then()
          .assertThat()
          .statusCode(405)


      }
    }
  }

  override def specification: String = ???
}
