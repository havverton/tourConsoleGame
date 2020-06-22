package com.havverton;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean repeat = true;   //проверка на цикл программы

        while (repeat) {
            Scanner sc = new Scanner(System.in);        //вводим подтверждение
            System.out.print("Создать новый турнир?\nОтвет: ");
            int answer = sc.nextInt();
            if (answer == 1) {
                ArrayList heroesArray = createHeroList();
                startTurik(heroesArray); //запускаем турнир

            } else {
                repeat = false;  //выходим если турнир не нужен
            }
        }
    }

    //создание списка персонажей
    public static ArrayList createHeroList () {
        ArrayList<Hero> heroesArray = new ArrayList<>(); //создаем список участников
        System.out.println("Наши герои: ");
        //выводим список всех участников
        for (int i = 1; i <= 16; i++) {
            heroesArray.add(Hero.createRandomHero()); // добавляем персонажей в массив
           // showHero(i,heroesArray);//выводим список героев
            Hero.showHeroInfo(heroesArray.get(i-1));
        }
        return heroesArray;
    }

    //основной метод старта турнира
    public static Hero startTurik(ArrayList heroesArray){
        int numberOfHeroes; //количество победителей

        Hero winner = null; //создаем пустой объект героя

        ArrayList<Hero> winners = new ArrayList<>(); //создаем список победителей
        ArrayList<Hero> battlers = heroesArray;  //создаём список участников и заполняем его

        // цикл раундов
        for (int a = 0; a<4; a++) {

            //проверка на первую инициализацию
            numberOfHeroes = battlers.size(); //считываем количество участников
                System.out.println("Число участников: " + numberOfHeroes); //выводим это количество
                //цикл битв внутри раунда
                winners.clear();           //очищаем список победителей
                winners = startCombat(battlers, numberOfHeroes);
                battlers.clear();  //очищаем список участников прошлого раунда
                battlers.addAll(winners);  //вносим в список победителей раунда

            //проверка на финал
            if (numberOfHeroes == 2) {
                System.out.println("НАШ ПОБЕДИТЕЛЬ! " + battlers.get(0).getName());
                winner=battlers.get(0);
            }
        }
            return winner;
    }

    public static ArrayList<Hero> startCombat(ArrayList <Hero> battlers, int numberOfHeroes){
        ArrayList<Hero> winners = new ArrayList<>(); //создаем список победителей
        int j = 0; //обнуление списка героев
        //начинаем цикл боёвок по раундам
        for (int i = 1; i <= (numberOfHeroes/2); i++) {
            //выводим каждый бой
            System.out.println("Бой №"+ (i + 1));
            // выводим соперников в консоль ( надо бы убрать в отдельный метод)
            System.out.println(battlers.get(j).getName() + " (Health: " + battlers.get(j).getHealth() +
                    ") VS " + battlers.get(j + 1).getName() + " (Health: " + battlers.get(j + 1).getHealth() + ")");
            Hero winner = heroesCombat(battlers.get(j), battlers.get(j + 1));// определяем победителя
            winners.add(winner); //добавляем победителя в список
            j += 2;
        }
        return winners; //возвращаем список победителей
    }

    //метод для схватки героев
    public static Hero heroesCombat(Hero hero1, Hero hero2){
        Hero winner = null; //пустой победитель
        int heroHealth_1= hero1.getHealth();
        int heroHealth_2= hero2.getHealth();
        int punch; //урон атаки

        //запускаем цикл сражения
        while (heroHealth_1 >= 0 && heroHealth_2 >= 0){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            punch = punchDamage(hero1); // определяем урон и крит первого
            System.out.println("Игрок №1 (" + hero1.getName() + ") атакует на " + punch + " урона. Здоровье героя №2 : " + (heroHealth_2 -= punch ));
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) { }

            punch = punchDamage(hero2);// определяем урон и крит второго
            System.out.println("Игрок №2 (" + hero2.getName() + ") атакует на " + punch  + " урона. Здоровье героя №1: " + (heroHealth_1 -= punch ));
        }

        if(heroHealth_1 > 0 && heroHealth_2 <= 0){
            System.out.println("Победил первый боец: " + hero1.getName());
            return hero1;
        }else if (heroHealth_2 > 0 && heroHealth_1 <= 0){
            System.out.println("Победил второй боец: " + hero2.getName());
            return hero2;
        }else {
            System.out.println("Кинем монетку...");
            int a = (int)Math.random()*2;
            switch (a) {
                case 0:
                    System.out.println("Победил первый боец: " + hero1.getName());
                    return hero1;

                case 1:
                    System.out.println("Победил второй боец: " + hero2.getName());
                    return hero2;


                default:
                    System.out.println("Накосячил с рандомом");
                    break;
            }
        } return null;

    }
    //рассчитываем урон удара
    public static int punchDamage(Hero hero){
        int punch;
        boolean isCrit;
        int isCritChance;
        punch = (int)(Math.random()*3 +(hero.getPower()));
        isCritChance= (int)(Math.random()*10+1); // 10% шанс крита
        isCrit = (isCritChance == 10 ? false : true);
            if(!isCrit){
                System.out.println("КРИТ!!");
                punch*=2; //крит это увсиление удара на 2
            }
        return punch;
    }

}

