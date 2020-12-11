package daniel.id.candidates.feature

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import daniel.id.candidates.R
import daniel.id.candidates.core.base.*
import daniel.id.candidates.core.model.BasicInfoResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_candidate.view.*
import kotlinx.android.synthetic.main.layout_no_connection.*

class MainActivity : BaseActivity() {
    private val dataInfo = ArrayList<BasicInfoResponse>()
    private var adapter: Adapter? = null

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun setLayout(): Int {
        return R.layout.activity_main
    }

    override fun setUpVariable() {
        initView()
        initAdapter()
        initDataObserver()
        viewModel.getBasicInfo()
        refresh.setOnRefreshListener {
            viewModel.getBasicInfo()
        }
    }

    private fun initView(){
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//  set status text dark
    }

    override fun internetAvailable() {
        tv_title.visible()
        rv_candidates.visible()
        no_connection.gone()
        viewModel.getBasicInfo()
    }

    override fun internetUnAvailable() {
        tv_title.gone()
        rv_candidates.gone()
        no_connection.visible()
    }

    private fun initAdapter(){
        rv_candidates.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(this, dataInfo)
        rv_candidates.adapter = adapter
    }

    private fun initDataObserver() {
        viewModel.resultBasicInfo.observe(this, Observer {
            dataInfo.clear()
            dataInfo.addAll(it)
            adapter?.notifyDataSetChanged()
            Log.i("data", dataInfo.toString())
        })

        viewModel.onError.observe(this, Observer {
            internetUnAvailable()
            if(refresh.isRefreshing){
                refresh.isRefreshing = false
            }
        })

        viewModel.state.observe(this, Observer {
            refresh.isRefreshing = it == State.LOADING
        })
    }

    inner class Adapter(val context: Context, val datas: ArrayList<BasicInfoResponse>) : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_candidate, parent, false))
        }

        override fun getItemCount(): Int {
            return datas.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.setIsRecyclable(false)
            val item = datas[position]
            holder.tvName.text = item.name
            holder.tvStatus.text = item.experience?.status
            holder.tvJobTitle.text = item.experience?.jobTitle

            item.photo?.let {image ->
                Glide.with(context)
                    .load(image)
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.ivCandidate)
            }

            holder.cvCandidate.setOnClickListener {
                val bundle = Bundle().apply {
                    putParcelable("data", item)
                }
                toActivity(bundle, CandidateActivity::class.java)
            }

        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCandidate: ImageView = itemView.iv_candidate
        val tvName: TextView = itemView.tv_name
        val tvStatus: TextView = itemView.tv_status
        val tvJobTitle: TextView = itemView.tv_job_title
        val cvCandidate: CardView = itemView.cv_candidate
    }
}