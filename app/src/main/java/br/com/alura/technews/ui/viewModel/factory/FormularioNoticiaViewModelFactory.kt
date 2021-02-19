package br.com.alura.technews.ui.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.ui.viewModel.FormularioNoticiaViewModel

class FormularioNoticiaViewModelFactory(private val repository: NoticiaRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FormularioNoticiaViewModel(repository) as T
    }
}
