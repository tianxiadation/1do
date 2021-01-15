# 1do接口文档  （默认post请求）

## 正式 https://1do.hzxc.gov.cn:8443 

## 测试 http://59.202.68.43:8080

[TOC]

------

### 1 功能 ：项目看板新增节点

####  请求方法：/1do/projectBoard/addItem

####  参数说明：

| 字段      | 类型   | 是否必填 | 描述及要求                           |
| :-------- | :----- | :------- | :----------------------------------- |
| itemName  | String | 必填     | 节点名称                             |
| type      | int    | 必填     | 1-项目总览分类 2-项目子节点分类      |
| parentId  | long   | 否       | 父节点id，没有填0或不填              |
| projectId | long   | 否       | 项目id（项目脑图必填，项目总览不填） |

```
{
    "itemName": "信息项目",
    "type":1,
    "parentId":1
}
```

####  Output

| 字段     | 类型   | 描述及要求                  |
| :------- | :----- | :-------------------------- |
| id       | int    | 节点id                      |
| itemName | String | 节点名称                    |
| type     | int    | 1-项目分类 2-项目子节点分类 |
| parentId | int    | 父节点id                    |

**Success**

```
{
    "code": 200,
    "data": {
        "ID": 9,
        "TYPE": 1,
        "ITEM_NAME": "信息项目",
        "PARENT_ID": 1
    },
    "message": "Success"
}
```

### 2 功能 ：项目看板新增项目

####  请求方法：/1do/projectBoard/addProject

####  参数说明：

| 字段        | 类型      | 是否必填 | 描述及要求              |
| :---------- | :-------- | :------- | :---------------------- |
| name        | String    | 必填     | 姓名名称                |
| finishTime  | long      | 否       | 结束时间                |
| parentId    | int       | 必填     | 父节点id，没有填0或不填 |
| stakeHolder | resPerson | 必填     | 干系人列表              |
| nodeList    | List      | 否       | 子节点                  |

stakeHolder

| 字段   | 类型   | 是否必填 | 描述及要求 |
| :----- | :----- | :------- | :--------- |
| showId | String | 必填     | 干系人     |

nodeList

| 字段      | 类型   | 是否必填 | 描述及要求 |
| :-------- | :----- | -------- | :--------- |
| item_name | stirng | 必填     | 节点名称   |
| children  | List   | 否       | 子节点     |

```json
{
    "name": "归集分析",
    "parentId":18,
    "stakeHolder":[
    	{
    		"showId":"lQDNVWVQZ2HnpYoq"
    	},{
    		"showId":"PQV8oo3jeeiDkLbY"
    	}],
	"finishTime":1563968847539,
	"nodeList":[
        {
            "children": [
                {
                    "children": [],
                    "item_name": "信息项目",
                    "type": 1
                },
                {
                    "children": [],
                    "parent_id": 1,
                    "item_name": "数字规模化项目",
                    "id": 10,
                    "type": 1
                },
                {
                    "children": [
                        {
                            "children": [],
                            "parent_id": 11,
                            "item_name": "小屏展示项目",
                            "id": 18,
                            "type": 1
                        }
                    ],
                    "parent_id": 1,
                    "item_name": "展示项目",
                    "id": 11,
                    "type": 1
                },
                {
                    "children": [],
                    "parent_id": 1,
                    "item_name": "部门项目",
                    "id": 12,
                    "type": 1
                }
            ],
            "parent_id": 0,
            "item_name": "下城项目",
            "id": 1,
            "type": 1
        }
    ]
}
```

####  Output

| 字段 | 类型 | 描述及要求 |
| :--- | :--- | :--------- |
| id   | int  | 项目id     |

**Success**

```
{
    "code": 200,
    "data": {
        "id": 10
    },
    "message": "Success"
}
```

### 3 功能 ：项目看板修改节点

####  请求方法：/1do/projectBoard/updateItem

#### 参数说明：

| 字段      | 类型   | 是否必填 | 描述及要求                           |
| :-------- | :----- | :------- | :----------------------------------- |
| id        | Long   | 是       | 节点id                               |
| itemName  | String | 必填     | 节点名称                             |
| parentId  | long   | 否       | 父节点id，没有填0或不填              |
| projectId | long   | 否       | 项目id（项目脑图必填，项目总览不填） |
| type      | int    | 是       | 1-项目总览节点 2-项目子节点          |

```
{
    "id":2,
    "itemName": "数字化项目",
    "parentId":1
}
```

####  Output

| 字段     | 类型   | 描述及要求                  |
| :------- | :----- | :-------------------------- |
| id       | int    | 节点id                      |
| itemName | String | 节点名称                    |
| type     | int    | 1-项目分类 2-项目子节点分类 |
| parentId | int    | 父节点id                    |

**Success**

```
{
    "code": 200,
    "data": {
        "ID": 2,
        "TYPE": 1,
        "ITEM_NAME": "数字化项目",
        "PARENT_ID": 1
    },
    "message": "Success"
}
```

### 4 功能 ：项目看板修改项目

####  请求方法：/1do/projectBoard/updateProject

####  参数说明：

| 字段        | 类型      | 是否必填 | 描述及要求              |
| :---------- | :-------- | :------- | :---------------------- |
| id          | Long      | 是       | 项目id                  |
| name        | String    | 必填     | 姓名名称                |
| finishTime  | long      | 否       | 结束时间                |
| parentId    | int       | 必填     | 父节点id，没有填0或不填 |
| stakeHolder | resPerson | 必填     | 干系人列表              |

stakeHolder

| 字段   | 类型   | 是否必填 | 描述及要求   |
| :----- | :----- | :------- | :----------- |
| showId | String | 必填     | 干系人showId |

```
{
	"id":22,
    "name": "下城城市大脑",
    "parentId":18,
    "stakeHolder":[
    	{
    		"showId":"yanqiuxia"
    	},{
    		"showId":"lQDNVWVQZ2HnpYoq"
    	}],
	"finishTime":1576668847539
}
```

#### Output

**Success**

```
{
    "code": 200,
    "data": "修改成功",
    "message": "Success"
}
```

### 5 功能 ：项目看板删除节点

####  请求方法：/1do/projectBoard/deleteItem

####  参数说明：

| 字段      | 类型 | 是否必填 | 描述及要求                           |
| :-------- | :--- | :------- | :----------------------------------- |
| id        | Long | 是       | 节点id                               |
| projectId | long | 否       | 项目id（项目脑图必填，项目总览不填） |

```
{
	"id":34,
	"projectId":23
}
```

####  Output

| 字段     | 类型   | 描述及要求                  |
| :------- | :----- | :-------------------------- |
| id       | int    | 节点id                      |
| itemName | String | 节点名称                    |
| type     | int    | 1-项目分类 2-项目子节点分类 |
| parentId | int    | 父节点id                    |

**Success**

```
{
    "code": 200,
    "data": {
        "ID": 34,
        "TYPE": 1,
        "PARENT_ID": 9,
        "ITEM_NAME": "当时的"
    },
    "message": "Success"
}
```

### 6 功能 ：项目看板删除项目

####  请求方法：/1do/projectBoard/deleteProject

#### 参数说明：

| 字段 | 类型 | 是否必填 | 描述及要求 |
| :--- | :--- | :------- | :--------- |
| id   | Long | 是       | 项目id     |

```
{
	"id":34
}
```

####  Output

**Success**

```
{
    "code": 200,
    "data": "删除成功",
    "message": "Success"
}
```

### 7 功能 ：项目看板设置为重点项目

####  请求方法：/1do/projectBoard/setKeyProject

####  参数说明：

| 字段 | 类型 | 是否必填 | 描述及要求 |
| :--- | :--- | :------- | :--------- |
| id   | Long | 是       | 项目id     |

```
{
	"id":22
}
```

####  Output

**Success**

```
{
    "code": 200,
    "data": "取消重点项目成功",
    "message": "Success"
}
```

### 8 功能 ：项目看板项目设置为办结

####  请求方法：/1do/projectBoard/setFinish

####  参数说明：

| 字段 | 类型 | 是否必填 | 描述及要求 |
| :--- | :--- | :------- | :--------- |
| id   | Long | 是       | 项目id     |

```
{
	"id":22
}
```

####  Output

**Success**

```
{
    "code": 200,
    "data": "设置成功",
    "message": "Success"
}
```

### 9 功能 ：获取项目总览

####  请求方法：/1do/projectBoard/getClassification

####  参数说明：

| 字段  | 类型 | 是否必填 | 描述及要求              |
| :---- | :--- | :------- | :---------------------- |
| isNew | int  | 否       | 第一次进入思维导图时填1 |

```
{
	"isNew":1
}
```

####  Output

| 字段  | 类型 | 描述及要求 |
| :---- | :--- | :--------- |
| items | List | 数据列表   |

items：

| 字段         | 类型    | 描述及要求                                                   |
| :----------- | :------ | :----------------------------------------------------------- |
| id           | int     | 节点id                                                       |
| topic        | String  | 节点名称                                                     |
| type         | int     | 1-项目分类  3-项目                                           |
| parentid     | int     | 父节点id                                                     |
| direction    | String  | 方向                                                         |
| hasProjrct   | boolean | 子级包含项目                                                 |
| isComplete   | int     | 项目完成情况（1-已完成，0-未完成）                           |
| rate         | int     | 百分比                                                       |
| completeTask | int     | 已完成需求量                                                 |
| allTask      | int     | 总需求量                                                     |
| isKey        | boolean | 是否是重点项目，只有项目节点有（type=3）（是-true，否-false） |

**Success**

```
{
    "code": 200,
    "data": {
        "items": [
            {
                "rate": 27,
                "topic": "评价系统1",
                "id": 822,
                "type": 3,
                "parentid": 1621,
                "isComplete": 1,
                "direction": "right"
            },
            {
                "topic": "下城项目",
                "isroot": true,
                "id": 1617,
                "hasProject": false,
                "type": 1
            },
            {
                "topic": "城市大脑",
                "id": 1618,
                "hasProject": false,
                "type": 1,
                "parentid": 1617,
                "direction": "right"
            },
            {
                "topic": "1do",
                "id": 1619,
                "hasProject": false,
                "type": 1,
                "parentid": 1617,
                "direction": "right"
            },
            {
                "topic": "任务",
                "id": 1620,
                "hasProject": false,
                "type": 1,
                "parentid": 1617,
                "direction": "right"
            },
            {
                "topic": "评价系统",
                "id": 1621,
                "hasProject": true,
                "type": 1,
                "parentid": 1620,
                "direction": "right"
            }
        ]
    },
    "message": "Success"
}
```

### 10 功能 ：获取项目分类（修改项目分类时调用）

####  请求方法：/1do/projectBoard/getProjectItems

####  参数说明：

无请求参数

