/**
 * Продолжаем дорабатывать домашнее задание из предыдущего семинара. За основу берём код решения из предыдущего
 * домашнего задания.
 *
 * — Добавьте новую команду export, которая экспортирует добавленные значения в текстовый файл в формате JSON.
 * Команда принимает путь к новому файлу. Например export /Users/user/myfile.json
 * — Реализуйте DSL (предметно ориентированный язык) на Kotlin, который позволит конструировать JSON и преобразовывать
 * его в строку.
 * — Используйте этот DSL для экспорта данных в файл.
 * — Выходной JSON не обязательно должен быть отформатирован, поля объектов могут идти в любом порядке. Главное,
 * чтобы он имел корректный синтаксис. Такой вывод тоже принимается:
 * [{"emails": ["ew@huh.ru"],"name": "Alex","phones": ["34355","847564"]},{"emails": [],"name": "Tom","phones": ["84755"]}]
 */
private var persons: MutableMap<String, Person> = mutableMapOf()

fun main() {
    var run: Boolean = true
    while (run) {
        val userCommand = readln().split(" ")
        run = runCommand(userCommand)
    }
}

private fun readCommand(userCommand: List<String>): Command? =
    when (userCommand[0]) {
        "exit" -> Exit
        "add" -> if (userCommand.size > 3) {
                    if (userCommand[2] == "phone") {
                        AddPhone(name = userCommand[1], phone = userCommand[3], personsMap = persons)
                    } else if (userCommand[2] == "email") {
                        AddEmail(name = userCommand[1], email = userCommand[3], personsMap = persons)
                    } else null
                } else null
        "help" -> Help
        "show" -> if (userCommand.size > 1) Show(name = userCommand[1], persons) else null
        "find" -> if (userCommand.size > 2) {
                    if (userCommand[1] == "phone") {
                        Find(phone = userCommand[2], personsMap = persons)
                    } else if (userCommand[1] == "email") {
                        Find(email = userCommand[2], personsMap = persons)
                    } else null
                } else null
        "export" -> if (userCommand.size > 1) Export(path = userCommand[1], personsMap = persons) else null
        else -> null
    }


private fun runCommand(userCommand: List<String>): Boolean {
    when (val parsedCommand: Command? = readCommand(userCommand)) {
        null -> { println("Wrong command"); Help.printHelp() }
        is Exit -> return false
        is AddEmail -> parsedCommand.getPerson()
        is AddPhone -> parsedCommand.getPerson()
        is Help -> parsedCommand.printHelp()
        is Show -> parsedCommand.printPerson()
        is Find ->
            if (parsedCommand.findPersons()?.isNotEmpty() == true)
                parsedCommand.findPersons()?.map { println(it) }
            else println("No such persons")

        is Export -> parsedCommand.saveToJson()
    }
    return true
}