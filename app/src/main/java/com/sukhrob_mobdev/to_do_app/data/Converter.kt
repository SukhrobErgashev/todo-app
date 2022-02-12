package com.sukhrob_mobdev.to_do_app.data

import androidx.room.TypeConverter
import com.sukhrob_mobdev.to_do_app.data.models.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}