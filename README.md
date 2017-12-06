* Реализовать сервис, который будет генерировать точку каждую секунду
public class Point implements Serializable {
public DateTime date;
public Double rate;
}
Дата каждой новой точки на 1 сек больше предыдущей
Значение каждой новой точки рассчитывается как сумма предыдущего значения и
случайного дробного числа от -1 до 1
* Реализовать добавление сгенерированной точки на график SciChart
* Реализовать рисование горизонтальной линии на графике. По оси X линия рисуется
по всей длине графика, по оси Y на уровне значения последней полученной точки
===
Дополнительное задание:
* Продумать архитектуру приложения таким образом, чтобы было легко добавить
сервис, который будет получать точки из интернета, а не генерировать случайным
образом. Сервисы должны быть легко взаимозаменяемы.
