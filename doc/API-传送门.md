# 1do接口文档  （默认post请求）

## 正式 https://1do.hzxc.gov.cn:8443 

## 测试 http://59.202.68.43:8080

[TOC]

------

#### 

### 1 功能 ：添加或删除标签（传送门）

####  请求方法：/1do/do/addOrDeleteLabel

####  参数说明：

| 字段    | 类型   | 是否必填 | 描述及要求                                            |
| ------- | ------ | -------- | ----------------------------------------------------- |
| SHOW_ID | String | 必填     | 1do工单编号                                           |
| method  | String | 必填     | 添加填：add，删除一个填：delete,删除全部填：deleteAll |
| id      | int    | 选填     | 删除标签时填写，删除全部不用填写                      |
| label   | String | 选填     | 添加标签是填写，删除全部不用填写                      |

```
{
    "method": "delete",
    "id":391,
    "label":"输入",
    "SHOW_ID":"48987485040017408"
}
```

**Success**

```json
{
    "code": 200,
    "data": {
        "SHOW_ID": "130155191126196224",
        "LABEL": "输ssss入asdafafaf",
        "ID": 14293
    },
    "message": "Success"
}
```



### 2 功能 ：获得关联1do（传送门）

#### 请求方法：/1do/do/getRelation

####  参数说明：

| 字段    | 字段名      | 类型   | 是否必填 | 描述及要求  |
| ------- | ----------- | ------ | -------- | ----------- |
| SHOW_ID | 1do工单编号 | String | 必填     | 1do工单编号 |

```
{"SHOW_ID":86898783106891776}
```

####  Output

| 字段            | 类型   | 描述及要求                                      |
| --------------- | ------ | ----------------------------------------------- |
| SHOW_ID         | String | 关联的1do工单编号                               |
| SIMILARITY      | int    | 相似度                                          |
| TYEP            | String | 0手动添加的关联。6已删除的1do，-1父1do，-2子1do |
| ID              | int    | ID（排序时用）                                  |
| O_DESCRIBE      | date   | 内容                                            |
| O_FINISH_TIME   | long   | 拟完成时间                                      |
| O_CUSTOMER      | String | 发起人showid                                    |
| O_CUSTOMER_NAME | String | 发起人                                          |
| O_EXECUTOR      | String | 参与人showid                                    |
| O_EXECUTOR_NAME | String | 参与人                                          |
| FBNUM           | int    | 反馈数                                          |
| LOOKNUM         | int    | 查看数                                          |
| O_CREATE_TIME   | long   | 创建时间                                        |
| O_STATUS        | String | 工单状态                                        |

**Success**

```json
{
    "code": 200,
    "data": [
        {
             "O_FINISH_TIME": 1530374400000,
            "O_CUSTOMER_NAME": "刘韬",
            "O_EXECUTOR_NAME": "方升群",
            "FBNUM": 0,
            "LOOKNUM": 0,
            "O_CREATE_TIME": 1552271527000,
            "SHOW_ID": "134734151214432256",
            "ID": 1182976,
            "SIMILARITY": 100,
            "TYPE": 3,
            "O_DESCRIBE": "对接主动办接口1",
            "O_STATUS": "待接单"
        },
        。。。。。
        {
             "O_FINISH_TIME": null,
            "O_CUSTOMER_NAME": "沈力伟",
            "O_EXECUTOR_NAME": "雷卿;于璟明",
            "FBNUM": 5,
            "LOOKNUM": 11,
            "O_CREATE_TIME": 1539223972000,
            "SHOW_ID": "80008740501192704",
            "ID": 1179017,
            "SIMILARITY": 11,
            "TYPE": 4,
            "O_DESCRIBE": "数据公示板里的6大模块页面内容，除了实时对接的那些内容外，其他都改为整理层设计的页面。",
            "O_STATUS": "已接单"
        }
    ],
    "message": "Success"
}
```



### 3 功能 ：关联排序（传送门）

