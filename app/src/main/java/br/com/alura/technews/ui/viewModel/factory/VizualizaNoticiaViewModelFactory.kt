package br.com.alura.technews.ui.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.ui.viewModel.VizualizaNoticiaViewModel

class VizualizaNoticiaViewModelFactory(private val id: Long, private val repository: NoticiaRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VizualizaNoticiaViewModel(id, repository) as T
    }
}