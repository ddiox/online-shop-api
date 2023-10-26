# Online Shop Project (Back End)

This project is a personal effort to learn and practice using the Spring Boot framework.

## Product Endpoint
- Product Fields
```
Long id

String name

String description

Double price
```

## **GET /api/products**
returns all products
- **Success Response:**
```
[
    {
        "id": Long,
        "name": String,
        "description": String,
        "price" : Double
    },
]
```

## **GET /api/products/:id**
returns single products by id.
- **Success Response:**
```

{
    "id": Long,
    "name": String,
    "description": String,
    "price" : Double
}

```

## Order Endpoint
- Order Fields
```
Long id

Lists Product, Many to Many Relationship
```

## **POST /api/orders**
create order based on productId inputs
- **Data Params**  
  list of productId
```
[1,2,3, etc...]
```
- **Success Response:**
```
{
    "id": Long,
    "products": [
        {
            "id": Long,
            "name": String,
            "description": String,
            "price": Double
        },
    ]
}
```


## Payment Endpoint
- Payment Fields
```
Long id

Double Amount

Order order, One to One Relationship
```

## **POST /api/payments**
create payment based on orderId input
- **Data Params**  
  orderId
```
{
  "orderId": 1
}

```
- **Success Response:**
```
{
    "id": Long,
    "amount": Double,
    "order": {
        "id": Long,
        "products": [
            {
                "id": Long,
                "name": String,
                "description": String,
                "price": Double
            },
        ]
    }
}
```

## Shipping Endpoint
- Shipping Fields
```
Long id

String address

Payment payment, One to One Relationship
```

## **POST /api/shippings**
create shipping based on paymentId input
- **Data Params**  
  paymentId
```
{
    "paymentId": 1,
    "address": String
}

```
- **Success Response:**
```
{
    "id": Long,
    "address": String,
    "payment": {
        "id": Long,
        "amount": Double,
        "order": {
            "id": Long,
            "products": [
                {
                    "id": Long,
                    "name": String,
                    "description": String,
                    "price": Double
                },
            ]
        }
    }
}
```



