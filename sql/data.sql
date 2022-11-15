USE smack; 

INSERT INTO user(
  displayname,
  user_email
) VALUES 
  ('Daria Morgendorffer', 'test3@test.com'),
  ('Johan Wic', 'test1@test.com'),
  ('Beavis Buthead', 'test4@test.com'), 
  ('Jane Lane', 'test2@test.com');

INSERT INTO auth(
  auth_id,
  auth_email,
  auth_password
) VALUES
  ('c7786fad2a704f7c9060d38427d94076', 'test1@test.com','$2a$12$j8m7nxrz8/0VKorfBgZuOO/hl08aSF8ZYe0mRnl7fZRxUrKuGsqjy'),
  ('febc66f9aa6743588c2f60cbf9bd3ccf', 'test2@test.com','$2a$12$3mV.mkNdgAy5/0hqNYFMP.RmonmJmUpIrvy5jQwfVdPbYySmAw6va'),
  ('2987f2d3427046f98cd0fa757a6310b1', 'test3@test.com','$2a$12$9Hk3GWeo00hDhfWO.Sel2eL2cajxFM0CYXCA4KgCSt50PjzjP/mwW'),
  ('b9c96b66211e4bb0964543866c473cf7', 'test4@test.com', '$2a$12$Bzsa9noeLMmia8NYmM2tE.QeBrn31wpxEhIEDJXtVUk00AA4yJgui');

INSERT INTO chat(
  chat_id,
  chat_name
) VALUES 
  ('2fcb7618c37e474fbf998d0fe773806c', 'General'),
  ('61f5596105aa41b1a4edaaae61d4f8bd', 'Feedback'),
  ('ac729983fcef41bfa69a3233ee1dcedd', 'EDMW'); 

INSERT INTO subscription(
  subscription_id,
  user_email,
  chat_id
) VALUES
  ('bebfe242794e47a5bd1a75d95362d85c', 'test1@test.com', '2fcb7618c37e474fbf998d0fe773806c'),
  ('f4becdec769e4ba393a3b1058fdd4f1c', 'test2@test.com', '2fcb7618c37e474fbf998d0fe773806c'),
  ('b9aa4579e1d34a9c9cc5917934953b0e', 'test3@test.com', '2fcb7618c37e474fbf998d0fe773806c'),
  ('b9574ffca72b421088ec2d8d22e814f2', 'test4@test.com', '2fcb7618c37e474fbf998d0fe773806c'),
  ('24a5a3eab9744127ba5672d89377303a', 'test1@test.com', '61f5596105aa41b1a4edaaae61d4f8bd'),
  ('b6869adeea164fd599bc49b56eecb2e0', 'test2@test.com', '61f5596105aa41b1a4edaaae61d4f8bd'),
  ('b0127c6f6efb4e49bef3fedeec7c546e', 'test3@test.com', '61f5596105aa41b1a4edaaae61d4f8bd'),
  ('f1cee56926fd460092a5a071ca0ec4c2', 'test4@test.com', '61f5596105aa41b1a4edaaae61d4f8bd'),
  ('ebe8d1882d4e488b8f35a522906ea0d1', 'test1@test.com', 'ac729983fcef41bfa69a3233ee1dcedd'),
  ('d66ff855d3774d4a8308430a20bf8282', 'test2@test.com', 'ac729983fcef41bfa69a3233ee1dcedd'),
  ('c9376164aac644e48adabe9bc7afea35', 'test3@test.com', 'ac729983fcef41bfa69a3233ee1dcedd'),
  ('f38de7853d3e42ddbbde8c162c43969c', 'test4@test.com', 'ac729983fcef41bfa69a3233ee1dcedd');
