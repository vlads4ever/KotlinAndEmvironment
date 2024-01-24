package org.example

/**
 * За основу берём код решения домашнего задания из предыдущего семинара и дорабатываем его.
 *
 * — Создайте иерархию sealed классов, которые представляют собой команды. В корне иерархии интерфейс Command.
 * — В каждом классе иерархии должна быть функция isValid(): Boolean, которая возвращает true, если команда введена
 * с корректными аргументами. Проверку телефона и email нужно перенести в эту функцию.
 * — Напишите функцию readCommand(): Command, которая читает команду из текстового ввода, распознаёт её и возвращает
 * один из классов-наследников Command, соответствующий введённой команде.
 * — Создайте data класс Person, который представляет собой запись о человеке. Этот класс должен содержать поля:
 * name – имя человека
 * phone – номер телефона
 * email – адрес электронной почты
 * — Добавьте новую команду show, которая выводит последнее значение, введённой с помощью команды add. Для этого
 * значение должно быть сохранено в переменную типа Person. Если на момент выполнения команды show не было ничего
 * введено, нужно вывести на экран сообщение “Not initialized”.
 * — Функция main должна выглядеть следующем образом. Для каждой команды от пользователя:
 * Читаем команду с помощью функции readCommand
 * Выводим на экран получившийся экземпляр Command
 * Если isValid для команды возвращает false, выводим help. Если true, обрабатываем команду внутри when.
 */

var person: Person? = null

fun main() {
    var run: Boolean = true
    while (run) {
        val userCommand = readln().split(" ")
        run = runCommand(userCommand)
    }
}

fun readCommand(userCommand: List<String>): Command? =
    when (userCommand[0]) {
        "exit" -> Command.Exit
        "add" -> if (userCommand[2] == "phone") {
                    Command.AddPhone(userCommand[1], userCommand[3])
                } else if (userCommand[2] == "email") {
                    Command.AddEmail(userCommand[1], userCommand[3])
                } else null
        "help" -> Command.Help
        "show" -> Command.Show
        else -> null
    }


fun runCommand(userCommand: List<String>): Boolean {
    when (val parsedCommand = readCommand(userCommand)) {
        null -> println("Wrong command")
        is Command.Exit -> return false
        is Command.AddEmail ->
            if (parsedCommand.getPerson() != null) {
                person = parsedCommand.getPerson()
                println("Person was created")
            } else {
                println("Person was not created")
            }
        is Command.AddPhone ->
            if (parsedCommand.getPerson() != null) {
                person = parsedCommand.getPerson()
                println("Person was created")
            } else {
                println("Person was not created")
            }
        is Command.Help -> parsedCommand.printHelp()
        is Command.Show ->
            if (person != null) {
                println(person)
            } else {
                println("Not initialized")
            }
    }
    return true
}

