package accelerate.simple

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform