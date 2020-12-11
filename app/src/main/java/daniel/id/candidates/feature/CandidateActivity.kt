package daniel.id.candidates.feature

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ShareCompat
import daniel.id.candidates.R
import daniel.id.candidates.core.base.*
import daniel.id.candidates.core.model.BasicInfoResponse
import kotlinx.android.synthetic.main.activity_candidate.*
import kotlinx.android.synthetic.main.activity_candidate.iv_candidate
import kotlinx.android.synthetic.main.activity_candidate.tv_name
import java.util.*

class CandidateActivity : BaseActivity() {

    val dataCandidate by lazy {
        intent.getParcelableExtra<BasicInfoResponse>("data")
    }

    override fun setLayout(): Int {
        return R.layout.activity_candidate
    }

    override fun setUpVariable() {
        onClick()
        initView()
    }

    override fun internetAvailable() {
        ns_scroll.visible()
        no_connection.gone()
    }

    override fun internetUnAvailable() {
        ns_scroll.gone()
        no_connection.visible()
    }

    private fun initView(){
        dataCandidate.photo?.let { iv_candidate.loadImage(it) }
        tv_name.text = dataCandidate.name

        //count age
        val birthday = dataCandidate.birthday.split("/")
        val age = Utils.getAge(birthday[2].toInt(), birthday[0].toInt(), birthday[1].toInt())
        tv_age.text = "$age years old"

        tv_status.text = dataCandidate.experience?.status
        tv_job_title.text = dataCandidate.experience?.jobTitle
        tv_industry.text = dataCandidate.experience?.industry
        tv_address.text = dataCandidate.address?.address
        tv_city.text = dataCandidate.address?.city
        tv_zip.text = dataCandidate.address?.zipCode.toString()
        tv_state.text = dataCandidate.address?.state
    }

    private fun onClick(){
        iv_back.setOnClickListener {
            finish()
        }

        cv_call.setOnClickListener {
            dataCandidate.contact?.phone?.let {phone ->
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:" + phone.trim())
                startActivity(intent)
            }
        }

        cv_email.setOnClickListener {
            dataCandidate.contact?.email?.let {email ->
                ShareCompat.IntentBuilder.from(this)
                    .setType("message/rfc822")
                    .addEmailTo(email)
                    .startChooser()
            }
        }

        cv_whatsapp.setOnClickListener {
            dataCandidate.contact?.phone?.let { phone ->
                gotoLaunchWahatsapp(phone)
            }
        }

    }

    private fun gotoLaunchWahatsapp(phone: String){
        try {
            if(isAppInstalled("com.whatsapp")) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$phone")
                startActivity(intent)
            }else{
                Toast.makeText(this, "App not available on this device", Toast.LENGTH_SHORT).show()
            }
        }catch (e: ActivityNotFoundException){
            e.printStackTrace()
        }
    }

    fun isAppInstalled(packageName:String):Boolean {
        val pm = packageManager
        try
        {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            return true
        }
        catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return false
    }
}