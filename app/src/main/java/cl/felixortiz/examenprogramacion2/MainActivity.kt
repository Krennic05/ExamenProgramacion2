package cl.felixortiz.examenprogramacion2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
import cl.felixortiz.examenprogramacion2.entities.TipoMedidor
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class MainActivity : ComponentActivity() {

    private lateinit var medidorRepository:MedidorRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        medidorRepository = MedidorRepository.getInstance(this)

        setContent {
            Apptareas()

        }
    }

    companion object {
        var medidorObjeto: Medidor = Medidor(1,"",1, LocalDate.now())
    } //Esta variable global la creamos para almacenar el medidor que se va a crear o eliminar.
}

@Composable
fun AppMedidores(
    PageFormularioUI: () -> Unit, //Este botón nos lleva al formulario
    EliminarFormularioUI: () -> Unit, //Este botón nos permite eliminar elementos
    borrado: Boolean = false, //Esto indica si hay que borrar la variable global de la base de datos
    creado: Boolean = false, //Esto nos permitirá agregar nuevos medidores a la base de datos
    ) {

    val contexto = LocalContext.current
    var medidores by remember {
        mutableStateOf( emptyList<Medidor>() )
    }

    LaunchedEffect(Unit) {

        withContext(Dispatchers.IO) {

            if(borrado){
                MedidorRepository.getInstance(contexto).eliminar(MainActivity.medidorObjeto)
            }
            if(creado){
                MedidorRepository.getInstance(contexto).agregar(MainActivity.medidorObjeto)
            }

            medidores = MedidorRepository.getInstance(contexto).obtenerTodos()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
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
            ListaMedidoresUI(medidores,EliminarFormularioUI)
        }
    }
}

@Composable
fun ListaMedidoresUI(
    medidores:List<Medidor>,
    EliminarFormularioUI: () -> Unit,
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
                    Log.d("TAG",it.tipo)
                    Log.d("TAG",TipoMedidor.LUZ.toString())
                    IconoMedidor(it)
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
                        MainActivity.medidorObjeto = it
                        EliminarFormularioUI()
                    }) {
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
                {navController.navigate("formulario")},
                {navController.navigate("eliminar")},
            )
        }
        composable("formulario"){

            PageFormularioUI(
                {navController.navigate("inicio")},
                {navController.navigate("crear")},

            )
        }
        composable("eliminar"){
            AppMedidores(
                {navController.navigate("formulario")},
                {navController.navigate("eliminar")},
                true,
            )
        }
        composable("crear"){
            AppMedidores(
                {navController.navigate("formulario")},
                {navController.navigate("eliminar")},
                creado = true,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageFormularioUI(
    VolverUI: () -> Unit,
    CrearUI: () -> Unit,
    ){
    var valor by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val types = arrayOf("Luz", "Gas", "Agua")
    var selectedText by remember { mutableStateOf(types[0]) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {VolverUI()}) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
        })
    { innerPadding ->
    Column(
        modifier = Modifier
            .fillMaxSize().padding(horizontal = innerPadding.calculateLeftPadding(LayoutDirection.Ltr))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    value = selectedText,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    types.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedText = item
                                expanded = false
                                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = valor,
            onValueChange = { value -> valor = value.filter { it.isDigit() } },
            label = { Text("Valor") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                MainActivity.medidorObjeto = Medidor(0, selectedText, valor.toInt(), LocalDate.now())
                CrearUI()
                      },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }

    }
    }
}

@Composable
fun IconoMedidor(medidor: Medidor) {
    Log.d("TAG",medidor.tipo.toUpperCase())
    Log.d("TAG",medidor.tipo)
    Log.d("TAG",TipoMedidor.LUZ.toString())
    return when(TipoMedidor.valueOf(medidor.tipo.toUpperCase())) {
        TipoMedidor.LUZ -> Image(
            bitmap = painterResource(id = R.drawable.luz),
            TipoMedidor.LUZ.toString(),
        )

        TipoMedidor.GAS -> Icon(
            painter = painterResource(id = R.drawable.gas),
            TipoMedidor.GAS.toString(),
        )
        TipoMedidor.AGUA -> Icon(
            painter = painterResource(id = R.drawable.agua),
            TipoMedidor.AGUA.toString(),
        )
    }
}


