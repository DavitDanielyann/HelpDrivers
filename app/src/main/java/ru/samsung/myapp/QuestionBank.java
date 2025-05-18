package ru.samsung.myapp;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    public static List<Question> getQuestions(String category) {
        List<Question> questions = new ArrayList<>();

        if (category.equals("armenia")) {
            questions.add(new Question("What is the capital of Armenia?", "Yerevan", "Gyumri", "Vanadzor", "Ejmiatsin", "Yerevan"));
            questions.add(new Question("Which mountain is a symbol of Armenia?", "Mount Ararat", "Mount Aragats", "Mount Tatev", "Mount Hatis", "Mount Ararat"));
            questions.add(new Question("What is the official language of Armenia?", "Russian", "English", "Armenian", "Persian", "Armenian"));
            questions.add(new Question("Which sea is closest to Armenia?", "Black Sea", "Caspian Sea", "Mediterranean Sea", "Aral Sea", "Black Sea"));
            questions.add(new Question("What is the name of the Armenian alphabet creator?", "Mashtots", "Tigran", "Arshak", "Narekatsi", "Mashtots"));
            questions.add(new Question("What currency is used in Armenia?", "Dram", "Lira", "Ruble", "Euro", "Dram"));
            questions.add(new Question("What religion is dominant in Armenia?", "Christianity", "Islam", "Judaism", "Buddhism", "Christianity"));
            questions.add(new Question("When did Armenia become independent from the USSR?", "1991", "1989", "1993", "1990", "1991"));
            questions.add(new Question("Which city is Armenia's second largest?", "Gyumri", "Vanadzor", "Ejmiatsin", "Hrazdan", "Gyumri"));
            questions.add(new Question("What is the name of Armenia's national church?", "Apostolic Church", "Catholic Church", "Orthodox Church", "Evangelical Church", "Apostolic Church"));
            questions.add(new Question("What lake is found in Armenia?", "Sevan", "Van", "Issyk-Kul", "Baikal", "Sevan"));
            questions.add(new Question("Which ancient kingdom was located in Armenia?", "Urartu", "Persia", "Byzantium", "Rome", "Urartu"));
            questions.add(new Question("Which country borders Armenia to the south?", "Iran", "Turkey", "Georgia", "Azerbaijan", "Iran"));
            questions.add(new Question("What is the name of the traditional Armenian flatbread?", "Lavash", "Pita", "Naan", "Focaccia", "Lavash"));
            questions.add(new Question("Which color is NOT in the Armenian flag?", "Green", "Red", "Blue", "Orange", "Green"));
            questions.add(new Question("What is the official name of Armenia?", "Republic of Armenia", "Armenian State", "Armenia Federation", "Greater Armenia", "Republic of Armenia"));
            questions.add(new Question("Where is the Mother Armenia statue located?", "Yerevan", "Gyumri", "Vanadzor", "Ejmiatsin", "Yerevan"));
            questions.add(new Question("Which alphabet is used in Armenian writing?", "Armenian", "Cyrillic", "Latin", "Arabic", "Armenian"));
            questions.add(new Question("What is the Armenian Genocide Remembrance Day?", "April 24", "May 1", "March 15", "September 21", "April 24"));
            questions.add(new Question("Which region is a disputed area between Armenia and Azerbaijan?", "Nagorno-Karabakh", "Tavush", "Shirak", "Lori", "Nagorno-Karabakh"));

        } else if (category.equals("programming")) {
            questions.add(new Question("What does HTML stand for?", "Hyper Trainer Marking Language", "Hyper Text Markup Language", "Hyper Text Marketing Language", "Hyper Tool Multi Language", "Hyper Text Markup Language"));
            questions.add(new Question("Which language is mainly used for Android development?", "Python", "Java", "Swift", "C#", "Java"));
            questions.add(new Question("What does CSS stand for?", "Cascading Style Sheets", "Creative Style Sheet", "Colorful Style Sheets", "Computer Style Sheets", "Cascading Style Sheets"));
            questions.add(new Question("Which keyword is used to define a function in Java?", "def", "func", "void", "function", "void"));
            questions.add(new Question("Which company developed Java?", "Sun Microsystems", "Microsoft", "Apple", "IBM", "Sun Microsystems"));
            questions.add(new Question("Which language is used for iOS app development?", "Swift", "Java", "Kotlin", "C++", "Swift"));
            questions.add(new Question("What does OOP stand for?", "Object-Oriented Programming", "Original Open Programming", "Object Operational Process", "Open Object Programming", "Object-Oriented Programming"));
            questions.add(new Question("Which of the following is a Java framework?", "Spring", "Django", "Laravel", "Rails", "Spring"));
            questions.add(new Question("Which symbol is used for single-line comments in Java?", "//", "#", "--", "/*", "//"));
            questions.add(new Question("What is an IDE?", "Integrated Development Environment", "Internal Data Entity", "Integrated Debugging Engine", "Interactive Data Engine", "Integrated Development Environment"));
            questions.add(new Question("Which data type is used to store decimal numbers in Java?", "int", "float", "char", "boolean", "float"));
            questions.add(new Question("What is the extension of Java files?", ".js", ".class", ".java", ".jv", ".java"));
            questions.add(new Question("Which keyword is used to create an object in Java?", "object", "instance", "new", "class", "new"));
            questions.add(new Question("What does JVM stand for?", "Java Virtual Machine", "Java Verified Module", "Java Virtual Method", "Joint Virtual Machine", "Java Virtual Machine"));
            questions.add(new Question("What is the superclass of all classes in Java?", "Object", "Main", "Class", "Super", "Object"));
            questions.add(new Question("Which operator is used to compare values in Java?", "==", "=", ">", "!=", "=="));
            questions.add(new Question("Which loop checks condition after execution?", "do-while", "for", "while", "repeat", "do-while"));
            questions.add(new Question("Which Java keyword is used to inherit a class?", "extends", "inherits", "super", "this", "extends"));
            questions.add(new Question("Which method is the entry point of a Java application?", "main", "start", "run", "init", "main"));
            questions.add(new Question("Which of the following is not a primitive type?", "String", "int", "float", "char", "String"));

        } else if (category.equals("general")) {
            questions.add(new Question("Which planet is known as the Red Planet?", "Earth", "Mars", "Jupiter", "Venus", "Mars"));
            questions.add(new Question("Who painted the Mona Lisa?", "Van Gogh", "Picasso", "Da Vinci", "Michelangelo", "Da Vinci"));
            questions.add(new Question("Which is the longest river in the world?", "Amazon", "Nile", "Yangtze", "Mississippi", "Nile"));
            questions.add(new Question("Which element has the chemical symbol O?", "Gold", "Oxygen", "Silver", "Iron", "Oxygen"));
            questions.add(new Question("Who discovered gravity?", "Newton", "Einstein", "Galileo", "Tesla", "Newton"));
            questions.add(new Question("What is the capital of France?", "Paris", "Berlin", "Rome", "Madrid", "Paris"));
            questions.add(new Question("What is H2O?", "Water", "Oxygen", "Hydrogen", "Salt", "Water"));
            questions.add(new Question("How many continents are there?", "5", "6", "7", "8", "7"));
            questions.add(new Question("Which gas do plants absorb?", "Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen", "Carbon Dioxide"));
            questions.add(new Question("Which animal is known as the King of the Jungle?", "Tiger", "Elephant", "Lion", "Bear", "Lion"));
            questions.add(new Question("How many legs does a spider have?", "6", "8", "10", "12", "8"));
            questions.add(new Question("Which planet is closest to the Sun?", "Venus", "Mercury", "Earth", "Mars", "Mercury"));
            questions.add(new Question("What is the boiling point of water?", "100°C", "90°C", "120°C", "80°C", "100°C"));
            questions.add(new Question("Which organ pumps blood in the human body?", "Lungs", "Liver", "Heart", "Kidneys", "Heart"));
            questions.add(new Question("Which instrument has keys, pedals, and strings?", "Piano", "Guitar", "Violin", "Drum", "Piano"));
            questions.add(new Question("Which ocean is the largest?", "Atlantic", "Indian", "Pacific", "Arctic", "Pacific"));
            questions.add(new Question("What is the square root of 81?", "7", "8", "9", "10", "9"));
            questions.add(new Question("How many colors are in a rainbow?", "5", "6", "7", "8", "7"));
            questions.add(new Question("Which metal is liquid at room temperature?", "Mercury", "Iron", "Aluminum", "Copper", "Mercury"));
            questions.add(new Question("What is the largest mammal?", "Elephant", "Blue Whale", "Shark", "Giraffe", "Blue Whale"));
        }

        return questions;
    }
}