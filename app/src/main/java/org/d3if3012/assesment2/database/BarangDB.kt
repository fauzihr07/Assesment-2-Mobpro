package org.d3if3012.assesment2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3012.assesment2.model.Barang

@Database(entities = [Barang::class], version = 1, exportSchema = false)
abstract class BarangDB : RoomDatabase() {

    abstract val dao: NamaBarang

    companion object{

        @Volatile
        private var INSTANCE: BarangDB? = null

        fun getInstance(context: Context): BarangDB {
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BarangDB::class.java,
                        "barang.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

