package daniel.id.candidates.feature

import android.app.Application
import androidx.lifecycle.MutableLiveData
import daniel.id.candidates.core.model.BasicInfoResponse
import daniel.id.candidates.core.base.BaseViewModel
import daniel.id.candidates.core.base.State
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : BaseViewModel(application) {
    var dataCandidate = ArrayList<BasicInfoResponse>()
    var resultBasicInfo: MutableLiveData<ArrayList<BasicInfoResponse>> = MutableLiveData()

    fun getBasicInfo(){
        state.postValue(State.LOADING)
        dataCandidate.clear()
        val data = api.getBasicInfo()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                dataCandidate.addAll(it)
                getAddress()
            },{
                onError.postValue(it.message)
            },{
                state.postValue(State.COMPLETE)
            })

        compositeDisposable.add(data)
    }

    fun getAddress(){
        state.postValue(State.LOADING)
        val data = api.getAddress()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                it.forEach {address ->
                    for (i in 0 until dataCandidate.size){
                        if(address.id == dataCandidate[i].id){
                            val basicInfoResponse =
                                BasicInfoResponse(
                                    dataCandidate[i].id,
                                    dataCandidate[i].name,
                                    dataCandidate[i].gender,
                                    dataCandidate[i].photo,
                                    dataCandidate[i].birthday,
                                    address
                                )
                            dataCandidate[i] = basicInfoResponse
                        }
                    }
                }
                getContact()
            },{
                onError.postValue(it.message)
            },{
                state.postValue(State.COMPLETE)
            })

        compositeDisposable.add(data)
    }

    fun getContact(){
        state.postValue(State.LOADING)
        val data = api.getContact()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                it.forEach {contact ->
                    for (i in 0 until dataCandidate.size){
                        if(contact.id == dataCandidate[i].id){
                            val basicInfoResponse =
                                BasicInfoResponse(
                                    dataCandidate[i].id,
                                    dataCandidate[i].name,
                                    dataCandidate[i].gender,
                                    dataCandidate[i].photo,
                                    dataCandidate[i].birthday,
                                    dataCandidate[i].address,
                                    contact
                                )
                            dataCandidate[i] = basicInfoResponse
                        }
                    }
                }
                getExperience()
            },{
                onError.postValue(it.message)
            },{
                state.postValue(State.COMPLETE)
            })

        compositeDisposable.add(data)
    }

    fun getExperience(){
        state.postValue(State.LOADING)
        val data = api.getExperience()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                it.forEach {experience ->
                    for (i in 0 until dataCandidate.size){
                        if(experience.id == dataCandidate[i].id){
                            val basicInfoResponse =
                                BasicInfoResponse(
                                    dataCandidate[i].id,
                                    dataCandidate[i].name,
                                    dataCandidate[i].gender,
                                    dataCandidate[i].photo,
                                    dataCandidate[i].birthday,
                                    dataCandidate[i].address,
                                    dataCandidate[i].contact,
                                    experience
                                )
                            dataCandidate[i] = basicInfoResponse
                        }
                    }
                }
                resultBasicInfo.postValue(dataCandidate)
            },{
                onError.postValue(it.message)
            },{
                state.postValue(State.COMPLETE)
            })

        compositeDisposable.add(data)
    }

}