package com.example.tryingsubmission.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tryingsubmission.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favoriteentity ORDER BY login ASC")
    fun getAllListFavoriteUser():LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favoriteentity WHERE login = :username")
    fun getFavoriteByUsername(username : String): FavoriteEntity?

    @Query("SELECT * FROM favoriteentity WHERE login = :username")
    fun getDataFavoriteByUsername(username : String): LiveData<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite : FavoriteEntity)

    @Delete
    fun delete(favorite: FavoriteEntity)

}