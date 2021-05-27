package petstore.acceptancetests

import com.atlassian.oai.validator.OpenApiInteractionValidator
import com.atlassian.oai.validator.report.LevelResolverFactory
import com.atlassian.oai.validator.restassured.OpenApiValidationFilter
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.restassured.RestAssured
import io.swagger.util.Json

trait ApiAcceptanceTests {

  def specification: String
  def apiVersion: String
  def filterBasePath: String

  RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
  Json.mapper().registerModule(new JavaTimeModule())

  lazy val defaultFilter = new OpenApiValidationFilter(
    OpenApiInteractionValidator.createForInlineApiSpecification(specification).withBasePathOverride(filterBasePath).build()
  )

  lazy val filterIgnoringAdditionalProperties = new OpenApiValidationFilter(
    OpenApiInteractionValidator
      .createForInlineApiSpecification(specification)
      .withLevelResolver(LevelResolverFactory.withAdditionalPropertiesIgnored())
      .withBasePathOverride(filterBasePath)
      .build()
  )
}
