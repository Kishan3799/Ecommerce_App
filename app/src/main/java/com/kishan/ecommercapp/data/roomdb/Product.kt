package com.kishan.ecommercapp.data.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    @Nonnull
    val productId: String,

    @ColumnInfo("product_name")
    val productName:String? = "",

    @ColumnInfo("product_image")
    val productImage:String? = "",

    @ColumnInfo("product_selling_price")
    val productSellingPrice:String? = ""

)

