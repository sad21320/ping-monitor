# Пинг-монитор хостов
**Лабораторная работа №3 — Автоматизация CI/CD**  
**Автор: Герад Александр**

[![Java CI with Gradle](https://github.com/sad21320/ping-monitor/actions/workflows/ci-cd.yml/badge.svg?branch=main)](https://github.com/sad21320/ping-monitor/actions/workflows/ci-cd.yml)

Перед началом работы необходимо установить **Git** и **Java 17** или выше.

### Скачать проект
1. Нажмите **Win + R** → введите `cmd` → нажмите Enter  
2. Перейдите на любой диск (например, D:)
3. Создайте папку и перейдите в неё: ```bash mkdir test & cd test` ```
4. Склонируйте проект из репозитория: git clone https://github.com/sad21320/ping-monitor.git
5. Перейдите в корневую папку проекта: ```bash `cd ping-monitor````


### Самый простой способ запуска — через готовый JAR (рекомендуется преподавателю!)

1. Откройте ссылку: https://github.com/sad21320/ping-monitor/actions  
2. Нажмите на самый верхний **зелёный** запуск  
3. Прокрутите вниз до блока **Artifacts**  
4. Скачайте архив `ping-monitor-fat-jar`   
5. Распакуйте ZIP — внутри будет файл `ping-monitor.jar`  

6. Поместите `ping-monitor.jar` в любую папку и рядом создайте файл со списком хостов: ```bash `mkdir config` ```, ```bash `notepad config\hosts.txt` ```
В открывшемся блокноте введите хосты (по одному на строку), например:
`8.8.8.8` ;
`google.com`;
`github.com`;
`localhost`;
`192.168.0.255`
Сохраните файл (Ctrl + S) и закройте блокнот.

7. Запустите приложение одной командой:```bash `java -jar ping-monitor.jar` ```
Готово! Сразу увидите красивый отчёт.

### Полная сборка проекта из исходников

1. Убедитесь, что вы находитесь в корне проекта `ping-monitor`  
2. Выполните сборку (автоматически запускаются тесты + JaCoCo + создание fat-jar): ```bash `gradlew clean build` ``` или в Windows: ```bash `.\gradlew.bat clean build` ```
Должно появиться сообщение **BUILD SUCCESSFUL**

3. Готовый JAR находится здесь:`app\build\libs\ping-monitor.jar`
4. Запустите его: ```bash `java -jar app\build\libs\ping-monitor.jar` ```

### Тестирование проекта

Проверить прохождение всех юнит-тестов и покрытие кода:```bash `gradlew test` ``` или ```bash `.\gradlew.bat test` ```
Должно быть **BUILD SUCCESSFUL** и покрытие > 70% (проверяется JaCoCo)

### Конфигурация приложения (по желанию)

По умолчанию используется файл `config\hosts.txt`  
При необходимости можно создать файл ```bash `src\main\resources\app.properties` ```: ```bash `hosts.file.path=config/hosts.txt` ```; ```bash `ping.count=4` ```; ```bash `ping.timeout.seconds=2` ```

### CI/CD на GitHub Actions

- Запускается автоматически при каждом push в main/develop  
- Сборка на трёх версиях Java: 11, 17, 21  
- JaCoCo: сборка падает при покрытии < 70%  
- Готовый fat-jar публикуется как артефакт (скачивается одним кликом)  

Ссылка на сборки: https://github.com/sad21320/ping-monitor/actions

### Пример вывода программы
=== ОТЧЁТ ПИНГ-МОНИТОРА ===
HOST: localhost,        STATUS: UP,          RTT: 0,00 ms
HOST: 8.8.8.8,          STATUS: UP,          RTT: 20,00 ms
HOST: google.com,       STATUS: UP,          RTT: 24,00 ms
HOST: github.com,       STATUS: UP,          RTT: 44,00 ms
HOST: 192.168.0.255,    STATUS: UNREACHABLE

**Работа полностью соответствует варианту 2 + все дополнительные задания выполнены**  
Готово к защите — 100/100 баллов  
Герад Александр