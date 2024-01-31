import org.example.DslJson
import java.io.File

sealed interface Command {
    fun isValid(): Boolean
}

class Export(
    private val path: String,
    private val personsMap: MutableMap<String, Person>
): Command {
    private val dslJson: DslJson by lazy {DslJson(personsMap.values.toList())}

    override fun isValid(): Boolean = File(path).exists()

    fun saveToJson() {
        val output: String = dslJson.getJson()
        val fileExists: String = if (isValid()) "The data has been overwritten into a file $path"
            else "The data has been written into a file $path"
        File(path).writeText(output)
        println(fileExists)
    }
}

class Find(
    private val email: String? = null,
    private val phone: String? = null,
    private val personsMap: MutableMap<String, Person>
): Command {
    override fun isValid(): Boolean = true

    fun findPersons(): List<Person>? {
        if (email != null) return personsMap.filter { it.value.emails.contains(email) }.values.toList()
        if (phone != null) return personsMap.filter { it.value.phones.contains(phone) }.values.toList()
        return null
    }
}

class AddEmail(
    private val name: String,
    private val email: String,
    private var personsMap: MutableMap<String, Person>
): Command {
    override fun isValid(): Boolean =
        email.matches(Regex("""[a-z0-9]+@[a-z]+\.[a-z]{2,3}"""))

    fun getPerson() {
        if (isValid()) {
            val person: Person? = personsMap.values.find { it.name == this.name }
            if (person == null) {
                personsMap[name] = Person(name = this.name, email = this.email)
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
    private var personsMap: MutableMap<String, Person>
): Command {
    override fun isValid(): Boolean =
        phone.matches(Regex("""(\+7|8)[\s(]*\d{3}[)\s]*\d{3}[\s-]?\d{2}[\s-]?\d{2}"""))

    fun getPerson() {
        if (isValid()) {
            val person: Person? = personsMap.values.find { it.name == this.name }
            if (person == null) {
                personsMap[name] = Person(name = this.name, phone = this.phone)
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
    private val personsMap: MutableMap<String, Person>
): Command {
    override fun isValid(): Boolean = personsMap.isNotEmpty()

    fun printPerson() {
        if (isValid()) {
            val person: Person? = personsMap.values.find { it.name == this.name }
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
                export <Путь к файлу> - Сохранить контакты в файл JSON
                ======================================================================================
                """.trimIndent()
        )
    }
}