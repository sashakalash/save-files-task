Описание

В данной задаче Вы потренируетесь сериализовывать Java класс, используя интерфейс Serializable, записывать сериализованные файлы на жесткий диск, используя класс FileOutputStream, и упаковывать их в архив с помощью ZipOutputStream.

Для дальнейшей работы потребуется создать класс GameProgress, хранящий информацию об игровом процессе. 

Для выполнения задания потребуется проделать следующие шаги:

Создать три экземпляра класса GameProgress.
Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи.
Созданные файлы сохранений из папки savegames запаковать в архив zip.
Удалить файлы сохранений, лежащие вне архива.


Загрузка
В данной задаче необходимо произвести обратную операцию по разархивации архива и десериализации файла сохраненной игры в Java класс.

Таким образом, для выполнения задания потребуется проделать следующие шаги:

Произвести распаковку архива в папке savegames.
Произвести считывание и десериализацию одного из разархивированных файлов save.dat.
Вывести в консоль состояние сохранненой игры.
