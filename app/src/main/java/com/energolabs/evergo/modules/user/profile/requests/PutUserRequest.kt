package com.energolabs.evergo.modules.user.profile.requests

import android.content.Context

import com.energolabs.evergo.modules.location.models.LocationModel
import com.energolabs.evergo.modules.user.profile.models.UserModel
import com.energolabs.evergo.modules.user.profile.utils.GenderUtils
import com.energolabs.evergo.modules.user.validation.models.IdentityModel
import com.energolabs.evergo.requests.BaseResultListener
import com.energolabs.evergo.requests.EnergoRetrofitCallBuilder
import com.energolabs.evergo.requests.RequestBuilderWithBody

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Created by Jose on 11/19/2016.
 * Copyright (C) 2016 Energo Labs
 *
 *
 * Copy or sale of this class is forbidden.
 */
interface PutUserRequest {

    @PUT("users/{id}")
    fun putUser(
            @Body userModel: UserModel,
            @Path("id") id: String?
    ): Call<UserModel>

    class Builder(context: Context) : RequestBuilderWithBody<UserModel, UserModel>(context) {

        override val request = UserModel()

        override fun setRequest(request: UserModel?): Builder {
            setId(
                    request?._id
            )
            setPassword(
                    request?.password
            )
            setPhoneNumber(
                    request?.phoneNumber
            )
            setCountryCode(
                    request?.countryCode
            )
            setName(
                    request?.name
            )
            setLocation(
                    request?.location
            )
            setIdentity(
                    request?.identity
            )
            setAddress(
                    request?.address
            )
            setGender(
                    request?.gender
            )
            setRole(
                    request?.role
            )
            setWalletId(
                    request?.walletId
            )
            setAvatar(
                    request?.avatar
            )
            return this
        }

        fun setId(id: String?): Builder {
            request._id = id
            return this
        }

        fun setPhoneNumber(phoneNumber: String?): Builder {
            request.phoneNumber = phoneNumber
            return this
        }

        fun setCountryCode(phoneCode: String?): Builder {
            request.countryCode = phoneCode
            return this
        }

        fun setName(name: String?): Builder {
            request.name = name
            return this
        }

        fun setIdentity(identity: IdentityModel?): Builder {
            request.identity = identity
            return this
        }

        fun setLocation(locationModel: LocationModel?): Builder {
            request.location = locationModel
            return this
        }

        fun setAddress(address: String?): Builder {
            request.address = address
            return this
        }

        fun setGender(gender: Int?): Builder {
            request.gender = gender ?: GenderUtils.UNKNOWN
            return this
        }

        fun setRole(role: String?): Builder {
            request.role = role
            return this
        }

        fun setWalletId(walletId: String?): Builder {
            request.walletId = walletId
            return this
        }

        fun setPassword(password: String?): Builder {
            request.password = password
            return this
        }

        fun setAvatar(avatar: String?): Builder {
            request.avatar = avatar
            return this
        }

        override fun request() {
            val petUserRequest = EnergoRetrofitCallBuilder.createService(
                    context,
                    PutUserRequest::class.java
            )

            val call = petUserRequest.putUser(
                    request,
                    request._id
            )
            enqueue(call)
        }
    }

    interface PutUserRequestListener : BaseResultListener<UserModel>

}