####  请求方法：/1do/do/relationSort

####  参数说明：

| 字段   | 类型   | 是否必填 | 描述及要求                                                   |
| ------ | ------ | -------- | ------------------------------------------------------------ |
| list   | list   | 是       | 填获得关联中的id集合（相关1do，相关日志取ID值，相关反馈取RFID值） |
| target | String | 是       | 相关1do关联排序传：t_1do_relation；相关日志关联排序传：t_1do_relation_record；相关反馈关联排序传：t_1do_relation_feedback |

```
{
    "list": [
    	10604,
		10533,
		10603

    ],
    "target":"t_1do_relation_record"
}
```

**Success**

```json
{
    "code": 200,
    "data": "排序完成",
    "message": "Success"
}
```



### 4 功能 ：关键字查询1do（传送门）

####  请求方法：/1do/do/selectBybase

####  参数说明：

| 字段    | 类型   | 是否必填 | 描述及要求  |
| ------- | ------ | -------- | ----------- |
| SHOW_ID | String | 必填     | 1do工单编号 |
| BASE    | String | 必填     | 关键词      |

```
{"BASE":"五","SHOW_ID":"52186735907438592"}
```

####  Output

| 字段          | 类型   | 描述及要求  |
| ------------- | ------ | ----------- |
| SHOW_ID       | String | 1do工单编号 |
| O_CREATE_TIME | String | 创建时间    |
| O_DESCRIBE    | String | 内容        |

**Success**

```json
{
    "code": 200,
    "data": [
        {
            "O_CREATE_TIME": "2018-07-17 19:45:28",
            "SHOW_ID": "48987485040017408",
            "O_DESCRIBE": "测试测试测试测试测试测"
        },
        。。。
        {
            "O_CREATE_TIME": "2018-07-27 15:11:03",
            "SHOW_ID": "52542304723927040",
            "O_DESCRIBE": "测试"
        }
    ],
    "message": "Success"
}
```



### 5 功能 ：批量添加关联（传送门）

####  请求方法：/1do/do/batchAddRelation

####  参数说明：

| 字段    | 类型   | 是否必填 | 描述及要求        |
| ------- | ------ | -------- | ----------------- |
| SHOW_ID | String | 必填     | 1do工单编号       |
| list    | list   | 必填     | 关联的1do工单编号 |

```
{"list":["49024271405547520","49024315248607232"],"SHOW_ID":"52186735907438592"}
```

**Success**

```json
{
    "code": 200,
    "data": "添加成功",
    "message": "Success"
}
```

### 6 功能 ：删除关联（传送门）

#### 6 请求方法：/1do/do/deleteRelation

#### 6 参数说明：

| 字段             | 类型   | 是否必填 | 描述及要求        |
| ---------------- | ------ | -------- | ----------------- |
| SHOW_ID          | String | 必填     | 1do工单编号       |
| RELATION_SHOW_ID | String | 必填     | 关联的1do工单编号 |

```
{"RELATION_SHOW_ID":"49201126158893056","SHOW_ID":"49050403815292928"}
```

**Success**

```
{
    "code": 200,
    "data": 1,
    "message": "Success"
}
```



### 7 功能 ：获得关联反馈（传送门）

####  请求方法：/1do/do/getRelationFeedback

####  参数说明：

| 字段    | 字段名      | 类型   | 是否必填 | 描述及要求  |
| ------- | ----------- | ------ | -------- | ----------- |
| SHOW_ID | 1do工单编号 | String | 必填     | 1do工单编号 |

```
{"SHOW_ID":86898783106891776}
```

####  Output

| 字段        | 类型   | 描述及要求                     |
| ----------- | ------ | ------------------------------ |
| SHOW_ID     | String | 关联的1do工单编号              |
| SIMILARITY  | int    | 相似度                         |
| TYEP        | String | 1显示0不显示                   |
| ID          | int    | 反馈的id，定位用               |
| FBCONTENT   | date   | 内容                           |
| FB_TIME     | long   | 反馈时间                       |
| O_USER      | String | 反馈人showid                   |
| O_USER_NAME | String | 反馈人                         |
| RFID        | long   | 相关反馈表id用于显示隐藏是传值 |
| O_STATUS    | int    | 3待接单、4已接单、5已完成      |

