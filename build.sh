#!/usr/bin/env bash

mvn clean package -Dmaven.test.skip=true

docker build -t registry.cn-qingdao.aliyuncs.com/irdc-algorithm-platform/yudian:aliyun0.1 .

sudo docker login --username=sssugar666 registry.cn-qingdao.aliyuncs.com

docker push registry.cn-qingdao.aliyuncs.com/irdc-algorithm-platform/yudian:aliyun0.1