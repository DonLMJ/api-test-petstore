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

class UserSpec extends AnyFunSpec with PublicApiAcceptanceTests with Matchers {
  override val apiVersion = "v2"
  val baseUrl = s"$publicApiUrl/user"

  describe("Petstore Public APIs v0") {
    describe("GET /user/user1") {
      val url = s"$baseUrl/inventory"
      it("should return 200 and return the inventory") {

        val InventoryResult = given().get(url)

        InventoryResult.Then().assertThat().statusCode(200)

      }
    }



  }

  override def specification: String = ???
}
