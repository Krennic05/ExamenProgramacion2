package cl.felixortiz.examenprogramacion2.domain

import java.text.NumberFormat

class FormatCurrencyUseCase {

    private val formatter = NumberFormat.getCurrencyInstance()

    operator fun invoke(valor:Int):String {
        return formatter.format(valor)
    }
}