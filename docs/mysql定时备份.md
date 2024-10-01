#### dataBaseName.sh

- 创建 `.sh`脚本

  ```shell
                                                                                                                                                                              #!/bin/bash
  mysqldump -uroot -pirdc320 shortVideo > /root/mysql-backup/shortVideo_$(date +%Y%m%d_%H%M%S).sql
  
  # 压缩
  mysqldump -uroot -pirdc320 shortVideo | gzip > /root/mysql-backup/shortVideo_$(date +%Y%m%d_%H%M%S).sql.gz
  ```

  - -u：实际用户
  - -p: 数据库密码
  - shortVideo：需要备份的数据库
  - `>` 后面的是备份的位置 + 文件名

- 添加权限：`chmod u+x dataBaseName.sh`

  - 添加可执行权限之后先执行一下，看看脚本有没有错误，能不能正常使用：`./dataBaseName.sh`

- 定时

  - `crontab -e`
  - 添加任务：`*/1 * * * * /home/backup/bkDatabaseName.sh*`
  - 具体含义可以参考[crontab](https://blog.csdn.net/dbagaoshou/article/details/82116501?utm_medium=distribute.pc_relevant.none-task-blog-searchFromBaidu-2.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-searchFromBaidu-2.control)



#### 定时清理

可参考[linux中定时清理文件](https://blog.csdn.net/u011415782/article/details/87635514)

```shell
#!/bin/sh
find /root/mysql-backup -mmin +20 -name *.sql* -exec rm -rf {} \;
```



```shell
*/1 * * * * /root/mysql-backup/bkshortVideo.sh
*/20 * * * * /root/mysql-backup/clean_backup
```



