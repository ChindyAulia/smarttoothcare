package com.psi.smarttoothcare.ui.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.psi.smarttoothcare.model.Article
import com.psi.smarttoothcare.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {
    val facts = MutableLiveData<List<Article>>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        initFacts()
    }

    private fun initFacts() {
        compositeDisposable.add(articleRepository.getFacts().observeOn(AndroidSchedulers.mainThread()).subscribe({
            facts.postValue(it)
        }) {
            Timber.e(it)
        })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}