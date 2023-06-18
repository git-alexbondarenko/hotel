package com.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelApplication {

    /*Адміністратор готелю.
    Список номерів: клас, кількість місць. Список гостей: паспортні дані, дати приїзду та
    від’їзду, номер.
    • Поселення гостей: вибір відповідного номера (за наявності вільних місць), реєстрація,
    оформлення квитанції.
    • Від’їзд: вибір всіх гостей, які від’їжджають сьогодні, звільнення місця або оформлення
    затримки з випискою додаткової квитанції. Можливість дострокового від’їзду з
    перерахунком.
    • Пошук гостя за довільною ознакою.*/

    public static void main(String[] args) {
        SpringApplication.run(HotelApplication.class, args);

    }



}
