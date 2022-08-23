package sergio.sastre.multiplying.quality.of.unittests.texteditor.datastructures

import java.util.*

/**
 * A LIFO that allows a maximum amount of elements. If the [CircularBuffer] is at its max, and
 * a new element is pushed into it, it removes the oldest element first and then pushes the new one.
 */
class CircularBuffer<T>(private val bufferSize: Int) : Stack<T>() {

    override fun push(item: T): T {
        if (size == bufferSize){
            removeFirst()
        }
        return super.push(item)
    }
}