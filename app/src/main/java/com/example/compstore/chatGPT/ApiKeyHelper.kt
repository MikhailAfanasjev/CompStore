package com.example.compstore.chatGPT

import android.content.Context
import android.content.SharedPreferences
import com.example.linguareader.BuildConfig


object ApiKeyHelper {
    private const val PREFS_NAME = "shared_prefs"
    private const val KEY_API = "API_KEY"
    private lateinit var sharedPreferences: SharedPreferences

    // Локально определённый API‑ключ, например, из BuildConfig.
    private const val DEFAULT_API_KEY = BuildConfig.API_KEY

    // Инициализация SharedPreferences
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (getApiKey().isEmpty()) {
            storeApiKey(DEFAULT_API_KEY)
        }
    }

    // Сохраняет API‑ключ в SharedPreferences
    fun storeApiKey(apiKey: String) {
        sharedPreferences.edit().putString(KEY_API, apiKey).apply()
    }

    // Получает API‑ключ из SharedPreferences
    fun getApiKey(): String {
        return sharedPreferences.getString(KEY_API, "") ?: ""
    }
}


//object ApiKeyHelper {
//    private const val PREFS_NAME = "secret_shared_prefs"
//    private const val KEY_API = "API_KEY"
//    private lateinit var sharedPreferences: SharedPreferences
//
//    // Локально определённый API‑ключ. Рекомендуется получать значение из защищённого источника,
//    // например, через BuildConfig, и обфусцировать его.
//    private const val DEFAULT_API_KEY = BuildConfig.API_KEY
//
//    // Алиас для хранения секретного ключа шифрования в Android Keystore
//    private const val KEY_ALIAS = "header_key_alias"
//
//    // Инициализация EncryptedSharedPreferences с использованием MasterKey из Android Keystore
//    fun init(context: Context) {
//        val masterKey = MasterKey.Builder(context)
//            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
//            .build()
//
//        sharedPreferences = EncryptedSharedPreferences.create(
//            context,
//            PREFS_NAME,
//            masterKey,
//            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        )
//
//        // Если API‑ключ ещё не сохранён, устанавливаем локально заданное значение
//        if (getApiKey().isEmpty()) {
//            storeApiKey(DEFAULT_API_KEY)
//        }
//    }
//
//    // Сохраняет API‑ключ в зашифрованном хранилище
//    fun storeApiKey(apiKey: String) {
//        sharedPreferences.edit().putString(KEY_API, apiKey).apply()
//    }
//
//    // Получает API‑ключ из зашифрованного хранилища
//    fun getApiKey(): String {
//        return sharedPreferences.getString(KEY_API, "") ?: ""
//    }
//
//    // Получение или генерация секретного ключа для шифрования заголовка
//    private fun getSecretKey(): SecretKey? {
//        val keyStore = KeyStore.getInstance("AndroidKeyStore")
//        keyStore.load(null)
//        if (keyStore.containsAlias(KEY_ALIAS)) {
//            val secretKeyEntry = keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry
//            return secretKeyEntry.secretKey
//        } else {
//            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
//            val keySpec = KeyGenParameterSpec.Builder(
//                KEY_ALIAS,
//                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
//            )
//                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
//                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
//                .setRandomizedEncryptionRequired(true)
//                .build()
//            keyGenerator.init(keySpec)
//            return keyGenerator.generateKey()
//        }
//    }
//
//    // Шифрует API‑ключ с использованием AES/CBC/PKCS7Padding.
//    // Результат – Base64-строка, содержащая IV и зашифрованные данные.
//    fun encryptApiKey(): String {
//        val apiKey = getApiKey()
//        if (apiKey.isEmpty()) return ""
//        val secretKey = getSecretKey() ?: return ""
//        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
//        val iv = cipher.iv // вектор инициализации
//        val encryptedBytes = cipher.doFinal(apiKey.toByteArray(Charsets.UTF_8))
//        // Объединяем IV и зашифрованные данные
//        val combined = iv + encryptedBytes
//        return Base64.encodeToString(combined, Base64.NO_WRAP)
//    }
//}