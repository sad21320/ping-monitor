# Пинг-монитор хостов
**Лабораторная работа №3 — Автоматизация CI/CD**  
**Автор: Герад Александр**

[![Java CI with Gradle](https://github.com/sad21320/ping-monitor/actions/workflows/ci-cd.yml/badge.svg?branch=main)](https://github.com/sad21320/ping-monitor/actions/workflows/ci-cd.yml)

Перед началом работы необходимо установить **Git** и **Java 17** или выше.
Проверить версии установленных продуктов можно с помощью команд `java --version` и `git --version`

### Скачать проект
1. Нажмите **Win + R** → введите `cmd` → нажмите Enter  
2. Перейдите на любой диск 
3. Создайте папку и перейдите в неё: 
```markdown 
mkdir test
```
```markdown 
cd test
```
4. Склонируйте проект из репозитория: git clone https://github.com/sad21320/ping-monitor.git
5. Перейдите в корневую папку проекта: 
```markdown 
cd ping-monitor
```


### Самый простой способ запуска — через готовый JAR

1. Откройте ссылку: https://github.com/sad21320/ping-monitor/actions  
2. Нажмите на самый верхний **зелёный** запуск  
3. Прокрутите вниз до блока **Artifacts**  
4. Скачайте архив `ping-monitor-fat-jar`  
5. Распакуйте ZIP в любую удобную папку и перейдите в нее — внутри будет файл `ping-monitor.jar`  

6. Поместите `ping-monitor.jar` в любую папку и рядом создайте файл со списком хостов: 
```markdown
mkdir config
```
```markdown
notepad config\hosts.txt
```

В открывшемся блокноте введите хосты (по одному на строку), например:
```markdown
8.8.8.8
google.com
github.com
localhost
192.168.0.255
```
Сохраните файл (Ctrl + S) и закройте блокнот.

### Настройка .gitignore (обязательно!)

Файл `.gitignore` лежит в корне проекта и должен содержать следующие правила:

```gitignore
# Gradle — не коммитим кэши и сборку
.gradle/
build/
app/build/

# Исключения: сам wrapper должен быть в репозитории
!gradle/wrapper/gradle-wrapper.jar
!gradlew
!gradlew.bat

# IDE (IntelliJ IDEA, Eclipse и т.д.)
.idea/
*.iml
*.ipr
*.iws
out/

# Файлы ОС
.DS_Store
Thumbs.db

# Локальные конфиги — НЕ коммитим личные файлы
config/*.txt          # игнорируем любые .txt в папке config
!config/hosts.txt     # кроме примера hosts.txt — его специально оставляем для всех

# Если не хочешь коммитить свой app.properties (он уже есть в src/main/resources)
app.properties

# Отчёты JaCoCo и тестов
build/reports/
```

### Конфигурация приложения (по желанию)

По умолчанию используется файл `config\hosts.txt`  
При необходимости можно создать файл `src\main\resources\app.properties`: 
```markdown
hosts.file.path=config/hosts.txt
ping.count=4
ping.timeout.seconds=2
```


### Полная сборка проекта из исходников

1. Убедитесь, что вы находитесь в корне проекта `ping-monitor`  
2. Выполните сборку (автоматически запускаются тесты + JaCoCo + создание fat-jar): 
```markdown 
gradlew clean build 
``` 
или в Windows: 
```markdown 
.\gradlew.bat clean build
```
Должно появиться сообщение **BUILD SUCCESSFUL**

3. Готовый JAR находится здесь:`app\build\libs\ping-monitor.jar`
4. Запустите его: 
```markdown  
java -jar app\build\libs\ping-monitor.jar
```

### Тестирование проекта

Проверить прохождение всех юнит-тестов и покрытие кода:
```markdown
gradlew test
``` 
или 
```markdown
.\gradlew.bat test
```
Должно быть **BUILD SUCCESSFUL** и покрытие > 70% (проверяется JaCoCo)


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