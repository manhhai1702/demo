#setup DB
Tạo 2 server Mysql để làm server master-slave

Sửa lại Config ở file application.yml cho đúng với DB

Run DemoDbApplication sẽ tự động tạo table từ liquibase

#Cấu hình đồng MySQL Replication cho 2 DB
1. Cấu hình Master server

    1.1 Sửa nội dung my.cnf hoặc my.ini(win10)
        
        [mysqld] thêm vào nội dung sau:
        server-id=1
        log-bin=mysql-bin
        replicate-do-db='schema_name' //tên schema muốn copy
        Khởi động lại mysql-master
    1.2 Tạo một user trên master để slave đăng nhập và có thể Replicate

        Truy cập vào mysql thực hiện lệnh:
        CREATE USER 'repl_user'@'slave_host' IDENTIFIED BY 'password';
        GRANT REPLICATION SLAVE ON *.* TO 'repl_user'@'slave_host';
    1.3 Lấy thông tin Replication từ Master

        gõ lệnh:
        SHOW MASTER STATUS;

        Ghi lại giá trị của File và Position để dùng ở bước sau

2. Cấu hình Slave server

   2.1 Sửa nội dung my.cnf hoặc my.ini(win10)  

        [mysqld] thêm vào nội dung sau:
        server-id=2
        Khởi động lại mysql-master
   2.2 Change TO Master
        
        Truy cập vào mysql thực hiện lệnh:
          CHANGE MASTER TO
          MASTER_HOST = 'host',    -- Địa chỉ IP hoặc tên máy chủ của MySQL Master
          MASTER_PORT = 3306,            -- Cổng MySQL Master (mặc định là 3306)
          MASTER_USER = 'user',     -- Tên người dùng Replication trên MySQL Master 
          MASTER_PASSWORD = 'pw',  -- Mật khẩu của người dùng Replication trên MySQL Master  
          MASTER_LOG_FILE = 'File',  -- Giá trị File từ MASTER STATUS trên MySQL Master  
          MASTER_LOG_POS = Position;    -- Giá trị Position từ MASTER STATUS trên MySQL Master
          START SLAVE;
          Show slave status;
        Các giá trị Slave_SQL_Running và Slave_SQL_Running =YES là thành công
# test
   Có thể dùng Curl sau để test

      curl --location 'http://localhost:8080/user/update_user' \
      --header 'Content-Type: application/json' \
      --data-raw '{
      "username": "as1bag",
      "email": "as1bag@gmail.com"
      }'
   

