import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jloppad.proyectoandroid.datos.modelo.Articulo
import com.jloppad.proyectoandroid.datos.modelo.RespuestaNoticias
import com.jloppad.proyectoandroid.repositorio.NoticiasRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoticiasViewModel : ViewModel() {
    private val repositorio = NoticiasRepositorio()
    private val claveApi = "50e87cb808c448d58c11637891a26ddc"

    private val _listaNoticiasPupulares = MutableStateFlow<List<Articulo>>(emptyList())
    val listaNoticiasPopulares: StateFlow<List<Articulo>> = _listaNoticiasPupulares

    private val _listaNoticiasRecientes = MutableStateFlow<List<Articulo>>(emptyList())
    val listaNoticiasRecientes: StateFlow<List<Articulo>> = _listaNoticiasRecientes

    private val _listaNoticiasRelevantes= MutableStateFlow<List<Articulo>>(emptyList())
    val listaNoticiasRelevantes: StateFlow<List<Articulo>> = _listaNoticiasRelevantes

    private val _cargando = MutableStateFlow(true)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _busqueda = MutableStateFlow("España")
    val busqueda: StateFlow<String> = _busqueda

    init {
        obtenerNoticiasPopulares()
        obtenerNoticiasRecientes()
        obtenerNoticiasRelevantes()
    }

    fun actualizarBusqueda(nuevaBusqueda: String) {
        _busqueda.value = nuevaBusqueda
        if (nuevaBusqueda.isNotEmpty()) {
            obtenerNoticiasPopulares()
            obtenerNoticiasRecientes()
            obtenerNoticiasRelevantes()
        }
    }


    private fun obtenerNoticiasPopulares() {
        viewModelScope.launch {
            try {
                _cargando.value = true
                _error.value = null

                val respuesta: RespuestaNoticias = repositorio.obtenerNoticias(claveApi, _busqueda.value, "popularity")

                if (respuesta.articulos.isNullOrEmpty()) {
                    _error.value = "No hay noticias disponibles"
                } else {
                    _listaNoticiasPupulares.value = respuesta.articulos
                }

            } catch (e: Exception) {
                _error.value = "Error al obtener noticias: ${e.message}"
                _listaNoticiasPupulares.value = emptyList()
            } finally {
                _cargando.value = false
            }
        }
    }

    private fun obtenerNoticiasRecientes() {
        viewModelScope.launch {
            try {
                _cargando.value = true
                _error.value = null

                val respuesta: RespuestaNoticias = repositorio.obtenerNoticias(claveApi, _busqueda.value, "publishedAt")

                if (respuesta.articulos.isNullOrEmpty()) {
                    _error.value = "No hay noticias recientes disponibles"
                } else {
                    _listaNoticiasRecientes.value = respuesta.articulos
                }

            } catch (e: Exception) {
                _error.value = "Error al obtener noticias recientes: ${e.message}"
                _listaNoticiasRecientes.value = emptyList()
            } finally {
                _cargando.value = false
            }
        }
    }

    private fun obtenerNoticiasRelevantes() {
        viewModelScope.launch {
            try {
                _cargando.value = true
                _error.value = null

                val respuesta: RespuestaNoticias = repositorio.obtenerNoticias(claveApi, _busqueda.value, "relevancy")

                if (respuesta.articulos.isNullOrEmpty()) {
                    _error.value = "No hay noticias relevantes disponibles"
                } else {
                    _listaNoticiasRelevantes.value = respuesta.articulos
                }

            } catch (e: Exception) {
                _error.value = "Error al obtener noticias relevantes: ${e.message}"
                _listaNoticiasRelevantes.value = emptyList()
            } finally {
                _cargando.value = false
            }
        }
    }

}
