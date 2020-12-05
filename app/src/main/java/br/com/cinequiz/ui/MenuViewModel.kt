package br.com.cinequiz.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.cinequiz.BuildConfig
import br.com.cinequiz.domain.Filme
import br.com.cinequiz.domain.FilmePopular
import br.com.cinequiz.domain.FilmeSimilar
import br.com.cinequiz.service.Repository
import kotlinx.coroutines.launch

class MenuViewModel(val repository: Repository) : ViewModel() {

    val apiKey: String = ""

    val listaFilmesPopulares = MutableLiveData<List<FilmePopular>>()
    val filme = MutableLiveData<Filme>()
    val listaFilmesUtilizaveis = MutableLiveData<List<Filme>>()

    fun getResults() {

        try {
            viewModelScope.launch {
                listaFilmesPopulares.value = repository.getFilmesPopulares(
                    apiKey,
                    "pt-BR"
                ).listaFilmesPopulares
            }
        } catch (e: Exception) {
            Log.e("MenuViewModel", e.toString())
        }
    }

    fun getFilme(filmeID: Int) {
        try {
            viewModelScope.launch {
                filme.value = repository.getFilme(
                    filmeID,
                    apiKey,
                    "pt-BR"
                )

                getFilmesSimiliares(filmeID)
                getImagensFilme(filmeID)

            }
        } catch (e: Exception) {
            Log.e("MenuViewModel", e.toString())
        }
    }

    fun getFilmesSimiliares(filmeID: Int) {
        try {
            viewModelScope.launch {
                val listaFilmesSimilares = repository.getFilmesSimiliares(
                    filmeID,
                    apiKey,
                    "pt-BR"
                ).listaFilmesSimilares
                filme.value?.filmesSimilares = listaFilmesSimilares
                Log.i("FILMES SIMILARES:", filmeID.toString() + " " + listaFilmesSimilares.toString())
            }
        } catch (e: Exception) {
            Log.e("MenuViewModel", e.toString())
        }
    }

    fun getImagensFilme(filmeID: Int) {
        try {
            viewModelScope.launch {
                val listaImagensFilme = repository.getImagensFilme(
                    filmeID,
                    apiKey,
                    "null"
                ).listaImagensFilme
                //filme.value?.imagensFilme = listaImagensFilme
                Log.i("IMAGENS:", filmeID.toString() + " " + listaImagensFilme?.toString())

            }
        } catch (e: Exception) {
            Log.e("MenuViewModel", e.toString())
        }
    }

}

