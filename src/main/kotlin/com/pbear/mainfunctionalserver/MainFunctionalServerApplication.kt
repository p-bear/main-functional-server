package com.pbear.mainfunctionalserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MainFunctionalServerApplication

fun main(args: Array<String>) {
	runApplication<MainFunctionalServerApplication>(*args)
}
