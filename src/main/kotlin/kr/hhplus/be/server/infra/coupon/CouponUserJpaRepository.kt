package kr.hhplus.be.server.infra.coupon

import jakarta.persistence.LockModeType
import kr.hhplus.be.server.domain.coupon.CouponUserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface CouponUserJpaRepository : JpaRepository<CouponUserEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    override fun findById(id: Long): Optional<CouponUserEntity>

    // JOIN FETCH / INNER JOIN 차이점 확인 필요
    @Query(
        """
        SELECT cu FROM CouponUserEntity cu
        JOIN FETCH cu.coupon
        WHERE cu.user.id = :userId
    """
    )
    fun findCouponUserByUserId(@Param("userId") userId: Long): List<CouponUserEntity>


}