/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SleepDatabaseDao{
    @Insert
    fun insert (night: sleepNight)

    @Update
    fun update(night: sleepNight)

    @Query( "SELECT * FROM daily_sleep_quality_table WHERE id= :key")
    fun get(key: Long): sleepNight?

    @Query( "DELETE FROM daily_sleep_quality_table")
    fun clear()

    @Query (" SELECT * FROM daily_sleep_quality_table ORDER BY id DESC LIMIT 1")
    fun getTonight(): sleepNight?

    @Query ("SELECT * FROM daily_sleep_quality_table ORDER BY id DESC")
    fun getAllNights(): LiveData<List<sleepNight>>


}
