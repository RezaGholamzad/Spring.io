Steps for install RabbitMQ server

Step 1. Import Erlang GPG key:
wget -O- https://packages.erlang-solutions.com/ubuntu/erlang_solutions.asc | sudo apt-key add -

Step 2. Add the Erlang Repository:
echo "deb https://packages.erlang-solutions.com/ubuntu bionic contrib" | sudo tee /etc/apt/sources.list.d/rabbitmq.list

Step 3. Install Erlang:
sudo apt update
sudo apt -y install erlang
erl -v

Step 4. Import RabbitMQ GPG key:
wget -O - "https://github.com/rabbitmq/signing-keys/releases/download/2.0/rabbitmq-release-signing-key.asc" | sudo apt-key add -

Step 5. Add RabbitMQ repository:
Create Rabbitmq repository file:
sudo nano /etc/apt/sources.list.d/bintray.rabbitmq.list
With below content:
deb https://dl.bintray.com/rabbitmq-erlang/debian bionic erlang
deb https://dl.bintray.com/rabbitmq/debian bionic main
Save and close the file.

Step 6. Update the system package list:
sudo apt-get update

Step 7. Install RabbitMQ server:
sudo apt-get install rabbitmq-server

Step 8. Manage RabbitMQ service:
Check status of service:
sudo systemctl status rabbitmq-server.service
Stop the service:
sudo systemctl stop rabbitmq-server.service
Start the service:
sudo systemctl start rabbitmq-server.service
Restart the service:
sudo systemctl restart rabbitmq-server.service
