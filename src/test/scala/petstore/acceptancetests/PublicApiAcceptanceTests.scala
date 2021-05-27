package petstore.acceptancetests

trait PublicApiAcceptanceTests extends ApiAcceptanceTests {

    lazy val publicApiUrl: String = Endpoints.PUBLIC_API(apiVersion)

    override lazy val filterBasePath = s"/$apiVersion"
}


