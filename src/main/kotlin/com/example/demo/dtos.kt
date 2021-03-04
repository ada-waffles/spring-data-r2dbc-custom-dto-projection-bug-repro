package com.example.demo

import io.r2dbc.spi.Row
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter

data class AutoDto(val id: Int, val data: String, val tag: String) {
    init {
        println("AutoDto constructor called")
    }
}

//Using an abstract class to demonstrate the issue
//as Spring's reflection-driven conversion mechanism seems content to call a merely private constructor
abstract class CustomDto {
    abstract val id: Int
    abstract val data: String
    abstract val tag: String

    data class Instance(override val id: Int, override val data: String, override val tag: String): CustomDto()
}

@ReadingConverter
class CustomDtoReadConverter : Converter<Row, CustomDto> {
    override fun convert(source: Row): CustomDto {
        println("CustomDtoReadConverter called")
        return CustomDto.Instance(source.getCol("id")!!, source.getCol("data")!!, source.getCol("tag")!!)
    }
}

/** Alias of [Row.get] making use of Kotlin reified type parameters */
inline fun <reified T> Row.getCol(name: String) = get(name, T::class.java)
