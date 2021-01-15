# 1do接口文档  （默认POST请求，Content-Type: application/json）

## 正式 https://1do.hzxc.gov.cn:8443

## 测试 http://59.202.68.43:8080

[TOC]

------

### 1功能 ：1do登入（1cll调1do设置session）

#### 请求方法：  /1do/do/login

#### 参数说明：

| 字段        | 类型   | 是否必填 | 描述及要求 |
| ----------- | ------ | -------- | ---------- |
| useraccount | String | 必填     | 账号       |

```json
{
    "useraccount": "fangshengqun",
    
}
```

####  Output

| 字段        | 字段名       | 类型    | 描述及要求                                                   |
| ----------- | ------------ | ------- | ------------------------------------------------------------ |
| D_NAME      | 1call字段    | int     | 我的部门名                                                   |
| U_DEPT_ID   | 1call字段    | list    | 我的部门id                                                   |
| cShowId     | 1call字段    | lsit    | 部门根id                                                     |
| isfw        | 1do字段      | boolean | 是否是整理层false否，true是                                  |
| loginName   | 1call字段    | String  | showid                                                       |
| LoginToken  | 1call字段    | String  | token                                                        |
| useraccount | 1call字段    | String  | 账号                                                         |
| username    | 1call字段    | String  | 姓名                                                         |
| POWER       | 权限         | int     | 权限表（1整理层2领导3普通用户4项目干系人）项目干系人只在特殊接口里才出现，这里不设置 |
| YSCG_POWER  | 云上城管权限 | int     | 云上城管权限，默认0、无权限，1、街道账号，2、整治小组账号，3、专班账号、4、事件发起账号 |

**Success**

```json
{
    "cShowId": "b5WJZ1bRLDCb7x2B",
    "code": 200,
    "LAST_UPDATE_TIME": "2019-08-16 12:30:45",
    "POWER": 1,
    "isfw": true,
    "message": "Success",
    "D_NAME": [
        "技术组"
    ],
    "YSCG_POWER": 0,
    "U_DEPT_ID": [
        "20181106114714565-8405-D3D01C822"
    ],
    "loginName": "PQV8oo3jeeiDkLbY",
    "LoginToken": "oLq4XeIfm+tNSTxfW8cicbbFPWs=",
    "useraccount": "fangshengqun",
    "username": "方升群"
}
```



### 3 功能 ：普通反馈

#### 请求方法：  /1do/do/feedback

#### 参数说明：

| 字段         | 字段名              | 类型   | 是否必填 | 描述及要求                                                   |
| ------------ | ------------------- | ------ | -------- | ------------------------------------------------------------ |
| SHOW_ID      | 1do工单编号         | String | 必填     | 1do工单编号                                                  |
| FBCONTENT    | 反馈内容            | String | 必填     | 反馈内容根据反馈类型而定                                     |
| FB_TYPE      | 反馈类型            | int    | 必填     | 反馈类型标签1，正常反馈；2，回复反馈；3，附件反馈  4，催办反馈，5，办结反馈；6，评价反馈，7删除反馈,8删除反馈时加1条数据,9.语音反馈填,10网页链接 |
| FB_USER      | 对应用户show_id     | String | 选填     | 反馈时相应的show_id（如回复反馈回复谁的show_id）             |
| FB_USER_NAME | 对应用户show_id名字 | String | 选填     | 反馈时相应的show_id名字（如回复馈回复谁的show_id名字）       |
| star         | 评星                | int    | 选填     | 评价的星星数                                                 |
| AT           | @人相关信息         | String | 选填     | @人相关信息AT如：["V267EnA0yEUjXZ5N@顾清清","DlrD1jVjadcbLaVD@胡滨"] |
| loginName    | 人员showid          | String | 选填     | 人员showid（账号未登入时填写）                               |

```json
{
    "SHOW_ID": "41068020382040064",
    "FBCONTENT": "",
    "FB_TYPE": 4,
    "FB_USER":"",
    "FB_USER_NAME":""
}
```

```反馈列子
其他反馈列子：
{
    "SHOW_ID": "41068020382040064",
    "FB_TIME": "2018-06-21 12:01:28",
    "FBCONTENT": "回复谢洁保证在下个星期三之前完成",//方升群催办//方升群确认办结
    "FB_TYPE": 2,//4//5
    "FB_USER":"XzaqamVZPDtAR3WP",
    "FB_USER_NAME":"谢洁"
}
```

```
评价反馈
{
    "SHOW_ID": "41068020382040064",
    "FBCONTENT": "干的漂亮
    "FB_TYPE": 6
    "FB_USER":"",
    "FB_USER_NAME":"",
    "star":5
}
```



#### Output

| 字段         | 字段名              | 类型   | 是否必填 | 描述及要求                                                   |
| ------------ | ------------------- | ------ | -------- | ------------------------------------------------------------ |
| O_USER       | 反馈人员show_id     | String | 必填     | 反馈人员show_id                                              |
| O_USER_NAME  | 反馈人员show_id名字 | String | 必填     | 反馈人员show_id名字                                          |
| FB_TIME      | 反馈时间            | String | 必填     | 反馈时间                                                     |
| FBCONTENT    | 反馈内容            | String | 必填     | 反馈内容根据反馈类型而定                                     |
| FB_TYPE      | 反馈类型            | int    | 必填     | 反馈类型标签1，正常反馈；2，回复反馈；3，附件反馈  4，催办反馈，5，办结反馈；6，评价反馈，7,删除反馈，【8删除反馈时加1条数据】9.语音反馈 |
| FB_USER_NAME | 对应用户show_id名字 | String | 必填     | 反馈时相应的show_id名字（如回复、催办反馈 回复、催办谁的show_id名字） |
| ATTR_PATH    | 附件路径            | String | 必填     | 附件路径                                                     |
| star         | 评星                | int    | 必填     | 评价的星星。                                                 |
| AT           | @人相关信息         | String | 必填     | @人相关信息                                                  |
| ID           | 编号                | int    | 必填     | 编号                                                         |

**Success**

```json
{
    "code": 200,
    "data": {
        "FB_USER_NAME": "",
        "AT": "[]",
        "star": 0,
        "FB_TIME": "2018-11-09 16:10:02",
        "O_USER_NAME": "刘韬",
        "FB_USER": "",
        "SHOW_ID": "89868108176556032",
        "O_USER": "Np1ZK5KO3ZcXx1NJ",
        "FBCONTENT": "222",
        "FB_TYPE": 1,
        "TIME_STAMP": 1541751002956,
        "ID": 3565
    },
    "message": "Success"
}
```







