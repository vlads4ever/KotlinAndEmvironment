package org.example

import Person

class DslJson(
    private val personsList: List<Person>
) {
    fun getJson(): String {
        val jsonContent = json {
            for (person in personsList) {
                jsonObject {
                    name(person.name)
                    emails {
                        for (emailItem in person.emails) {
                            email(emailItem)
                        }
                    }
                    phones {
                        for (phoneItem in person.phones) {
                            phone(phoneItem)
                        }
                    }
                }
            }
        }
        return jsonContent.toString()
    }
}

class Json {
    private val jsonObjects: MutableList<Any> = mutableListOf()

    fun jsonObject(callback: JsonObject.() -> Unit) {
        jsonObjects.add(JsonObject().apply(callback))
    }

    override fun toString(): String {
        return "[${jsonObjects.joinToString(",")}]"
    }
}

fun json(callback: Json.() -> Unit): Json {
    val builder = Json()
    builder.callback()
    return builder
}

class JsonObject {
    private var nameTag = Name()
    private var emailsTag = Emails()
    private var phonesTag = Phones()

    fun name(value: String) {
        nameTag = Name(value)
    }

    fun emails(callback: Emails.() -> Unit) {
        emailsTag.callback()
    }

    fun phones(callback: Phones.() -> Unit) {
        phonesTag.callback()
    }

    override fun toString(): String {
        return """{$nameTag,$emailsTag,$phonesTag}"""
    }
}

class Name (val nameTag: String? = null) {
    override fun toString(): String {
        return """"name": "$nameTag""""
    }
}

class Emails {
    private var emailsList: MutableList<Email> = mutableListOf()

    fun email(value: String) {
        emailsList.add(Email(value))
    }

    override fun toString(): String {
        return """"emails": [${emailsList.joinToString(",")}]"""
    }
}

class Email(val emailTag: String? = null) {
    override fun toString(): String {
        return """"$emailTag""""
    }
}

class Phones {
    private var phonesList: MutableList<Phone> = mutableListOf()

    fun phone(value: String) {
        phonesList.add(Phone(value))
    }

    override fun toString(): String {
        return """"phones": [${phonesList.joinToString(",")}]"""
    }
}

class Phone(val phoneTag: String? = null) {
    override fun toString(): String {
        return """"$phoneTag""""
    }
}

