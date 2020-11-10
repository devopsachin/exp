import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import groovy.json.JsonBuilder


node {
	def rootdir="/home/ubuntu"
	def hostname="${hostname}"

	stage ('Loading pipelines scripts '){
		checkout scm 
		common=load "${rootdir}/exp/Common.groovy"
}

	stage ('update the system'){
		common.upgrade()
}
	stage ('Install Docker'){
		common.docker()
}
	stage ('enable docker'){
		common.docker_enable()
}
	stage ('start docker'){
		common.docker_start()
}

	stage ('installing curl and wget'){
		common.curl_wget()
}
	stage ('preparing prerequsites for kub adding key'){
                common.addkey()
}

	stage ('add kub repo'){
		common.addrepo()
}
	stage ('installing kub tools and keeping in hold'){
		common.kubctl()
}
	stage ('setting up hostname for kub master'){
		common.hostname(hostname)
}
	stage ('opening n/w for other nodes'){
		common.addnode()
}
	stage ('finalizing kubernetes'){
		common.kubfinal()
}

	stage ('deploying pod n/w'){
		common.pod()
}
}

