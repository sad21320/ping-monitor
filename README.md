# Пинг-монитор хостов  
**Лабораторная работа №3 — Автоматизация CI/CD (Герад Александр)**

Java-утилита, которая читает список хостов из файла, пингует их, определяет статус доступности и выводит красивый отсортированный отчёт.

## Функционал (полностью соответствует варианту 2)
- Чтение списка хостов из `config/hosts.txt`
- Поддержка Windows и Linux (корректный парсинг разного вывода ping)
- Определение статуса: **UP**, **FLAKY**, **UNREACHABLE**
- Сортировка: сначала UP (по возрастанию RTT), затем FLAKY, затем UNREACHABLE
- Вывод отчёта в консоль
- Fat-jar — запускается одной командой
- Покрытие тестами > 70% (JaCoCo с падением сборки при < 70%)
- Сборка и тесты на JDK 11 / 17 / 21 через GitHub Actions

## Как запустить у себя на компьютере (2 минуты)

### Вариант 1 — Самый простой (рекомендуется для проверки)
```bash
# 1. Скачай готовый JAR из последнего GitHub Actions
#    → https://github.com/sad21320/ping-monitor/actions
#выбери зелёную сборку#Artifacts → ping-monitor-fat-jar

# 2. Создай папку config и файл hosts.txt
mkdir config
echo 8.8.8.8 > config/hosts.txt
echo google.com >> config/hosts.txt
echo github.com >> config/hosts.txt
echo 192.168.0.255 >> config/hosts.txt   # пример недоступного хоста

# 3. Запусти
java -jar ping-monitor.jar

### Вариант 2 — Самый простой (рекомендуется для проверки)
# Клонируем проект
git clone https://github.com/sad21320/ping-monitor.git
cd ping-monitor

# Собираем fat-jar (всё автоматически: тесты + JaCoCo + shadowJar)
./gradlew clean build

# Или на Windows:
.\gradlew.bat clean build

# Запускаем
java -jar app/build/libs/ping-monitor.jar