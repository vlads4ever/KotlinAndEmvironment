package org.example

sealed interface Command {
    fun isValid(): Boolean

    class AddEmail(
        private val name: String,
        private val email: String
    ): Command {
        override fun isValid(): Boolean =
            email.matches(Regex("""[a-z0-9]+@[a-z]+\.[a-z]{2,3}"""))

        fun getPerson(): Person? {
            if (isValid()) {
                return Person(name,null, email)
            } else {
                println("Wrong email format")
                Help.printHelp()
                return null
            }
        }
    }

    class AddPhone(
        private val name: String,
        private val phone: String
    ): Command {
        override fun isValid(): Boolean =
            phone.matches(Regex("""(\+7|8)[\s(]*\d{3}[)\s]*\d{3}[\s-]?\d{2}[\s-]?\d{2}"""))

        fun getPerson(): Person? {
            if (isValid()) {
                return Person(name,phone, null)
            } else {
                println("Wrong phone number format")
                Help.printHelp()
                return null
            }
        }
    }

    data object Show : Command {
        override fun isValid(): Boolean {
            TODO("Not yet implemented")
        }
    }

    data object Exit : Command {
        override fun isValid(): Boolean {
            TODO("Not yet implemented")
        }
    }

    data object Help : Command {
        override fun isValid(): Boolean {
            TODO("Not yet implemented")
        }

        fun printHelp() {
            println(
                """
            exit - Выход
            help - Помощь
            add <Имя> phone <Номер телефона> - Добавление контакта с телефоном
                Формат телефона: +7(123)123-45-67
            add <Имя> email <Адрес электронной почты> - Добавление контакта с электронным адресом
                Формат email: someone@gmail.com
            show - Вывести последнюю запись
        """.trimIndent()
            )
        }
    }
}

