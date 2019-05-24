/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/5/24 14:56:10                           */
/*==============================================================*/

drop table if exists Channel_Accsss;

drop table if exists Person_Access;

drop table if exists Access;

drop table if exists Article;

drop table if exists Channel;

drop table if exists Person;


/*==============================================================*/
/* Table: Access                                                */
/*==============================================================*/
create table Access
(
   access_id            int not null auto_increment,
   access_name          varchar(1024),
	 access_decs          varchar(1024),
   primary key (access_id)
);

insert into Access (access_name,access_decs) values ('人员管理','可以修改人员的Access属性');
insert into Access (access_name,access_decs) values ('添加文章','可以添加文章');
insert into Access (access_name,access_decs) values ('编辑文章','可以编辑已存在的文章');
insert into Access (access_name,access_decs) values ('删除文章','可以删除已存在的文章');
insert into Access (access_name,access_decs) values ('浏览文章','可以浏览文章内容');

/*==============================================================*/
/* Table: Article                                               */
/*==============================================================*/
create table Article
(
   article_id           int not null auto_increment,
   person_id            int not null,
   article_title        varchar(1024),
   article_editor       varchar(1024),
   article_content      varchar(2048),
   primary key (article_id)
);

/*==============================================================*/
/* Table: Channel                                               */
/*==============================================================*/
create table Channel
(
   Channel_id           int not null auto_increment,
   Channel_title        varchar(1024),
   Channel_desc         varchar(1024),
   primary key (Channel_id)
);

/*==============================================================*/
/* Table: Channel_Article                                        */
/*==============================================================*/
create table Channel_Article
(
   Channel_id           int not null auto_increment,
   article_id           int not null,
   primary key (Channel_id, article_id)
);

/*==============================================================*/
/* Table: Person                                                */
/*==============================================================*/
create table Person
(
   person_id            int not null auto_increment,
   person_name          varchar(1024),
   person_pwd           varchar(1024),
   role_name            varchar(1024),
   primary key (person_id)
);

/*==============================================================*/
/* Table: Person_Access                                         */
/*==============================================================*/
create table Person_Access
(
   person_id            int not null auto_increment,
   access_id            int not null,
   primary key (person_id, access_id)
);

alter table Article add constraint FK_systemeditor_article foreign key (person_id)
      references Person (person_id) on delete restrict on update restrict;

alter table Channel_Article add constraint FK_Channel_Article foreign key (Channel_id)
      references Channel (Channel_id) on delete restrict on update restrict;

alter table Channel_Article add constraint FK_Channel_Article2 foreign key (article_id)
      references Article (article_id) on delete restrict on update restrict;

alter table Person_Access add constraint FK_Person_Access foreign key (person_id)
      references Person (person_id) on delete restrict on update restrict;

alter table Person_Access add constraint FK_Person_Access2 foreign key (access_id)
      references Access (access_id) on delete restrict on update restrict;

