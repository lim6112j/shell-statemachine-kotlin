package com.ciel.shell.turnstile

import org.springframework.context.annotation.Configuration
import org.springframework.statemachine.config.EnableStateMachine
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer

import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import java.util.EnumSet
@Configuration
@EnableStateMachine
class StateMachineConfig : EnumStateMachineConfigurerAdapter<States, Events>(){
		override fun configure(states: StateMachineStateConfigurer<States, Events>){
				states
						.withStates()
						.initial(States.LOCKED)
						.states(EnumSet.allOf(States::class.java))
		}
		override fun configure(transition: StateMachineTransitionConfigurer<States, Events>){
				transition
						.withExternal()
						.source(States.LOCKED)
						.target(States.UNLOCKED)
						.event(Events.COIN)
						.and()
						.withExternal()
						.source(States.UNLOCKED)
						.target(States.LOCKED)
						.event(Events.PUSH)
		}
}
enum class States {
		LOCKED, UNLOCKED
}
enum class Events {
		COIN, PUSH
}
