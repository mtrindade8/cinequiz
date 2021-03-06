package br.com.cinequiz.room.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import br.com.cinequiz.domain.Usuario
import br.com.cinequiz.domain.UsuarioRecorde
import br.com.cinequiz.room.dao.UsuarioDao
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val listaUsuarios: Flow<List<Usuario>> = usuarioDao.selecionaTodosUsuarios()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(usuario: Usuario) {
        usuarioDao.insereUsuario(usuario)
    }

    fun selecionaPorID(idUsuario: String): LiveData<Usuario> {
        return usuarioDao.selecionaPorID(idUsuario)
    }
}
