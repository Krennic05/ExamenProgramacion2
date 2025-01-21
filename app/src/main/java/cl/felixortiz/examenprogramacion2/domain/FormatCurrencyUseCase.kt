package cl.felixortiz.examenprogramacion2.domain

import java.text.NumberFormat
import java.util.Locale

class FormatCurrencyUseCase {

    private val formatter = NumberFormat.getCurrencyInstance(Locale("es","CL"))
    //Aqui fije que los valores se viesen en pesos chilenos

    operator fun invoke(valor:Int):String {
        return formatter.format(valor)
    }
}