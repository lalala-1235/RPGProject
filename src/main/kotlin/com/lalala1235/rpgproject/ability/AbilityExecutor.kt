package com.lalala1235.rpgproject.ability

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AbilityExecutor(val abilityName: String, val abilityParameter: AbilityParameter)