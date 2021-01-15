# 1do接口文档  

#### 下城正式 https://1do.hzxc.gov.cn:8443 （ip:172.16.9.231）

#### 下城测试 http://59.202.68.43:8080

#### 南苑正式：https://ny1do.67tech.cn/backend

#### 裕华正式：http://218.11.12.30:8007

[TOC]

------

### 1 功能 ：注册来源

#### 请求方法： {{baseUrl}}/1do/common/registerSource

| 字段       | 类型   | 是否必填 | 描述及要求 |
| ---------- | ------ | -------- | ---------- |
| sourceName | String | 必填     | 来源名称   |

```json
http://59.202.68.43:8080/1do/common/registerSource?sourceName=测试
```

####  Output

| 字段        | 字段名   |
| ----------- | -------- |
| SOURCE_NAME | 来源名称 |
| SOURCE      | 来源     |
| O_TYPE_ID   | 事件类型 |

**Success**

```json
{
    "code": 200,
    "data": {
        "O_TYPE_ID": 0,
        "SOURCE_NAME": "测试",
        "SOURCE": 48
    },
    "message": "Success"
}
```

### 2 功能 ：注册事件类型

#### 请求方法：{{baseUrl}}/1do/common/registerType?typeName=类型111&source=45

####  参数说明：

| 字段     | 类型   | 是否必填 | 描述及要求   |
| -------- | ------ | -------- | ------------ |
| typeName | String | 必填     | 事件类型名称 |
| source   | int    | 必填     | 来源         |

```json
{{baseUrl}}/1do/common/registerType?typeName=类型111&source=48
```

####  Output

| 字段      | 字段名       |
| --------- | ------------ |
| TYPE_NAME | 事件类型名称 |
| SOURCE    | 来源         |
| O_TYPE_ID | 事件类型     |

**Success**

```json
{
    "code": 200,
    "data": {
        "O_TYPE_ID": 49,
        "SOURCE": 48,
        "TYPE_NAME": "类型111"
    },
    "message": "Success"
}}
```

