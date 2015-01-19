
    alter table deadline 
        drop 
        foreign key FK1E04DA5877048842;

    alter table deadline 
        drop 
        foreign key FK1E04DA58BB71EB0D;

    alter table deadline 
        drop 
        foreign key FK1E04DA584FD56926;

    alter table invoice 
        drop 
        foreign key FK74D6432DBB71EB0D;

    alter table payment 
        drop 
        foreign key FKD11C3206BB71EB0D;

    alter table payment 
        drop 
        foreign key FKD11C32069B33567F;

    alter table payment_packageCourses 
        drop 
        foreign key FKA80D7BEB77048842;

    alter table payment_packageCourses 
        drop 
        foreign key FKA80D7BEB4FD56926;

    alter table subscription_packageCourses 
        drop 
        foreign key FKC67B63F477048842;

    alter table subscription_packageCourses 
        drop 
        foreign key FKC67B63F4BB71EB0D;

    drop table if exists deadline;

    drop table if exists invoice;

    drop table if exists packageCourses;

    drop table if exists payment;

    drop table if exists payment_packageCourses;

    drop table if exists subscription;

    drop table if exists subscription_packageCourses;

    drop table if exists user;

    create table deadline (
        id bigint not null auto_increment,
        active char(1) NOT NULL DEFAULT 'T' not null,
        deadlineDate datetime,
        packageCoursesId bigint,
        paymentId bigint,
        subscriptionId bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table invoice (
        id bigint not null auto_increment,
        creationDate datetime not null,
        lastPrintDate datetime,
        subscriptionId bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table packageCourses (
        id bigint not null auto_increment,
        amount bigint not null,
        description varchar(500),
        expirationType varchar(50) not null,
        name varchar(255) not null,
        singleLesson char(1) NOT NULL DEFAULT 'F' not null,
        subscriptionPlain char(1) NOT NULL DEFAULT 'F' not null,
        teacherPercentage double precision not null,
        primary key (id),
        unique (name),
        unique (id)
    ) ENGINE=InnoDB;

    create table payment (
        id bigint not null auto_increment,
        amountPaied double precision not null,
        paymentDate datetime not null,
        percentageDiscount double precision not null,
        invoiceId bigint,
        subscriptionId bigint,
        primary key (id)
    ) ENGINE=InnoDB;

    create table payment_packageCourses (
        paymentId bigint not null,
        packageCoursesId bigint not null
    ) ENGINE=InnoDB;

    create table subscription (
        id bigint not null auto_increment,
        active char(1) not null,
        address varchar(255) not null,
        birthdayDate datetime not null,
        cellNumber varchar(255),
        email varchar(255),
        fiscalCode varchar(255),
        medicalCertificateDate datetime,
        name varchar(255) not null,
        phoneNumber varchar(255),
        placeOfBirth varchar(255),
        registrationDate datetime not null,
        surname varchar(255) not null,
        primary key (id),
        unique (fiscalCode),
        unique (id)
    ) ENGINE=InnoDB;

    create table subscription_packageCourses (
        subscriptionId bigint not null,
        packageCoursesId bigint not null
    ) ENGINE=InnoDB;

    create table user (
        id bigint not null auto_increment,
        name varchar(255) not null,
        password longtext not null,
        surname varchar(255) not null,
        username varchar(255) not null,
        primary key (id),
        unique (username),
        unique (id)
    ) ENGINE=InnoDB;

    alter table deadline 
        add index FK1E04DA5877048842 (packageCoursesId), 
        add constraint FK1E04DA5877048842 
        foreign key (packageCoursesId) 
        references packageCourses (id);

    alter table deadline 
        add index FK1E04DA58BB71EB0D (subscriptionId), 
        add constraint FK1E04DA58BB71EB0D 
        foreign key (subscriptionId) 
        references subscription (id);

    alter table deadline 
        add index FK1E04DA584FD56926 (paymentId), 
        add constraint FK1E04DA584FD56926 
        foreign key (paymentId) 
        references payment (id);

    alter table invoice 
        add index FK74D6432DBB71EB0D (subscriptionId), 
        add constraint FK74D6432DBB71EB0D 
        foreign key (subscriptionId) 
        references subscription (id);

    alter table payment 
        add index FKD11C3206BB71EB0D (subscriptionId), 
        add constraint FKD11C3206BB71EB0D 
        foreign key (subscriptionId) 
        references subscription (id);

    alter table payment 
        add index FKD11C32069B33567F (invoiceId), 
        add constraint FKD11C32069B33567F 
        foreign key (invoiceId) 
        references invoice (id);

    alter table payment_packageCourses 
        add index FKA80D7BEB77048842 (packageCoursesId), 
        add constraint FKA80D7BEB77048842 
        foreign key (packageCoursesId) 
        references packageCourses (id);

    alter table payment_packageCourses 
        add index FKA80D7BEB4FD56926 (paymentId), 
        add constraint FKA80D7BEB4FD56926 
        foreign key (paymentId) 
        references payment (id);

    alter table subscription_packageCourses 
        add index FKC67B63F477048842 (packageCoursesId), 
        add constraint FKC67B63F477048842 
        foreign key (packageCoursesId) 
        references packageCourses (id);

    alter table subscription_packageCourses 
        add index FKC67B63F4BB71EB0D (subscriptionId), 
        add constraint FKC67B63F4BB71EB0D 
        foreign key (subscriptionId) 
        references subscription (id);
