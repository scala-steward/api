package com.foreignlanguagereader.api.controller.v1.language

import com.foreignlanguagereader.api.controller.v1.PlaySpec
import com.foreignlanguagereader.content.types.Language
import com.foreignlanguagereader.content.types.internal.word.Word
import com.foreignlanguagereader.domain.metrics.MetricsReporter
import com.foreignlanguagereader.domain.metrics.label.RequestPath
import com.foreignlanguagereader.domain.service.definition.DefinitionService
import io.prometheus.client.Histogram
import org.mockito.{Mockito, MockitoSugar}
import org.scalatest.Outcome
import play.api.Application
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.Future

class DefinitionControllerSpec extends PlaySpec with MockitoSugar {

  val mockMetricsReporter: MetricsReporter = mock[MetricsReporter]
  val mockDefinitionService: DefinitionService = mock[DefinitionService]

  val app: Application = new GuiceApplicationBuilder()
    .bindings(bind[MetricsReporter].toInstance(mockMetricsReporter))
    .bindings(bind[DefinitionService].toInstance(mockDefinitionService))
    .build()

  override def withFixture(test: NoArgTest): Outcome = {
    Mockito.reset(mockMetricsReporter)
    super.withFixture(test)
  }

  "Definitions endpoints" should {
    val goodRequest = "/v1/language/definition/SPANISH/test/"
    val badLanguageRequest = "/v1/language/definition/ELEPHANT/test/"

    "get good requests from the router" in {
      when(
        mockDefinitionService
          .getDefinition(
            Language.SPANISH,
            Language.ENGLISH,
            Word.fromToken("test", Language.SPANISH)
          )
      ).thenReturn(Future.successful(List()))

      val mockTimer = Some(mock[Histogram.Timer])
      when(
        mockMetricsReporter.reportRequestStarted("GET", RequestPath.DEFINITIONS)
      ).thenReturn(mockTimer)

      val request = FakeRequest(GET, goodRequest)
      val goodResponse = route(app, request).get

      status(goodResponse) mustBe OK
      contentAsString(goodResponse) must include("[]")

      verify(mockMetricsReporter)
        .reportRequestStarted("GET", RequestPath.DEFINITIONS)
      verify(mockMetricsReporter)
        .reportRequestFinished(mockTimer)
    }

    "appropriately handle bad requests from the router" in {
      when(
        mockDefinitionService
          .getDefinition(
            Language.SPANISH,
            Language.ENGLISH,
            Word.fromToken("test", Language.SPANISH)
          )
      ).thenReturn(Future.successful(List()))

      val mockTimer = Some(mock[Histogram.Timer])
      when(
        mockMetricsReporter.reportRequestStarted("GET", RequestPath.DEFINITIONS)
      ).thenReturn(mockTimer)

      val request = FakeRequest(GET, badLanguageRequest)
      val badResponse = route(app, request).get

      status(badResponse) mustBe 400
      contentAsString(badResponse) must include("{\"message\":\"ELEPHANT\"}")

      verify(mockMetricsReporter)
        .reportRequestStarted("GET", RequestPath.DEFINITIONS)
      verify(mockMetricsReporter)
        .reportRequestFinished(mockTimer)
    }
  }
}
