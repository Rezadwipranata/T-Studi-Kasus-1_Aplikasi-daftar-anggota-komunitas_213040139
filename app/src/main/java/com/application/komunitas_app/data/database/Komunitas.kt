package com.application.komunitas_app.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "komunitas")
data class Komunitas(
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "tglJoin")
    var tglJoin: String,

    @ColumnInfo(name = "memberLvl")
    var memberLvl: String,

    @ColumnInfo(name = "period")
    var period: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)