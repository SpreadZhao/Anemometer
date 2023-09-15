package persistant

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import network.cookie.CookieManager
import java.io.File

private val storePath = System.getProperty("user.home") + File.separator + "Library" + File.separator + "Anemometer" + File.separator + "Anemometer.preferences_pb"

private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create {
    File(storePath)
}

object AnemometerKeys {
    val cookieTrieKey = stringPreferencesKey("cookie_trie")
}

object DataStoreUtil {

    fun getCookie(): Flow<String> = dataStore.data.map { preferences ->
        preferences[AnemometerKeys.cookieTrieKey] ?: ""
    }

    suspend fun saveCookie(manager: CookieManager) {
        dataStore.edit { preferences ->
            preferences[AnemometerKeys.cookieTrieKey] = manager.toXML()
        }
    }
}