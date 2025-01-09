package kr.hhplus.be.server.infra.product

object ProductQuery {

    const val findProduct = """
        SELECT p.id, p.name, p.price, pi.inventory FROM ProductEntity p 
        INNER JOIN ProductInventoryEntity pi ON p.id = pi.productId 
        WHERE p.id = :id
    """
}