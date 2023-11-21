

-- Вставка данных в таблицу "user"
INSERT INTO "users" (username, email, registration_date, phone_number, first_name, last_name, password, group_id) VALUES
                                                                                                                      ('user1', 'user1@example.com', '2023-01-01', '123-456-7890', 'John', 'Doe', 'password123', 1),
                                                                                                                      ('user2', 'user2@example.com', '2023-01-02', '987-654-3210', 'Jane', 'Smith', 'pass456', 2);

INSERT INTO public.users (username, email, registration_date, phone_number, first_name, last_name, password, group_id, role) VALUES
                                                                                                                                 ('admin1', 'admin1@example.com', '2023-01-01', '+123456789', 'Admin', 'One', 'admin_password', 1, 'admin'),
                                                                                                                                 ('customer1', 'customer1@example.com', '2023-01-02', '+987654321', 'John', 'Doe', 'customer_password', 2, 'customer'),
                                                                                                                                 ('supplier1', 'supplier1@example.com', '2023-01-03', '+111222333', 'Supplier', 'One', 'supplier_password', 3, 'supplier');

-- Sample data for public.customer table
INSERT INTO public.customer (name, email, phone_number, user_id) VALUES
                                                                     ('Customer One', 'customer1@example.com', '+987654321', 2),
                                                                     ('Customer Two', 'customer2@example.com', '+555666777', 4);  -- No associated user for this customer

-- Sample data for public.supplier table
INSERT INTO public.supplier (name, email, contact_person, user_id) VALUES
                                                                       ('Supplier One', 'supplier1@example.com', 1, 4),
                                                                       ('Supplier Two', 'supplier2@example.com', 2, 2);  -- No associated user for this supplier

-- Sample data for public.order_box table
INSERT INTO public.order_box (order_date, customer_id, status) VALUES
                                                                   ('2023-01-10 12:00:00', 1, 'Pending'),
                                                                   ('2023-01-15 14:30:00', 1, 'Shipped'),
                                                                   ('2023-01-20 10:00:00', 2, 'Delivered');

-- Sample data for public.product table
INSERT INTO public.product (name, description, price, supplier_id, image_url, category, available) VALUES
                                                                                                       ('Product One', 'Description One', 19.99, 1, 'product1.jpg', 1, true),
                                                                                                       ('Product Two', 'Description Two', 29.99, 1, 'product2.jpg', 2, true),
                                                                                                       ('Product Three', 'Description Three', 39.99, 2, 'product3.jpg', 3, true);  -- Not available

-- Commit the transactions
COMMIT;   