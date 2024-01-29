class Person(
    val name: String,
    phone: String? = null,
    email: String? = null
) {
    var phones: MutableList<String> = mutableListOf()
    var emails: MutableList<String> = mutableListOf()

    init {
        if (phone != null) phones.add(phone)
        if (email != null) emails.add(email)
    }

    fun addEmail(email: String) {
        emails.add(email)
        println("Email was added")
    }

    fun addPhone(phone: String) {
        phones.add(phone)
        println("Phone was added")
    }

    override fun toString(): String {
        return "Person(name='$name',\n" +
                "      phones=$phones,\n" +
                "      emails=$emails)"
    }


}
