package com.example.demo

import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@Component
class DemoStartupListener(private val demoRepository: DemoRepository) {
    @EventListener
    fun onApplicationStarted(event: ApplicationStartedEvent) = runBlocking<Unit> {
        //Insert a row so there's data to retrieve
        println("Initializing...")
        demoRepository.save(DemoEntity(id = 1, data = "test"))

        //Sanity check: Can we get the entity?
        println("Checking inserted entity...")
        assert(demoRepository.findAll().single().id == 1)

        //Try getting the automatically instantiated DTO projection
        println("Getting automatic DTO projection...")
        demoRepository.getAutoProjections().single().run {
            assert(id == 1)
            assert(tag == "auto")
        }

        //Try getting the DTO projection with a custom converter
        println("Getting custom DTO projection...")
        demoRepository.getCustomProjections().single().run {
            assert(id == 1)
            assert(tag == "custom")
        }
    }
}
