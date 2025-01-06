package kr.hhplus.be.server.domain.user


interface UserRepository {
//    fun saveUserBalance(id: Long, totalBalance: Long): UserEntity
    fun findBalanceById(id: Long): Int
    fun findById(id: Long): UserEntity?
}