//создаем класс героя
class Hero {
    private String name;
    private int power;
    private int health;
    private String weapon;

    //конструктор создания персонажа
    public Hero (){
        this.name = "default Ivan";
        this.power = 1;
        this.health= 100;
        this.weapon = "cock";
    }

    public Hero(String name,int health){
        this.name = name;
        this.health=health;
    }
    //геттеры
     public String getName(){
        return name;
    }
    public int getPower(){
        return power;
    }
    public int getHealth(){
        return health;
    }
    public String getWeapon(){
        return weapon;
    }

    //Сеттеры
    public String setName(String name){
        return name;
    }
    public String setAnotherName(String name){
        String nameOfHero = Hero.randomName(false,name);
        return nameOfHero;
    }
    public  int setPower(int power){
        this.power=power;
        return power;
    }
    public String setWeapon(String weapon){
        this.weapon=weapon;
        return weapon;
    }

    //генерация случайного персонажа (не знаю почему я реализовал именно так, можно было через конструктор. Видимо для разнообразия
    public static Hero createRandomHero (){
        Hero newHero = new Hero(Hero.randomName(true, null),randomHealth());
        newHero.giveWeapon(newHero);
        return newHero;
    }

    //информация о герое
    public static void showHeroInfo(Hero hero){
        System.out.println("Имя: " + hero.getName() + "; Weapon: " + hero.getWeapon() + "; Power: "+ hero.getPower()); // выводим имена
    }

    //метод создания случайного имени с учетом смены при повторном имени предыдущего участника
    public static String randomName(boolean random, String existName){
        String[] heroNames = {"Oleg", "Anton", "Starykh", "Olya", "Inga","Leha","Alex","Petya"};
        String name = heroNames[(int) (Math.random() * 8)];
        /*if (!random) {
            while (name == existName) {
               name = heroNames[(int) (Math.random() * 5)];
            }
        }*/
        return name;
    }

    //выдача случайного оружия из списка
    public void giveWeapon(Hero hero){
        String [][] weaponList = Weapon.createWeaponList(); //создаём массив с оружиями
        String[] heroWeapon = Weapon.chooseWeapon(weaponList); //выбираем случайный элемент из массива
        hero.setWeapon(heroWeapon[0]); //выдаем оружие герою
        int power = Integer.parseInt(heroWeapon[1]); // переводим строковое значение из массивы в численное
        hero.setPower(power); // записываем мощь героя

    }
    //случайно определяем хп героев
    public static int randomHealth(){
        int health= (int) (Math.random()*20+20) ;
        return health;
    }
}
//создаём класс оружий
class Weapon{
    //protected String name;
    protected String type;
    protected  int power;
    //protected int durability;


        public static String[][] createWeaponList(){
            String weaponList[][] = new String[7][2];

            for(int i=0; i < 7; i++){
                    int j =0;
                    String [] heroWeapons = {"Sword", "Axe", "Bow", "Spear", "Fists","Knife","Stuff"};
                    String [] heroWeaponsPower = {"5", "7", "4", "8", "2","3","7"};
                    weaponList[i][j] = heroWeapons[i];
                    weaponList[i][j+1]= heroWeaponsPower[i];
                }
            return weaponList;
        }

        public static String[] chooseWeapon(String[][] weaponList){
            String[] weapon = new String[2];
            int a = (int) (Math.random()*7);
            weapon[0] = weaponList[a][0];
            weapon[1] = weaponList[a][1];
            return weapon;
        }


       /* public static String randomWeapon(){
            String [] heroWeapons = {"Sword", "Axe", "Bow", "Spear", "Fists","Knife","Stuff"};
            String weapon= heroWeapons[(int)(Math.random()*6)];
            return weapon;
        }*/

    Weapon(){
       // this.name="default";
        this.type="default";
        this.power=1;
       // this.durability=1;
    }

    Weapon(String type, int power){
      //  this.name="default";
        this.type=type;
        this.power=power;
       // this.durability=1;
    }

    /*public String setName(){
        return name;
    }
    public String getName(String name){
        return name;
    }*/
    public String setType(){
        return type;
    }
    public String getType(String name){
        return type;
    }
    public int setPower(){
        return power;
    }
    public int getPower(String name){
        return power;
    }

/*
    public static Weapon getRandomWeapon(){
        Weapon weapon = new Weapon();
        return weapon;
    }
*/

}
