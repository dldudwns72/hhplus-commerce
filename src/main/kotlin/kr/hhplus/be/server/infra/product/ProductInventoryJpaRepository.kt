package kr.hhplus.be.server.infra.product

import kr.hhplus.be.server.domain.product.ProductInventoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query


interface ProductInventoryJpaRepository : JpaRepository<ProductInventoryEntity, Long> {
    fun findByProductId(productId: Long): ProductInventoryEntity?

    // update & delete don't receive response if you take response_entity 서비스 계층에서 처리하라
    @Query("update ProductInventoryEntity pi set pi.inventory = :inventory where pi.productId = :productId")
    @Modifying
    fun updateInventory(productId: Long, inventory: Int)
}