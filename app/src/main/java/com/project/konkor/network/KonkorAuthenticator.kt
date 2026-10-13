package com.project.konkor.network

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.project.konkor.model.TokenContainer
import com.project.konkor.util.Constants.REFRESH_TOKEN
import com.project.konkor.util.Constants.TOKEN
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit

class KonkorAuthenticator(
    retrofit: Retrofit.Builder,
    context: Context
) : Authenticator {

    private val apiInterface = retrofit
        .build().create(ApiInterface::class.java)

    private val shredPrefAuth: SharedPreferences =
        context.getSharedPreferences("authInfo", Context.MODE_PRIVATE)

    override fun authenticate(route: Route?, response: Response): Request? {
        if (!TokenContainer.accessToken.isNullOrEmpty() &&
            !TokenContainer.refreshToken.isNullOrEmpty() &&
            !response.request.url.pathSegments.last().equals("refreshToken", false)
        ) {
            try {
                val accessToken = refreshToken()
                if (accessToken.isEmpty()) {
                    return null
                }
                return response.request.newBuilder().header("Authorization", accessToken).build()
            } catch (e: Exception) {
                Log.e("KonkorAuthenticator", "authenticate: ", e)
            }
        }

        return null
    }

    private fun refreshToken(): String {
        val response = apiInterface.refreshToken(TokenContainer.refreshToken!!).execute()

        response.body()?.let {
            TokenContainer.update(it.accessToken, it.refreshToken)
            shredPrefAuth.edit {
                putString(TOKEN, it.accessToken)
                putString(REFRESH_TOKEN, it.refreshToken)
            }
            return it.accessToken
        }

        return ""
    }

}