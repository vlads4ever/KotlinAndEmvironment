sealed interface Command {
    fun isValid(): Boolean

    class Find(
        private val email: String? = null,
        private val phone: String? = null,
        private val personsList: MutableList<Person>
    ): Command {
        override fun isValid(): Boolean = true

        fun findPersons(): List<Person>? {
            if (email != null) return personsList.filter { it.emails.contains(email) }
            if (phone != null) return personsList.filter { it.phones.contains(phone) }
            return null
        }
    }

    class AddEmail(
        private val name: String,
        private val email: String,
        private var personsList: MutableList<Person>
    ): Command {
        override fun isValid(): Boolean =
            email.matches(Regex("""[a-z0-9]+@[a-z]+\.[a-z]{2,3}"""))

        fun getPerson() {
            if (isValid()) {
                val person: Person? = personsList.find { it.name == this.name }
                if (person == null) {
                    personsList.add(Person(name = this.name, email = email))
                    println("Person was created")
                } else {
                    person.addEmail(this.email)
                }
            } else {
                println("Wrong email format")
                Help.printHelp()
            }
        }
    }

    class AddPhone(
        private val name: String,
        private val phone: String,
        private var personsList: MutableList<Person>
    ): Command {
        override fun isValid(): Boolean =
            phone.matches(Regex("""(\+7|8)[\s(]*\d{3}[)\s]*\d{3}[\s-]?\d{2}[\s-]?\d{2}"""))

        fun getPerson() {
            if (isValid()) {
                val person: Person? = personsList.find { it.name == this.name }
                if (person == null) {
                    personsList.add(Person(name = this.name, phone = this.phone))
                    println("Person was created")
                } else {
                    person.addPhone(this.phone)
                }
            } else {
                println("Wrong phone format")
                Help.printHelp()
            }
        }
    }

    class Show(
        private val name: String,
        private val personsList: MutableList<Person>
    ): Command {
        override fun isValid(): Boolean = personsList.isNotEmpty()

        fun printPerson() {
            if (isValid()) {
                val person: Person? = personsList.find { it.name == this.name }
                if (person != null) println(person) else println("No such person")
            } else println("Not initialized")
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
                ======================================================================================
                exit - Выход
                help - Помощь
                add <Имя> phone <Номер телефона> - Добавление телефона к существующему контакту, 
                    либо создание нового контакта
                    Формат телефона: +7(123)123-45-67
                add <Имя> email <Адрес электронной почты> - Добавление почты к существующему контакту,
                    либо создание нового контакта
                    Формат email: someone@gmail.com
                show <Имя> - Вывести телефон и почту контакта
                find phone <Номер телефона> - Вывести список контактов с таким телефоном
                find email <Адрес электронной почты> - Вывести список контактов с таким адресом
                ======================================================================================
                """.trimIndent()
            )
        }
    }
}