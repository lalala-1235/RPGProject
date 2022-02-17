package com.lalala1235.rpgproject.utils

class DamageCalculator {
    companion object {
        fun getDamage(baseDmg: Double, str: Double): Double {
            return baseDmg * ((0.01 * str) + 1)
        }

        fun getDamage(baseDmg: Double, str: Double, def: Double): Double {
            return (baseDmg * ((0.01 * str) + 1)) * (def / (def + 100))
        }

        fun getDamage(baseDmg: Double, str: Double, def: Double, multiplier: Double): Double {
            return (baseDmg * ((0.01 * str) + 1)) * (def / (def + 100)) * multiplier
        }
    }
}