#!/bin/bash
sudo su 
sudo apt-get update && sudo apt-get upgrade -y
sudo apt-get install docker.io -y
sudo systemctl enable docker
sudo systemctl status docker
sudo systemctl start docker
curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add
sudo apt-add-repository "deb http://apt.kubernetes.io/ kubernetes-xenial main"
sudo apt-get install kubeadm kubelet kubectl -y
sudo apt-mark hold kubeadm kubelet kubectl

kubeadm version
sudo hostnamectl set-hostname master-node
kubeadm init --pod-network-cidr=10.244.0.0/16
mkdir -p $HOME/.kube
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config
sudo kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml
kubectl get pods --all-namespaces
sleep 30 

kubectl get pods --all-namespaces
#kubectl taint nodes --all node-role.kubernetes.io/master-node

kubectl get pods --all-namespaces
sudo touch Dockerfile
echo "
FROM ubuntu

ARG DEBIAN_FRONTEND=noninteractive
# Add Maintainer Info
MAINTAINER sachin

# Creating Code repo
RUN mkdir /home/app

# Working Directory
WORKDIR /home/app

# Copying files from repo to Working Directory
RUN apt-get update
RUN apt-get install wget -y
RUN apt-get install git -y
RUN apt install default-jre -y
RUN apt install openjdk-8-jre-headless -y
RUN apt install maven -y
RUN git clone https://github.com/akeotech/devops-kubernetes.git
WORKDIR devops-kubernetes/
WORKDIR java-springboot


# Creating JAR package
RUN mvn clean package

# Add a volume
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080
#RUN wget https://repo1.maven.org/maven2/co/elastic/apm/elastic-apm-agent/1.18.0.RC1/elastic-apm-agent-1.18.0.RC1.jar
# The application's jar file
ENTRYPOINT [\"java\",\"-Djava.security.egd=file:/dev/./urandom\",\"-jar\",\"target/sample-websocket-demo-0.0.1.jar\"]
">Dockerfile

docker build -t sachin:latest .

sudo touch k8.yml

echo "
apiVersion: apps/v1
kind: Deployment
metadata:
 name: sample-springboot-app
 labels:
   app: sample-springboot-app
spec:
 selector:
   matchLabels:
     app: sample-springboot-app
 template:
   metadata:
      labels:
        app: sample-springboot-app
   spec:
     nodeName: master-node
     containers:
     - image: sachin:latest
       name: springboot-chat
       imagePullPolicy: IfNotPresent
       ports:
       - containerPort: 8080
---
apiVersion: v1
kind: Service
metadata:
 name: sample-springboot-app-svc
spec:
    ports:
    - name: "sample-springboot-app"
      targetPort: 8080
      port: 8080
      nodePort: 30205
      protocol: TCP
    selector:
      app: sample-springboot-app
    type: NodePort
" > k8.yml

kubectl apply -f k8.yml
sleep 25 
kubectl get pods



