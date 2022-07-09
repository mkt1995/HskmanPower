
import com.example.hkspower.data.api.apis.API
import com.example.hkspower.util.Constants.BASE_URL_2
import com.example.hkspower.utils.Network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    var token = "SessionManegar().getString(this,)"


    companion object {

        private val retrofitLogin by lazy {
            val logging = HttpLoggingInterceptor()
            var nwobj = Network()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()

             .addInterceptor(object : Interceptor{
                 override fun intercept(chain: Interceptor.Chain): Response {
                     val request = chain.request().newBuilder()
                         .addHeader("Accept","application/json")
                         .addHeader("Auth", "MzI3NmEwZmI1MWNmMTYxZDE0N2JjMTRiNGYwYTE3NDk=")
                         .addHeader("Content-Type","application/json")
                         .build()
                     return chain.proceed(request)
                 }

             })
               .build()
           /* var okHttpClient = OkHttpClient.Builder().apply {
                addInterceptor(
                    Interceptor { chain ->
                        val builder = chain.request().newBuilder()
                        builder.header("X-App-Version", "1.23")
                        builder.header("X-Platform", "Android")
                        builder.header("Auth", "MzI3NmEwZmI1MWNmMTYxZDE0N2JjMTRiNGYwYTE3NDk=")
                        return@Interceptor chain.proceed(builder.build())
                    }
                )
            }.build()*/
                /*.addInterceptor(Interceptor { chain ->
                    val request: Request =
                        chain.request().newBuilder().addHeader("Content-Type", "application/json")
                            .addHeader("Auth","MzI3NmEwZmI1MWNmMTYxZDE0N2JjMTRiNGYwYTE3NDk=").
                        build()
                    chain.proceed(request)
                })
                .build()*/
              /*  .addInterceptor(logging)
                .build()*/




            Retrofit.Builder()
                .baseUrl(BASE_URL_2)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        private val retrofitPicsum by lazy {
            var Accept = "Accept"
            var value = "application/json"
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(Interceptor {chain ->
                    val request: Request =
                        chain.request().newBuilder().addHeader("Content-Type", "application/json")
                            .addHeader("Auth","MzI3NmEwZmI1MWNmMTYxZDE0N2JjMTRiNGYwYTE3NDk=").
                        build()
                    chain.proceed(request)
                })

                .addInterceptor(object : Interceptor{
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val request = chain.request().newBuilder()
                            .addHeader(Accept,value)
                            .addHeader("Auth", "MzI3NmEwZmI1MWNmMTYxZDE0N2JjMTRiNGYwYTE3NDk=")
                            .addHeader("Content-Type","application/json")
                            .build()
                        return chain.proceed(request)
                    }

                })

                .build()


               /* .addInterceptor(logging)
                .build()*/

            Retrofit.Builder()
                .baseUrl(BASE_URL_2)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)

                .build()
        }


        val loginApi by lazy {
            retrofitLogin.create(API::class.java)
        }

        val picsumApi by lazy {
            retrofitPicsum.create(API::class.java)
        }
    }
}