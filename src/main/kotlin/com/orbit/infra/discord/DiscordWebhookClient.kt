package com.orbit.infra.discord

import com.orbit.logger
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class DiscordWebhookClient(
    private val discordWebhookProperties: DiscordWebhookProperties
) {
    private val log = logger()
    private val webClient: WebClient = WebClient.builder()
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .filter(ExchangeFilterFunction.ofRequestProcessor { request ->
            log.info("[Discord Webhook Request] ${request.url()}")
            Mono.just(request)
        })
        .filter(ExchangeFilterFunction.ofResponseProcessor { response ->
            if (response.statusCode().isError) {
                log.error("[Discord Webhook Error] ${response.statusCode()}")
                return@ofResponseProcessor Mono.error(
                    IllegalArgumentException("Discord webhook error: ${response.statusCode()}")
                )
            }
            log.info("[Discord Webhook Response] Success")
            Mono.just(response)
        })
        .build()

    fun sendMessage(message: String) {
        webClient.post()
            .uri(discordWebhookProperties.url)
            .bodyValue(mapOf("content" to message))
            .retrieve()
            .toBodilessEntity()
            .block()
    }
}