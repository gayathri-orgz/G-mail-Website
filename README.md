# 🚀 Deploying a Static Website on AWS using EC2, ECR & ALB

## 📌 Project Overview
This project demonstrates how to **deploy a containerized static website** on **AWS**.  
The workflow includes:

- Building a **Docker image** of the website.  
- Pushing the image to **Amazon Elastic Container Registry (ECR)**.  
- Running the container on an **EC2 instance**.  
- Exposing the application through an **Application Load Balancer (ALB)**.  
- Configuring **security groups** to allow external traffic securely.  

This project highlights my skills in **AWS Cloud, Docker, Networking, and Load Balancing**.

---

## 🏗️ Architecture

```
         +-------------+
         |   Client    |
         +-------------+
                 |
                 v
        +-------------------+
        |  Application LB   |  (HTTP:80)
        +-------------------+
                 |
                 v
    +---------------------------+
    |   EC2 Instance(s)         |
    |  Running Docker Container |
    |  (Website on port 8080)   |
    +---------------------------+
                 |
                 v
     +--------------------------+
     | Amazon ECR (Container)   |
     +--------------------------+
```

---

## ⚙️ Steps to Reproduce

### 1️⃣ Build & Push Docker Image to ECR
```bash
# Build the Docker image
docker build -t gmail-website .

# Authenticate Docker with ECR
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <account_id>.dkr.ecr.us-east-1.amazonaws.com

# Tag the image
docker tag gmail-website:latest <account_id>.dkr.ecr.us-east-1.amazonaws.com/gmail-website:latest

# Push the image to ECR
docker push <account_id>.dkr.ecr.us-east-1.amazonaws.com/gmail-website:latest
````

### 2️⃣ Launch EC2 Instance & Install Docker

```bash
# Update system & install Docker
sudo yum update -y
sudo amazon-linux-extras enable docker
sudo yum install -y docker

# Start & enable Docker service
sudo systemctl start docker
sudo systemctl enable docker
```

### 3️⃣ Run Website Container on EC2

```bash
# Pull image from ECR
docker pull <account_id>.dkr.ecr.us-east-1.amazonaws.com/gmail-website:latest

# Run container (map EC2 port 8080 → container port 80)
docker run -d -p 8080:80 <account_id>.dkr.ecr.us-east-1.amazonaws.com/gmail-website:latest
```

### 4️⃣ Configure Application Load Balancer (ALB)

* Create an **Application Load Balancer** (Internet-facing).
* Create a **Target Group** that points to port **8080** on EC2 instances.
* Add a **Listener** on port **80 (HTTP)** → Forward to target group.
* Register your EC2 instance in the target group.

### 5️⃣ Configure Security Groups

* **ALB Security Group**: Allow **HTTP (80)** from `0.0.0.0/0`.
* **EC2 Security Group**: Allow **TCP (8080)** *only from ALB security group*.


### 6️⃣ Test the Setup

* Get ALB DNS name from AWS Console:

  ```
  http://<ALB-DNS-Name>
  ```
* Open it in a browser → You should see the static website! 🎉

## ✅ Results

* Website hosted successfully in a **Docker container on EC2**.
* Traffic distributed via **Application Load Balancer**.
* Used **ECR as a private container registry**.
* Ensured **secure communication** using SG rules.

## 🛠️ Tech Stack

* **AWS EC2** → Compute service to host Docker containers.
* **Amazon ECR** → Store & manage Docker images.
* **Docker** → Containerize static website.
* **AWS ALB** → Distribute incoming traffic across instances.
* **Security Groups** → Control inbound/outbound traffic.

## 📚 Learnings

* Hands-on with **containerization & cloud deployment**.
* Practical understanding of **load balancing, target groups, and SGs**.
* Experience in **pushing/pulling Docker images to ECR**.
* Deployed a **scalable & secure static website** on AWS.
