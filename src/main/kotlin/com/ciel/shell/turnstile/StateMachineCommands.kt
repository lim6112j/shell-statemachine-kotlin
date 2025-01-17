package com.ciel.shell.turnstile

import org.springframework.stereotype.Component
import org.springframework.shell.standard.ShellOption
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.statemachine.StateMachine
import org.springframework.messaging.support.MessageBuilder
import reactor.core.publisher.Mono

@ShellComponent
class StateMachineCommands(
		@Autowired private val stateMachine: StateMachine<States, Events>
) {
		@ShellMethod(key = arrayOf("sm start"))
		fun start(@ShellOption(defaultValue = "spring") arg: String): String {
        stateMachine.startReactively().subscribe()
				return "State machine started"
		}
		@ShellMethod(key = arrayOf("sm event"))
		fun event(@ShellOption(defaultValue = "spring") event: Events): String {
				stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(event).build())).subscribe()
				return "Event " + event + " sent"
		}
}
