package kr.hhplus.be.server.domain.user

object UserQuery {
    const val findByUserId = """
        SELECT * FROM users
        WHERE id = :userId
    """

    const val saveUserBalance = """
        UPDATE users set balance = :totalBalance where id = :userId
    """
}