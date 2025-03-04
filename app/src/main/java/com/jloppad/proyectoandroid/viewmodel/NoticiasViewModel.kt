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

    private val _listaNoticias = MutableStateFlow<List<Articulo>>(emptyList())
    val listaNoticias: StateFlow<List<Articulo>> = _listaNoticias

    private val _cargando = MutableStateFlow(true)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _busqueda = MutableStateFlow("España")
    val busqueda: StateFlow<String> = _busqueda

    init {
        obtenerNoticias()
    }

    fun actualizarBusqueda(nuevaBusqueda: String) {
        _busqueda.value = nuevaBusqueda
        if (nuevaBusqueda.isNotEmpty()){
            obtenerNoticias()
        }
    }

    private fun obtenerNoticias() {
        viewModelScope.launch {
            try {
                _cargando.value = true
                _error.value = null  // Resetear errores

                val respuesta: RespuestaNoticias = repositorio.obtenerNoticias(claveApi, _busqueda.value)

                if (respuesta.articulos.isNullOrEmpty()) {
                    _error.value = "No hay noticias disponibles"
                } else {
                    _listaNoticias.value = respuesta.articulos
                }

            } catch (e: Exception) {
                _error.value = "Error al obtener noticias: ${e.message}"
                _listaNoticias.value = emptyList()
            } finally {
                _cargando.value = false
            }
        }
    }
}
