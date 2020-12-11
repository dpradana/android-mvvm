package daniel.id.candidates.core.network

import daniel.id.candidates.core.model.AddressResponse
import daniel.id.candidates.core.model.BasicInfoResponse
import daniel.id.candidates.core.model.ContactResponse
import daniel.id.candidates.core.model.ExperienceResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface Api {

    @GET("260cbda9-908e-4894-87a3-7faa41587499")
    fun getBasicInfo(): Observable<ArrayList<BasicInfoResponse>>

    @GET("093e3196-7406-4bf9-843f-a0923f20709e")
    fun getAddress(): Observable<ArrayList<AddressResponse>>

    @GET("fea1e636-49e0-44f8-991a-6d4107cd8736")
    fun getContact(): Observable<ArrayList<ContactResponse>>

    @GET("910fab34-1b52-46cf-8926-3c6c3e6e7ab3")
    fun getExperience(): Observable<ArrayList<ExperienceResponse>>
}