/**
 * Написать программу, которая обрабатывает введённые пользователем в консоль команды:
 * exit
 * help
 * add <Имя> phone <Номер телефона>
 * add <Имя> email <Адрес электронной почты>
 * После выполнения команды, кроме команды exit, программа ждёт следующую команду.
 * Имя – любое слово.
 * Если введена команда с номером телефона, нужно проверить, что указанный телефон может начинаться с +,
 * затем идут только цифры. При соответствии введённого номера этому условию – выводим его на экран вместе с
 * именем, используя строковый шаблон. В противном случае - выводим сообщение об ошибке.
 * Для команды с электронной почтой делаем то же самое, но с другим шаблоном – для простоты, адрес должен содержать
 * три последовательности букв, разделённых символами @ и точкой.
 */
fun main(args: Array<String>) {
    while (true) {
        val userCommand = readln().split(" ")
        when {
            userCommand[0] == "exit" -> return
            userCommand[0] == "add" -> addPerson(userCommand)
            userCommand[0] == "help" -> printHelp()
            else -> println("Wrong command")
        }
    }
}

fun addPerson(userCommand: List<String>) {
    if (userCommand[2] == "phone") {
        if (checkNumberFormat(userCommand[3])) {
            println(
                "Person added:\n " +
                "Name: ${userCommand[1]}\n " +
                "Phone number: ${userCommand[3]}"
            )
        } else println("Wrong phone format")
    }
    if (userCommand[2] == "email")
        if (checkEmailFormat(userCommand[3])) {
            println(
                "Person added:\n " +
                "Name: ${userCommand[1]}\n " +
                "Email: ${userCommand[3]}"
            )
        } else println("Wrong email format")
}

fun checkNumberFormat(phoneNumber: String): Boolean =
    phoneNumber.matches(Regex("""(\+7|8)[\s(]*\d{3}[)\s]*\d{3}[\s-]?\d{2}[\s-]?\d{2}"""))

fun checkEmailFormat(email: String): Boolean =
    email.matches(Regex("""[a-z0-9]+@[a-z]+\.[a-z]{2,3}"""))

fun printHelp() {
    println("""
        exit - Выход
        help - Помощь
        add <Имя> phone <Номер телефона> - Добавление контакта с телефоном
            Формат телефона: +7(123)123-45-67
        add <Имя> email <Адрес электронной почты> - Добавление контакта с электронным адресом
            Формат email: someone@gmail.com
    """.trimIndent())
}


