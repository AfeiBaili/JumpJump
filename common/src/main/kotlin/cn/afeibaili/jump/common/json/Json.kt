package cn.afeibaili.jump.common.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

val JSON_MAPPER: ObjectMapper = ObjectMapper().registerModule(KotlinModule.Builder().build())