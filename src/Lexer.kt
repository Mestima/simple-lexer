import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import java.lang.StringBuilder

private val allTerminals: List<Terminal> = arrayListOf(
    Terminal("VAR", "[a-zA-Z_][a-zA-Z0-9_]*"),
    Terminal("TYPE", "string|int|float", 1),
    Terminal("NUM", "\\d*"),
    Terminal("FLOAT", "\\d+[.]\\d*"),
    Terminal("STR", "[\"][\\s\\S][\"]",1),
    Terminal("WHILE_KEYWORD", "while", 1),
    Terminal("OP", "[+-/*]"),
    Terminal("EQ", "="),
    Terminal("WS", "\\s+"),
    Terminal("TAB", "\\t+"),
    Terminal("BR", ";")
)

fun main(args: Array<String>) {
    val input = StringBuilder(lookupInput(args))
    val lexemes: ArrayList<Lexeme> = arrayListOf()

    input.append('&') // cuz I don't want to add it every time manually
    // also Windows does not support this symbol input lmao

    while (input[0] != '&') {
        val lex: Lexeme = nextLexeme(input)
        lexemes.add(lex)
        input.delete(0, lex.getValue().length)
    }
    fancyPrint(lexemes)
}

fun lookupInput(args: Array<String>): String {
    if (args.isEmpty()) {
        throw IllegalArgumentException("Input string not found")
    }
    return args[0]
}

fun anyTerminalMatches(buffer: StringBuilder): Boolean {
    return lookupTerminals(buffer).isNotEmpty()
}

fun lookupTerminals(buffer: StringBuilder): ArrayList<Terminal> {
    val terminals: ArrayList<Terminal> = arrayListOf()

    allTerminals.forEach {
        if (it.matches(buffer)) {
            terminals.add(it)
        }
    }
    return terminals
}

fun nextLexeme(input: StringBuilder): Lexeme {
    val buffer = StringBuilder()
    buffer.append(input[0])
    if (anyTerminalMatches(buffer)) {
        while (anyTerminalMatches(buffer) && buffer.length < input.length) {
            buffer.append(input[buffer.length])
        }
        buffer.deleteCharAt(buffer.length - 1)

        val terminals: ArrayList<Terminal> = lookupTerminals(buffer)
        return Lexeme(getPrioritizedTerminal(terminals), buffer.toString())
    } else {
        throw RuntimeException("Unexpected symbol '$buffer'")
    }
}

fun getPrioritizedTerminal(terminals: ArrayList<Terminal>): Terminal {
    var priorTerm: Terminal = terminals[0]
    terminals.forEach {
        if (it.getPriority() > priorTerm.getPriority()) {
            priorTerm = it
        }
    }
    return priorTerm
}

fun fancyPrint(lexemes: ArrayList<Lexeme>) {
    lexemes.forEach {
        println("[${it.getTerminal().getId()} ${it.getValue()}]")
    }
}