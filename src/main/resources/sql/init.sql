CREATE TABLE IF NOT EXISTS `users`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `name`       varchar(255) NOT NULL,
    `balance`    bigint       NOT NULL,
    `created_at` datetime(6)  NOT NULL,
    `updated_at` datetime(6)  NOT NULL,
    `version`    int          NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO users (name, balance, created_at, updated_at, version)
VALUES ('User1', 0, FROM_UNIXTIME(UNIX_TIMESTAMP()), FROM_UNIXTIME(UNIX_TIMESTAMP()), 0),
       ('User2', 100, FROM_UNIXTIME(UNIX_TIMESTAMP()), FROM_UNIXTIME(UNIX_TIMESTAMP()), 0),
       ('User3', 200, FROM_UNIXTIME(UNIX_TIMESTAMP()), FROM_UNIXTIME(UNIX_TIMESTAMP()), 0);


CREATE TABLE IF NOT EXISTS `product`
(
    `id`         bigint      NOT NULL AUTO_INCREMENT,
    `name`       varchar(255) DEFAULT NULL,
    `price`      int         NOT NULL,
    `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO product (name, price, created_at, updated_at)
VALUES ('Product1', 10000, FROM_UNIXTIME(UNIX_TIMESTAMP()), FROM_UNIXTIME(UNIX_TIMESTAMP())),
       ('Product2', 13000, FROM_UNIXTIME(UNIX_TIMESTAMP()), FROM_UNIXTIME(UNIX_TIMESTAMP())),
       ('Product3', 20000, FROM_UNIXTIME(UNIX_TIMESTAMP()), FROM_UNIXTIME(UNIX_TIMESTAMP()));

CREATE TABLE IF NOT EXISTS `product_inventory`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `inventory`  int    NOT NULL,
    `product_id` bigint NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO product_inventory (inventory, product_id)
VALUES (1000, 1),
       (500, 2),
       (300, 3)
;

CREATE TABLE IF NOT EXISTS `coupon`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT,
    `name`           varchar(200) NOT NULL,
    `capacity`       int          NOT NULL,
    `discount_type`  varchar(100) NOT NULL,
    `discount_value` int          NOT NULL,
    `created_at`     datetime(6)  NOT NULL,
    `updated_at`     datetime(6)  NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO coupon (capacity, name, discount_type, discount_value, created_at, updated_at)
VALUES (100, '쿠폰1', 'AMOUNT', 1000, FROM_UNIXTIME(UNIX_TIMESTAMP()), FROM_UNIXTIME(UNIX_TIMESTAMP())),
       (100, '쿠폰2', 'PERCENT', 10, FROM_UNIXTIME(UNIX_TIMESTAMP()), FROM_UNIXTIME(UNIX_TIMESTAMP()))
;

CREATE TABLE IF NOT EXISTS `coupon_user`
(
    `id`         bigint      NOT NULL AUTO_INCREMENT,
    `user_id`    bigint      NOT NULL,
    `coupon_id`  bigint      NOT NULL,
    `is_used`    bit(1)      NOT NULL,
    `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `orders`
(
    `id`           bigint      NOT NULL AUTO_INCREMENT,
    `coupon_id`    bigint       DEFAULT NULL,
    `product_id`   bigint      NOT NULL,
    `total_amount` bigint      NOT NULL,
    `user_id`      bigint      NOT NULL,
    `created_at`   datetime(6) NOT NULL,
    `status`       varchar(255) DEFAULT NULL,
    `updated_at`   datetime(6) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS `order_product`
(
    `id`         bigint      NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) NOT NULL,
    `quantity`   int         NOT NULL,
    `order_id`   bigint      NOT NULL,
    `product_id` bigint      NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `payment`
(
    `id`         bigint      NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) NOT NULL,
    `updated_at` datetime(6) NOT NULL,
    `quantity`   int         NOT NULL,
    `order_id`   bigint      NOT NULL,
    `product_id` bigint      NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
