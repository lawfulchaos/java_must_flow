Отчет 26.03.2019
Что есть:
1.	Генерация мира World Generator (допилит Радик, Маша на подсосе), отрисовка
1.1.	не трогать метод build
1.2.	должен получиться массив тайлов
1.3.	играешь пока не умрешь
2.	Java must flow можно делать тикеты (карточки с выбором заданий)
3.	из tutorial’а выпилить некоторые важные
4.	пошаговая организация мира (для реализации атаки)
4.1.	можно сделать отрисовку выше прорисовки шага
4.2.	*лестница и многоуровневая карта (закидывает на новый уровень)*
4.3.	*ограничение скорости*
5.	Отображение поворота персонажа (исполнение Creator)
6.	Пилить fog (туман войны/поле видимости)

Чего нет:
1.	Логики мобов (creator) (мобы не атакают) АИ (отвечает за это Евгений который не поляковский)
1.1.	Мобы наследуют от игрока всякую дичь (опыт, уровень)
1.2.	в радиусе агро должны двигаться на игрока
1.3.	прикрутить им вещи, чтобы они могли ими пользоваться
1.4.	разная механика разных мобов (заморозка, стан, яд и прочее)
1.5.	рандомить количество мобов на карте
1.6.	здорово было бы прописать отдельные классы каждому из мобов (кто-то хилит, кто –то дамажит)
2.	Логика лута 
2.1.	Аптечка или зелья здоровья
2.2.	Инвентарь в отдельном экране (screen), настроить взаимодействие с экранами
3.	Боевая система, сложность (играешь пока не сдохнешь)
3.1.	Завести уровни сложности, коэффициенты(множители) сложностей
4.	Нет оружия, можно попробовать завести классы (маги, воины, дальнобойная)
5.	Сам игрок (creator)  - Женек поляковский ???
*как моб, с прикрученным управлением*
5.1.	При каждом убийстве опыт растет 
5.2.	Завести характеристики
5.3.	*скиллы способности*
5.4.	Хочется сделать игрока отдельным классом
6.	Худ/статус бар (полосочки со здоровьем )
6.1.	Журнал боя (кто кого пырнул)
6.2.	Отодвинуть консоль и прорисововать массив по координатам