**Success**

```json
{
    "code": 200,
    "data": [
        {
            "O_USER_NAME": "王德锋",
            "FB_TIME": 1540623388000,
            "SHOW_ID": "72138455206854656",
            "RFID": 155843,
            "O_USER": "6KqjALYDe6U0vBb3",
            "ID": 3701,
            "FBCONTENT": "测试",
            "SIMILARITY": 100,
            "TYPE": 1,
            "O_STATUS": 5
        },
        {
            "O_USER_NAME": "石西庆",
            "FB_TIME": 1535097307000,
            "SHOW_ID": "62320837830639616",
            "RFID": 953371,
            "O_USER": "deWYBLVaY9fk8plW",
            "ID": 925,
            "FBCONTENT": "于璟明测试1DO",
            "SIMILARITY": 33,
            "TYPE": 1,
            "O_STATUS": 5
        }
    ],
    "message": "Success"
}
```



### 8 功能 ：修改相关反馈显示和隐藏（传送门）

####  请求方法：/1do/do/updateRelationFeedback

####  参数说明：

| 字段 | 类型 | 是否必填 | 描述及要求                     |
| ---- | ---- | -------- | ------------------------------ |
| RFID | long | 必填     | 相关反馈表id用于显示隐藏是传值 |

```
{"RFID": 4150}
```

**Success**

```json
{
    "code": 200,
    "data": "成功",
    "message": "Success"
}
```

### 9 功能 ：获得关联日志（传送门）

####  请求方法：/1do/do/getRelationRecord

####  参数说明：

| 字段    | 字段名      | 类型   | 是否必填 | 描述及要求  |
| :------ | :---------- | :----- | :------- | :---------- |
| SHOW_ID | 1do工单编号 | String | 必填     | 1do工单编号 |

```
{"SHOW_ID":86898783106891776}
```

####  Output

| 字段        | 类型   | 描述及要求                     |
| :---------- | :----- | :----------------------------- |
| RECORDID    | String | 日志id                         |
| SIMILARITY  | int    | 相似度                         |
| TYEP        | String | 1显示0不显示                   |
| ID          | long   | 相关日志表id用于显示隐藏时传值 |
| RECORD      | date   | 日志内容                       |
| CREATETIME  | long   | 日志创建时间                   |
| O_USER      | String | 日报人showid                   |
| O_USER_NAME | String | 日报人                         |

**Success**

```
{
    "code": 200,
    "data": [
        {
            "O_USER_NAME": "徐杰",
            "RECORD": "测试",
            "O_USER": "avE8bm0K1LFDYnVl",
            "ID": 627858,
            "SIMILARITY": 100,
            "CREATETIME": 1470877881000,
            "TYPE": 0,
            "RECORDID": 763568979422740480
        },
        {
            "O_USER_NAME": "徐杰",
            "RECORD": "测试",
            "O_USER": "avE8bm0K1LFDYnVl",
            "ID": 630237,
            "SIMILARITY": 100,
            "CREATETIME": 1470877881000,
            "TYPE": 0,
            "RECORDID": 763663125584019456
        }
        
    ],
    "message": "Success"
}
```



### 10 功能 ：修改相关日志显示和隐藏（传送门）

####  请求方法：/1do/do/updateRelationRecord

####  参数说明：

| 字段 | 类型 | 是否必填 | 描述及要求                     |
| :--- | :--- | :------- | :----------------------------- |
| ID   | long | 必填     | 相关相关表id用于显示隐藏是传值 |

```
{"ID": 4150}
```

**Success**

```
{
    "code": 200,
    "data": "成功",
    "message": "Success"
}
```

