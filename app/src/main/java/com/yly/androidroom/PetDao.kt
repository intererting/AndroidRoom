package com.yly.androidroom

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PetDao {

    @Query("SELECT id,name FROM USER")
    fun loadUserAndPets(): LiveData<UserNameAndAllPets>

    @Insert
    fun insertUser(user: User)

    @Insert
    fun insertPets(pets: List<Pet>)
}