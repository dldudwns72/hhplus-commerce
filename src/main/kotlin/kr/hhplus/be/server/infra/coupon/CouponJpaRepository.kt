package kr.hhplus.be.server.infra.coupon

import jakarta.persistence.LockModeType
import kr.hhplus.be.server.domain.coupon.CouponEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface CouponJpaRepository : JpaRepository<CouponEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    // @QueryHints(QueryHint(name = "javax.persistence.lock.timeout", value = "5000")) // 5초
    @Query("SELECT c FROM CouponEntity c WHERE c.id = :id")
    fun findCouponWithLock(id: Long): CouponEntity?

}