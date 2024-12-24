package com.orbit.infra.discord

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "discord.webhook")
data class DiscordWebhookProperties(
    var url: String = ""
)