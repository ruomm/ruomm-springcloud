## docker安装mysql8
#### 创建数据卷
```
    mkdir ~/db-dev/mysql-niu/conf
    mkdir ~/db-dev/mysql-niu/data
    chmod -R 755 ~/db-dev/mysql-niu/
```
#### 创建配置文件
```
vim ~/db-dev/mysql-niu/conf/my.cnf
内容如下：

[client]
# 设置mysql客户端连接服务端时默认使用的端口
port = 3306
default-character-set = utf8mb4
[mysqld]
# 设置3306端口
port = 3306
# 设置mysql的安装目录 - mysql的安装路径
# basedir = /usr/mysql
# 设置mysql数据库的数据的存放目录 -安装文件路径下的data文件夹会自行创建
# datadir = /var/lib/mysql
# datadir = /usr/mysql/data
# 允许最大连接数
max_connections = 300
# 允许最大用户连接数
max_user_connections = 100
# 允许连接失败的次数。
max_connect_errors = 100
# ip地址无限制
bind-address = 0.0.0.0
mysqlx-bind-address = 0.0.0.0
# 服务端使用的字符集默认为utf8mb4
character_set_server = utf8mb4
# 连接服务端使用的字符集默认为utf8mb4_bin
collation_server = utf8mb4_bin
# 创建新表时将使用的默认存储引擎
default-storage-engine = INNODB
# 默认使用“mysql_native_password”插件认证
# default_authentication_plugin = mysql_native_password
secure-file-priv = NULL
# Disabling symbolic-links is recommended to prevent assorted security risks
symbolic-links = 0
# Custom config should go here
!includedir /etc/mysql/conf.d/
```
#### 创建容器
```
docker run --name mysql-niu --restart=always -v /dellnas/home/niuyaohui/db-dev/mysql-niu/conf/my.cnf:/etc/mysql/my.cnf -v /dellnas/home/niuyaohui/db-dev/mysql-niu/data:/var/lib/mysql -v /etc/localtime:/etc/localtime:ro -p 11006:3306 -e MYSQL_ROOT_PASSWORD=xiaoniu123456 -d mysql:8
```
#### 进入容器
```
docker exec -it mysql-niu /bin/bash
```
### 配置mysql数据库
```
参考mysql8数据库linux配置方法
```