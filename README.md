# JancoBlog

在这里做一些规定：

首页导航栏的内容  

```sql
	select * from t_type where pre_type_id is null
```


## 一些东西的格式：


### 用户ID

使用自增id，root的id为10000

### 文章ID

文章的ID为

用户的ID + 文章发布的时候的时间戳

将文章存储为静态的html文件

文章名称命名规范：

文章ID.html

---

文章中的图片、用户的头像存在服务器的文件夹中