#### Output

| 字段      | 类型   | 描述及要求                         |
| :-------- | :----- | :--------------------------------- |
| id        | int    | 节点id                             |
| item_name | String | 节点名称                           |
| type      | int    | 1-项目分类 2-项目子节点分类 3-项目 |
| parent_id | int    | 父节点id                           |
| children  | List   | 子对象                             |

**Success**

```
{
    "code": 200,
    "data": [
        {
            "children": [
                {
                    "children": [],
                    "parent_id": 1,
                    "item_name": "数字化项目",
                    "id": 2,
                    "type": 1
                },
                {
                    "children": [
                        {
                            "children": [],
                            "parent_id": 9,
                            "item_name": "1do",
                            "id": 10,
                            "type": 3
                        }
                    ],
                    "parent_id": 1,
                    "item_name": "信息项目",
                    "id": 9,
                    "type": 1
                },
                {
                    "children": [
                        {
                            "children": [
                                {
                                    "children": [],
                                    "parent_id": 13,
                                    "item_name": "1懂",
                                    "id": 23,
                                    "type": 3
                                }
                            ],
                            "parent_id": 11,
                            "item_name": "大屏展示项目",
                            "id": 13,
                            "type": 1
                        },
                        {
                            "children": [
                                {
                                    "children": [],
                                    "parent_id": 18,
                                    "item_name": "下城城市大脑",
                                    "id": 22,
                                    "type": 3
                                }
                            ],
                            "parent_id": 11,
                            "item_name": "小屏展示项目",
                            "id": 18,
                            "type": 1
                        }
                    ],
                    "parent_id": 1,
                    "item_name": "展示项目",
                    "id": 11,
                    "type": 1
                },
                {
                    "children": [
                        {
                            "children": [],
                            "parent_id": 12,
                            "item_name": "业务流项目",
                            "id": 14,
                            "type": 1
                        },
                        {
                            "children": [
                                {
                                    "children": [
                                        {
                                            "children": [],
                                            "parent_id": 16,
                                            "item_name": "积分系统",
                                            "id": 24,
                                            "type": 3
                                        }
                                    ],
                                    "parent_id": 15,
                                    "item_name": "开发",
                                    "id": 16,
                                    "type": 1
                                },
                                {
                                    "children": [],
                                    "parent_id": 15,
                                    "item_name": "测试",
                                    "id": 17,
                                    "type": 1
                                }
                            ],
                            "parent_id": 12,
                            "item_name": "工作项目",
                            "id": 15,
                            "type": 1
                        }
                    ],
                    "parent_id": 1,
                    "item_name": "部门项目",
                    "id": 12,
                    "type": 1
                }
            ],
            "parent_id": 0,
            "item_name": "下城",
            "id": 1,
            "type": 1
        }
    ],
    "message": "Success"
}
```

### 11 功能 ：修改1do所属项目

####  请求方法：/1do/projectBoard/update1doItem

####  参数说明：

| 字段    | 类型   | 是否必填 | 描述及要求                     |
| :------ | :----- | :------- | :----------------------------- |
| SHOW_ID | String | 是       | 1do工单SHOW_ID                 |
| ID      | long   | 是       | 最后一个节点id(取消1do关联传0) |

```
{
	"SHOW_ID":"184381971101646848",
	"ID":29
}
```

#### Output

**Success**

```
{
    "code": 200,
    "data": "修改成功",
    "message": "Success"
}
```

### 12 功能 ：1do详情获得所有项目及节点

#### 请求方法：/1do/projectBoard/getAllItem

#### 参数说明：无

####  Output

| 字段      | 类型   | 描述及要求               |
| :-------- | :----- | :----------------------- |
| children  | List   | 子节点                   |
| parent_id | long   | 父节点id                 |
| item_name | stirng | 节点名称                 |
| type      | int    | 节点类型2子节点3项目节点 |
| id        | long   | 节点id                   |



**Success**

```
{
    "code": 200,
    "data": [
        {
            "children": [
                {
                    "children": [
                        {
                            "children": [],
                            "parent_id": 25,
                            "item_name": "计划制定",
                            "id": 28,
                            "type": 2
                        },
                        {
                            "children": [],
                            "parent_id": 25,
                            "item_name": "数据对照",
                            "id": 31,
                            "type": 2
                        },
                        {
                            "children": [],
                            "parent_id": 25,
                            "item_name": "展示",
                            "id": 32,
                            "type": 2
                        }
                    ],
                    "parent_id": 10,
                    "item_name": "工作方",
                    "id": 25,
                    "type": 2
                },
                {
                    "children": [],
                    "parent_id": 10,
                    "item_name": "参与方",
                    "id": 26,
                    "type": 2
                },
                {
                    "children": [],
                    "parent_id": 10,
                    "item_name": "目标",
                    "id": 27,
                    "type": 2
                }
            ],
            "parent_id": 9,
            "item_name": "1do",
            "id": 10,
            "type": 3
        },
        {
            "children": [],
            "parent_id": 18,
            "item_name": "下城城市大脑",
            "id": 22,	
            "type": 3
        },
        {
            "children": [
                {
                    "children": [],
                    "parent_id": 23,
                    "item_name": "工作方",
                    "id": 29,
                    "type": 2
                },
                {
                    "children": [],
                    "parent_id": 23,
                    "item_name": "参与方",
                    "id": 30,
                    "type": 2
                }
            ],
            "parent_id": 13,
            "item_name": "1懂",
            "id": 23,
            "type": 3
        },
        {
            "children": [],
            "parent_id": 16,
            "item_name": "积分系统",
            "id": 24,
            "type": 3
        }
    ],
    "message": "Success"

```

### 13 功能 ：获取项目子节点

####  请求方法：/1do/projectBoard/getProjectChildNode

####  参数说明：

| 字段  | 类型 | 是否必填 | 描述及要求              |
| :---- | :--- | :------- | :---------------------- |
| id    | long | 是       | 项目id                  |
| hide  | int  | 是       | 是否隐藏 1是0否         |
| isNew | int  | 否       | 第一次进入思维导图时填1 |

```
{
	"id":822,
	"hide":1
}
```

####  Output

| 字段      | 类型    | 描述及要求                                                   |
| :-------- | :------ | :----------------------------------------------------------- |
| power     | int     | 权限（1-整理层，2-领导，3-普通用户，4-干系人），1和2可编辑新增 |
| items     | List    | 数据列表                                                     |
| itemId    | long    | 项目id                                                       |
| isLastOne | boolean | 是否是最后一条（若为true，则撤销按钮）                       |
| isKey     | int     | 是否是重点项目是--true否--false(只在项目节点出现)            |

items：

| 字段         | 类型   | 描述及要求                                                   |
| :----------- | :----- | :----------------------------------------------------------- |
| id           | int    | 节点id                                                       |
| topic        | String | 节点名称                                                     |
| type         | int    | 1-项目总览节点 2-项目子节点 3-项目节点（项目总览中）4-1do 5-项目节点（项目脑图中） |
| parentid     | int    | 父节点id                                                     |
| direction    | String | 方向                                                         |
| timeout      | int    | 超过拟办结时间的为1                                          |
| status       | int    | 3/4 进行中 5 已完成  （状态5不管timeout  显示绿色 ，进行中 timeout为1 显示 红色 ，其他显示灰色） |
| SHOW_ID      | String | 1do的showid，打开1do详情用                                   |
| stakeholders | list   | 关系人                                                       |
| COMPLETION   | String | 完成情况，若为“已完成”则完成                                 |
| showId       | String | 展示在项目子节点名称之前                                     |

**Success**

