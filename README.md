# INT2215_81_NHOM20
# Arkanoid Game

## Thành viên
1. Khổng Việt Anh - BSKT_30
2. Nguyễn Doãn Dũng - 24020089
3. Nguyễn Khắc Kiên - 21021509

*Giảng viên: * La Trịnh Hoàng Việt
*Học kì: * HKP – 2026
______________________________________

## Giới thiệu

Đây là dự án triển khai lại trò chơi Arkanoid cổ điển, được phát triển bằng JavaFX. Mục tiêu của dự án là tạo ra một trò chơi Arkanoid đầy đủ chức năng với giao diện người dùng trực quan, logic trò chơi vững chắc và khả năng mở rộng.

Dự án này được phát triển bởi Nhóm 20.

## Các tính năng chính

*   **Lối chơi Arkanoid cổ điển**: Thanh trượt (paddle), bóng (ball), gạch (bricks) và các power-up quen thuộc.
*   **Nhiều màn chơi (Levels)**: Các màn chơi được định nghĩa sẵn với layout gạch khác nhau.
*   **Hệ thống Power-up**:
    *   **BigBall**: Tăng kích thước bóng.
    *   **ExpandPaddle**: Tăng chiều rộng thanh trượt.
    *   **MultiBall**: Nhân đôi số lượng bóng hiện có.
    *   **ExplosiveBrick**: Gạch nổ, phá hủy các gạch xung quanh.
*   **Bảng xếp hạng (Highscores)**: Lưu trữ và hiển thị điểm số cao của người chơi.
*   **Giao diện người dùng JavaFX**: Đồ họa mượt mà và tương tác.



## Cách chạy dự án

### Yêu cầu

*   Java Development Kit (JDK) 21 trở lên.
*   Maven hoặc Gradle (để quản lý dependencies và build).

### Các bước

1.  **Clone repository**:
    ```bash
    git clone https://github.com/Chocolaty349/arknoid.git
    cd arknoid-project
    ```

2.  **Build dự án (Maven)**:
    ```bash
    mvn clean install
    ```


3.  **Chạy ứng dụng**:
    *   **Từ IDE (IntelliJ IDEA, Eclipse)**: Mở dự án trong IDE của bạn, sau đó chạy lớp `org.chocolaty.arknoid.Main`.
    *   **Từ dòng lệnh (sau khi build với Maven)**:
        ```bash
        java -jar target/arkanoid-project-1.0-SNAPSHOT.jar # Tên file có thể khác tùy thuộc vào phiên bản
        ```
        (Nếu gặp lỗi liên quan đến JavaFX Modules, bạn có thể cần thêm các `--module-path` và `--add-modules` khi chạy từ dòng lệnh mà không có IDE. Tuy nhiên, chạy từ IDE thường đơn giản hơn.)

## Kiểm tra (Unit Tests)

Dự án bao gồm các bài kiểm tra đơn vị để đảm bảo tính đúng đắn của logic cốt lõi.

Để chạy các bài kiểm tra:

*   **Sử dụng Maven**:
    ```bash
    mvn test
    ```

*   **Từ IDE**: Chạy trực tiếp các test class hoặc test suite trong IDE của bạn.

## Đóng góp (cho thành viên nhóm)

*   **Branching Strategy**: Chúng tôi sử dụng chiến lược Gitflow hoặc Feature Branching.
    *   `main` (hoặc `master`): Chứa mã nguồn ổn định, đã sẵn sàng để triển khai.
    *   `develop`: Chứa mã nguồn đang trong quá trình phát triển, tích hợp các tính năng mới.
    *   `feature/tên-tính-năng`: Các branch riêng lẻ cho từng tính năng mới.
    *   `bugfix/mã-bug`: Các branch riêng lẻ để sửa lỗi.
*   **Workflow**:
    1.  Tạo một branch mới từ `develop` cho tính năng hoặc sửa lỗi của bạn (`git checkout -b feature/my-new-feature develop`).
    2.  Thực hiện thay đổi, commit thường xuyên với các tin nhắn commit rõ ràng, mô tả.
    3.  Đảm bảo mã của bạn tuân thủ coding style của dự án.
    4.  Viết hoặc cập nhật unit tests cho các thay đổi của bạn.
    5.  Chạy tất cả các unit tests và đảm bảo chúng đều PASS.
    6.  Tạo một Pull Request (PR) về branch `develop`.
    7.  Yêu cầu review mã từ ít nhất một thành viên khác trong nhóm.
    8.  Giải quyết mọi phản hồi từ reviewer.
    9.  Sau khi được approve, hợp nhất (merge) PR vào `develop`.
*   **Coding Style**: (Ví dụ: Tuân thủ Google Java Style Guide, hoặc Java Code Conventions, hoặc quy tắc riêng của nhóm). Hãy thống nhất và viết ở đây.
*   **Code Review**: Mọi PR phải được review và approve bởi ít nhất một thành viên khác trước khi được merge.

## Các công cụ và thư viện

*   **Ngôn ngữ**: Java
*   **Framework UI**: JavaFX
*   **Hệ thống build**: Maven / Gradle

## Thông tin liên hệ

Nếu có bất kỳ câu hỏi hoặc cần hỗ trợ, vui lòng liên hệ với các thành viên trong nhóm:

*   Khổng Việt Anh - BSKT_30
*   Nguyễn Doãn Dũng - 24020089
*   Nguyễn Khắc Kiên - 21021509

## Giấy phép (License)

Dự án này được cấp phép theo Giấy phép MIT. Xem file [LICENSE](LICENSE) để biết thêm chi tiết. <!-- Tạo file LICENSE.md nếu bạn muốn có một giấy phép chính thức -->

---