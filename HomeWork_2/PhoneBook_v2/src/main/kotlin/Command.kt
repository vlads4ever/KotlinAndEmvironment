package org.example

sealed interface Command {
    fun isValid(): Boolean

    class AddEmail(
        private val name: String,
        private val email: String
    ): Command {
        override fun isValid(): Boolean =
            email.matches(Regex("""[a-z0-9]+@[a-z]+\.[a-z]{2,3}"""))

        fun getPerson(): Person? =
            if (isValid()) {
                Person(name = this.name, email = this.email)
            } else {
                println("Wrong email format")
                Help.printHelp()
                null
            }
    }

    class AddPhone(
        private val name: String,
        private val phone: String
    ): Command {
        override fun isValid(): Boolean =
            phone.matches(Regex("""(\+7|8)[\s(]*\d{3}[)\s]*\d{3}[\s-]?\d{2}[\s-]?\d{2}"""))

        fun getPerson(): Person? =
            if (isValid()) {
                Person(name = this.name, phone = this.phone)
            } else {
                println("Wrong phone number format")
                Help.printHelp()
                null
            }
    }

    class Show(private val person: Person?) : Command {
        override fun isValid(): Boolean = person != null

        fun printPerson() {
            if (isValid()) println(person) else println("Not initialized")
        }
    }

    data object Exit : Command {
        override fun isValid(): Boolean = true
    }

    data object Help : Command {
        override fun isValid(): Boolean = true

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

