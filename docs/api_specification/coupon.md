# CouponController API 명세서

## 1. 쿠폰 생성 API

- **HTTP 메서드**: `POST`
- **요청 경로**: `/v1/coupon`
- **설명**: 새로운 쿠폰을 생성하는 API입니다.

### 요청 파라미터

#### RequestBody
- `CouponRequest`: 쿠폰 생성 요청 데이터.
    - **필드**:
        - `name`: `String` - 쿠폰 이름.
        - `balance`: `BigDecimal` - 쿠폰 금액.

### 요청 예시

```json
{
  "name": "Welcome Coupon",
  "balance": 1000.00
}
```

### 응답 예시
```json
{
  "id": 101,
  "name": "Welcome Coupon",
  "balance": 1000.00
}
```
