package com.example.shahingram.Retrofit

class ApiProvider {
    object STon {
        val instance = ApiClient().getClient().create(ApiService::class.java)
    }
}