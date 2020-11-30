enum class Currency {
    EURO {
        override fun toRus(): String = "евро"
        override fun toEng(): String = "euro"
        override fun sign(): String = "€"
    },
    DOLLAR {
        override fun toRus(): String = "доллар"
        override fun toEng(): String = "dollar"
        override fun sign(): String = "$"
    };

    abstract fun toRus(): String
    abstract fun toEng(): String
    abstract fun sign(): String
}