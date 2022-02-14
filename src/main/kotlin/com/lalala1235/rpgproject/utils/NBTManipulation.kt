package com.lalala1235.rpgproject.utils

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.entity.EntityLiving
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftEntity
import org.bukkit.entity.Entity

class NBTManipulation {
    companion object {
        fun addNBTTag(e: Entity, key: String, value: String) {
            val nbt = getNBTCompound(e)

            nbt.a(key, value)

            setNBTCompound(e, nbt)
        }

        fun getValue(e: Entity, key: String): String? {
            val nbt = getNBTCompound(e)

            return nbt.c(key)?.toString()
        }

        private fun getNBTCompound(e: Entity): NBTTagCompound {
            val nmsEntity = (e as CraftEntity).handle

            var nbt = NBTTagCompound()

            nbt = nmsEntity.f(nbt)

            return nbt
        }

        private fun setNBTCompound(e: Entity, nbt: NBTTagCompound) {
            val nmsEntity = (e as CraftEntity).handle

            nmsEntity.f(nbt.g())
        }
    }
}