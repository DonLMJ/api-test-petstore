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

class PetsSpec extends AnyFunSpec with PublicApiAcceptanceTests with Matchers {
  override val apiVersion = "v2"
  val baseUrl = s"$publicApiUrl/pet"

  describe("Petstore Public APIs v0") {
    describe("GET pet/{id}") {
      val url = s"$baseUrl"
      it("should return 200 and return pet by ID") {
        val idPet = "9"
        val expectedResponse = s"""
                                  |{
                                  |    "id": 9,
                                  |    "category": {
                                  |        "id": 9,
                                  |        "name": "string"
                                  |    },
                                  |    "name": "sweety",
                                  |    "photoUrls": [
                                  |        "string"
                                  |    ],
                                  |    "tags": [
                                  |        {
                                  |            "id": 0,
                                  |            "name": "string"
                                  |        }
                                  |    ],
                                  |    "status": "available"
                                  |}
                                  |""".stripMargin


        val PetResult = given().get(url + s"/$idPet")
        val actualResponse = PetResult.Then().extract().response()
        PetResult.Then().assertThat().statusCode(200)
        actualResponse.asString().parseJson shouldBe expectedResponse.parseJson
        actualResponse.body().jsonPath().getString("id") shouldBe idPet

      }

      it("should return 404 NotFound if id not valid") {
        val idPetInvalid = "0"
        val expectedResponse = """
                                 |{
                                 |    "code": 1,
                                 |    "type": "error",
                                 |    "message": "Pet not found"
                                 |}
                                 |""".stripMargin

        val PetResult = given()
        .get(url + s"/$idPetInvalid")
        val actualResponse = PetResult.Then().extract().response()

        PetResult.Then().assertThat().statusCode(404)
        actualResponse.asString().parseJson shouldBe expectedResponse.parseJson


      }
    }

    describe("POST pet/{id}") {
      val url = s"$baseUrl"

      it("should return 200 and return the created") {
        val jsonRequest =
          s"""
             |{
             |  "id": 0,
             |  "category": {
             |    "id": 0,
             |    "name": "string"
             |  },
             |  "name": "doggie",
             |  "photoUrls": [
             |    "string"
             |  ],
             |  "tags": [
             |    {
             |      "id": 0,
             |      "name": "string"
             |    }
             |  ],
             |  "status": "available"
             |}
             |""".stripMargin

        given()
          .contentType("application/json")
          .when
          .body(jsonRequest)
          .post(url)
          .Then()
          .assertThat().statusCode(200)




      }
    }
  }

  override def specification: String = ???
}