```
{
    "code": 200,
    "data": {
        "itemId": 822,
        "isLastOne": true,
        "power": 1,
        "items": [
            {
                "topic": "项目分类节点20190805",
                "id": "928",
                "COMPLETION": "80%",
                "stakeholders": [
                    {
                        "trueName": "刘佳民",
                        "showId": "297NKDKkDzHZe2da"
                    },
                    {
                        "trueName": "詹子恒",
                        "showId": "W11arbRpxjhkNWaR"
                    },
                    {
                        "trueName": "方升群",
                        "showId": "PQV8oo3jeeiDkLbY"
                    },
                    {
                        "trueName": "阎秋霞",
                        "showId": "ZD1ybAYvAou36nzJ"
                    },
                    {
                        "trueName": "王帅帅",
                        "showId": "WeKJDWROnaHQn6Yq"
                    },
                    {
                        "trueName": "陈昕煜",
                        "showId": "X0e98ol7zLTroE6x"
                    }
                ],
                "type": 2,
                "parentid": "822",
                "direction": "right"
            },
            {
                "topic": "需要",
                "id": "929",
                "COMPLETION": "未完成",
                "stakeholders": [
                    {
                        "trueName": "陶丹",
                        "showId": "pvE5pqrVjWUqdP59"
                    },
                    {
                        "trueName": "沈霄莉",
                        "showId": "lQDNVWVQZ2HnpYoq"
                    }
                ],
                "type": 2,
                "parentid": "928",
                "direction": "right"
            },
            {
                "topic": "A",
                "id": "948",
                "COMPLETION": "已完成",
                "stakeholders": [
                    {
                        "trueName": "陶丹",
                        "showId": "pvE5pqrVjWUqdP59"
                    },
                    {
                        "trueName": "庞潇潇",
                        "showId": "Nv0Gz0pP5NFdXyE7"
                    },
                    {
                        "trueName": "苏金芳",
                        "showId": "DWZdGQmmjDhbRVrz"
                    },
                    {
                        "trueName": "徐杰",
                        "showId": "avE8bm0K1LFDYnVl"
                    },
                    {
                        "trueName": "王德锋",
                        "showId": "6KqjALYDe6U0vBb3"
                    },
                    {
                        "trueName": "周侠晨",
                        "showId": "8ZavkQ2ePVS8ZW9e"
                    },
                    {
                        "trueName": "胡甜甜",
                        "showId": "KDy1ONNJzbuV2rG3"
                    },
                    {
                        "trueName": "程太锟",
                        "showId": "or0e55Xyz8hODB8K"
                    },
                    {
                        "trueName": "梅舒芬",
                        "showId": "oNoPnDVEe1i8NDQe"
                    },
                    {
                        "trueName": "陈丹",
                        "showId": "DO0RvoNK7xhaxVXy"
                    },
                    {
                        "trueName": "林鹏",
                        "showId": "kVoZD18XY2trJRb0"
                    },
                    {
                        "trueName": "沈周科",
                        "showId": "29jvymQOOYCElQWN"
                    },
                    {
                        "trueName": "沈罗熠",
                        "showId": "PmrEy9ObkkuZQPLX"
                    },
                    {
                        "trueName": "骆姜斌",
                        "showId": "3ZzmorkJ90h0RGWq"
                    }
                ],
                "type": 2,
                "parentid": "928",
                "direction": "right"
            },
            {
                "topic": "B",
                "id": "949",
                "COMPLETION": "已完成",
                "stakeholders": [
                    {
                        "trueName": "骆姜斌",
                        "showId": "3ZzmorkJ90h0RGWq"
                    },
                    {
                        "trueName": "蒋娅楠",
                        "showId": "3LBYkvlD7rHmLn72"
                    },
                    {
                        "trueName": "薛伟",
                        "showId": "EWJ0WkR2LKiXOqRz"
                    },
                    {
                        "trueName": "方升群",
                        "showId": "PQV8oo3jeeiDkLbY"
                    }
                ],
                "type": 2,
                "parentid": "929",
                "direction": "right"
            },
            {
                "topic": "Fang",
                "id": "951",
                "COMPLETION": "",
                "stakeholders": [
                    {
                        "trueName": "孙凯",
                        "showId": "QkVkqDPP1dtGr5b3"
                    },
                    {
                        "trueName": "金锡军",
                        "showId": "AdE0zZ6nqQTdN9am"
                    },
                    {
                        "trueName": "刘立征",
                        "showId": "GRDloDB3KYsOEmy8"
                    }
                ],
                "type": 2,
                "parentid": "961",
                "direction": "right"
            },
            {
                "topic": "E",
                "id": "952",
                "COMPLETION": "",
                "stakeholders": [
                    {
                        "trueName": "刘亚伟",
                        "showId": "nRDLE2DWYXuaEnlx"
                    },
                    {
                        "trueName": "吴郑鑫",
                        "showId": "pnkl2LLE9aHWknaN"
                    },
                    {
                        "trueName": "张静",
                        "showId": "kbomAZmAoKUryN52"
                    },
                    {
                        "trueName": "余念",
                        "showId": "ZXV1lyA6boHYqR0y"
                    },
                    {
                        "trueName": "Fiona",
                        "showId": "EPkGoBno0vuBdWVV"
                    }
                ],
                "type": 2,
                "parentid": "951",
                "direction": "right"
            },
            {
                "topic": "123J",
                "id": "960",
                "COMPLETION": "已完成",
                "stakeholders": [
                    {
                        "trueName": "詹子恒",
                        "showId": "W11arbRpxjhkNWaR"
                    }
                ],
                "type": 2,
                "parentid": "822",
                "direction": "right"
            },
            {
                "topic": "Kzhyang",
                "id": "961",
                "COMPLETION": "",
                "stakeholders": [
                    {
                        "trueName": "琳雨轩",
                        "showId": "aXxae5v5P9IXZxzr"
                    },
                    {
                        "trueName": "徐攻博",
                        "showId": "byekalKEEOuNxrXV"
                    },
                    {
                        "trueName": "毛瑞琛",
                        "showId": "ldrao1WLBkCAErv9"
                    }
                ],
                "type": 2,
                "parentid": "1378",
                "direction": "right"
            },
            {
                "topic": "3333",
                "id": "1376",
                "COMPLETION": "",
                "stakeholders": [
                    {
                        "trueName": "方升群",
                        "showId": "PQV8oo3jeeiDkLbY"
                    },
                    {
                        "trueName": "张友堃",
                        "showId": "nQQqyLxAabIZoEp6"
                    }
                ],
                "type": 2,
                "parentid": "949",
                "direction": "right"
            },
            {
                "topic": "测试333",
                "id": "1378",
                "COMPLETION": "",
                "stakeholders": [],
                "type": 2,
                "parentid": "822",
                "direction": "right"
            },
            {
                "topic": "内容",
                "id": "1587",
                "COMPLETION": "",
                "stakeholders": [],
                "type": 2,
                "parentid": "822",
                "direction": "right"
            },
            {
                "topic": "8.6测试2次",
                "SHOW_ID": "188503709905321984",
                "id": "188503709905321984",
                "type": 4,
                "parentid": "960",
                "status": 3
            },
            {
                "topic": "dasasaddsasdsa",
                "SHOW_ID": "177961590790815744",
                "id": "177961590790815744",
                "type": 4,
                "parentid": "960",
                "status": 5
            },
            {
                "topic": "41234",
                "SHOW_ID": "178277190540984320",
                "id": "178277190540984320",
                "type": 4,
                "parentid": "960",
                "status": 4
            },
            {
                "topic": "dasd",
                "SHOW_ID": "201112254924455936",
                "id": "201112254924455936",
                "type": 4,
                "parentid": "948",
                "status": 3
            },
            {
                "topic": "测试内容",
                "SHOW_ID": "229679287324639232",
                "id": "229679287324639232",
                "type": 4,
                "parentid": "952",
                "status": 3
            },
            {
                "isKey": false,
                "topic": "评价系统1",
                "isroot": true,
                "id": "822",
                "stakeholders": [
                    {
                        "trueName": "杨能丹",
                        "showId": "k0K3loQLpVTy3mX7"
                    },
                    {
                        "trueName": "韦凤珠",
                        "showId": "YpdVqPzk61UzY27Z"
                    },
                    {
                        "trueName": "刘苏生",
                        "showId": "0Zmov7DBv2I6RGAa"
                    },
                    {
                        "trueName": "胡振宇",
                        "showId": "9APPXxJjr0TXl3JN"
                    },
                    {
                        "trueName": "黄飞",
                        "showId": "m7Doj3Zl9GCYByWz"
                    },
                    {
                        "trueName": "严娜俊",
                        "showId": "7XDOnNek82sjqPrp"
                    },
                    {
                        "trueName": "刘佳民",
                        "showId": "297NKDKkDzHZe2da"
                    },
                    {
                        "trueName": "陈清清",
                        "showId": "Ne8V8J8b7ASA2DBN"
                    },
                    {
                        "trueName": "方升群",
                        "showId": "PQV8oo3jeeiDkLbY"
                    },
                    {
                        "trueName": "王帅帅",
                        "showId": "WeKJDWROnaHQn6Yq"
                    },
                    {
                        "trueName": "张友堃",
                        "showId": "nQQqyLxAabIZoEp6"
                    },
                    {
                        "trueName": "陈昕煜",
                        "showId": "X0e98ol7zLTroE6x"
                    }
                ],
                "type": 3
            }
        ]
    },
    "message": "Success"
}
```

### 14 功能 ：获取项目（修改项目时调用）

####  请求方法：/1do/projectBoard/getProject

####  参数说明：

| 字段 | 类型 | 是否必填 | 描述及要求 |
| :--- | :--- | :------- | :--------- |
| id   | long | 是       | 项目id     |

```
{
	"id":23
}
```

####  Output

| 字段         | 类型      | 描述及要求 |
| :----------- | :-------- | :--------- |
| id           | Long      | 项目id     |
| name         | String    | 姓名名称   |
| createTime   | long      | 创建时间   |
| finishTime   | long      | 结束时间   |
| parentId     | long      | 父节点id   |
| stakeholders | resPerson | 干系人列表 |
| children     | List      | 子节点     |
| items        | List      | 父级分类   |

stakeholders

| 字段     | 类型   | 描述及要求   |
| :------- | :----- | :----------- |
| showId   | String | 干系人showId |
| trueName | String | 真实姓名     |

children

| 字段      | 类型   | 描述及要求                         |
| :-------- | :----- | :--------------------------------- |
| id        | int    | 节点id                             |
| item_name | String | 节点名称                           |
| type      | int    | 1-项目分类 2-项目子节点分类 3-项目 |
| parent_id | int    | 父节点id                           |
| children  | List   | 子对象                             |

items

items：

| 字段     | 类型   | 描述及要求                         |
| :------- | :----- | :--------------------------------- |
| id       | int    | 节点id                             |
| topic    | String | 节点名称                           |
| type     | int    | 1-项目分类 2-项目子节点分类 3-项目 |
| parentid | int    | 父节点id                           |

**Success**

```
{
    "code": 200,
    "data": {
        "finishTime": 1564068848000,
        "createTime": 1564022154000,
        "children": [
            {
                "children": [
                    {
                        "children": [],
                        "parent_id": 23,
                        "item_name": "工作方",
                        "id": 29,
                        "type": 2
                    },
                    {
                        "children": [],
                        "parent_id": 23,
                        "item_name": "参与方",
                        "id": 30,
                        "type": 2
                    }
                ],
                "parent_id": 13,
                "item_name": "1懂",
                "id": 23,
                "type": 3
            }
        ],
        "name": "1懂",
        "id": 23,
        "stakeholders": [
            {
                "trueName": "方升群",
                "showId": "PQV8oo3jeeiDkLbY"
            },
            {
                "trueName": "谢洁",
                "showId": "XzaqamVZPDtAR3WP"
            }
        ],
        "items": [
            {
                "topic": "下城",
                "id": 1,
                "type": 1,
                "parentid": 0
            },
            {
                "topic": "展示项目",
                "id": 11,
                "type": 1,
                "parentid": 1
            },
            {
                "topic": "大屏展示项目",
                "id": 13,
                "type": 1,
                "parentid": 11
            },
            {
                "topic": "1懂",
                "id": 23,
                "type": 3,
                "parentid": 13
            }
        ],
        "parentId": 13
    },
    "message": "Success"
}
```

### 15 功能 ：节点添加干系人

####  请求方法：/1do/projectBoard/insertStakeholder

####  参数说明：

| 字段       | 类型 | 是否必填 | 描述及要求      |
| :--------- | :--- | :------- | :-------------- |
| SHOW_ID    | List | 是       | 人员SHOW_ID集合 |
| ITEM_ID    | long | 是       | 节点id          |
| PROJECT_ID | long | 是       | 项目id          |

```
{
    "SHOW_ID": [
        "6ZYbRyY7mDivQ5e7",
        "Ne8V8J8b7ASA2DBN",
        "WeKJDWROnaHQn6Yq",
        "X0e98ol7zLTroE6x",
        "PQV8oo3jeeiDkLbY"
    ],
    "ITEM_ID": 1309,
    "PROJECT_ID":1305
}
```

####  Output



**Success**

```
{
    "code": 200,
    "data": "添加成功",
    "message": "Success"
}
```

#### 

### 16 功能 ：看板列表

####  请求方法：/1do/projectBoard/getProjectList

####  参数说明：

####  参数说明：

| 字段       | 类型   | 是否必填 | 描述及要求                             |
| :--------- | :----- | :------- | :------------------------------------- |
| pageNumber | int    | 是       | 页码                                   |
| pageSize   | int    | 是       | 一页显示条数                           |
| name       | String | 否       | 模糊搜索项目名称                       |
| isDeleted  | int    | 是       | 是否删除1是0否，回收站传1，正常看板传0 |

