package petstore.acceptancetests

object Endpoints {
  def PUBLIC_API(apiVersion: String) = s"https://petstore.swagger.io/$apiVersion"
}
