1、使用POSTMAN：
PUT 方法：http://localhost:9200/testes
（Body-raw-JSON）
{
    "mappings":
    {
        "enterprise": //类型名称
        {
          "properties": //定义属性
            {
             "id": //字段
              {
                "type": "long",//属性
                "store": true
              },
            "name": {
              "store":true,
              "type": "text"
            }
          }
        }
    }
}