package com.ebookfrenzy.withmap.network.response

import com.ebookfrenzy.withmap.data.GetMyRegisterPinData
import com.ebookfrenzy.withmap.network.WithMapService
import io.reactivex.Single

interface DataModel {
    fun getData(latitude: Double, longitude: Double): Single<List<CommonPinInfo>>

}

class DataModelImpl(private val service: WithMapService) : DataModel {
    override fun getData(latitude: Double, longitude: Double): Single<List<CommonPinInfo>> {
        return service.getPinsAroundPosition(
            latitude, longitude
        )
    }

}