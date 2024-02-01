package com.breno.evernotekt.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.breno.evernotekt.data.model.Note
import com.breno.evernotekt.data.model.RemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class NoteRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val compositeDisposable: CompositeDisposable
) : NoteRepository {
    override fun getAllNotes(): LiveData<List<Note>?> {
        val data = MutableLiveData<List<Note>?>()

        val disposable = remoteDataSource.listNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<Note>>() {
                override fun onNext(notes: List<Note>) {
                    data.postValue(notes)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    data.postValue(null)
                }

                override fun onComplete() {
                    println("Complete")
                }

            })

        compositeDisposable.add(disposable)

        return data
    }

    override fun getNote(noteId: Int): LiveData<Note?> {
        val data = MutableLiveData<Note?>()
        val disposable = remoteDataSource.getNote(noteId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Note>() {

                override fun onComplete() {
                    println("complete")
                }

                override fun onNext(note: Note) {
                    data.postValue(note)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    data.postValue(null)
                }

            })

        compositeDisposable.add(disposable)

        return data
    }

    override fun createNote(note: Note): LiveData<Note> {
        val data = MutableLiveData<Note>()

        val disposable = remoteDataSource.createNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Note>() {

                override fun onComplete() {
                    println("complete")
                }

                override fun onNext(note: Note) {
                    data.postValue(note)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    data.postValue(null)
                }

            })

        compositeDisposable.add(disposable)

        return data
    }
}