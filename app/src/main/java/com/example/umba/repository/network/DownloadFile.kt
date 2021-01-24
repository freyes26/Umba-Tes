package com.example.umba.repository.network

import android.content.Context
import com.example.umba.BaseApplication
import okhttp3.ResponseBody
import retrofit2.Response

object DownloadFile {

    fun saveImage(context: Context, name:String,body: ResponseBody){
        context.openFileOutput(name.replace("/",""),Context.MODE_PRIVATE).use {
            it.write(body.bytes())
        }
    }

    fun dwonload(data :String) : Response<ResponseBody> {
        val url ="https://image.tmdb.org/t/p/w500${data}"
        return BaseApplication.application.imageDownload.getImage(url).execute()
    }
}
