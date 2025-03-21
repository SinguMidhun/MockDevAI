package `in`.singu.mockdevai

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform