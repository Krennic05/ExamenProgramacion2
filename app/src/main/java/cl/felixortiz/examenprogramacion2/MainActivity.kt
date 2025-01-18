package cl.felixortiz.examenprogramacion2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.felixortiz.examenprogramacion2.domain.FormatCurrencyUseCase
import cl.felixortiz.examenprogramacion2.domain.FormatDateUseCase
import cl.felixortiz.examenprogramacion2.entities.Medidor
import cl.felixortiz.examenprogramacion2.entities.MedidorRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private lateinit var medidorRepository:MedidorRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        medidorRepository = MedidorRepository.getInstance(this)

        lifecycleScope.launch(Dispatchers.IO) {
            medidorsDePrueba().forEach {
                medidorRepository.agregar(
                    it
                )
            }
        }

        setContent {
            Apptareas()
            //val navController = rememberNavController()
            //AppMedidores(
            //    PageFormularioUI = {navController.navigate("formulario")}
            //)
        }
    }
}

fun medidorsDePrueba():List<Medidor> {
    return listOf(
        Medidor(1, "Supermercado", 150_000, LocalDate.now()),
        Medidor(2,"Veterinario Sta María", 30_000, LocalDate.now()),
        Medidor(3,"Salida a comer", 40_000, LocalDate.now()),
    )
}

//@Preview(showSystemUi = true)
@Composable
fun AppMedidores(
    PageFormularioUI: () -> Unit,) {
    Log.i("TAG", "Hello World")
    val contexto = LocalContext.current
    var medidores by remember {
        mutableStateOf( emptyList<Medidor>() )
    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            medidores = MedidorRepository.getInstance(contexto).obtenerTodos()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                Log.d("TAG", "AAAAH")
                PageFormularioUI()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        },
        modifier = Modifier.padding(horizontal = 10.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = innerPadding.calculateLeftPadding(LayoutDirection.Ltr))
        ) {
            ListaMedidoresUI(medidores)
        }
    }
}

@Composable
fun AppMedidores2(
    PageFormularioUI: () -> Unit,
) {
    val contexto = LocalContext.current
    var medidores by remember {
        mutableStateOf( emptyList<Medidor>() )
    }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            medidores = MedidorRepository.getInstance(contexto).obtenerTodos()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                Log.d("TAG", "AAAAH2")
                PageFormularioUI()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        },
        modifier = Modifier.padding(horizontal = 10.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = innerPadding.calculateLeftPadding(LayoutDirection.Ltr))
        ) {
            ListaMedidoresUI(medidores)
        }
    }
}

//@Composable
//fun IconoMedidor(medidor: Medidor) {
//    when(TipoMedidor.valueOf(medidor.categoria)) {
//        TipoMedidor.DIVERSION -> Icon(
//            imageVector = Icons.Filled.Face, //BUSCAR ICONOS MAS APROPIADOS
//            contentDescription = TipoMedidor.AGUA.toString())
//        TipoMedidor.SALUD -> Icon(
//            imageVector = Icons.Filled.Favorite,
//            contentDescription = TipoMedidor.LUZ.toString())
//        TipoMedidor.COMIDA -> Icon(
//            imageVector = Icons.Filled.Home,
//      contentDescription = TipoMedidor.GAS.toString())
//    }
//}

@Composable
fun ListaMedidoresUI(
    medidores:List<Medidor>
) {
    LazyColumn() {
        items(medidores){
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = FormatDateUseCase()(it.fecha),
                        style = TextStyle(
                            fontSize = 10.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column() {
                        Text(it.tipo)
                        Text(
                            text = FormatCurrencyUseCase()(it.valor),
                            style = TextStyle(
                                fontSize = 10.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                Row() {
                    IconButton(onClick = {
                        Log.d("TAG", "AAAAH3")}) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = {
                        Log.d("TAG", "AAAAH4") }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Eliminar")
                    }
                }
            }
            Divider()
        }
    }
}

@Composable
fun Apptareas(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "inicio"){
        composable("inicio"){
            AppMedidores(
                {navController.navigate("formulario")}
            )
        }
        composable("formulario"){
            //AppMedidores2(
            //    {navController.navigate("inicio")}
            //)
            PageFormularioUI(
                onClick = {navController.popBackStack()}
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PageInicioUI(
    onClick: () -> Unit = {}
){
   Box(
       modifier = Modifier.fillMaxSize()
           .background(Color.Gray)
           .clickable { onClick() }

   )
}

@Composable
fun PageFormularioUI(
    onClick: () -> Unit = {}
){
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.LightGray)
            .clickable { onClick() }

    )
}

