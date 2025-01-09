package kr.hhplus.be.server.domain.user


interface UserRepository {
    fun saveUserBalance(userEntity: UserEntity)
    fun saveUserBalance2(userId: Long, totalBalance: Long)
    fun findBalanceById(id: Long): Int
    fun findById(id: Long): UserEntity?
}