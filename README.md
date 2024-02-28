# Инструкция по запуску проекта для пользователя
1. Открыть [код программы](https://github.com/AlsuNW/GradWorkQA) в Intellij IDEA
1. Выполнить команду `docker compose up` для запуска трех docker контейнеров

   <img width="712" alt="запуск docker" src="https://github.com/AlsuNW/GradWorkQA/assets/132990479/b47e7d30-1f71-41fb-8c4d-c6fbab9d6261">

1. Запустить jar файл в отдельном окне терминала командой `java -jar aqa-shop.jar`

   <img width="672" alt="jar файл" src="https://github.com/AlsuNW/GradWorkQA/assets/132990479/9c2d1231-ef5e-46d8-a932-fcf2075658c2">

1. Запустить тесты командой `./gradlew clean test` в терминале. Можно добавить флаг `--info` для получения дополнительной информации в логах
   
   <img width="699" alt="тесты" src="https://github.com/AlsuNW/GradWorkQA/assets/132990479/ece79d81-dbc1-441e-a127-429d9b009c1b">

1. Для получения отчета выполнить команду `./gradlew allureServe` . Отчет откроется автоматически
  
   <img width="677" alt="отчет" src="https://github.com/AlsuNW/GradWorkQA/assets/132990479/56e7e759-9e97-405f-81e0-59bb8824c173">
