package kr.hhplus.be.server.infra.product

import kr.hhplus.be.server.domain.product.ProductInventoryEntity
import kr.hhplus.be.server.domain.product.ProductInventoryRepository
import org.springframework.stereotype.Repository


@Repository
class ProductInventoryRepositoryImpl(
    private val productInventoryJpaRepository: ProductInventoryJpaRepository
) : ProductInventoryRepository {

    override fun inventoryUpdate(productId: Long, inventory: Int) {
        productInventoryJpaRepository.updateInventory(productId, inventory)
    }

    override fun findById(productId: Long): ProductInventoryEntity? {
        return productInventoryJpaRepository.findByProductId(productId)
    }
}