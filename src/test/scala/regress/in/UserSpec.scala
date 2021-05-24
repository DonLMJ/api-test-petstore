package regress.in

import io.restassured.RestAssured.given
import io.qameta.allure.scalatest.AllureScalatestContext
import io.restassured.http.ContentType
import org.mockito.scalatest.MockitoSugar
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import org.hamcrest.Matchers.hasSize
import org.hamcrest.core.IsEqual.equalTo


class UserSpec extends AnyFunSpec with Matchers with ScalaFutures with MockitoSugar with AllureScalatestContext {


  //API used by REST Assured supports the familiar Given/When/Then syntax from behavior-driven development (BDD)
  describe("regres.in public APIs") {
    //With REST Assured verify response body contents, HTTP response status code, the response content type, and other response headers
    val basicURL = "https://reqres.in"

    describe("GET /users") {

      val pageNumber = "2"
      val idNumber = "7"
      val expectedName = "Michael"
      val expectedLastName = "Lawson"

      it("should return list of users at given page in JSON format") {
        given()
          .param("page", pageNumber)
          .when
          .get(basicURL + "/api/users")
          .Then()
          .assertThat()
          .statusCode(200)
          .and()
          .contentType(ContentType.JSON)
      }

      it("should return status code 200 when successful") {
        given()
          .param("page", pageNumber)
          .when
          .get(basicURL + "/api/users")
          .Then()
          .assertThat()
          .statusCode(200)
      }

      it("should 6 users per page") {
        given()
          .param("page", pageNumber)
          .when
          .get(basicURL + "/api/users")
          .Then()
          .assertThat()
          .body("data.id", hasSize(6))
      }

      it("should allow everything in CORS") {
        given()
          .when
          .get(basicURL + "/api/users")
          .Then()
          .assertThat()
          .header("Access-Control-Allow-Origin", equalTo("*"))
      }

      it("should have name and surname of the given user") {
        //using query parameters, these are appended at the end of a RESTful API endpoint and can be identified by the question mark in front of them


        given()
          .param("page", pageNumber)
          .param("id", idNumber)
          .when()
          .get(basicURL + "/api/users")
          .Then()
          .assertThat()
          .body("data.first_name", equalTo(expectedName))
          .body("data.last_name", equalTo(expectedLastName))
      }

      it("should get 404 for not present userid") {
        given()
          .when()
          .get(basicURL + "/api/users/23")
          .Then()
          .statusCode(404)
      }
    }

    describe("POST /users") {

      it("should create user successfully and send 200") {
        given()
          .body(
            """{
              |  "name": chaya,
              |  "job": BAA
              |}""")
          .when
          .post(basicURL + "/api/users")
          .Then()
          .statusCode(201)
      }

    }

    describe("PUT /users") {

      it("should update user successfully") {

        given()
          .body(
            """{
              |  "name": chaya,
              |  "job": AZZZ
              |}""")
          .when
          .put("https://reqres.in/api/users/2")
          .Then()
          .statusCode(200)
      }
    }

    describe("DELETE /users") {

      it("should delete user successfully") {

        given()
          .when
          .delete("https://reqres.in/api/users/2")
          .Then()
          .statusCode(204)
      }

    }
  }

}
