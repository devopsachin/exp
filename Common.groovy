import java.util.regex.Matcher;		
import java.util.regex.Pattern;		
import groovy.json.JsonSlurper;
import groovy.json.JsonSlurperClassic
import groovy.json.*

import java.util.Map;
import java.io.Reader;
import java.util.HashMap;


def createfile (def filename, def path){
	sh """cd $path """
	sh """ touch $filename """
}


def addrepo(def filename, def path){
	sh """ echo "deb https://artifacts.elastic.co/packages/7.x/apt stable main" | sudo tee $path/$filename"""
}
def upgrade (){
 	sh """sudo apt-get update"""
}

def docker(){
	sh """ sudo apt-get install docker.io -y"""
}

def docker_enable(){
	sh """sudo systemctl enable docker"""

}
def docker_start(){
	sh """ sudo systemctl start docker"""
}

def addkey(){
	sh """ curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add """
}

def curl_wget(){
	sh """ sudo apt-get install curl -y """
}
def addrepo(){
	sh """sudo apt-add-repository "deb http://apt.kubernetes.io/ kubernetes-xenial main" """
}

def kubctl(){
	sh """ sudo apt-get install kubeadm kubelet kubectl -y"""
	sh """ sudo apt-mark hold kubeadm kubelet kubectl"""
}

def hostname(def hostname){
	sh """ sudo hostnamectl set-hostname ${hostname}"""
}

def addnode(){
	sh """ sudo kubeadm init --pod-network-cidr=10.244.0.0/16"""
}

def kubfinal(){
	sh 'sudo mkdir -p /root/.kube'
	sh 'sudo cp -i /etc/kubernetes/admin.conf /root/.kube/config '
	sh 'sudo chown 0:0 /root/.kube/config'
}

def pod(){
	sh """sudo kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml """
}
def getpod(){
	sh """kubectl get pods --all-namespaces"""
	sh """kubectl taint nodes --all node-role.kubernetes.io/${hostname}"""
}

def loginaws(){
	sh """ aws sts get-caller-identity """
}
def createInstance(def Instanceid){
	sh """ aws ec2 run-instances --image-id ami-0a4a70bd98c6d6441 --count 1 --instance-type t2.medium --key-name atos --security-group-ids sg-cf6543ab --subnet-id subnet-4a020d22  > /tmp/instance.id """
	sh """ Instanceid=$(cat /tmp/any.txt | awk '{print $9}')"""
	sh """ aws ec2 create-tags --resources $Instanceid --tags Key=Name,Value=Petclinic """

}


return this 
