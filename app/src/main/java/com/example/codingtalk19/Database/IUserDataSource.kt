package com.example.codingtalk19.Database

import com.example.codingtalk19.Model.User
import io.reactivex.Flowable

interface IUserDataSource {
    val allUsers: Flowable<List<User>>
    fun insertUser(vararg users: User)
    fun updateUser(nama: String, id: Int)
    fun deleteUser(id: Int)
}