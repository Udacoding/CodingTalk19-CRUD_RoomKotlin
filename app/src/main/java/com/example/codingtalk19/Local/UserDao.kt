package com.example.codingtalk19.Local

import android.arch.persistence.room.*
import com.example.codingtalk19.Model.User
import io.reactivex.Flowable

@Dao
interface UserDao {

    @get:Query("SELECT * FROM users")
    val allUsers: Flowable<List<User>>

    @Insert
    fun insertUser(vararg users: User)

    @Query("Update users SET nama =:nama WHERE id=:id")
    fun updateUser(nama: String, id: Int)

    @Query("DELETE FROM users WHERE id=:id")
    fun deleteUser(id: Int)


}