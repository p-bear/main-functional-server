package com.pbear.mainfunctionalserver.common.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@Configuration
class ObjectMapperConfig {
    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(objectMapperModule())
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        return objectMapper
    }

    @Bean
    fun objectMapperModule(): Module {
        val module = JavaTimeModule()
        module.addSerializer(LocalDate::class.java, LocalDateSerializer())
        module.addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer())
        module.addDeserializer(LocalDate::class.java, LocalDateDeserializer())
        module.addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer())
        return module
    }
}

class LocalDateSerializer: StdSerializer<LocalDate>(LocalDate::class.java) {
    private val zoneId = ZoneId.systemDefault()
    override fun serialize(value: LocalDate?, gen: JsonGenerator, provider: SerializerProvider?) =
        value?.let {
            gen.writeNumber(value.atStartOfDay(zoneId).toInstant().toEpochMilli())
        } ?: run {
            gen.writeNull()
        }
}

class LocalDateTimeSerializer : StdSerializer<LocalDateTime>(LocalDateTime::class.java) {
    private val zoneId = ZoneId.systemDefault()
    override fun serialize(value: LocalDateTime?, gen: JsonGenerator, provider: SerializerProvider?) =
        value?.let {
            gen.writeNumber(value.atZone(zoneId).toInstant().toEpochMilli())
        } ?: run {
            gen.writeNull()
        }
}

class LocalDateDeserializer: StdDeserializer<LocalDate>(LocalDate::class.java) {
    private val zoneId = ZoneId.systemDefault()
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): LocalDate {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(p.text.toLong()), zoneId).toLocalDate()
    }

}

class LocalDateTimeDeserializer: StdDeserializer<LocalDateTime>(LocalDateTime::class.java) {
    private val zoneId = ZoneId.systemDefault()
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): LocalDateTime {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(p.text.toLong()), zoneId).toLocalDateTime()
    }
}