```
{
    "pageNumber": 1,
    "pageSize": 10,
    "name": "",
    "isDeleted":1
}
```

#### 

#### Output

| 字段          | 类型    | 描述及要求   |
| :------------ | :------ | :----------- |
| O_CREATE_TIME | String  | 创建时间     |
| U_TRUE_NAME   | String  | 创建人       |
| ITEM_ID       | int     | 项目id       |
| UPDATE_TIME   | String  | 修改时间     |
| NAME          | String  | 项目名称     |
| totalRow      | int     | 总条数       |
| pageNumber    | int     | 当前页码     |
| firstPage     | boolean | 是否第一页   |
| lastPage      | boolean | 是否最后一页 |
| totalPage     | int     | 总页数       |
| pageSize      | int     | 一页总数     |

```
{
    "code": 200,
    "data": {
        "totalRow": 27,
        "pageNumber": 1,
        "firstPage": true,
        "lastPage": false,
        "totalPage": 6,
        "pageSize": 5,
        "list": [
            {
                "O_CREATE_TIME": "2019-08-26 10:24:42",
                "U_TRUE_NAME": "沈霄莉",
                "ITEM_ID": 964,
                "UPDATE_TIME": "2019-09-05 16:10:15",
                "NAME": "日志系统"
            },
            {
                "O_CREATE_TIME": "2019-08-26 11:13:02",
                "U_TRUE_NAME": "沈霄莉",
                "ITEM_ID": 1026,
                "UPDATE_TIME": "2019-09-05 16:09:59",
                "NAME": "测试数据模版1"
            },
            {
                "O_CREATE_TIME": "2019-08-29 14:42:11",
                "U_TRUE_NAME": "沈霄莉",
                "ITEM_ID": 1253,
                "UPDATE_TIME": "2019-09-04 14:54:47",
                "NAME": "测试模版2.0.0.0"
            },
            {
                "O_CREATE_TIME": "2019-08-29 14:39:27",
                "U_TRUE_NAME": "沈霄莉",
                "ITEM_ID": 1132,
                "UPDATE_TIME": "2019-09-04 11:39:40",
                "NAME": "测试模版1.1.1.1"
            },
            {
                "O_CREATE_TIME": "2019-08-26 10:57:24",
                "U_TRUE_NAME": "沈霄莉",
                "ITEM_ID": 979,
                "UPDATE_TIME": "2019-09-04 11:32:36",
                "NAME": "测试空白模版"
            }
        ]
    },
    "message": "Success"
}
```

### 17 功能 ：列表添加项目看板

#### 请求方法：/1do/projectBoard/addProjectBoard

####  参数说明：

| 字段     | 类型   | 是否必填 | 描述及要求             |
| :------- | :----- | :------- | :--------------------- |
| itemName | String | 是       | 看板名称               |
| type     | int    | 是       | 0-空白项目，1-模版项目 |

```
{
    "itemName": "评价系统",
    "type": 0
}
```

####  Output

| 字段  | 类型 | 描述及要求                                                   |
| :---- | :--- | :----------------------------------------------------------- |
| power | int  | 权限（1-整理层，2-领导，3-普通用户，4-干系人），1和2可编辑新增 |
| items | List | 数据列表                                                     |

items：

| 字段         | 类型   | 描述及要求                                                   |
| :----------- | :----- | :----------------------------------------------------------- |
| id           | int    | 节点id                                                       |
| topic        | String | 节点名称                                                     |
| type         | int    | 1-项目分类 2-项目子节点分类 3-项目4-1do                      |
| parentid     | int    | 父节点id                                                     |
| direction    | String | 方向                                                         |
| timeout      | int    | 超过拟办结时间的为1                                          |
| status       | int    | 3/4 进行中 5 已完成  （状态5不管timeout  显示绿色 ，进行中 timeout为1 显示 红色 ，其他显示灰色） |
| SHOW_ID      | String | 1do的showid，打开1do详情用                                   |
| stakeholders | list   | 关系人                                                       |

**Success**

```
{
    "code": 200,
    "data": {
        "power": 2,
        "items": [
            {
                "topic": "原则",
                "id": "965",
                "stakeholders": [],
                "type": 2,
                "parentid": "964",
                "direction": "right"
            },
            {
                "topic": "项目管理",
                "id": "966",
                "stakeholders": [],
                "type": 2,
                "parentid": "965",
                "direction": "right"
            },
            {
                "topic": "技术实施",
                "id": "967",
                "stakeholders": [],
                "type": 2,
                "parentid": "965",
                "direction": "right"
            },
            {
                "topic": "步骤",
                "id": "968",
                "stakeholders": [],
                "type": 2,
                "parentid": "964",
                "direction": "right"
            },
            {
                "topic": "需求梳理",
                "id": "969",
                "stakeholders": [],
                "type": 2,
                "parentid": "968",
                "direction": "right"
            },
            {
                "topic": "预算确定",
                "id": "970",
                "stakeholders": [],
                "type": 2,
                "parentid": "968",
                "direction": "right"
            },
            {
                "topic": "任务分解",
                "id": "971",
                "stakeholders": [],
                "type": 2,
                "parentid": "968",
                "direction": "right"
            },
            {
                "topic": "招标采购",
                "id": "972",
                "stakeholders": [],
                "type": 2,
                "parentid": "968",
                "direction": "right"
            },
            {
                "topic": "项目实施与对接",
                "id": "973",
                "stakeholders": [],
                "type": 2,
                "parentid": "968",
                "direction": "right"
            },
            {
                "topic": "验收",
                "id": "974",
                "stakeholders": [],
                "type": 2,
                "parentid": "968",
                "direction": "right"
            },
            {
                "topic": "评价",
                "id": "975",
                "stakeholders": [],
                "type": 2,
                "parentid": "968",
                "direction": "right"
            },
            {
                "topic": "分工",
                "id": "976",
                "stakeholders": [],
                "type": 2,
                "parentid": "964",
                "direction": "right"
            },
            {
                "topic": "工作内容",
                "id": "977",
                "stakeholders": [],
                "type": 2,
                "parentid": "976",
                "direction": "right"
            },
            {
                "topic": "预算分解",
                "id": "978",
                "stakeholders": [],
                "type": 2,
                "parentid": "976",
                "direction": "right"
            },
            {
                "topic": "日志系统",
                "isroot": true,
                "id": "964",
                "stakeholders": [],
                "type": 3
            }
        ]
    },
    "message": "Success"
}
```

### 18 功能 ：修改项目列表中的列表名称

####  请求方法：/1do/projectBoard/updateProjectListName

####  参数说明：

| 字段    | 类型   | 是否必填 | 描述及要求 |
| :------ | :----- | :------- | :--------- |
| NAME    | String | 是       | 项目名称   |
| ITEM_ID | Long   | 是       | 项目id     |

```
{
	"NAME":"191717505020985344",
	"ITEM_ID":822
}
```

####  Output

```
{
    "code": 200,
    "data": "修改成功",
    "message": "Success"
}
```

### 19 功能 ：删除项目列表数据(过期，调用39接口)

####  请求方法：/1do/projectBoard/deleteProjectList

####  参数说明：

| 字段    | 类型 | 是否必填 | 描述及要求 |
| :------ | :--- | :------- | :--------- |
| ITEM_ID | Long | 是       | 项目id     |

```
{

	"ITEM_ID":823
}
```

####  Output

```
{
    "code": 200,
    "data": "删除成功",
    "message": "Success"
}
```

###81.1 功能 ：获取每日项目公司日报报送时间

#### 81.2 请求方法：/1do/projectBoard/getCompanyLogTimes

#### 81.3 参数说明：

| 字段      | 类型   | 是否必填 | 描述及要求 |
| :-------- | :----- | :------- | :--------- |
| projectId | Long   | 是       | 项目id     |
| date      | String | 是       | 报告日期   |

```
{
	"projectId":930,
	"date":"2019-08-20"
}
```

#### 81.4 Output

| 字段          | 类型   | 描述及要求 |
| :------------ | :----- | :--------- |
| companyShowId | String | 公司ShowId |
| companyName   | String | 公司名称   |
| time          | String | 报送时间   |

```
{
    "code": 200,
    "data": [
        {
            "companyShowId": "ZrAx88o1dphDbeYQ",
            "companyName": "SSW",
            "time": "18:33:38"
        },
        {
            "companyShowId": "bn8nKRd02oHNj8z7",
            "companyName": "竹云",
            "time": "19:54:19"
        }
    ],
    "message": "Success"
}
```

### 20 功能 ：获取日报字数

####  请求方法：/1do/projectBoard/getLogNumber

####  参数说明：

| 字段      | 类型   | 是否必填 | 描述及要求 |
| :-------- | :----- | :------- | :--------- |
| projectId | Long   | 是       | 项目id     |
| date      | String | 是       | 报告日期   |

```
{
	"projectId":930,
	"date":"2019-08-20"
}
```

####  Output

| 字段          | 类型   | 描述及要求 |
| :------------ | :----- | :--------- |
| companyShowId | String | 公司ShowId |
| companyName   | String | 公司名称   |
| number        | int    | 日报字数   |

```
{
    "code": 200,
    "data": [
        {
            "companyShowId": "bn8nKRd02oHNj8z7",
            "number": 49,
            "companyName": "竹云"
        },
        {
            "companyShowId": "ZrAx88o1dphDbeYQ",
            "number": 118,
            "companyName": "SSW"
        }
    ],
    "message": "Success"
}
```

### 21 功能 ：修改/新增报告备注

####  请求方法：/1do/projectBoard/updateRemarks

####  参数说明：

| 字段      | 类型   | 是否必填 | 描述及要求                                                 |
| :-------- | :----- | :------- | :--------------------------------------------------------- |
| projectId | Long   | 是       | 项目id                                                     |
| date      | String | 是       | 报告日期                                                   |
| content   | String | 是       | 内容                                                       |
| type      | int    | 是       | 1-日报评价，2-日报备注，3-项目完整进展备注，4-项目进展备注 |

```
{
	"projectId":930,
	"date":"2019-08-21",
	"content":"bbbbbbbb",
	"type":1
}
```

####  Output

```
{
    "code": 200,
    "data": "成功",
    "message": "Success"
}
```

### 22 功能 ：获取报告标题，及所有时间

####  请求方法：/1do/projectBoard/getTitle

####  参数说明：

| 字段      | 类型 | 是否必填 | 描述及要求 |
| :-------- | :--- | :------- | :--------- |
| projectId | Long | 是       | 项目id     |

```
{
	"projectId":930
}
```

####  Output

| 字段  | 类型   | 描述及要求 |
| :---- | :----- | :--------- |
| title | String | 标题       |
| dates | list   | 可选日期   |

