package com.energolabs.evergo.utils.json

import android.text.TextUtils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


/**
 * Returns the **JSONObject** located at a given position in a
 * **JSONArray**

 * @param position
 * *
 * @param jsonArray
 * *
 * @return The **JSONObject** located at the given position. If position
 * * is out of the array range or array is null, it will return a
 * * **null** object.
 * *
 * @author JoseMiguel
 */
fun getJSONObjectFromArray(
        position: Int,
        jsonArray: JSONArray?
): JSONObject? {
    if (jsonArray == null) {
        return null
    }

    if (position < 0) {
        return null
    }

    val lenght = jsonArray.length()
    if (position >= lenght) {
        return null
    }

    return jsonArray.optJSONObject(position)
}

/**
 * @param object
 * *
 * @param key
 * *
 * @return
 */
fun getString(
        `object`: JSONObject?,
        key: String?
): String {
    if (`object` == null) {
        return ""
    }

    if (TextUtils.isEmpty(key)) {
        return ""
    }

    if (!`object`.has(key)) {
        return ""
    }

    try {
        return `object`.getString(key).trim { it <= ' ' }
    } catch (e: JSONException) {
        return ""
    }

}

/**
 * @param object
 * *
 * @param key
 * *
 * @return
 */
fun getInt(
        `object`: JSONObject?,
        key: String?
): Int {
    if (`object` == null) {
        return 0
    }

    if (TextUtils.isEmpty(key)) {
        return 0
    }

    return `object`.optInt(key)
}

/**
 * @param object
 * *
 * @param key
 * *
 * @return
 */
fun getLong(
        `object`: JSONObject?,
        key: String?
): Long {
    if (`object` == null) {
        return 0
    }

    if (TextUtils.isEmpty(key)) {
        return 0
    }

    return `object`.optLong(key)

}

/**
 * @param object
 * *
 * @param key
 * *
 * @return
 * *
 * @author JoseMiguel
 */
fun getDouble(
        `object`: JSONObject?,
        key: String?
): Double {
    if (`object` == null) {
        return 0.0
    }

    if (TextUtils.isEmpty(key)) {
        return 0.0
    }

    return `object`.optDouble(key)

}

/**
 * @param object
 * *
 * @param key
 * *
 * @return
 */
fun getBoolean(
        `object`: JSONObject?,
        key: String?
): Boolean {
    if (`object` == null) {
        return false
    }

    if (TextUtils.isEmpty(key)) {
        return false
    }

    return `object`.optBoolean(key)

}

/**
 * @param object
 * *
 * @param key
 * *
 * @return JSONArray containing the array indicated by key, if not
 * * available, an empty array
 */
fun getJsonArray(
        `object`: JSONObject?,
        key: String?
): JSONArray {
    if (`object` == null) {
        return JSONArray()
    }

    if (TextUtils.isEmpty(key)) {
        return JSONArray()
    }

    if (!`object`.has(key)) {
        return JSONArray()
    }

    try {
        return `object`.getJSONArray(key)
    } catch (e: JSONException) {
        return JSONArray()
    }

}

/**
 * @param object
 * *
 * @param key
 * *
 * @return
 */
fun getJsonObject(
        `object`: JSONObject?,
        key: String?
): JSONObject {
    if (`object` == null) {
        return JSONObject()
    }

    if (TextUtils.isEmpty(key)) {
        return JSONObject()
    }

    if (!`object`.has(key)) {
        return JSONObject()
    }

    try {
        return `object`.getJSONObject(key)
    } catch (e: JSONException) {
        return JSONObject()
    }

}

/**
 * @param array
 * *
 * @return true , if array is empty
 */
fun isEmpty(
        array: JSONArray?
): Boolean {
    if (array == null) {
        return true
    }
    return array.length() <= 0
}
