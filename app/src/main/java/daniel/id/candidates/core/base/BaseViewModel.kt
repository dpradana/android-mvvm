package daniel.id.candidates.core.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import daniel.id.candidates.core.network.RetrofitClient
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val api = RetrofitClient(application).service()
    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    var onError: MutableLiveData<String> = MutableLiveData()
    var state: MutableLiveData<State> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}