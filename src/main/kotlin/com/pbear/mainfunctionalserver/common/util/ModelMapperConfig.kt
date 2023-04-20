package com.pbear.mainfunctionalserver.common.util

import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ModelMapperConfig {
    @Bean
    fun modelMapper() = ModelMapper().apply {
        configuration.matchingStrategy = MatchingStrategies.LOOSE
        configuration.isFieldMatchingEnabled = true
        configuration.fieldAccessLevel = org.modelmapper.config.Configuration.AccessLevel.PRIVATE
        configuration.isSkipNullEnabled = true
    }
}