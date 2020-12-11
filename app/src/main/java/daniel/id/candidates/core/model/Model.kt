package daniel.id.candidates.core.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BasicInfoResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("photo") val photo: String?,
    @SerializedName("birthday") val birthday: String,
    var address: AddressResponse? = null,
    var contact: ContactResponse? = null,
    val experience: ExperienceResponse? = null
) : Parcelable

@Parcelize
data class AddressResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("address") val address: String,
    @SerializedName("city") val city: String?,
    @SerializedName("state") val state: String?,
    @SerializedName("zip_code") val zipCode: Int?
) : Parcelable

@Parcelize
data class ContactResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String?,
    @SerializedName("phone") val phone: String?
) : Parcelable

@Parcelize
data class ExperienceResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("status") val status: String,
    @SerializedName("job_title") val jobTitle: String,
    @SerializedName("company_name") val companyName: String,
    @SerializedName("industry") val industry: String
) : Parcelable

