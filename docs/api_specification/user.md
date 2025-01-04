# UserController API 명세서

## 1. 유저 잔액 충전 API

- **HTTP 메서드**: `POST`
- **요청 경로**: `/v1/user/{userId}/balance`
- **설명**: 유저의 잔액을 충전하는 API입니다.

### 요청 예시

```json
{
  "point": 1000
}
```

### 응답 예시

```json
{
  "userId": 123,
  "balance": 1000
}
```

---

## 2. 유저 잔액 조회 API

- **HTTP 메서드**: `GET`
- **요청 경로**: `/v1/user/{userId}/balance`
- **설명**:  유저의 잔액을 조회하는 API입니다.

### 응답 예시

```json
{
  "userId": 123,
  "balance": 5000
}
```

---

## 3. 유저 주문 조회 API

- **HTTP 메서드**: `GET`
- **요청 경로**: `/v1/user/{userId}/orders`
- **설명**:  유저의 주문 목록을 조회하는 API입니다.

### 응답 예시

```json
  {
  "userId": 1234,
  "orders": [
    {
      "orderId": 101
    },
    {
      "orderId": 102
    }
  ]
}
```

--- 

## 4. 유저 쿠폰 조회 API

- **HTTP 메서드**: `GET`
- **요청 경로**: `/v1/user/{userId}/coupons`
- **설명**: 유저의 쿠폰 정보를 조회하는 API입니다.

### 응답 예시

```json
{
  "userId": 123,
  "coupons": [
    {
      "couponId": 1
    },
    {
      "couponId": 2
    }
  ]
}
```

-