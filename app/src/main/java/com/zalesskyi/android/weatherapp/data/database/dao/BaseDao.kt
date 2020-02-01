package com.zalesskyi.android.weatherapp.data.database.dao

import androidx.room.*

@Dao
interface BaseDao<in I> {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: I): Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg objects: I)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(objects: List<I>)

    @Transaction
    @Update
    fun update(obj: I)

    @Update
    fun updateAll(vararg objects: I)

    @Update
    fun updateAll(objects: List<I>)

    @Transaction
    @Delete
    fun delete(obj: I)

    @Delete
    fun deleteList(objs: List<I>)
}