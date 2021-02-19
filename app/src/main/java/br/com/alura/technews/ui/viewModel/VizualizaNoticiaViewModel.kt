package br.com.alura.technews.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.repository.Resource

class VizualizaNoticiaViewModel (id: Long, private val repository: NoticiaRepository) : ViewModel() {

    //mandamos o id em vez da noticia pq assim o viewModel que fica com a responsabilidade de buscar e manter a noticia
    //a activity fica livre da responsabilidade de remover noticia
    val noticiaEncontrada = repository.buscaPorId(id)


    fun remove() : LiveData<Resource<Void?>> {
        return noticiaEncontrada.value?.run {
            repository.remove(this)
        } ?: MutableLiveData<Resource<Void?>>().also {
            it.value = Resource(null, "Notícia não encontrada")
        }

    }


}