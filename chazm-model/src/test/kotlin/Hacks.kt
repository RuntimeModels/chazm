import org.mockito.Mockito


fun Double.scaleInt(scalar: Int = 10) = (this * scalar).toInt()

fun <T> any(): T {
    return Mockito.any<T>() as T
}

inline fun <reified T> mock(): T {
    return Mockito.mock(T::class.java)
}
