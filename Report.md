# Отчет
**Краткое описание:** Была проведена работа по тестированию формы оплаты туров. Все поля формы пригодны к заполнению.
* При нажатии кнопок “Купить” или “Купить в кредит” корректно открывается либо одна форма, либо другая.
* Поле “Владелец” можно заполнить цифрами или специальными символами, что является дефектом, и из-за этого возникает опасность SQL injections.
* При вводе в остальные поля формы невалидных значений форма корректно не дает ввести невалидные данные.
* Также при заполнении формы рандомными валидными данными форма всегда выдает сообщение об ошибке. При заполнениии формы валидными данными и номером карты, соответствующим успеху по условию задачи, форма корректно выдает сообщение об успехе. При заполнении формы валидными данными и номером карты, соответствующим ошибке по условию задачи, форма так же выдает сообщение об успехе, что является некорректной работы формы.
* Форма корректно справляется с датами в прошлом, выдавая предупреждающее сообщение внизу поля формы.
* При отправке пустой формы корректно выдается сообщение об ошибке.
* При вводе нулевых значений форма выводит сообщение об ошибке, что является дефектом.
* Сообщение, появляющееся после нажатия кнопки “Продолжить” корректно закрывается с помощью крестика.
* Формы оплаты и кредита корректно заносят данные о статусе операции в базы данных после нажатия кнопки “Продолжить”.

**Количество тест-кейсов:** 33 тест-кейса (17 тестов проходят, 12 не проходят)

**Процент успешных и неуспешных тест-кейсов:** 64% и 36% соответственно

**Общие рекомендации:**
* Необходимо, чтобы форма выдавала сообщение об успехе в случае корректного заполнения формы валидными данными и выдавала сообщение об ошибке в случае некорректного заполнения.
* Необходимо исправить ввод значений в поле формы “Владелец”, чтобы избежать взлома формы и некорректного введения информации.
* Необходимо сделать так, чтобы форма принимала нулевые значения, как корректные, так как не исключена ситуация, что реальные значения могут быть нулевыми.

<img width="1252" alt="скриншот allure" src="https://github.com/AlsuNW/GradWorkQA/assets/132990479/9450b516-eb96-44e1-9934-f4afc902ecf2">
