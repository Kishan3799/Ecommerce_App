package com.kishan.ecommercapp.data.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert
    suspend fun insertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT * FROM products")
    fun getAllProduct() : LiveData<List<Product>>

    @Query("SELECT * FROM products WHERE productId  = :id")
    fun isProductExist(id:String) : Product
}