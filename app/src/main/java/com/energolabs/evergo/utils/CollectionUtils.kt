package com.energolabs.evergo.utils

import java.util.*

/**
 * Created by Jose on 5/19/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
object CollectionUtils {

    /**
     * In case of empty collections, this is the only position to be returned
     * to mark that we don't have a real position to return.
     */
    val INVALID_POSITION = -1


    /**
     * Checks if the reference collection is empty or not

     * @param collection The reference collection
     * *
     * @return true if collection is null or empty, false otherwise
     */
    fun isEmpty(
            collection: Collection<*>?
    ): Boolean {
        return collection?.isEmpty() ?: true
    }

    /**
     * Checks if the reference position is a valid one within the collection.

     * @param collection The reference collection
     * *
     * @param position   The reference position
     * *
     * @return false if collection is null, empty or position is invalid.
     * * true if position is within [0, `collection.size()`)
     */
    fun validPosition(
            collection: Collection<*>?,
            position: Int
    ): Boolean {
        if (isEmpty(collection)) {
            return false
        }

        if (position < 0) {
            return false
        }

        return position < (collection?.size ?: 0)

    }


    /**
     * Returns the size of the reference collection avoiding null pointer exceptions.

     * @param collection The reference collection
     * *
     * @return Regular case is `collection.size()`, in case of null collection, 0.
     */
    fun size(
            collection: Collection<*>?
    ): Int {
        return collection?.size ?: 0
    }

    /**
     * Random position index according to the provided collection size.

     * @param collection The reference collection
     * *
     * @return Random position index within the range [0, `collection.size()`).
     * * If collection null or empty returns [CollectionUtils.INVALID_POSITION]
     */
    fun getRandomPosition(collection: Collection<*>): Int {
        val min = 0
        val max = size(collection)
        if (max <= 0) {
            return INVALID_POSITION
        }

        val r = Random()
        return r.nextInt(max - min) + min
    }

}