```
{
    "code": 200,
    "data": {
        "dates": [
            {
                "date": "2019-08-21"
            },
            {
                "date": "2019-08-20"
            }
        ],
        "title": "1do项目进展情况汇报"
    },
    "message": "Success"
}
```

### 23 功能 ：获取日报备注接口

####  请求方法：/1do/projectBoard/getReportRemarks

####  参数说明：

| 字段      | 类型   | 是否必填 | 描述及要求 |
| :-------- | :----- | :------- | :--------- |
| projectId | Long   | 是       | 项目id     |
| date      | String | 是       | 报告日期   |

```
{
	"projectId":930,
	"date":"2019-08-21"
}
```

####  Output

| 字段    | 类型   | 描述及要求                                                 |
| :------ | :----- | :--------------------------------------------------------- |
| ID      | long   | id                                                         |
| TYPE    | int    | 1-日报评价，2-日报备注，3-项目完整进展备注，4-项目进展备注 |
| CONTENT | String | 内容                                                       |

```
{
    "code": 200,
    "data": [
        {
            "ID": 1,
            "CONTENT": "bbbbbbbbooo1234",
            "TYPE": 1
        },
        {
            "ID": 3,
            "CONTENT": "任务进展很顺利dsds123",
            "TYPE": 2
        },
        {
            "ID": 4,
            "CONTENT": "公司及时报日志",
            "TYPE": 3
        },
        {
            "CONTENT": "",
            "TYPE": 4
        }
    ],
    "message": "Success"
}
```

### 24 功能 ：修改/新增报告任务评估

####  请求方法：/1do/projectBoard/updateEvaluate

####  参数说明：

| 字段      | 类型   | 是否必填 | 描述及要求 |
| :-------- | :----- | :------- | :--------- |
| taskId    | Long   | 是       | 任务id     |
| content   | String | 否       | 内容       |
| companyId | String | 是       | 公司ShowId |

```
{
	"taskId":1,
	"content":"鹭栖公司完成度很高",
	"companyId":"20181106114714565-8405-D3D01C822"
} 
```

####  Output

```
{
    "code": 200,
    "data": "成功",
    "message": "Success"
}
```

### 25 功能 ：获取项目完整进展图

####  请求方法：/1do/projectBoard/getBoardTask

####  参数说明：

| 字段      | 类型   | 是否必填 | 描述及要求 |
| :-------- | :----- | :------- | :--------- |
| projectId | Long   | 是       | 项目id     |
| date      | String | 是       | 报告日期   |

```
{
	
	"date":"2019-08-22",
	"projectId":822
	
}
```

####  Output

| 字段              | 类型   | 描述及要求                                |
| :---------------- | :----- | :---------------------------------------- |
| ID                | long   | 自增id                                    |
| TASK              | int    | 任务                                      |
| DATE              | date   | 日期                                      |
| PROJECT_ID        | long   | 项目id                                    |
| COMPLETION        | String | 完成情况                                  |
| PRINCIPLE         | String | 负责人                                    |
| PRINCIPLE_SHOW_ID | String | 负责人showid                              |
| PLANNED_DATE      | String | 计划日期                                  |
| DATA              | String | 各个实际日期状态(A是已完成B是骷髅C是闪电) |
| ITEM_ID           | long   | 项目节点id                                |
| IS_OPEN           | int    | 是否展开：1展开1do进度图，0不展开         |
| PLANNED_TIME      | String | 计划时间                                  |

