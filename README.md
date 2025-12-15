# 植物大战僵尸风格 Spring Boot 演示

这是一个使用 Spring Boot 开发的“植物大战僵尸”风格小游戏后端。项目包含：

- **主线模式**：内置 10 个关卡，逐步提升僵尸强度。
- **小游戏模式**：多种独立玩法示例，随时开启。
- **无尽模式**：无限波次，难度随波次递增。
- **植物图鉴**：提供 15 种以上植物的基础属性（费用、伤害、冷却）。

## 快速开始

1. 安装 JDK 17+ 和 Maven 3.9+。
2. 在项目根目录运行：

   ```bash
   mvn spring-boot:run
   ```

3. 服务启动后，默认监听 `http://localhost:8080`，可通过以下接口体验：

   - **主线模式**：
     - 创建存档：`POST /api/game/mainline/start`
     - 推进关卡：`POST /api/game/mainline/{sessionId}/progress`
   - **小游戏模式**：
     - 创建存档：`POST /api/game/minigame/start`，请求体示例 `{ "name": "Fog Runner" }`
   - **无尽模式**：
     - 创建存档：`POST /api/game/endless/start`
   - **查询状态**：`GET /api/game/session/{sessionId}`
   - **查看图鉴**：
     - 植物列表：`GET /api/game/plants`
     - 关卡信息：`GET /api/game/levels`
     - 小游戏清单：`GET /api/game/minigames`

> 提示：接口返回 JSON，可直接用 Postman、curl 或 IDEA 内置的 HTTP Client 进行调用。

## 运行测试

```bash
mvn test
```

## 项目结构

- `src/main/java/com/example/pvz/game`：启动类与入口配置。
- `src/main/java/com/example/pvz/game/api`：REST 控制器。
- `src/main/java/com/example/pvz/game/model`：游戏实体（植物、僵尸、关卡、存档）。
- `src/main/java/com/example/pvz/game/service`：核心玩法逻辑与数据预置。
- `src/test/java/com/example/pvz/game`：基础单元测试。
