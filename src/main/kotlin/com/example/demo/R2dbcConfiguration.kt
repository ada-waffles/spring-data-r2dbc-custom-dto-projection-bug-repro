package com.example.demo

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration

@Configuration
class R2dbcConfiguration(private val connectionFactory: ConnectionFactory) : AbstractR2dbcConfiguration() {
    override fun connectionFactory() = connectionFactory

    override fun getCustomConverters() = listOf(CustomDtoReadConverter())
}
