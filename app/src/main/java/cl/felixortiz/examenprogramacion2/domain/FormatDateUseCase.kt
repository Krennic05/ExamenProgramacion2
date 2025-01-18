package cl.felixortiz.examenprogramacion2.domain

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

class FormatDateUseCase {
    val formatter = DateTimeFormatterBuilder()
                        .appendValue(ChronoField.YEAR)
                        .appendLiteral("-")
                        .appendValue(ChronoField.MONTH_OF_YEAR)
                        .appendLiteral("-")
                        .appendValue(ChronoField.DAY_OF_MONTH)
                        .toFormatter()

    operator fun invoke(fecha:LocalDate):String {
        return fecha.format(formatter)
    }
}