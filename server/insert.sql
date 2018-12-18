use course;
insert into user (login,password,role,email) values ('admin','admin','admin','YanKuhalski@gmail.ru');
insert into user (login,password,role,email) values ('user','user','user','VanoDaun@gmail.ru');
insert into user (login,password,role,email) values ('alex','uriev','user','AlexUriev@gmail.com');
insert into user (login,password,role,email) values ('boris','tcarikov','user','BorisPsina@mail.ru');
insert into user (login,password,role,email) values ('artur','vologin','user','KonchZa500@mail.ru');
insert into user (login,password,role,email) values ('roma','zaleskiy','user','Debiltupui@tut.by');
insert into user (login,password,role,email) values ('yan','kuhalski','user','YanKuhalski@gmail.com');

insert into test (name) values ('Java - для начинающих'),('Java - средний уровень'),('Java - эксперт');
 
insert into question (test_id,text,right_answer) values
(1,' Что выведет на экран фрагмент кода?
\n int x = 12;
\n while(x < 10) {
\n	x--;
\n System.out.println(x);',1),
(1,'Какие из сдледующих методова выбрасывают исключение InterruptedException?',1),
(1,'Какой результат выполнения данного кода:
\n System.out.println( 0.3 == 0.1d + 0.1d + 0.1d );',1),
(1,'Скомпилируется ли данный код? Если да, что будет являться результатом его выполнения?
\n public class Autoboxing {
\n public static void main(String[] args) {
\n  Integer oIntl = null;
\n Integer oIntZ = 0;
\n final int intl = oIntl;
\n final int int2 = oIntZ;
\n System.out.println(int1 == intZ);',1);
 
 insert into answer(question_id,text) values 
 (1,'System.out.println не будет выполнен'),
 (1,'0'),
 (1,'10'),
 (1,'12'),
 (2,'yield'),
 (2,'wait'),
 (2,'sleep'),
 (2,'notify'),
 (3,'true'),
 (3,'false'),
 (3,'Ошибка компиляции'),
 (3,'0'),
 (4,'Ошибка компиляции'),
 (4,'Ошибка выполнения'),
 (4,'Будет напечатано "true"'),
 (4,'Будет напечатано "false"');
 
 insert into result  (user_id ,test_id,result) values 
 (2,1,12.4),
 (3,1,44.4),
 (4,1,55.4),
 (5,1,6.4),
 (6,1,70.4),
 (6,1,11.4),
 (2,2,92.4),
 (3,2,88.4),
 (4,2,25.4),
 (5,2,11.4),
 (7,2,88.4);
 



 select * from question;
  select * from answer;
   select * from test;
 select  * from user;
 select * from result;


SELECT t.name, SUM(result)/count(result) as mid_result
FROM result r 
inner join test t on r.test_id= t.id 
GROUP BY test_id;

 select  * from interview;