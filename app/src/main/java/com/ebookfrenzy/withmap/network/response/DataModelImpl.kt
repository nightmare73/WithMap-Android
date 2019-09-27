package com.ebookfrenzy.withmap.network.response

import com.ebookfrenzy.withmap.network.WithMapService
import io.reactivex.Single

interface DataModel {
    fun getData(latitude: Double, longitude: Double): Single<List<CommonPinInfo>>
}

class DataModelImpl(private val service: WithMapService) : DataModel {
    override fun getData(latitude: Double, longitude: Double): Single<List<CommonPinInfo>> {
        return service.getPinsAroundPosition(
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJob21lc2tpbkBuYXZlci5jb20iLCJuaWNrbmFtZSI6Iuy1nOyEoOyerCIsImlzcyI6IldJVEhNQVAiLCJpYXQiOjE1NjkzMjI0NjEsImV4cCI6MTU2OTkyNzI2MX0.c7mUFv1BhyQLwiemXbYYfF_y8tEb45AoOVQ9-btpC_w",
            latitude, longitude
        )
    }
}