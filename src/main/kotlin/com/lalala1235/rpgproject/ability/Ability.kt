package com.lalala1235.rpgproject.ability

import org.bukkit.entity.Entity
import kotlin.reflect.KCallable
import kotlin.reflect.full.findAnnotation

open class Ability {
    companion object {
        private val abilityList = HashMap<String, KCallable<*>>()
        private val paramList = HashMap<String, AbilityParameter>()
        private val declaringClass = HashMap<String, AbilityExecutors>()

        /**
         * execute ability with one parameter, effected entity
         *
         * ability's AbilityParameter must be AbilityParameter.EFFECTED_ENTITY_ONE
         *
         * @param abilityName name of ability to execute
         * @param en effected entity
         */
        fun executeAbility(abilityName: String?, en: Entity) {
            if(!abilityList.contains(abilityName)) return
            if(!paramList.contains(abilityName)) return
            if(!declaringClass.contains(abilityName)) return

            if(paramList[abilityName] != AbilityParameter.EFFECTED_ENTITY_ONE) return

            abilityList[abilityName]!!.call(declaringClass[abilityName]!!::class.java.getDeclaredConstructor().newInstance(), en)
        }

        fun register(ability: AbilityExecutors) {
            val kClass = ability.javaClass.kotlin
            val functions = kClass.members.filter { it.findAnnotation<AbilityExecutor>() != null}

            if(functions.isEmpty()) return

            functions.forEach {
                val annotation = it.findAnnotation<AbilityExecutor>()
                abilityList[annotation!!.abilityName] = it
                paramList[annotation.abilityName] = annotation.abilityParameter
                declaringClass[annotation.abilityName] = ability
            }
        }

        fun getParams(abilityName: String): AbilityParameter? {
            return paramList[abilityName]
        }
    }
}