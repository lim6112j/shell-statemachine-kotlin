package com.ciel.shell.turnstile

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.statemachine.config.EnableStateMachine
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer

import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer
import org.springframework.statemachine.listener.StateMachineListener
import org.springframework.statemachine.listener.StateMachineListenerAdapter
import org.springframework.statemachine.state.State
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
		override fun configure(config: StateMachineConfigurationConfigurer<States, Events>){
				config.withConfiguration()
						.autoStartup(true).listener(listener())
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
		fun listener() : StateMachineListener<States, Events> {
				return object : StateMachineListenerAdapter<States, Events>(){
						override fun stateChanged(from: State<States, Events>?, to: State<States, Events>?){
								println("State changed to " + to!!.id)
						}
				}
		}
}

enum class States {
		LOCKED, UNLOCKED
}
enum class Events {
		COIN, PUSH
}
