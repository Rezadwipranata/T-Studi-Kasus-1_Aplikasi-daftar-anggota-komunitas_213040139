package com.application.komunitas_app.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface KomunitasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCommunity(komunitas: Komunitas)

    @Query("SELECT * FROM komunitas WHERE id = :id")
    fun getCommunity(id: Int): Komunitas

    @Query("SELECT * FROM komunitas")
    fun getAllCommunities(): List<Komunitas>

    @Delete
    fun deleteCommunity(komunitas: Komunitas)

    @Query("UPDATE komunitas SET name = :name, tglJoin = :tglJoin, memberLvl = :memberLvl, period = :period WHERE id = :id")
    fun updateCommunity(id: Int, name: String, tglJoin: String, memberLvl: String, period: String)
}