```
{
    "code": 200,
    "data": [
        {
            "TASK": "",
            "ITEM_ID": null,
            "PRINCIPLE": "",
            "PRINCIPLE_SHOW_ID": null,
            "ATEMP": null,
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": null,
            "DATA": "[\"二\",\"三\",\"四\",\"五\",\"六\",\"日\",\"一\",\"二\",\"三\",\"四\",\"五\",\"六\",\"日\",\"一\",\"二\",\"三\",\"四\",\"五\",\"六\",\"日\",\"一\",\"二\",\"三\",\"四\",\"五\",\"六\",\"日\",\"一\",\"二\",\"三\",\"四\",\"五\",\"六\",\"日\",\"一\",\"二\",\"三\",\"四\",\"五\",\"六\",\"日\",\"一\",\"二\",\"三\",\"四\",\"五\",\"六\",\"日\",\"一\",\"二\",\"三\",\"四\",\"五\",\"六\",\"日\",\"一\",\"二\"]",
            "IS_OPEN": 0,
            "ID": 53731,
            "COMPLETION": null,
            "PROJECT_ID": 1397,
            "PLANNED_DATE": null,
            "BTEMP": null
        },
        {
            "TASK": "任务",
            "ITEM_ID": null,
            "PRINCIPLE": "负责人",
            "PRINCIPLE_SHOW_ID": null,
            "ATEMP": null,
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": null,
            "DATA": "[\"09.03\",\"09.04\",\"09.05\",\"09.06\",\"09.07\",\"09.08\",\"09.09\",\"09.10\",\"09.11\",\"09.12\",\"09.13\",\"09.14\",\"09.15\",\"09.16\",\"09.17\",\"09.18\",\"09.19\",\"09.20\",\"09.21\",\"09.22\",\"09.23\",\"09.24\",\"09.25\",\"09.26\",\"09.27\",\"09.28\",\"09.29\",\"09.30\",\"10.01\",\"10.02\",\"10.03\",\"10.04\",\"10.05\",\"10.06\",\"10.07\",\"10.08\",\"10.09\",\"10.10\",\"10.11\",\"10.12\",\"10.13\",\"10.14\",\"10.15\",\"10.16\",\"10.17\",\"10.18\",\"10.19\",\"10.20\",\"10.21\",\"10.22\",\"10.23\",\"10.24\",\"10.25\",\"10.26\",\"10.27\",\"10.28\",\"10.29\"]",
            "IS_OPEN": 0,
            "ID": 53732,
            "COMPLETION": "完成情况",
            "PROJECT_ID": 1397,
            "PLANNED_DATE": null,
            "BTEMP": null
        },
        {
            "TASK": "步骤",
            "ITEM_ID": 1401,
            "PRINCIPLE": "数据局",
            "PRINCIPLE_SHOW_ID": null,
            "ATEMP": "[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"A\"]",
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": null,
            "DATA": "[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]",
            "IS_OPEN": 0,
            "ID": 53733,
            "COMPLETION": "已完成",
            "PROJECT_ID": 1397,
            "PLANNED_DATE": null,
            "BTEMP": "[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]"
        },
        {
            "TASK": "任务分解",
            "ITEM_ID": 1404,
            "PRINCIPLE": "数据局",
            "PRINCIPLE_SHOW_ID": null,
            "ATEMP": "[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"A\"]",
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": null,
            "DATA": "[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]",
            "IS_OPEN": 0,
            "ID": 53734,
            "COMPLETION": "已完成",
            "PROJECT_ID": 1397,
            "PLANNED_DATE": null,
            "BTEMP": "[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]"
        },
        {
            "TASK": "综合指挥平台界面开发",
            "ITEM_ID": 1412,
            "PRINCIPLE": "鹭栖科技",
            "PRINCIPLE_SHOW_ID": "NpnL9d0xPAcZoE8A",
            "ATEMP": "[\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"A\"]",
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": "2018-07-01~2019-09-10",
            "DATA": "[\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]",
            "IS_OPEN": 1,
            "ID": 53735,
            "COMPLETION": "已完成",
            "PROJECT_ID": 1397,
            "PLANNED_DATE": "07.01-09.10",
            "BTEMP": "[\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]"
        },
        {
            "TASK": "指挥平台后端接口开发",
            "ITEM_ID": 1413,
            "PRINCIPLE": "鹭栖科技",
            "PRINCIPLE_SHOW_ID": "NpnL9d0xPAcZoE8A",
            "ATEMP": "[\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"A\"]",
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": "2018-07-01~2019-09-10",
            "DATA": "[\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]",
            "IS_OPEN": 1,
            "ID": 53736,
            "COMPLETION": "已完成",
            "PROJECT_ID": 1397,
            "PLANNED_DATE": "07.01-09.10",
            "BTEMP": "[\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]"
        },
        {
            "TASK": "三维地图开发",
            "ITEM_ID": 1414,
            "PRINCIPLE": "杭勘院",
            "PRINCIPLE_SHOW_ID": "20181119162441128-3297-70D2FA318",
            "ATEMP": "[\"\",\"04\",\"05\",\"06\",\"C\",\"C\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"A\"]",
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": "2019-09-03~2019-09-15",
            "DATA": "[\"\",\"04\",\"05\",\"06\",\"C\",\"C\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]",
            "IS_OPEN": 1,
            "ID": 53737,
            "COMPLETION": "已完成",
            "PROJECT_ID": 1397,
            "PLANNED_DATE": "09.03-09.15",
            "BTEMP": "[\"\",\"04\",\"05\",\"06\",\"C\",\"C\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]"
        },
        {
            "TASK": "四个平台开发(含 1DO 对接)",
            "ITEM_ID": 1415,
            "PRINCIPLE": "综合指挥平台开发群",
            "PRINCIPLE_SHOW_ID": "20190820093022732-7D5A-9813901FA",
            "ATEMP": "[\"\",\"04\",\"05\",\"06\",\"C\",\"C\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"A\"]",
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": "2019-09-03~2019-09-15",
            "DATA": "[\"\",\"04\",\"05\",\"06\",\"C\",\"C\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]",
            "IS_OPEN": 1,
            "ID": 53738,
            "COMPLETION": "已完成",
            "PROJECT_ID": 1397,
            "PLANNED_DATE": "09.03-09.15",
            "BTEMP": "[\"\",\"04\",\"05\",\"06\",\"C\",\"C\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]"
        },
        {
            "TASK": "指挥功能-视频办小程序接入开发",
            "ITEM_ID": 1416,
            "PRINCIPLE": "SSW",
            "PRINCIPLE_SHOW_ID": "ZrAx88o1dphDbeYQ",
            "ATEMP": "[\"\",\"04\",\"05\",\"06\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\",\"\",\"\",\"\",\"\",\"\",\"A\"]",
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": "2019-09-03~2019-09-15",
            "DATA": "[\"\",\"04\",\"05\",\"06\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\"]",
            "IS_OPEN": 1,
            "ID": 53739,
            "COMPLETION": "已完成",
            "PROJECT_ID": 1397,
            "PLANNED_DATE": "09.03-09.15",
            "BTEMP": "[\"\",\"04\",\"05\",\"06\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\"]"
        },
        {
            "TASK": "指挥功能-1DO 对接开发",
            "ITEM_ID": 1417,
            "PRINCIPLE": "鹭栖科技",
            "PRINCIPLE_SHOW_ID": "NpnL9d0xPAcZoE8A",
            "ATEMP": "[\"\",\"04\",\"05\",\"06\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\",\"\",\"\",\"\",\"\",\"\",\"A\"]",
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": "2019-09-03~2019-09-15",
            "DATA": "[\"\",\"04\",\"05\",\"06\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\"]",
            "IS_OPEN": 1,
            "ID": 53740,
            "COMPLETION": "已完成",
            "PROJECT_ID": 1397,
            "PLANNED_DATE": "09.03-09.15",
            "BTEMP": "[\"\",\"04\",\"05\",\"06\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\"]"
        },
        {
            "TASK": "指挥功能-语音对接开发",
            "ITEM_ID": 1418,
            "PRINCIPLE": "鹭栖科技",
            "PRINCIPLE_SHOW_ID": "NpnL9d0xPAcZoE8A",
            "ATEMP": "[\"\",\"\",\"\",\"\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\",\"\",\"\",\"\",\"\",\"\",\"A\"]",
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": "2019-09-06~2019-09-10",
            "DATA": "[\"\",\"\",\"\",\"\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\"]",
            "IS_OPEN": 1,
            "ID": 53741,
            "COMPLETION": "已完成",
            "PROJECT_ID": 1397,
            "PLANNED_DATE": "09.06-09.10",
            "BTEMP": "[\"\",\"\",\"\",\"\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\"]"
        },
        {
            "TASK": "指挥功能-短信对接开发",
            "ITEM_ID": 1419,
            "PRINCIPLE": "鹭栖科技",
            "PRINCIPLE_SHOW_ID": "NpnL9d0xPAcZoE8A",
            "ATEMP": "[\"\",\"04\",\"05\",\"06\",\"C\",\"C\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"A\"]",
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": "2019-09-03~2019-09-15",
            "DATA": "[\"\",\"04\",\"05\",\"06\",\"C\",\"C\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]",
            "IS_OPEN": 1,
            "ID": 53742,
            "COMPLETION": "已完成",
            "PROJECT_ID": 1397,
            "PLANNED_DATE": "09.03-09.15",
            "BTEMP": "[\"\",\"04\",\"05\",\"06\",\"C\",\"C\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]"
        },
        {
            "TASK": "智慧小区接入开发",
            "ITEM_ID": 1420,
            "PRINCIPLE": "鹭栖科技",
            "PRINCIPLE_SHOW_ID": "NpnL9d0xPAcZoE8A",
            "ATEMP": "[\"\",\"\",\"\",\"06\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\",\"\",\"\",\"\",\"\",\"\",\"A\"]",
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": "2019-09-03~2019-09-15",
            "DATA": "[\"\",\"\",\"\",\"06\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\"]",
            "IS_OPEN": 1,
            "ID": 53743,
            "COMPLETION": "已完成",
            "PROJECT_ID": 1397,
            "PLANNED_DATE": "09.03-09.15",
            "BTEMP": "[\"\",\"\",\"\",\"06\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\"]"
        },
        {
            "TASK": "统一用户整合接入开发",
            "ITEM_ID": 1421,
            "PRINCIPLE": "综合指挥平台开发群,鹭栖科技,杭勘院",
            "PRINCIPLE_SHOW_ID": "NpnL9d0xPAcZoE8A,20181119162441128-3297-70D2FA318,20190820093022732-7D5A-9813901FA",
            "ATEMP": "[\"\",\"\",\"\",\"\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\"]",
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": "2019-09-06~2019-09-10",
            "DATA": "[\"\",\"\",\"\",\"\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\"]",
            "IS_OPEN": 1,
            "ID": 53744,
            "COMPLETION": "80%",
            "PROJECT_ID": 1397,
            "PLANNED_DATE": "09.06-09.10",
            "BTEMP": null
        },
        {
            "TASK": "平台发布硬件资源、网络环境准备",
            "ITEM_ID": 1422,
            "PRINCIPLE": "鹭栖科技",
            "PRINCIPLE_SHOW_ID": "NpnL9d0xPAcZoE8A",
            "ATEMP": "[\"\",\"\",\"C\",\"C\",\"C\",\"C\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"A\"]",
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": "2019-09-03~2019-09-15",
            "DATA": "[\"\",\"\",\"C\",\"C\",\"C\",\"C\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]",
            "IS_OPEN": 1,
            "ID": 53745,
            "COMPLETION": "已完成",
            "PROJECT_ID": 1397,
            "PLANNED_DATE": "09.03-09.15",
            "BTEMP": "[\"\",\"\",\"C\",\"C\",\"C\",\"C\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]"
        },
        {
            "TASK": "系统整体联调",
            "ITEM_ID": 1423,
            "PRINCIPLE": "综合指挥平台开发群,鹭栖科技,杭勘院",
            "PRINCIPLE_SHOW_ID": "NpnL9d0xPAcZoE8A,20181119162441128-3297-70D2FA318,20190820093022732-7D5A-9813901FA",
            "ATEMP": "[\"\",\"\",\"\",\"\",\"\",\"\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"A\"]",
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": "2019-09-08~2019-09-10",
            "DATA": "[\"\",\"\",\"\",\"\",\"\",\"\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]",
            "IS_OPEN": 1,
            "ID": 53746,
            "COMPLETION": "已完成",
            "PROJECT_ID": 1397,
            "PLANNED_DATE": "09.08-09.10",
            "BTEMP": "[\"\",\"\",\"\",\"\",\"\",\"\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]"
        },
        {
            "TASK": "数据局与长庆街道演示与预确认",
            "ITEM_ID": 1424,
            "PRINCIPLE": "长庆街道领导,信息中心",
            "PRINCIPLE_SHOW_ID": "D00703,20190429162025102-7AE3-A2B7CB295",
            "ATEMP": "[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\"]",
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": "2019-09-10~2019-09-10",
            "DATA": "[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\"]",
            "IS_OPEN": 1,
            "ID": 53747,
            "COMPLETION": "未开始",
            "PROJECT_ID": 1397,
            "PLANNED_DATE": "09.10-09.10",
            "BTEMP": null
        },
        {
            "TASK": "平台完善",
            "ITEM_ID": 1425,
            "PRINCIPLE": "综合指挥平台开发群,鹭栖科技,SSW,杭勘院",
            "PRINCIPLE_SHOW_ID": "NpnL9d0xPAcZoE8A,ZrAx88o1dphDbeYQ,20181119162441128-3297-70D2FA318,20190820093022732-7D5A-9813901FA",
            "ATEMP": "[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"11\",\"12\",\"13\",\"14\",\"15\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\"]",
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": "2019-09-10~2019-09-15",
            "DATA": "[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"11\",\"12\",\"13\",\"14\",\"15\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\"]",
            "IS_OPEN": 1,
            "ID": 53748,
            "COMPLETION": "未开始",
            "PROJECT_ID": 1397,
            "PLANNED_DATE": "09.10-09.15",
            "BTEMP": null
        },
        {
            "TASK": "正式发布上线",
            "ITEM_ID": 1426,
            "PRINCIPLE": "综合指挥平台开发群,鹭栖科技,SSW,杭勘院",
            "PRINCIPLE_SHOW_ID": "NpnL9d0xPAcZoE8A,ZrAx88o1dphDbeYQ,20181119162441128-3297-70D2FA318,20190820093022732-7D5A-9813901FA",
            "ATEMP": "[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"11\",\"12\",\"13\",\"14\",\"15\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"A\"]",
            "DATE": "2019-10-29 00:00:00",
            "PLANNED_TIME": "2019-09-10~2019-09-15",
            "DATA": "[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"11\",\"12\",\"13\",\"14\",\"15\",\"C\",\"C\",\"C\",\"C\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\",\"B\"]",
            "IS_OPEN": 1,
            "ID": 53749,
            "COMPLETION": "未开始",
            "PROJECT_ID": 1397,
            "PLANNED_DATE": "09.10-09.15",
            "BTEMP": null
        }
    ],
    "message": "Success"
}
```

### 26 功能 ： 修改完整进展图中的完成情况

####  请求方法：/1do/projectBoard/updateBoardTaskCompletion

####  参数说明：

| 字段       | 类型   | 是否必填 | 描述及要求                   |
| :--------- | :----- | :------- | :--------------------------- |
| DATE       | String | 否       | 日期（在思维导图标记可不填） |
| COMPLETION | String | 是       | 完成情况                     |
| ITEM_ID    | long   | 是       | 节点id                       |

```
{
	
	"DATE":"2019-08-23",
	"COMPLETION":"已完成",
	"ITEM_ID":"948"
}
```

####  Output

```
{
    "code": 200,
    "data": "修改成功",
    "message": "Success"
}
```

### 27 功能 ： 获取项目进展列表

####  请求方法：/1do/projectBoard/getProjectProgress

####  参数说明：

| 字段      | 类型   | 是否必填 | 描述及要求 |
| :-------- | :----- | :------- | :--------- |
| projectId | Long   | 是       | 项目id     |
| date      | String | 是       | 报告日期   |

```
{
	"projectId":822,
	"date":"2019-08-23"
}
```

#### Output

| 字段          | 类型   | 描述及要求 |
| :------------ | :----- | :--------- |
| company       | String | 公司       |
| principle     | String | 对接人     |
| table         | list   | 表格内容   |
| companyShowId | String | 公司showId |

table：

| 字段        | 类型   | 描述及要求 |
| :---------- | :----- | :--------- |
| taskId      | long   | 自增id     |
| task        | int    | 任务       |
| plannedDate | String | 计划日期   |
| evaluate    | String | 评估       |
| logs        | list   | 日志       |
| complete    | String | 完成情况   |

```
{
    "code": 200,
    "data": [
        {
            "principle": "于璟明,张城",
            "company": "鹭栖科技",
            "table": [
                {
                    "plannedDate": null,
                    "task": "A",
                    "logs": [
                        "8.23 对人员详情饼图做调整 与刘亚伟沟通处理一些之前的问题  首页接口的编写 参见人员接口的编写 思考人员参见项目部分中项目经理不与人员相关的问题，找到解决办法 处理获取人员详细信息，人员为1，9的内容无法显示问题已解决 学习了把项目部署在远程服务器的方法 解决了项目在远程服务器端口冲突的问题"
                    ],
                    "evaluate": null,
                    "taskId": 498
                }
            ]
        },
        {
            "principle": "梁斌",
            "company": "SSW",
            "table": [
                {
                    "plannedDate": "01.03-02.28",
                    "task": "项目分类节点20190805",
                    "logs": [
                        "8.23 1do管理日志报送情况开发和接口的对接，图标和时间轴接口的对接。备注信息和评价接口的对接，项目完整进展的开发。和部分接口的对接。 8h"
                    ],
                    "evaluate": null,
                    "taskId": 496
                },
                {
                    "plannedDate": null,
                    "task": "A",
                    "logs": [
                        "8.23 对人员详情饼图做调整 与刘亚伟沟通处理一些之前的问题  首页接口的编写 参见人员接口的编写 思考人员参见项目部分中项目经理不与人员相关的问题，找到解决办法 处理获取人员详细信息，人员为1，9的内容无法显示问题已解决 学习了把项目部署在远程服务器的方法 解决了项目在远程服务器端口冲突的问题"
                    ],
                    "evaluate": null,
                    "taskId": 498
                }
            ]
        }
    ],
    "message": "Success"
}
```

