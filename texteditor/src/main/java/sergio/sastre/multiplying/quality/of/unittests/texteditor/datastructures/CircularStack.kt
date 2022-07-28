package sergio.sastre.multiplying.quality.of.unittests.texteditor.datastructures

import java.util.*

/**
 * A stack which removes the oldest element before pushing a new one if
 * the bufferSize is reached.
 */
class CircularStack<T>(private val bufferSize: Int) : Stack<T>() {

    override fun push(item: T): T {
        if (size == bufferSize){
            removeFirst()
        }
        return super.push(item)
    }
}