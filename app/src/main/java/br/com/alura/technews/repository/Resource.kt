package br.com.alura.technews.repository

import br.com.alura.technews.model.Noticia

class Resource<T>(
    val dado: T?,
    val erro: String? = null
)

//erro inicializado como null para não ter que ficar enviando o null toda hora


//deixar generica também (para poder reutilizar)
 fun <T> criaResourceFalha(
    resourceAtual: Resource<T?>?,
    erro: String?
): Resource<T?> {
    if (resourceAtual != null) {
        return Resource(dado = resourceAtual.dado, erro = erro)
    }
    return Resource(dado = null, erro = erro)
}