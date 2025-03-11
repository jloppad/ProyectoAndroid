import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jloppad.proyectoandroid.datos.modelo.Articulo
import com.jloppad.proyectoandroid.datos.modelo.RespuestaNoticias
import com.jloppad.proyectoandroid.datos.modelo.TipoNoticia
import com.jloppad.proyectoandroid.repositorio.NoticiasRepositorio
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoticiasViewModel : ViewModel() {
    private val repositorio = NoticiasRepositorio()
    private val claveApi = "50e87cb808c448d58c11637891a26ddc"

    private val _listaNoticiasPopulares = MutableStateFlow<List<Articulo>>(emptyList())
    val listaNoticiasPopulares: StateFlow<List<Articulo>> = _listaNoticiasPopulares

    private val _listaNoticiasRecientes = MutableStateFlow<List<Articulo>>(emptyList())
    val listaNoticiasRecientes: StateFlow<List<Articulo>> = _listaNoticiasRecientes

    private val _listaNoticiasRelevantes = MutableStateFlow<List<Articulo>>(emptyList())
    val listaNoticiasRelevantes: StateFlow<List<Articulo>> = _listaNoticiasRelevantes

    private val _cargando = MutableStateFlow(true)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _busqueda = MutableStateFlow("España")
    val busqueda: StateFlow<String> = _busqueda

    private val _pantallaActiva = MutableStateFlow<TipoNoticia>(TipoNoticia.RECIENTES)
    val pantallaActiva: StateFlow<TipoNoticia> = _pantallaActiva

    init {
        obtenerNoticiasRecientes()

        viewModelScope.launch {
            _pantallaActiva.collect { tipoNoticia ->
                actualizarNoticiasSegunPantalla(tipoNoticia)
            }
        }
    }

    fun cambiarPantalla(nuevaPantalla: TipoNoticia) {
        _pantallaActiva.value = nuevaPantalla
        actualizarNoticiasSegunPantalla(nuevaPantalla)
    }


    fun actualizarBusqueda(nuevaBusqueda: String, tipoNoticia: TipoNoticia) {
        _busqueda.value = nuevaBusqueda
        cambiarPantalla(tipoNoticia) 
    }


    private fun actualizarNoticiasSegunPantalla(tipoNoticia: TipoNoticia) {
        when (tipoNoticia) {
            TipoNoticia.POPULARES -> obtenerNoticiasPopulares()
            TipoNoticia.RECIENTES -> obtenerNoticiasRecientes()
            TipoNoticia.RELEVANTES -> obtenerNoticiasRelevantes()
        }
    }

    private fun obtenerNoticiasPopulares() {
        viewModelScope.launch {
            try {
                _cargando.value = true
                _error.value = null

                val respuesta: RespuestaNoticias = repositorio.obtenerNoticias(claveApi, _busqueda.value, "popularity")

                if (respuesta.articulos.isNullOrEmpty()) {
                    _error.value = "No hay noticias populares disponibles"
                } else {
                    _listaNoticiasPopulares.value = respuesta.articulos
                }

            } catch (e: Exception) {
                _error.value = "Error al obtener noticias populares: ${e.message}"
                _listaNoticiasPopulares.value = emptyList()
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
