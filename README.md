# INT2215_81_NHOM20
# Arkanoid Game - Object-Oriented Programming Project

## Author
1. Khổng Việt Anh - BSKT_30
2. Nguyễn Doãn Dũng - 24020089
3. Nguyễn Khắc Kiên - 21021509

*Instructor:* Hoàng Việt
*Semester:* HKP – 2026
______________________________________
## Description

*Key features:*
1.	The game is developed using Java 17+ with JavaFX/Swing for GUI.
2.	Features multiple pre-defined levels with unique brick layouts, loaded dynamically from external configuration files.
3.	Implements precise physics for ball bouncing mechanics against the paddle, bricks, and walls to ensure a smooth and realistic gameplay experience.
4.	Includes sound effects, animations, and power-up systems.

*Game mechanics:*

•	Control a paddle to bounce a ball and destroy bricks  
•	Collect power-ups for special abilities  
•	Progress through multiple levels with increasing difficulty  
•	Score points and compete on the leaderboard
______________________________________
## Installation
1.	Clone the project from the repository.
2.	Open the project in the IDE.
3.	Run the project.

## Usage
### Controls

| Key | Action |
|------|------------|
| ← |	Move paddle left |
| → |	Move paddle right |
| SPACE |	Launch ball |

### How to Play
1.	*Start the game:* Press ENTER to start game.
2.	*Control the paddle:* Use arrow keys to move left and right.
3.	*Launch the ball:* Press SPACE to launch the ball from the paddle.
4.	*Destroy bricks:* Bounce the ball to hit and destroy bricks.
5.	*Collect power-ups:* Catch falling power-ups for special abilities.
6.	*Avoid losing the ball:* Keep the ball from falling below the paddle.
7.	*Complete the level:* Destroy all destructible bricks to advance.
______________________________________
## Future Improvements
#### Planned Features
1.	*Additional game modes*  
      o	Time attack mode   
      o	Co-op multiplayer mode
2.	*Enhanced gameplay*
      o	More power-up varieties (freeze time, shield wall, etc.)  
      o	Achievements system
3.	*Technical improvements*  
      o	Migrate to LibGDX or JavaFX for better graphics  
      o	Add particle effects and advanced animations  
      o	Implement AI opponent mode  
      o	Add online leaderboard with database backend
______________________________________
## Notes
•	The game was developed as part of the Object-Oriented Programming with Java course curriculum.  
•	All code is written by group members with guidance from the instructor.  
•	Some assets (images, sounds) may be used for educational purposes under fair use.  
•	The project demonstrates practical application of OOP concepts and design patterns.
______________________________________
Last updated: 14/8/2026

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

*   Java Development Kit (JDK) 17 trở lên.
*   Maven hoặc Gradle (để quản lý dependencies và build).

### Các bước

1.  **Clone repository**:
    ```bash
    git clone https://github.com/your-team/arkanoid-project.git
    cd arkanoid-project
    ```
    (Thay đổi `https://github.com/your-team/arkanoid-project.git` bằng URL thực tế của repository của bạn)

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
*   **Testing**: JUnit 5

## Thông tin liên hệ

Nếu có bất kỳ câu hỏi hoặc cần hỗ trợ, vui lòng liên hệ với các thành viên trong nhóm:

*   Khổng Việt Anh - BSKT_30
*   Nguyễn Doãn Dũng - 24020089
*   Nguyễn Khắc Kiên - 21021509

## Giấy phép (License)

Dự án này được cấp phép theo Giấy phép MIT. Xem file [LICENSE](LICENSE) để biết thêm chi tiết. <!-- Tạo file LICENSE.md nếu bạn muốn có một giấy phép chính thức -->

---