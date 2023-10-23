create table categories (
    id int auto_increment not null,
    name varchar(50) unique,
    primary key (id)
);

create table projects (
  id int auto_increment not null,
  name varchar(50) not null,
  category_id int not null,
  description TEXT not null,
  deadline date not null,
  primary key (id),
  foreign key (category_id) references categories(id)
);

create table students (
  id int auto_increment not null,
  name varchar(50) not null,
  primary key (id)
);

create table students_projects (
   id int auto_increment not null,
   student_id int not null,
   project_id int not null,
   primary key (id),
   foreign key (student_id) references students(id),
   foreign key (project_id) references projects(id)
);

create table assigned_projects (
   id int auto_increment not null,
   student_id int not null,
   project_id int not null,
   primary key (id),
   unique (student_id, project_id),
   foreign key (student_id) references students(id),
   foreign key (project_id) references projects(id)
);

insert into categories(name) values('Academic');
insert into categories(name) values('Extracurricular');
insert into categories(name) values('Personal');

insert into projects(name, category_id, description, deadline) values('Biology Research Paper', 1, 'Conduct a research study on a specific aspect of biology and write a comprehensive research paper. This project involves literature review, data collection, analysis, and presenting your findings.', '2024-01-01');
insert into projects(name, category_id, description, deadline) values('Physics Experiment Presentation', 1, 'Design and perform a physics experiment, then create a presentation to explain the experiment, methodology, and results to your class. This project emphasizes communication and scientific understanding.', '2024-10-20');
insert into projects(name, category_id, description, deadline) values('Environmental Club Cleanup Campaign', 2, 'Organize and lead a community cleanup event as part of the Environmental Club. This project involves planning logistics, engaging volunteers, and contributing to environmental conservation efforts.', '2025-02-10');
insert into projects(name, category_id, description, deadline) values('Charity Fundraising Event', 2, 'Coordinate a charity fundraising event, including selecting a cause, planning activities, and raising funds. This project combines leadership, event management, and social responsibility.', '2025-05-22');
insert into projects(name, category_id, description, deadline) values('Learning a New Language', 3, 'Commit to learning a new language, with a focus on reaching a conversational level of fluency. This project involves daily practice, using language learning resources, and tracking progress.', '2026-01-10');

insert into students(name) values('Sarah');
insert into students(name) values('Michael');
insert into students(name) values('Emily');

insert into students_projects(student_id, project_id) values(1, 1);
insert into students_projects(student_id, project_id) values(2, 1);
insert into students_projects(student_id, project_id) values(2, 4);
insert into students_projects(student_id, project_id) values(3, 1);
insert into students_projects(student_id, project_id) values(3, 4);
insert into students_projects(student_id, project_id) values(3, 5);