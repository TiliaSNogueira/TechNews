package br.com.alura.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.alura.technews.R
import br.com.alura.technews.database.AppDatabase
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.ui.activity.extensions.mostraErro
import br.com.alura.technews.ui.viewModel.FormularioNoticiaViewModel
import br.com.alura.technews.ui.viewModel.VizualizaNoticiaViewModel
import br.com.alura.technews.ui.viewModel.factory.FormularioNoticiaViewModelFactory
import br.com.alura.technews.ui.viewModel.factory.VizualizaNoticiaViewModelFactory
import kotlinx.android.synthetic.main.activity_visualiza_noticia.*

private const val NOTICIA_NAO_ENCONTRADA = "Notícia não encontrada"
private const val TITULO_APPBAR = "Notícia"
private const val MENSAGEM_FALHA_REMOCAO = "Não foi possível remover notícia"

class VisualizaNoticiaActivity : AppCompatActivity() {

    private val viewModel by lazy {
        val repository = NoticiaRepository(AppDatabase.getInstance(this).noticiaDAO)
        val factory = VizualizaNoticiaViewModelFactory(noticiaId, repository)
        val provedor = ViewModelProviders.of(this, factory)
        provedor.get(VizualizaNoticiaViewModel::class.java)
    }

    private val noticiaId: Long by lazy {
        intent.getLongExtra(NOTICIA_ID_CHAVE, 0)
    }

    private lateinit var noticia: Noticia

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualiza_noticia)
        title = TITULO_APPBAR
        verificaIdDaNoticia()
        buscaNoticiaSelecionada()
    }
//não precisa mais do onResume pois o armazenamento está como LiveData então atualiza automaticamente
//    override fun onResume() {
//        super.onResume()
//        buscaNoticiaSelecionada()
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.visualiza_noticia_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.visualiza_noticia_menu_edita -> abreFormularioEdicao()
            R.id.visualiza_noticia_menu_remove -> remove()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun buscaNoticiaSelecionada() {
        viewModel.noticiaEncontrada.observe(this, Observer {
            if (it != null) {
                this.noticia = it
                preencheCampos(it)
            }

        }
        )

    }

    private fun verificaIdDaNoticia() {
        if (noticiaId == 0L) {
            mostraErro(NOTICIA_NAO_ENCONTRADA)
            finish()
        }
    }

    private fun preencheCampos(noticia: Noticia) {
        activity_visualiza_noticia_titulo.text = noticia.titulo
        activity_visualiza_noticia_texto.text = noticia.texto
    }

    private fun remove() {
        viewModel.remove().observe(this, Observer {
            if (it.erro == null) {
                finish()
            } else {
                mostraErro(MENSAGEM_FALHA_REMOCAO)
            }
        })
    }

    private fun abreFormularioEdicao() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, noticiaId)
        startActivity(intent)
    }

}
