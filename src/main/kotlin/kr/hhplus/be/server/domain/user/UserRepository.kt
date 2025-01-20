package kr.hhplus.be.server.domain.user


interface UserRepository {
    fun saveUserBalance(userEntity: UserEntity)
    fun updateUserBalance(userId: Long, totalBalance: Long)
    fun findBalanceById(id: Long): Int
    fun findById(id: Long): UserEntity?
    fun save(user: UserEntity): UserEntity
}