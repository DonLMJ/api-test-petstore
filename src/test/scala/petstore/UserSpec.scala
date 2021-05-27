package petstore

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
  describe("petstore.swagger.io public APIs") {
    //With REST Assured verify response body contents, HTTP response status code, the response content type, and other response headers
    val basicURL = "petstore.swagger.io"

    describe("GET /users") {


    }
  }

}
