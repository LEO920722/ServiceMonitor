History Version

Version 1.1
1. 增加 Rule 页面 insert or update 逻辑
2. 增加 Rule 页面 Content Logging on Errors Only 逻辑


Version 1.2
1. bug修复：输入内容中 ： 被替换为 =
2. 增加 Rule 页面 Active Flag on Errors Only 逻辑


Version 1.21
1. bug 修复：无法插入 Rule


Version 1.3
1. 取消 Transaction 默认全查询


Version: 1.4
1. Transaction 页面增加 Content 按钮 -- 未有逻辑
2. Rule 页面增加删除按钮及逻辑
3. Bug fix：修复 Transaction 页面无法查询带下划线参数


Version 1.41
1. 增加版本记录 /WebContent/META-INF/ReadMe.txt
2. 还原 /WebContent/META-INF/MANIFEST.MF

Version 1.42
1. 修复查询逻辑 -- 增加匹配条件：Rule ID 

Version 1.5
1. 增加 getContent 页面跳转逻辑及直接重推逻辑


Version 1.51
1. 调整 QueryAllRules Select 逻辑，改为根据 RULE_ID 升序排列

Version 1.52
1. 修复查询bug -- 无法查询 BUSINESS_TYPE 和 REFERENCE_ID

Version 1.6 -- 20180611
1. 修改 SearchTrasaction 页面，增加 From Time 和 To Time 选项
2. 修改 SearchTrasaction 页面结果展示，按照 MODIFIED_TIMESTAMP 倒序展示
3. 增加数据查询最大 10000 条限制
4. 删除 order by id desc限制
