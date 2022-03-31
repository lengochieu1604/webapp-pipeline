# **LAB DOCKER, K8s, JENKINS**

## CHAPTER 1 	OBJECTIVES
    In this assignment, we will create and configure an environment with an automated CI/CD pipeline to build and deploy simple application (Angular, Postgre, NodeJS).
    It will containerize applications using Jenkins, Docker, and use Helm to deploy on Kubernetes. 
    Every time the content of the app has changed and push to SCM (source code manager), go to Jenkins, trigger the pipeline to deploy the new version of the App. 
    You also need to apply the New Relic for monitoring the infrastructure at the basic level.

## CHAPTER 2 	INTRODUCTION 
### 2.1	Get Started
    This assignment includes 3 main components: Frontend (Angular), Backend(Postre & NodeJS) and CI/CD pipeline.  
	    o Build & Push Image job creates FE and BE image, docker image to Docker Registry (Docker Hub, Azure Container Registry).
	    o Deploy K8s Cluster job pull image from Docker Registry and automate deploy cluster in K8s .
	    o CI/CD Pipeline job create and automate run pipeline to deploy application in VM.
	    
### Architecture of assignment:
![image](https://user-images.githubusercontent.com/98753976/161120969-7ac5cdb5-041a-4cb1-97dd-1f0d48bd36ee.png)

 
### 2.2	Prerequisites
### 2.2.1	Software version information
![image](https://user-images.githubusercontent.com/98753976/161121128-92577104-84f5-428e-99eb-068eee6c604a.png)


### 2.2.2	Install Docker + Kubernetes + Jenkins 
    First of all, we will install Docker, K8s, Jenkins using documentation below:
    1.  https://docs.docker.com/engine/install/ubuntu/
    2.	https://kubernetes.io/docs/tasks/tools/install-kubectl-linux/
    3.	https://minikube.sigs.k8s.io/docs/start/
    4.	https://www.jenkins.io/doc/book/installing/docker/
	
### 2.2.3	Setup Environments
### 2.2.3.1	Docker login to ACR
    Run the following commands to log into Azure:
> docker login assignmentw3.azurecr.io 

### 2.2.3.2	Jenkins Credential
    ACR Credential
	    The following steps show how to specify your Azure credential:
	    1. Make sure the Credentials plug-in is installed.
	    2. Within the Jenkins dashboard, select Credentials -> System ->.
	    3. Select Global credentials(unrestricted).
	    4. Select Add Credentials to add a Microsoft Azure service principal. 
	    Make sure that the credential kind is Username with password and enter the following items:
	      •	Username: Service principal 
	      •	Password: Service principal 
	      •	ID: Credential identifier (such as AzureServicePrincipal)


 
	Gitlab Credential
	Similar to the method of ACR Credential

### 2.2.3.3 Jenkins Plugin
	Jenkins provides two methods for installing plugins on the controller:
		o Using the "Plugin Manager" in the web UI.
		o Using the Jenkins CLI install-plugin command.
	The simplest and most common way of installing plugins is through the Manage Jenkins > Manage Plugins view, available to administrators of a Jenkins environment.
	Under the Available tab, plugins available for download from the configured Update Center can be searched and considered:
	Docker Pipeline & Docker Plugin

	Gitlab
 
### 2.2.3.4 Gitlab Integration
	Grant Jenkins access to the GitLab API
		Create a personal access token to authorize Jenkins to access GitLab.
		1. Sign in to GitLab as the user to be used with Jenkins.
		2. On the top bar, in the top right corner, select your avatar.
		3. Select Edit profile.
		4. On the left sidebar, select Access Tokens.
		5. Create a personal access token and select the API scope.
		6. Copy the personal access token. You need it to configure the Jenkins server.
	
	Configure the Jenkins server
	Install and configure the Jenkins plugin. The plugin must be installed and configured to authorize the connection to GitLab.
		1. On the Jenkins server, select Manage Jenkins > Manage Plugins.
		2. Install the Jenkins GitLab Plugin.
		3. Select Manage Jenkins > Configure System.
		4. In the GitLab section, select Enable authentication for ‘/project’ end-point.
		5. Select Add, then choose Jenkins Credential Provider.
		6. Select GitLab API token as the token type.
		7. Enter the GitLab personal access token’s value in API Token and select Add.
		8. Enter the GitLab server’s URL in GitLab host URL.
		9. To test the connection, select Test Connection.


### 2.2.3.5	Create an image pull secret in K8s
	kubectl create secret docker-registry <secret-name> \
	    --namespace <namespace> \
	    --docker-server=<container-registry-name>.azurecr.io \
	    --docker-username=<service-principal-ID> \
	    --docker-password=<service-principal-password>
	Add secret to yaml file:

# CHAPTER 3 	HOW TO DEPLOY
## 3.1	Build FE & BE Docker Image
### 3.1.1 FE Docker Image
	 Run the following commands to build container from FE source code:
>$ docker build [OPTIONS] PATH | URL | 

### 3.1.2 BE Docker Image
	Run the following commands to build container from FE source code:
>$ docker build [OPTIONS] PATH | URL | 

### 3.1.3 Tag name
	To push an image to a private registry and not the central Docker registry you must tag it with the registry hostname and port (if needed).
>$ docker tag 0e5574283393 myregistryhost:5000/fedora/httpd:version1.0
	Tag an image for a private repository
>docker tag assignmentw3.azurecr.io/lengochieu/angularhieulengochieu1604/angularhieu:v1
>docker tag assignmentw3.azurecr.io/lengochieu/nodejshieu lengochieu1604/nodejshieu:v2

### 3.1.4 Push
### 3.1.4.1 FE Docker Image
	Run the following commands to build container from FE source code:
>docker push assignmentw3.azurecr.io/lengochieu/angularhieu

### 3.1.4.2 BE Docker Image
	Similar to the method of item 3.1.4.1
### 3.2	Build & Deploy K8s Cluster
### 3.2.1 Configure Yaml file
### 3.2.1.1 secret.yaml
	A Secret is an object that contains a small amount of sensitive data such as a password, a token, or a key. Such information might otherwise be put in a Pod specification or in a container image. Using a Secret means that you don't need to include confidential data in your application code.
 
### 3.2.1.2 backend.yaml
	Deployment
	A Deployment provides declarative updates for Pods and ReplicaSets.
 
	Service
	
### 3.2.1.3 frontend.yaml
	Deployment 

	Service

### 3.2.2 Apply yaml file
	Run the following commands to apply yaml file:

### 3.2.3 Get K8s Cluster
	Run the following commands to describe the pod:
	![image](https://user-images.githubusercontent.com/98753976/161122760-08a028f7-da48-4639-a4fb-c0319965c233.png)

	Run the following command to get all the components in the K8s cluster
	![image](https://user-images.githubusercontent.com/98753976/161122770-397eeeba-6ed7-4d7e-bf13-d33d6cf33820.png)

	Authorize BE
	![image](https://user-images.githubusercontent.com/98753976/161122778-7dde39da-0886-449b-9aee-f827726492cd.png)

	Get FE & BE url
	![image](https://user-images.githubusercontent.com/98753976/161122797-f690dd99-318b-4675-ae3e-a6515881ede2.png)
	![image](https://user-images.githubusercontent.com/98753976/161122816-57b7ea34-cabf-4d55-a329-719d4d3ec777.png)


 
## 3.2.4 Output
![image](https://user-images.githubusercontent.com/98753976/161122872-b5121284-d886-474d-a35e-8464b149f45e.png)

 
	Access BE & FE using Web-brower
	![image](https://user-images.githubusercontent.com/98753976/161122913-7bb83bfc-9687-4d54-8cbf-affbdf7a8714.png)
	![image](https://user-images.githubusercontent.com/98753976/161122968-2f9dd04f-9e66-4613-844c-5b80a1eb7ddc.png)


	Validate DB in pod
>kubectl exec -it nodejs-backend-65b9b4565c-rlmn6 -c pg bin/bash
>psql -U admin
>select * from users;
	
## 3.3	CI/CD
### 3.3.1 FE Jenkinsfile
	Enviroment
	Stages
![image](https://user-images.githubusercontent.com/98753976/161123147-ede828fc-6ba4-4414-a059-ff984a0c5f37.png)

	Post

### 3.3.2 BE Jenkinsfile
	BE Jenkinsfile is similar to the FE Jenkinsfile
### 3.3.3 Create Node to running stage Deploy to K8s
	Environment
	To run this guide you will need a machine with:
		o Java installation
		o Jenkins installation
		o Docker installation
		o SSH key pair
	Generating an SSH key pair
		o In a terminal window run the command: ssh-keygen -f ~/.ssh/jenkins_agent_key
		o Provide a passphrase to use with the key
		o Create a Jenkins SSH credential
		o Creating agent
	Result
![image](https://user-images.githubusercontent.com/98753976/161123325-41f5af0e-6084-4710-a992-5f1a5387caf6.png)


### 3.3.4 Create Pipeline
![image](https://user-images.githubusercontent.com/98753976/161123348-fe96c7df-f6ac-47cf-bb46-d3190281c5db.png)

### 3.4	Result 
	>FE CI/CD
	![image](https://user-images.githubusercontent.com/98753976/161123393-c055cf4b-4bd5-44e0-94f8-d777d52dbc5c.png)

	>BE CI/CD
![image](https://user-images.githubusercontent.com/98753976/161123406-06836037-38fc-40c2-a15e-a3ed33f5dc13.png)

# CHAPTER 4 	TROUBLESHOOTING
## 4.1	Resolve NPM issue whilel running node JS application
### 4.1.1 Proxy issue 
	Run below commands in npm command prompt or Visual Studio Code terminal.
		o npm config rm proxy
		o npm config rm https-proxy
	
### 4.1.2 npm error
>npm audit fix
npm audit
minikube start --driver=none

### 4.2	Can't start minikube
	You can use minikube delete to delete the old cluster. After that start minikube using minikube start or run below command to fix it:
		o minikube start --driver=none
		o sudo sysctl fs.protected_regular=0
		o sudo apt install conntrack
		o sudo minikube start --vm-driver=
		
### 4.3 K8s Troubleshoot
	Run below commands to troubleshoot in K8s:
		o kubectl describe pod angular-frontend-74cff454dc-494rb
		o kubectl delete all --all
		o kubectl delete -f
		o kubectl delete service nodejs-backend
		o kubectl delete deployment nodejs-backend
		o kubectl set image deployment/angular-frontend angular-frontend=lengochieu1604/angularhieu:v2
		o kubectl set image deployment/nodejs-backend nodejs-backend=lengochieu1604/nodejshieu:v3
		
  	Call API
	if you don't have the DB in the container you can add it manually like this:
	kubectl exec -it nodejs-backend-75b67d488d-5vzwv -c postgres bin/bash
	psql -U admin
		o CREATE TABLE users (
		user_id VARCHAR ( 50 ) PRIMARY KEY,
		user_name VARCHAR ( 50 ) NOT NULL,
		password VARCHAR ( 50 ) NOT NULL,
		email VARCHAR ( 255 ) NOT NULL
		);

		o INSERT INTO users(user_id, user_name, password, email) VALUES ('U001', 'User 001', 'U1234', 'user01@gmail.com');
		o INSERT INTO users(user_id, user_name, password, email) VALUES ('U002', 'User 002', 'U1234', 'user02@gmail.com');
		o INSERT INTO users(user_id, user_name, password, email) VALUES ('U003', 'User 003', 'U1234', 'user03@gmail.com');
		o INSERT INTO users(user_id, user_name, password, email) VALUES ('U004', 'User 004', 'U1234', 'user04@gmail.com');
		o INSERT INTO users(user_id, user_name, password, email) VALUES ('U005', 'User 005', 'U1234', 'user05@gmail.com');


