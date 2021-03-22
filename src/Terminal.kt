import java.util.regex.Pattern

class Terminal(private val id: String, private val pattern: String, private val priority: Int = 0) {
    private val patternReal: Pattern = Pattern.compile(pattern)

    fun matches(ch: CharSequence): Boolean {
        return patternReal.matcher(ch).matches()
    }

    fun getId(): String {
        return id
    }

    fun getPriority(): Int {
        return priority
    }
}