### 28 功能 ： 任务关联日志接口

####  请求方法：/1do/projectBoard/taskLinkedLogs

####  参数说明：

| 字段   | 类型 | 是否必填 | 描述及要求   |
| :----- | :--- | :------- | :----------- |
| taskId | Long | 是       | 任务id       |
| logId  | Long | 是       | 日报id       |
| itemId | Long | 是       | 项目子节点id |

```json
{
	"taskId":2453,
	"logId":38,
	"itemId":92
}
```

####  Output

```
{
    "code": 200,
    "data": "关联成功",
    "message": "Success"
}
```

### 29 功能 ：根据任务获取项目公司日志接口

####  请求方法：/1do/projectBoard/getLogs

####  参数说明：

| 字段   | 类型 | 是否必填 | 描述及要求 |
| :----- | :--- | :------- | :--------- |
| taskId | Long | 是       | 任务id     |

```
{
	"taskId":401
}
```

####  Output

| 字段        | 类型   | 描述及要求                   |
| :---------- | :----- | :--------------------------- |
| id          | long   | id                           |
| companyName | String | 公司名称                     |
| content     | String | 内容                         |
| taskId      | long   | 任务id，不为null就默认为选中 |

```
{
    "code": 200,
    "data": [
        {
            "companyName": "鹭栖科技",
            "id": 37,
            "content": "8.23 1do项目进度完整表算法及获取接口完成，1do服务器迁移。8h",
            "taskId": null
        },
        {
            "companyName": "鹭栖科技",
            "id": 38,
            "content": "8.23 1do项目看板思维导图插件调整。移除权限控制，添加同级下级节点及右键菜单功能调整。首页列表页修改及删除功能对接测试 8h",
            "taskId": null
        },
        {
            "companyName": "鹭栖科技",
            "id": 40,
            "content": "8.23 1do项目看板获取报告评论接口，修改/删除任务评估接口。6h",
            "taskId": null
        },
        {
            "companyName": "鹭栖科技",
            "id": 41,
            "content": "8.23 1do管理日志报送情况开发和接口的对接，图标和时间轴接口的对接。备注信息和评价接口的对接，项目完整进展的开发。和部分接口的对接。 8h",
            "taskId": null
        }
    ],
    "message": "Success"
}
```

### 30 功能 ：分享汇报

####  请求方法：/1do/projectBoard/share

####  参数说明：

| 字段 | 类型   | 是否必填 | 描述及要求                                                   |
| :--- | :----- | :------- | :----------------------------------------------------------- |
| to   | list   | 是       | 分享对象的数值                                               |
| url  | String | 是       | 分享汇报地址或者附件地址（pdf分享或者图片分享前先调用36接口获得附件地址） |
| type | String | 是       | 不填默认Message；普通分享填：Message，pdf分享填File，图片分享填Image， |

```
{
	"to":["8BmEYpm9kQUlNN7m","WeKJDWROnaHQn6Yq"],
	"url":"http://username=ssss:ssssssssss",
	"type":"File"
	
}
```

####  Output

```
{
    "code": 200,
    "data": "分享成功",
    "message": "Success"
}
```

### 31 功能 ：日志系统挂接项目任务

#### 请求方法：/1do/projectBoard/updateLog

#### 参数说明：

| 字段           | 类型   | 是否必填 | 描述及要求     |
| :------------- | :----- | :------- | :------------- |
| op             | list   | 否       | 分享对象的数值 |
| WorkRecordId   | String | 是       | 分享汇报地址   |
| WorkRecordName | String | 是       | 日报内容       |
| msgAddTime     | Date   | 是       | 日报报送时间   |
| WorkRecordDate | Date   | 是       | 日报日期       |
| account        | String | 是       | 统一用户账号   |
| projectTask    | list   | 是       |                |
| projectId      | Long   | 是       | 项目id         |
| taskId         | Long   | 否       | 任务id         |

删除参数：

```
{
	"op":"delete",
	"WorkRecordId":1171624872405630976
}
```

新增修改参数：

```
{
	"WorkRecordId":1171624872405630976,
	"WorkRecordName":"9.11 1do项目看板导入日志的方法修改。3h",
	"msgAddTime":"2019-09-11 11:21:51.976",
	"WorkRecordDate":"2019-09-11 10:00:00.000",
	"account":"shenxiaoli1",
	"projectTask":[
		{
			"projectId":930,
			"taskId":942
		}]
}
```

####  Output

```
{
    "code": 200,
    "data": "成功",
    "message": "Success"
}
```



### 32 功能 ：根据群名获取项目及任务（日志系统调用）

####  请求方法：/1do/projectBoard/getProjectsByGroup

####  参数说明：

| 字段    | 类型   | 是否必填 | 描述及要求 |
| :------ | :----- | :------- | :--------- |
| groupId | String | 是       | 群id       |

```
{
    "groupId":"12457@ChatRoom"
}
```

####  Output

| 字段    | 类型   | 描述及要求 |
| :------ | :----- | :--------- |
| project | object | 项目       |
| tasks   | list   | 任务       |

```
{
    "code": 200,
    "data": {
        "project": {
            "ID": 1397,
            "ITEM_NAME": "综合指挥平台"
        },
        "tasks": [
            {
                "topic": "步骤",
                "id": 1401
            },
            {
                "topic": "任务分解",
                "id": 1404
            },
            {
                "topic": "综合指挥平台界面开发",
                "id": 1412
            },
            {
                "topic": "指挥平台后端接口开发",
                "id": 1413
            },
            {
                "topic": "三维地图开发",
                "id": 1414
            },
            {
                "topic": "四个平台开发(含 1DO 对接)",
                "id": 1415
            },
            {
                "topic": "指挥功能-视频办小程序接入开发",
                "id": 1416
            },
            {
                "topic": "指挥功能-1DO 对接开发",
                "id": 1417
            },
            {
                "topic": "指挥功能-语音对接开发",
                "id": 1418
            },
            {
                 "topic": "指挥功能-短信对接开发",
                 "id": 1419
             },
             {
                 "topic": "智慧小区接入开发",
                 "id": 1420
             },
             {
                 "topic": "统一用户整合接入开发",
                 "id": 1421
             },
             {
                 "topic": "平台发布硬件资源、网络环境准备",
                 "id": 1422
             },
             {
                 "topic": "系统整体联调",
                 "id": 1423
             },
             {
                 "topic": "数据局与长庆街道演示与预确认",
                 "id": 1424
             },
             {
                 "topic": "平台完善",
                 "id": 1425
             },
             {
                 "topic": "正式发布上线",
                 "id": 1426
             }
         ]
     },
     "message": "Success"
 }
```

### 33 功能 ：获得所有项目

####  请求方法：/1do/projectBoard/getAllProjects

####  参数说明：

无请求参数

####  Output

| 字段      | 类型   | 描述及要求 |
| :-------- | :----- | :--------- |
| ID        | int    | 项目id     |
| ITEM_NAME | String | 项目名     |

```
{
    "code": 200,
    "data": [
        {
            "ID": 822,
            "ITEM_NAME": "评价系统1"
        },
        {
            "ID": 823,
            "ITEM_NAME": "看板看板"
        },
       ....
        {
            "ID": 1397,
            "ITEM_NAME": "综合指挥平台"
        },
        {
            "ID": 1446,
            "ITEM_NAME": "通天塔"
        }
    ],
    "message": "Success"
}
```

### 34 功能 ：获得1do进展图

#### 请求方法：/1do/projectBoard/get1doByItemID

#### 参数说明：

| 字段    | 类型   | 是否必填 | 描述及要求 |
| :------ | :----- | :------- | :--------- |
| DATE    | String | 是       | 日期       |
| ITEM_ID | long   | 是       | 节点id     |

```
{
	
	"DATE":"2019-08-23",
	"ITEM_ID":"948"
}
```

#### Output

| 字段              | 类型   | 描述及要求                                |
| :---------------- | :----- | :---------------------------------------- |
| ID                | long   | 自增id                                    |
| TASK              | int    | 任务                                      |
| DATE              | date   | 日期                                      |
| PROJECT_ID        | long   | 项目id                                    |
| COMPLETION        | String | 完成情况                                  |
| PRINCIPLE         | String | 负责人                                    |
| PRINCIPLE_SHOW_ID | String | 负责人showid                              |
| PLANNED_DATE      | String | 计划日期                                  |
| DATA              | String | 各个实际日期状态(A是已完成B是骷髅C是闪电) |
| ITEM_ID           | long   | 项目节点id                                |

```
{
    "code": 200,
    "data": [
        {
            "DATE": "2019-08-23 00:00:00",
            "TASK": "新建一个测试1do",
            "DATA": "[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"27\",\"28\",\"29\",\"30\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]",
            "ITEM_ID": 948,
            "SHOW_ID": "196063997043146752",
            "PRINCIPLE": "陈清清;王帅帅;傅锦进",
            "ID": 77,
            "COMPLETION": "待接单",
            "PRINCIPLE_SHOW_ID": "Ne8V8J8b7ASA2DBN;WeKJDWROnaHQn6Yq;1Z1o92oERvfrOLkl",
            "ATEMP": "[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]",
            "BTEMP": null
        },
        {
            "DATE": "2019-08-23 00:00:00",
            "TASK": "dasd",
            "DATA": "[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"10\",\"11\",\"12\",\"13\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]",
            "ITEM_ID": 948,
            "SHOW_ID": "201112254924455936",
            "PRINCIPLE": "陶丹;庞潇潇;苏金芳;徐杰;王德锋;周侠晨;胡甜甜;程太锟;梅舒芬;陈丹;林鹏;沈周科;沈罗熠;骆姜斌;蒋娅楠;薛伟;陈昕煜;方升群;王帅帅;刘佳民;张友堃;陈清清;黄飞;杨能丹;严娜俊;刘苏生;胡振宇;韦凤珠;1Call综合客服",
            "ID": 78,
            "COMPLETION": "待接单",
            "PRINCIPLE_SHOW_ID": "pvE5pqrVjWUqdP59;Nv0Gz0pP5NFdXyE7;DWZdGQmmjDhbRVrz;avE8bm0K1LFDYnVl;6KqjALYDe6U0vBb3;8ZavkQ2ePVS8ZW9e;KDy1ONNJzbuV2rG3;or0e55Xyz8hODB8K;oNoPnDVEe1i8NDQe;DO0RvoNK7xhaxVXy;kVoZD18XY2trJRb0;29jvymQOOYCElQWN;PmrEy9ObkkuZQPLX;3ZzmorkJ90h0RGWq;3LBYkvlD7rHmLn72;EWJ0WkR2LKiXOqRz;X0e98ol7zLTroE6x;PQV8oo3jeeiDkLbY;WeKJDWROnaHQn6Yq;297NKDKkDzHZe2da;nQQqyLxAabIZoEp6;Ne8V8J8b7ASA2DBN;m7Doj3Zl9GCYByWz;k0K3loQLpVTy3mX7;7XDOnNek82sjqPrp;0Zmov7DBv2I6RGAa;9APPXxJjr0TXl3JN;YpdVqPzk61UzY27Z;Jj8z6v7rBjTQQqYk",
            "ATEMP": "[\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"A\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]",
            "BTEMP": null
        }
    ],
    "message": "Success"
}
```

