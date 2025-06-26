package com.example.sia.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.sia.models.Asset  // ✅ Your actual Asset class
import com.google.gson.Gson
import androidx.core.content.edit
import com.example.sia.main.AssetCollector
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken

class SharedPrefs {
    companion object {
        private const val SHARED_PREFS_FILE_NAME = "mobile_app_shared_prefs"
        private const val ASSET_LIST_KEY = "asset_list_key"
        const val ACCESS_CONTROL_IP_ADDRESS = "146.64.207.201"

        // 👇 Add keys for user data
        private const val USERNAME = "username"
        private const val PASSWORD = "password"
        private const val KEY_NATIONAL_ID = "national_id"
        private const val KEY_FIRST_NAME = "first_name"
        private const val KEY_SURNAME = "surname"
        private const val KEY_OCCUPATION = "occupation"
        private const val SYSTEM_TOKEN = "token"



        // ✅ Save individual values
        fun setUsername(context: Context, value: String) {
            context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .edit().putString(USERNAME, value).apply()

        }

        fun setPassword(context: Context, value: String) {
            context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .edit().putString(PASSWORD, value).apply()

        }

        fun setNationalId(context: Context, value: String) {
            context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .edit().putString(KEY_NATIONAL_ID, value).apply()
        }

        fun setFirstName(context: Context, value: String) {
            context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .edit().putString(KEY_FIRST_NAME, value).apply()
        }

        fun setSurname(context: Context, value: String) {
            context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .edit().putString(KEY_SURNAME, value).apply()
        }

        fun setOccupation(context: Context, value: String) {
            context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .edit().putString(KEY_OCCUPATION, value).apply()
        }
        fun setToken(context: Context, value: String) {
            context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .edit().putString(SYSTEM_TOKEN, value).apply()
        }

        // ✅ Retrieve individual values
        fun getUsername(context: Context): String? {
            return context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .getString(USERNAME, "")
        }

        fun getPassword(context: Context): String? {
            return context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .getString(PASSWORD, "")
        }

        fun getNationalId(context: Context): String? {
            return context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .getString(KEY_NATIONAL_ID, "")
        }

        fun getFirstName(context: Context): String? {
            return context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .getString(KEY_FIRST_NAME, "")
        }

        fun getSurname(context: Context): String? {
            return context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .getString(KEY_SURNAME, "")
        }

        fun getOccupation(context: Context): String? {
            return context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .getString(KEY_OCCUPATION, "")
        }
        fun getToken(context: Context): String? {
            return context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .getString(SYSTEM_TOKEN, "")
        }

        // ✅ Existing asset-related code stays unchanged
        fun saveAssetList(context: Context, assetList: List<Asset>) {
            val prefs: SharedPreferences = context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
            val json = Gson().toJson(assetList)
            prefs.edit().putString(ASSET_LIST_KEY, json).apply()
        }

        fun clearAssetList(context: Context) {
            val prefs = context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
            prefs.edit().remove(ASSET_LIST_KEY).apply()
        }

        fun getAssetList(context: Context): List<Asset> {
            val prefs: SharedPreferences =
                context.getSharedPreferences(SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
            val json = prefs.getString(ASSET_LIST_KEY, null)
            return if (json != null) {
                val type = object : TypeToken<List<Asset>>() {}.type
                Gson().fromJson(json, type)
            } else {
                emptyList()
            }
        }
    }
}