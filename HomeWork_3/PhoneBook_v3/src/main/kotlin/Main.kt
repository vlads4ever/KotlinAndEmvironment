/**
 * Продолжаем дорабатывать домашнее задание из предыдущего семинара. За основу берём код решения из
 * предыдущего домашнего задания.
 *
 * — Измените класс Person так, чтобы он содержал список телефонов и список почтовых адресов,
 * связанных с человеком.
 * — Теперь в телефонной книге могут храниться записи о нескольких людях. Используйте для этого наиболее
 * подходящую структуру данных.
 * — Команда AddPhone теперь должна добавлять новый телефон к записи соответствующего человека.
 * — Команда AddEmail теперь должна добавлять новый email к записи соответствующего человека.
 * — Команда show должна принимать в качестве аргумента имя человека и выводить связанные с ним телефоны и адреса
 * электронной почты.
 * — Добавьте команду find, которая принимает email или телефон и выводит список людей, для которых записано
 * такое значение.
 */
private var persons: MutableList<Person> = mutableListOf()

fun main() {
    var run: Boolean = true
    while (run) {
        val userCommand = readln().split(" ")
        run = runCommand(userCommand)
    }
}

private fun readCommand(userCommand: List<String>): Command? =
    when (userCommand[0]) {
        "exit" -> Command.Exit
        "add" -> if (userCommand.size > 1) {
                    if (userCommand[2] == "phone") {
                        Command.AddPhone(name = userCommand[1], phone = userCommand[3], personsList = persons)
                    } else if (userCommand[2] == "email") {
                        Command.AddEmail(name = userCommand[1], email = userCommand[3], personsList = persons)
                    } else null
                } else null
        "help" -> Command.Help
        "show" -> if (userCommand.size > 1) Command.Show(name = userCommand[1], persons) else null
        "find" -> if (userCommand.size > 1) {
                    if (userCommand[1] == "phone") {
                        Command.Find(phone = userCommand[2], personsList = persons)
                    } else if (userCommand[1] == "email") {
                        Command.Find(email = userCommand[2], personsList = persons)
                    } else null
                } else null
        else -> null
    }


private fun runCommand(userCommand: List<String>): Boolean {
    when (val parsedCommand = readCommand(userCommand)) {
        null -> { println("Wrong command"); Command.Help.printHelp() }
        is Command.Exit -> return false
        is Command.AddEmail -> parsedCommand.getPerson()
        is Command.AddPhone -> parsedCommand.getPerson()
        is Command.Help -> parsedCommand.printHelp()
        is Command.Show -> parsedCommand.printPerson()
        is Command.Find ->
            if (parsedCommand.findPersons()?.isNotEmpty() == true)
                parsedCommand.findPersons()?.map { println(it) }
            else println("No such persons")
    }
    return true
}