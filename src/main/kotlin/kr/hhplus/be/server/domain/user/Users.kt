package kr.hhplus.be.server.domain.user

data class Users(
    val id: Long,
    val name: String,
    var balance: Long,
) {

    fun addPoint(point: Long): Long {
        return balance + point
    }

    fun pay(point: Long): Long {
        return balance - point
    }

}

fun UserEntity.toUsers() =
    Users(
        id = this.id,
        name = this.name,
        balance = this.balance
    )
