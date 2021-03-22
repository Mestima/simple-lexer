class Lexeme(private val terminal: Terminal, private val value: String) {
    fun getTerminal(): Terminal {
        return terminal
    }

    fun getValue(): String {
        return value
    }
}