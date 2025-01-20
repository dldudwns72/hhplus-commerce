package kr.hhplus.be.server.infra.coupon

import kr.hhplus.be.server.domain.coupon.CouponUserEntity
import kr.hhplus.be.server.domain.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CouponUserJpaRepository : JpaRepository<CouponUserEntity, Long> {

    // JOIN FETCH / INNER JOIN 차이점 확인 필요
    @Query(
        """
        SELECT cu FROM CouponUserEntity cu
        JOIN FETCH cu.coupon
        WHERE cu.user.id = :userId
    """
    )
    fun findCouponByUserId(@Param("userId") userId: Long): List<CouponUserEntity>

    @Query(
        """
        SELECT cu FROM CouponUserEntity cu
        WHERE cu.coupon.id = :couponId
    """
    )
    fun findCouponByCouponId(@Param("couponId") couponId: Long): List<CouponUserEntity>

    fun user(user: UserEntity): MutableList<CouponUserEntity>


}