### 35 功能 ：获取项目子节点（日志系统调用）

#### 请求方法：/1do/projectBoard/getProjectChildNodes

#### 参数说明：

| 字段 | 类型 | 是否必填 | 描述及要求 |
| :--- | :--- | :------- | :--------- |
| id   | long | 是       | 项目id     |

```
{
	"id":23
}
```

#### Output

| 字段     | 类型   | 描述及要求                              |
| :------- | :----- | :-------------------------------------- |
| id       | int    | 节点id                                  |
| topic    | String | 节点名称                                |
| type     | int    | 1-项目分类 2-项目子节点分类 3-项目4-1do |
| parentid | int    | 父节点id                                |

**Success**

```
{
    "code": 200,
    "data": [
        {
            "topic": "项目分类节点20190805",
            "id": 928,
            "type": 2,
            "parentid": 822
        },
        ....
        {
            "topic": "44441",
            "id": 1378,
            "type": 2,
            "parentid": 1376
        }
    ],
    "message": "Success"
}
```

### 36 功能 ：上传附件

#### 请求方法：/1do/file/uploadFile

#### 数据格式：multipart/form-data

#### 参数说明：file

| 字段 | 类型 | 是否必填 | 描述及要求 |
| :--- | :--- | :------- | :--------- |
| file | File | 是       | 附件       |

#### Output

| 字段 | 类型 | 描述及要求 |
| ---- | ---- | ---------- |
| data | list | 附件地址   |



```
{
    "code": 200,
    "data": [
        "http://59.202.68.43:8080/1do/upload/212711298956263424.jpeg",
        "http://59.202.68.43:8080/1do/upload/212711298989817856.jpeg",
        "http://59.202.68.43:8080/1do/upload/212711299002400768.jpeg",
        "http://59.202.68.43:8080/1do/upload/212711299019177984.jpeg",
        "http://59.202.68.43:8080/1do/upload/212711299052732416.jpeg"
    ],
    "message": "Success"
}
```

### 

### 37 功能 ：写总结/编辑总结

#### 请求方法：/1do/projectBoard/summary

#### 参数说明：

| 字段       | 类型   | 是否必填 | 描述及要求                                           |
| :--------- | :----- | :------- | :--------------------------------------------------- |
| DATE       | String | 是       | 日期                                                 |
| SUMMARY    | String | 是       | 总结（如需插入图片附件，先调用上传附件接口获得地址） |
| NUMBER     | int    | 是       | 编号（1，2，3）三个位置的总结                        |
| PROJECT_ID | long   | 是       | 项目id                                               |

```
{
    "DATE": "2019-10-12",
    "SUMMARY": "撒飒飒深V暗示法1.👌 
    http://59.202.68.43:8080/1do/upload/212711298989817856.jpeg",
    "NUMBER": 3,
    "PROJECT_ID": 1397
}
```

#### Output

```
{
    "code": 200,
    "data": {
        "DATE": "2019-10-12 00:00:00",
        "NUMBER": 3,
        "SUMMARY": "撒飒飒深V暗示法1.👌 ",
        "ID": 5,
        "PROJECT_ID": 1397
    },
    "message": "Success"
}
```

### 38 功能 ：获得总结

#### 请求方法：/1do/projectBoard/getSummary

#### 参数说明：

| 字段      | 类型   | 是否必填 | 描述及要求 |
| :-------- | :----- | :------- | :--------- |
| projectId | Long   | 是       | 项目id     |
| date      | String | 是       | 报告日期   |

```
{
	
	"date":"2019-10-12",
	"projectId":1397
	
}
```

#### Output

| 字段       | 类型   | 描述及要求                    |
| :--------- | :----- | :---------------------------- |
| DATE       | String | 日期                          |
| SUMMARY    | String | 总结                          |
| NUMBER     | int    | 编号（1，2，3）三个位置的总结 |
| PROJECT_ID | long   | 项目id                        |
| ID         | int    | 自增id                        |

**Success**

```
{
    "code": 200,
    "data": [
        {
            "DATE": "2019-10-12 00:00:00",
            "NUMBER": 1,
            "SUMMARY": "撒飒飒深V暗示法",
            "ID": 1,
            "PROJECT_ID": 1397
        },
        {
            "DATE": "2019-10-12 00:00:00",
            "NUMBER": 3,
            "SUMMARY": "撒飒飒深V暗示法1.👌 ",
            "ID": 5,
            "PROJECT_ID": 1397
        }
    ],
    "message": "Success"
}
```

### 39 功能 ：删除总结 

#### 请求方法：/1do/projectBoard/ deleteSummary 

#### 参数说明：

| 字段       | 类型   | 是否必填 | 描述及要求                    |
| :--------- | :----- | :------- | :---------------------------- |
| DATE       | String | 是       | 日期                          |
| NUMBER     | int    | 是       | 编号（1，2，3）三个位置的总结 |
| PROJECT_ID | long   | 是       | 项目id                        |

```
{
    "DATE": "2019-10-12",
    "SUMMARY": "撒飒飒深V暗示法1.👌 11111222wwww222",
    "NUMBER": 3,
    "PROJECT_ID": 1397
}
```

#### Output

```
{
    "code": 200,
    "data": "删除成功",
    "message": "Success"
}
```

### 40 功能 ：项目列表删除或恢复

####  请求方法：/1do/projectBoard/deleteOrReplyToProject 

####  参数说明：

| 字段    | 类型 | 是否必填 | 描述及要求 |
| :------ | :--- | :------- | :--------- |
| ITEM_ID | Long | 是       | 项目id     |

```
{
	"ITEM_ID":822
}
```

####  Output

```
{
    "code": 200,
    "data": 1,
    "message": "Success"
}
```

### 41 功能 ：节点撤销

#### 请求方法：/1do/projectBoard/undoNode

#### 参数说明：

无请求参数

#### Output

| 字段      | 类型    | 描述及要求                             |
| :-------- | :------ | :------------------------------------- |
| isLastOne | boolean | 是否是最后一条（若为true，则撤销按钮） |

**Success**

```
{
    "code": 200,
    "data": {
        "isLastOne": true
    },
    "message": "Success"
}
```

### 42 功能 ：项目总览节点关联项目

#### 请求方法：/1do/projectBoard/relatedProject

#### 参数说明：

| 字段       | 类型   | 是否必填 | 描述及要求 |
| :--------- | :----- | :------- | :--------- |
| id         | Long   | 是       | 节点id     |
| projectIds | long[] | 是       | 项目id列表 |

```
{
    "id":1618,
    "projectIds":[1535,822]
}
```

#### Output

**Success**

```
{
    "code": 200,
    "data": "修改成功",
    "message": "Success"
}
```

### 43 功能 ：获取项目进展图

#### 请求方法：/1do/projectBoard/getProjectTrend

#### 参数说明：

| 字段      | 类型 | 是否必填 | 描述及要求 |
| :-------- | :--- | :------- | :--------- |
| projectId | Long | 是       | 项目id     |

```
{
	"projectId":822
}
```

#### Output

| 字段           | 类型   | 描述及要求     |
| :------------- | :----- | :------------- |
| DATE           | String | 日期           |
| RATE           | int    | 比例           |
| ALREADY_NUMBER | int    | 已经完成任务数 |
| PROJECT_ID     | long   | 项目id         |
| ALL_NUMBER     | int    | 所有任务数量   |

**Success**

```
{
    "code": 200,
    "data": [
        {
            "DATE": "2020-01-01 00:00:00",
            "RATE": 0,
            "ALREADY_NUMBER": 0,
            "ALL_NUMBER": 5,
            "PROJECT_ID": 822
        },
        {
            "DATE": "2020-01-02 00:00:00",
            "RATE": 14,
            "ALREADY_NUMBER": 1,
            "ALL_NUMBER": 7,
            "PROJECT_ID": 822
        },
        {
            "DATE": "2020-01-03 00:00:00",
            "RATE": 9,
            "ALREADY_NUMBER": 1,
            "ALL_NUMBER": 11,
            "PROJECT_ID": 822
        },
        {
            "DATE": "2020-01-04 00:00:00",
            "RATE": 18,
            "ALREADY_NUMBER": 2,
            "ALL_NUMBER": 11,
            "PROJECT_ID": 822
        },
        {
            "DATE": "2020-01-05 00:00:00",
            "RATE": 27,
            "ALREADY_NUMBER": 3,
            "ALL_NUMBER": 11,
            "PROJECT_ID": 822
        }
    ],
    "message": "Success"
}
```

### 44 功能 ：项目总览节点取消关联项目

#### 请求方法：/1do/projectBoard/unRelatedProject

#### 参数说明：

| 字段 | 类型 | 是否必填 | 描述及要求 |
| :--- | :--- | :------- | :--------- |
| id   | Long | 是       | 项目节点id |

```
{
    "id":822
}
```

#### Output

**Success**

```
{
    "code": 200,
    "data": "修改成功",
    "message": "Success"
}
```

### 45 功能 ：获取所有未关联的项目

#### 请求方法：/1do/projectBoard/getAllUnrelatedProjects

#### 参数说明：

无请求参数

#### Output

| 字段      | 类型   | 描述及要求 |
| :-------- | :----- | :--------- |
| ID        | int    | 项目id     |
| ITEM_NAME | String | 项目名     |

```
{
    "code": 200,
    "data": [
        {
            "ID": 822,
            "ITEM_NAME": "90"
        },
        {
            "ID": 1305,
            "ITEM_NAME": "1懂32"
        },
        {
            "ID": 1375,
            "ITEM_NAME": "测试"
        },
        {
            "ID": 1397,
            "ITEM_NAME": "综合指挥平台"
        },
        {
            "ID": 1588,
            "ITEM_NAME": "新建测试看板"
        }
    ],
    "message": "Success"
}
```






