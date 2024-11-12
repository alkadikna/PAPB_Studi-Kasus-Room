/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * ItemDao merupakan komponen utama dalam Room yang bertanggung jawab untuk melakukan suatu action ke database.
 * Room library menyediakan anotasi seperti @Query, @Insert, @Update, dan @Delete untuk melakukan operasi data
 * ke database. Selain itu Room tidak memperbolehkan operasi database berada pada Main Thread, sehingga fungsi
 * untuk melakukan operasinya perlu menggunakan suspend agar berjalan di thread terpisah. Terdapat juga argumen
 * onConflict untuk melakukan handling jika ada konflik antara data yang ada di database dan data yang di-add.
 * Ada juga Flow dimana berguna untuk menjaga data agar up-to-date dengan memberikan notifikasi jika data di
 * database berubah. UI akan diperbarui dan jika menggunakan Flow otomatis proses berjalan di thread terpisah.
 */
@Dao
interface ItemDao {

    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>

    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)
}
