package kr.hhplus.be.server.domain.user


interface UserRepository {
    fun findBalanceById(id: Long): Int
    fun findById(id: Long): UserEntity?
    fun save(user: UserEntity): UserEntity
    fun saveAndFlush(user: UserEntity): UserEntity
}