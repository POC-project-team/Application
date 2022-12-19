package ru.nsu.poc2.utils
import android.util.Patterns.EMAIL_ADDRESS
import ru.nsu.poc2.R
import java.util.regex.Pattern

class FieldValidators {
    companion object{
        fun validateFieldsNotEmpty(field: String): Boolean{
            return field!=""
        }
        fun validateEmail(field: String): Boolean{
            return EMAIL_ADDRESS.matcher(field).matches()
        }
        fun validatePassword(field: String): Int{
            if (field.length<8 || field.length > 16) return R.string.short_password
            val numbersAmount = field.count {
                it.isDigit()
            }
            if (numbersAmount<3) return R.string.not_enough_numbers_password
            return 0
        }
    }
}