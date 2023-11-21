
create table public.customer (
                                 customer_id integer primary key not null default nextval('customer_customer_id_seq'::regclass),
                                 name character varying(255) not null,
                                 email character varying(255) not null,
                                 phone_number character varying(20),
                                 user_id integer,
                                 foreign key (user_id) references public.users (user_id)
                                     match simple on update no action on delete no action
);
create unique index customer_email_key on customer using btree (email);
create index idx_customer_email on customer using btree (email);



create table public.order_box (
                                  order_id integer primary key not null default nextval('order_box_order_id_seq'::regclass),
                                  order_date timestamp without time zone default CURRENT_TIMESTAMP,
                                  customer_id integer,
                                  status character varying(20),
                                  foreign key (customer_id) references public.customer (customer_id)
                                      match simple on update no action on delete no action,
                                  foreign key (customer_id) references public.customer (customer_id)
                                      match simple on update no action on delete no action
);
create index idx_order_box_customer_id on order_box using btree (customer_id);

create table public.product (
                                product_id integer primary key not null default nextval('product_product_id_seq'::regclass),
                                name character varying(255) not null,
                                description text,
                                price numeric(10,2) not null,
                                supplier_id integer,
                                image_url character varying(255) not null,
                                category character varying(50) not null,
                                available boolean not null,
                                foreign key (supplier_id) references public.supplier (supplier_id)
                                    match simple on update no action on delete no action,
                                foreign key (supplier_id) references public.supplier (supplier_id)
                                    match simple on update no action on delete no action
);
create index idx_product_name on product using btree (name);

create table public.product_order_box (
                                          product_id integer not null,
                                          order_id integer not null,
                                          primary key (product_id, order_id),
                                          foreign key (order_id) references public.order_box (order_id)
                                              match simple on update no action on delete no action,
                                          foreign key (product_id) references public.product (product_id)
                                              match simple on update no action on delete no action
);

create table public.supplier (
                                 supplier_id integer primary key not null default nextval('supplier_supplier_id_seq'::regclass),
                                 name character varying(255) not null,
                                 email character varying(255) not null,
                                 contact_person character varying(255),
                                 user_id integer,
                                 foreign key (user_id) references public.users (user_id)
                                     match simple on update no action on delete no action
);
create unique index supplier_email_key on supplier using btree (email);

create table public.token (
                              expired boolean not null,
                              id integer primary key not null default nextval('token_seq'::regclass),
                              revoked boolean not null,
                              token character varying(255),
                              token_type character varying(255),
                              user_id integer,
                              foreign key (user_id) references public.users (user_id)
                                  match simple on update no action on delete no action
);
create unique index token_token_key on token using btree (token);

create table public.users (
                              user_id integer primary key not null default nextval('user_user_id_seq'::regclass),
                              username character varying(255) not null,
                              email character varying(255) not null,
                              registration_date date,
                              phone_number character varying(20),
                              first_name character varying(50),
                              last_name character varying(50),
                              password character varying(255) not null,

                              role text
);




