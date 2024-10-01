## 使用nginx做反向代理
### 创建雨点docker镜像
在`bulid.sh`中，这里使用了阿里云作为docker的镜像仓库(可根据需要改)

```shell
#!/usr/bin/env bash

mvn clean package -Dmaven.test.skip=true -U

docker build -t registry.cn-qingdao.aliyuncs.com/irdc-algorithm-platform/yudian:v0.1 .

sudo docker login --username=sssugar666 registry.cn-qingdao.aliyuncs.com

docker push registry.cn-qingdao.aliyuncs.com/irdc-algorithm-platform/yudian:v0.1
```
### 拉取镜像

```shell
sudo docker login --username=sssugar666 registry.cn-qingdao.aliyuncs.com

sudo docker pull registry.cn-qingdao.aliyuncs.com/irdc-algorithm-platform/yudian:v0.1
```

### 运行docker
`docker run --name=yudian1 -d -p 8091:8090 registry.cn-qingdao.aliyuncs.com/irdc-algorithm-platform/yudian`

`docker run --name=yudian2 -d -p 8092:8090 registry.cn-qingdao.aliyuncs.com/irdc-algorithm-platform/yudian`

### 配置nginx
#### 镜像
1. `docker pull nginx`
2. `docker run --name=nginx -d -p 8090:80 nginx`
3. `docker exec -it nginx /bin/bash`
4. 更改配置：/etc/nginx/nginx.conf

```
upstream yudian{
       least_conn;
       server 10.112.94.138:8091;
       server 10.112.94.138:8092;
   }

server {
        listen 80;
        server_name 10.112.94.138;

        location / {
                proxy_pass http://yudian;
    }
}
```